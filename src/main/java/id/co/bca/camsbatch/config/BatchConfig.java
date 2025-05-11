package id.co.bca.camsbatch.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final Logger logger = LoggerFactory.getLogger("BatchErrorLogger");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CrosMasterDebiturConfig crosMasterDebiturConfig;
    private final CrosJaminanDebiturConfig crosJaminanDebiturConfig;
    private final CrosMasterKomitmenConfig crosMasterKomitmenConfig;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       CrosMasterDebiturConfig crosMasterDebiturConfig, CrosJaminanDebiturConfig crosJaminanDebiturConfig,
                       CrosMasterKomitmenConfig crosMasterKomitmenConfig) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.crosMasterDebiturConfig = crosMasterDebiturConfig;
        this.crosJaminanDebiturConfig = crosJaminanDebiturConfig;
        this.crosMasterKomitmenConfig = crosMasterKomitmenConfig;
    }

    @Bean
    public JobExecutionListener performanceMonitoringListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                logger.info("Starting job: {}", jobExecution.getJobInstance().getJobName());
                jobExecution.getExecutionContext().put("startTime", System.currentTimeMillis());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                long startTime = jobExecution.getExecutionContext().getLong("startTime");
                long endTime = System.currentTimeMillis();
                logger.info("Job {} completed with status: {}", 
                    jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
                logger.info("Job duration: {} ms", (endTime - startTime));

                // Summarize metrics from all steps
                int totalRead = 0;
                int totalWrite = 0;
                int totalSkip = 0;
                for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
                    totalRead += stepExecution.getReadCount();
                    totalWrite += stepExecution.getWriteCount();
                    totalSkip += stepExecution.getSkipCount();
                }

                logger.info("Total items: Read={}, Written={}, Skipped={}", 
                    totalRead, totalWrite, totalSkip);
            }
        };
    }

    @Bean
    public Job importJob() {
        JobBuilder jobBuilder = new JobBuilder("importJob", jobRepository);

        Flow flow = new FlowBuilder<Flow>("importJobFlow")
            .start(crosMasterDebiturConfig.crosMasterDebiturStep())
            .next(crosJaminanDebiturConfig.crosJaminanDebiturStep())
            .next(crosMasterKomitmenConfig.crosMasterKomitmenStep())
            .end();

        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .listener(performanceMonitoringListener())
                .start(flow)
                .end()
                .build();
    }
    

}
