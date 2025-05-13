package id.co.bca.camsbatch.config;


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
    private final CrosMasterDebiturConfig crosMasterDebiturConfig;
    private final CrosJaminanDebiturConfig crosJaminanDebiturConfig;
    private final CrosMasterKomitmenConfig crosMasterKomitmenConfig;
    private final CrosMasterPinjamanConfig crosMasterPinjamanConfig;
    private final CrosNplConfig crosNplConfig;
    private final CrosPemegangSahamConfig crosPemegangSahamConfig;
    private final CrosRelasiJaminanConfig crosRelasiJaminanConfig;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       CrosMasterDebiturConfig crosMasterDebiturConfig, CrosJaminanDebiturConfig crosJaminanDebiturConfig,
                       CrosMasterKomitmenConfig crosMasterKomitmenConfig, CrosMasterPinjamanConfig crosMasterPinjamanConfig,
                       CrosNplConfig crosNplConfig, CrosPemegangSahamConfig crosPemegangSahamConfig,
                       CrosRelasiJaminanConfig crosRelasiJaminanConfig) {
        this.jobRepository = jobRepository;
        this.crosMasterDebiturConfig = crosMasterDebiturConfig;
        this.crosJaminanDebiturConfig = crosJaminanDebiturConfig;
        this.crosMasterKomitmenConfig = crosMasterKomitmenConfig;
        this.crosMasterPinjamanConfig = crosMasterPinjamanConfig;
        this.crosNplConfig = crosNplConfig;
        this.crosPemegangSahamConfig = crosPemegangSahamConfig;
        this.crosRelasiJaminanConfig = crosRelasiJaminanConfig;
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

    @Bean(name = "importJob")
    public Job importJob() {
        JobBuilder jobBuilder = new JobBuilder("importJob", jobRepository);

        Flow flow = new FlowBuilder<Flow>("importJobFlow")
            .start(crosMasterDebiturConfig.crosMasterDebiturStep())
            .next(crosJaminanDebiturConfig.crosJaminanDebiturStep())
            .next(crosMasterKomitmenConfig.crosMasterKomitmenStep())
            .next(crosMasterPinjamanConfig.crosMasterPinjamanStep())
            .next(crosNplConfig.crosNplStep())
            .next(crosPemegangSahamConfig.crosPemegangSahamStep())
            .next(crosRelasiJaminanConfig.crosRelasiJaminanStep())
            .end();

        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .listener(performanceMonitoringListener())
                .start(flow)
                .end()
                .build();
    }

}
