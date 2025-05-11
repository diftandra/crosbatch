package id.co.bca.camsbatch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.dto.CrosMasterKomitmenInput;
import id.co.bca.camsbatch.model.CrosMasterKomitmen;
import id.co.bca.camsbatch.processor.CrosMasterKomitmenProcessor;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class CrosMasterKomitmenConfig {
    
    private final Logger logger = LoggerFactory.getLogger("BatchErrorLogger");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${file.input}/Cros Komitmen.txt")
    private Resource crosMasterKomitmenResource;

    public CrosMasterKomitmenConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
        EntityManagerFactory entityManagerFactory) {
            this.jobRepository = jobRepository;
            this.transactionManager = transactionManager;
            this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public FlatFileItemReader<CrosMasterKomitmenInput> crosMasterKomitmenReader() {
        return new FlatFileItemReaderBuilder<CrosMasterKomitmenInput>()
            .name("crosMasterKomitmenReader")
            .resource(crosMasterKomitmenResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names("tglData", "custLnCurrKey", "acctLnCurrKey", "commCurrKey", "productSubTypeKey", "facLnSegKey",
                "collectKey", "acctRegionKey", "acctKcuKey", "kodeKanwil", "kodeKcu", "noRekeningPinjaman",
                "noKomitmen", "tglBukaKomitmen", "tglTutupKomitmen", "kategoriKredit", "tipeProduk", "kodeProduk",
                "namaProduk", "mataUang", "plafondOcur",
                "balanceOcur", "balanceIdr", "bungaOcur", "bungaIdr", "dendaOcur", "dendaIdr", 
                "balanceOcurHb", "balanceIdrHb", "bungaOcurHb", "bungaIdrHb", "dendaOcurHb", "dendaIdrHb",
                "tglHb", "balanceOcurHt", "balanceIdrHt", "bungaOcurHt", "bungaIdrHt", "dendaOcurHt", "dendaIdrHt",
                "tglHt", "ppap", "posisiDataPpap", "ckpn", "posisiDataCkpn", "hariTunggakan",
                "isRestrukturisasi", "kodeRestrukturisasi", "flagHb")
            .targetType(CrosMasterKomitmenInput.class)
            .build();
    }

    @Bean
    public CrosMasterKomitmenProcessor crosMasterKomitmenProcessor() {
        return new CrosMasterKomitmenProcessor();
    }

    @Bean
    public JpaItemWriter<CrosMasterKomitmen> crosMasterKomitmenWriter() {
        return new JpaItemWriterBuilder<CrosMasterKomitmen>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build();
    }

    @Bean
    public Step crosMasterKomitmenStep() {
        StepBuilder stepBuilder = new StepBuilder("crosMasterKomitmenStep", jobRepository);
        return stepBuilder
                .<CrosMasterKomitmenInput, CrosMasterKomitmen>chunk(10, transactionManager)
                .reader(crosMasterKomitmenReader())
                .processor(crosMasterKomitmenProcessor())
                .writer(crosMasterKomitmenWriter())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skip(DataIntegrityViolationException.class)
                .skipLimit(50)
                .listener(new SkipListener<CrosMasterKomitmenInput, CrosMasterKomitmen>() {

                    @Override
                    public void onSkipInRead(Throwable t) {
                        if (t instanceof FlatFileParseException) {
                            FlatFileParseException exception = (FlatFileParseException) t;
                            logger.error("Skipping line {} due to incorrect token count: {}", 
                                    exception.getLineNumber(), exception.getInput());
                        }
                    }

                    @Override
                    public void onSkipInWrite(CrosMasterKomitmen item, Throwable t) {
                        logger.error("Error writing item: {}", item, t);
                    }

                    @Override
                    public void onSkipInProcess(CrosMasterKomitmenInput item, Throwable t) {
                        logger.error("Error processing item: {}", item, t);
                    }
                })
                .transactionAttribute(new DefaultTransactionAttribute() {{
                    setPropagationBehavior(Propagation.REQUIRED.value());
                    setIsolationLevel(Isolation.READ_COMMITTED.value());
                    setTimeout(180); // 3-minute timeout
                }})
                .build();
    }

}
