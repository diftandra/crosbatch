package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosJaminanDebiturInput;
import id.co.bca.camsbatch.domain.model.CrosJaminanDebitur;
import id.co.bca.camsbatch.processor.CrosJaminanDebiturProcessor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CrosJaminanDebiturConfig {

    @Value("${file.input}/Cros Jaminan.txt")
    private Resource crosJaminanDebiturResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosJaminanDebiturConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
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
    public Step crosJaminanDebiturStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosJaminanDebiturInput, CrosJaminanDebitur>configureStepBuilder(
                "crosJaminanDebiturStep",
                crosJaminanDebiturReader(),
                crosJaminanDebiturProcessor(),
                batchCommonConfig.<CrosJaminanDebitur>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }
    
}
