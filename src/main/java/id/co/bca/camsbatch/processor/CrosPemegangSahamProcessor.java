package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosPemegangSahamInput;
import id.co.bca.camsbatch.domain.model.CrosPemegangSaham;

public class CrosPemegangSahamProcessor implements ItemProcessor<CrosPemegangSahamInput, CrosPemegangSaham>{

    @Override
    public CrosPemegangSaham process(CrosPemegangSahamInput input) throws Exception {
        CrosPemegangSaham processedEntity = new CrosPemegangSaham();

        return processedEntity;
    }
    
}
