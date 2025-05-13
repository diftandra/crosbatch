package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosNplInput;
import id.co.bca.camsbatch.domain.model.CrosNpl;
import id.co.bca.camsbatch.processor.CrosNplProcessor;


@Configuration
public class CrosNplConfig {

    @Value("${file.input}/Cros NPL.txt")
    private Resource crosNplResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosNplConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    @Bean
    public FlatFileItemReader<CrosNplInput> crosNplReader() {
        return new FlatFileItemReaderBuilder<CrosNplInput>()
            .name("crosNplReader")
            .resource(crosNplResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names()
            .targetType(CrosNplInput.class)
            .build();
    }

    @Bean
    public CrosNplProcessor crosNplProcessor() {
        return new CrosNplProcessor();
    }

    @Bean
    public Step crosNplStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosNplInput, CrosNpl>configureStepBuilder(
                "crosNplStep",
                crosNplReader(),
                crosNplProcessor(),
                batchCommonConfig.<CrosNpl>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }
    
}
