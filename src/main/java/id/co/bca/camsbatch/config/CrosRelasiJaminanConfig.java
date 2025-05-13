package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosRelasiJaminanInput;
import id.co.bca.camsbatch.domain.model.CrosRelasiJaminan;
import id.co.bca.camsbatch.processor.CrosRelasiJaminanProcessor;


@Configuration
public class CrosRelasiJaminanConfig {
    
    @Value("${file.input}/Cros Relasi Jaminan.txt")
    private Resource crosRelasiJaminanResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosRelasiJaminanConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    @Bean
    public FlatFileItemReader<CrosRelasiJaminanInput> crosRelasiJaminanReader() {
        return new FlatFileItemReaderBuilder<CrosRelasiJaminanInput>()
            .name("crosRelasiJaminanReader")
            .resource(crosRelasiJaminanResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names()
            .targetType(CrosRelasiJaminanInput.class)
            .build();
    }

    @Bean
    public CrosRelasiJaminanProcessor crosRelasiJaminanProcessor() {
        return new CrosRelasiJaminanProcessor();
    }

    @Bean
    public Step crosRelasiJaminanStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosRelasiJaminanInput, CrosRelasiJaminan>configureStepBuilder(
                "crosRelasiJaminanStep",
                crosRelasiJaminanReader(),
                crosRelasiJaminanProcessor(),
                batchCommonConfig.<CrosRelasiJaminan>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }

}
