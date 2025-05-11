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

import id.co.bca.camsbatch.dto.CrosMasterDebiturInput;
import id.co.bca.camsbatch.model.CrosMasterDebitur;
import id.co.bca.camsbatch.processor.CrosMasterDebiturProcessor;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class CrosMasterDebiturConfig {

    private final Logger logger = LoggerFactory.getLogger("BatchErrorLogger");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${file.input}/Cros Debitur.txt")
    private Resource crosMasterDebiturResource;

    public CrosMasterDebiturConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
        EntityManagerFactory entityManagerFactory) {
            this.jobRepository = jobRepository;
            this.transactionManager = transactionManager;
            this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public FlatFileItemReader<CrosMasterDebiturInput> crosMasterDebiturReader() {
        return new FlatFileItemReaderBuilder<CrosMasterDebiturInput>()
            .name("crosMasterDebiturReader")
            .resource(crosMasterDebiturResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names("tglData", "custLnCurrKey", "collectKey", "acctRegionKey", "acctKcuKey",
                "cin", "namaDebitur", "tglMenjadiNasabah", "tglMenjadiDebitur", "noGrup",
                "namaGrup", "sektorEkonomi", "kategoriDebitur", "kodeKanwil", "kodeKcu",
                "kategoriKredit", "flagHb", "balanceIdr", "hariTunggakan")
            .targetType(CrosMasterDebiturInput.class)
            .build();
    }

    @Bean
    public CrosMasterDebiturProcessor crosMasterDebiturProcessor() {
        return new CrosMasterDebiturProcessor();
    }

    @Bean
    public JpaItemWriter<CrosMasterDebitur> crosMasterDebiturWriter() {
        return new JpaItemWriterBuilder<CrosMasterDebitur>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build();
    }

    @Bean
    public Step crosMasterDebiturStep() {
        StepBuilder stepBuilder = new StepBuilder("crosMasterDebiturStep", jobRepository);
        return stepBuilder
                .<CrosMasterDebiturInput, CrosMasterDebitur>chunk(10, transactionManager)
                .reader(crosMasterDebiturReader())
                .processor(crosMasterDebiturProcessor())
                .writer(crosMasterDebiturWriter())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skip(DataIntegrityViolationException.class)
                .skipLimit(50)
                .listener(new SkipListener<CrosMasterDebiturInput, CrosMasterDebitur>() {

                    @Override
                    public void onSkipInRead(Throwable t) {
                        if (t instanceof FlatFileParseException) {
                            FlatFileParseException exception = (FlatFileParseException) t;
                            logger.error("Skipping line {} due to incorrect token count: {}", 
                                    exception.getLineNumber(), exception.getInput());
                        }
                    }

                    @Override
                    public void onSkipInWrite(CrosMasterDebitur item, Throwable t) {
                        logger.error("Error writing item: {}", item, t);
                    }

                    @Override
                    public void onSkipInProcess(CrosMasterDebiturInput item, Throwable t) {
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
