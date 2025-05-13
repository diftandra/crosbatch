package id.co.bca.camsbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchCommonConfig {
    
    private final Logger logger = LoggerFactory.getLogger("BatchErrorLogger");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    
    public BatchCommonConfig(JobRepository jobRepository, 
                           PlatformTransactionManager transactionManager,
                           EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
    }
    
    /**
     * Configures a step with standard error handling and transaction settings.
     * 
     * @param <I> Input type
     * @param <O> Output type
     * @param stepName Name of the step
     * @param reader Item reader
     * @param processor Item processor
     * @param writer Item writer
     * @param chunkSize Chunk size
     * @return Configured step
     */
    public <I, O> SimpleStepBuilder<I, O> configureStepBuilder(
            String stepName,
            ItemReader<I> reader,
            ItemProcessor<I, O> processor,
            ItemWriter<O> writer,
            int chunkSize) {
        
        StepBuilder stepBuilder = new StepBuilder(stepName, jobRepository);
        
        return stepBuilder
                .<I, O>chunk(chunkSize, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skip(DataIntegrityViolationException.class)
                .skipLimit(50)
                .listener(createSkipListener());
    }
    
    /**
     * Creates a JPA item writer for the specified entity type.
     * 
     * @param <T> Entity type
     * @return Configured JpaItemWriter
     */
    public <T> JpaItemWriter<T> createJpaWriter() {
        return new JpaItemWriterBuilder<T>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
    
    /**
     * Creates standard transaction attributes for batch steps.
     * 
     * @return DefaultTransactionAttribute
     */
    public DefaultTransactionAttribute getStandardTransactionAttributes() {
        DefaultTransactionAttribute attr = new DefaultTransactionAttribute();
        attr.setPropagationBehavior(Propagation.REQUIRED.value());
        attr.setIsolationLevel(Isolation.READ_COMMITTED.value());
        attr.setTimeout(180); // 3-minute timeout
        return attr;
    }
    
    /**
     * Creates a standard skip listener for error handling.
     * 
     * @param <I> Input type
     * @param <O> Output type
     * @return SkipListener
     */
    public <I, O> SkipListener<I, O> createSkipListener() {
        return new SkipListener<I, O>() {
            @Override
            public void onSkipInRead(Throwable t) {
                if (t instanceof FlatFileParseException exception) {
                    logger.error("Skipping line {} due to incorrect token count: {}", 
                            exception.getLineNumber(), exception.getInput());
                }
            }

            @Override
            public void onSkipInWrite(O item, Throwable t) {
                logger.error("Error writing item: {}", item, t);
            }

            @Override
            public void onSkipInProcess(I item, Throwable t) {
                logger.error("Error processing item: {}", item, t);
            }
        };
    }

}
