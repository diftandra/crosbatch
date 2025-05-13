package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosMasterDebiturInput;
import id.co.bca.camsbatch.domain.model.CrosMasterDebitur;
import id.co.bca.camsbatch.processor.CrosMasterDebiturProcessor;
@Configuration
public class CrosMasterDebiturConfig {

    @Value("${file.input}/Cros Debitur.txt")
    private Resource crosMasterDebiturResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosMasterDebiturConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    /**
     * Creates and configures the reader for Cros Master Debitur data
     */
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

    /**
     * Creates and configures the processor for Cros Master Debitur data
     */
    @Bean
    public CrosMasterDebiturProcessor crosMasterDebiturProcessor() {
        return new CrosMasterDebiturProcessor();
    }

    /**
     * Creates the step for Cros Master Debitur processing
     */
    @Bean
    public Step crosMasterDebiturStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosMasterDebiturInput, CrosMasterDebitur>configureStepBuilder(
                "crosMasterDebiturStep",
                crosMasterDebiturReader(),
                crosMasterDebiturProcessor(),
                batchCommonConfig.<CrosMasterDebitur>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }
    
}
