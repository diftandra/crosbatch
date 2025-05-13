package id.co.bca.camsbatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import id.co.bca.camsbatch.domain.dto.CrosMasterKomitmenInput;
import id.co.bca.camsbatch.domain.model.CrosMasterKomitmen;
import id.co.bca.camsbatch.processor.CrosMasterKomitmenProcessor;

@Configuration
public class CrosMasterKomitmenConfig {

    @Value("${file.input}/Cros Komitmen.txt")
    private Resource crosMasterKomitmenResource;

    private final BatchCommonConfig batchCommonConfig;

    public CrosMasterKomitmenConfig(BatchCommonConfig batchCommonConfig) {
        this.batchCommonConfig = batchCommonConfig;
    }

    @Bean
    public FlatFileItemReader<CrosMasterKomitmenInput> crosMasterKomitmenReader() {
        return new FlatFileItemReaderBuilder<CrosMasterKomitmenInput>()
            .name("crosMasterKomitmenReader")
            .resource(crosMasterKomitmenResource)
            .linesToSkip(1)
            .delimited()
            .delimiter("|")
            .names("tglData", "custLnCurrKey", "acctLnCurrKey", "commCurrKey", "productSubTypeKey", "facLnSegKey",
                "collectKey", "acctRegionKey", "acctKcuKey", "kodeKanwil", "kodeKcu", "noRekeningPinjaman",
                "noKomitmen", "tglBukaKomitmen", "tglTutupKomitmen", "kategoriKredit", "tipeProduk", "kodeProduk",
                "namaProduk", "mataUang", "plafondOcur",
                "balanceOcur", "balanceIdr", "bungaOcur", "bungaIdr", "dendaOcur", "dendaIdr", 
                "balanceOcurHb", "balanceIdrHb", "bungaOcurHb", "bungaIdrHb", "dendaOcurHb", "dendaIdrHb",
                "tglHb", "balanceOcurHt", "balanceIdrHt", "bungaOcurHt", "bungaIdrHt", "dendaOcurHt", "dendaIdrHt",
                "tglHt", "ppap", "posisiDataPpap", "ckpn", "posisiDataCkpn", "hariTunggakan",
                "isRestrukturisasi", "kodeRestrukturisasi", "flagHb")
            .targetType(CrosMasterKomitmenInput.class)
            .build();
    }

    @Bean
    public CrosMasterKomitmenProcessor crosMasterKomitmenProcessor() {
        return new CrosMasterKomitmenProcessor();
    }

    @Bean
    public Step crosMasterKomitmenStep() {
        // Get standard transaction settings from common config
        DefaultTransactionAttribute txAttributes = batchCommonConfig.getStandardTransactionAttributes();
        
        // Create and configure the step with common patterns
        return batchCommonConfig.<CrosMasterKomitmenInput, CrosMasterKomitmen>configureStepBuilder(
                "crosMasterKomitmenStep",
                crosMasterKomitmenReader(),
                crosMasterKomitmenProcessor(),
                batchCommonConfig.<CrosMasterKomitmen>createJpaWriter(),
                10) // chunk size
            .transactionAttribute(txAttributes)
            .build();
    }

}
