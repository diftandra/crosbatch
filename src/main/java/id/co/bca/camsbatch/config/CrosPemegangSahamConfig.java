package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosPemegangSahamInput;
import id.co.bca.camsbatch.domain.model.CrosPemegangSaham;
import id.co.bca.camsbatch.processor.CrosPemegangSahamProcessor;


@Configuration
public class CrosPemegangSahamConfig {
    
    @Value("${file.input}/Cros Pemegang Saham.txt")
    private Resource crosPemegangSahamResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosPemegangSahamConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    @Bean
    public FlatFileItemReader<CrosPemegangSahamInput> crosPemegangSahamReader() {
        return new FlatFileItemReaderBuilder<CrosPemegangSahamInput>()
            .name("crosPemegangSahamReader")
            .resource(crosPemegangSahamResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names()
            .targetType(CrosPemegangSahamInput.class)
            .build();
    }

    @Bean
    public CrosPemegangSahamProcessor crosPemegangSahamProcessor() {
        return new CrosPemegangSahamProcessor();
    }

    @Bean
    public Step crosPemegangSahamStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosPemegangSahamInput, CrosPemegangSaham>configureStepBuilder(
                "crosPemegangSahamStep",
                crosPemegangSahamReader(),
                crosPemegangSahamProcessor(),
                batchCommonConfig.<CrosPemegangSaham>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }

}
