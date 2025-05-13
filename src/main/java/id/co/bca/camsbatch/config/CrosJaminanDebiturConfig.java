package id.co.bca.camsbatch.config;

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

import id.co.bca.camsbatch.domain.dto.CrosJaminanDebiturInput;
import id.co.bca.camsbatch.domain.model.CrosJaminanDebitur;
import id.co.bca.camsbatch.processor.CrosJaminanDebiturProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CrosJaminanDebiturConfig {

    private final Logger logger = LoggerFactory.getLogger("BatchErrorLogger");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${file.input}/Cros Jaminan.txt")
    private Resource crosJaminanDebiturResource;

    public CrosJaminanDebiturConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
        EntityManagerFactory entityManagerFactory) {
            this.jobRepository = jobRepository;
            this.transactionManager = transactionManager;
            this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public FlatFileItemReader<CrosJaminanDebiturInput> crosJaminanDebiturReader() {
        return new FlatFileItemReaderBuilder<CrosJaminanDebiturInput>()
            .name("crosJaminanDebiturReader")
            .resource(crosJaminanDebiturResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names("tglData", "custLnCurrKey", "collCurrKey", "collCurrencyKey", "noCollateral", "tipeJaminan",
                "mataUang", "deskripsiJaminan", "nilaiPasarOcur", "nilaiPasarIdr", "nilaiTaksasiOcur",
                "nilaiTaksasiIdr", "nilaiHakTanggunganOcur", "nilaiHakTanggunganIdr", "nilaiLikuidasiOcur", "nilaiLikuidasiIdr",
                "solidNonSolid", "tglBap")
            .targetType(CrosJaminanDebiturInput.class)
            .build();
    }

    @Bean
    public CrosJaminanDebiturProcessor crosJaminanDebiturProcessor() {
        return new CrosJaminanDebiturProcessor();
    }

    @Bean
    public JpaItemWriter<CrosJaminanDebitur> crosJaminanDebiturWriter() {
        return new JpaItemWriterBuilder<CrosJaminanDebitur>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build();
    }

    @Bean
    public Step crosJaminanDebiturStep() {
        StepBuilder stepBuilder = new StepBuilder("crosJaminanDebiturStep", jobRepository);
        return stepBuilder
                .<CrosJaminanDebiturInput, CrosJaminanDebitur>chunk(10, transactionManager)
                .reader(crosJaminanDebiturReader())
                .processor(crosJaminanDebiturProcessor())
                .writer(crosJaminanDebiturWriter())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skip(DataIntegrityViolationException.class)
                .skipLimit(50)
                .listener(new SkipListener<CrosJaminanDebiturInput, CrosJaminanDebitur>() {

                    @Override
                    public void onSkipInRead(Throwable t) {
                        if (t instanceof FlatFileParseException) {
                            FlatFileParseException exception = (FlatFileParseException) t;
                            logger.error("Skipping line {} due to incorrect token count: {}", 
                                    exception.getLineNumber(), exception.getInput());
                        }
                    }

                    @Override
                    public void onSkipInWrite(CrosJaminanDebitur item, Throwable t) {
                        logger.error("Error writing item: {}", item, t);
                    }

                    @Override
                    public void onSkipInProcess(CrosJaminanDebiturInput item, Throwable t) {
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
