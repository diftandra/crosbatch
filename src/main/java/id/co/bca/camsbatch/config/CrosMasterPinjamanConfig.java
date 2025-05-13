package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosMasterPinjamanInput;
import id.co.bca.camsbatch.domain.model.CrosMasterPinjaman;
import id.co.bca.camsbatch.processor.CrosMasterPinjamanProcessor;

@Configuration
public class CrosMasterPinjamanConfig {
    
    @Value("${file.input}/Cros Pinjaman.txt")
    private Resource crosMasterPinjamanResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosMasterPinjamanConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    @Bean
    public FlatFileItemReader<CrosMasterPinjamanInput> crosMasterPinjamanReader() {
        return new FlatFileItemReaderBuilder<CrosMasterPinjamanInput>()
            .name("crosMasterPinjamanReader")
            .resource(crosMasterPinjamanResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names()
            .targetType(CrosMasterPinjamanInput.class)
            .build();
    }

    @Bean
    public CrosMasterPinjamanProcessor crosMasterPinjamanProcessor() {
        return new CrosMasterPinjamanProcessor();
    }

    @Bean
    public Step crosMasterPinjamanStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosMasterPinjamanInput, CrosMasterPinjaman>configureStepBuilder(
                "crosMasterPinjamanStep",
                crosMasterPinjamanReader(),
                crosMasterPinjamanProcessor(),
                batchCommonConfig.<CrosMasterPinjaman>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }

}
