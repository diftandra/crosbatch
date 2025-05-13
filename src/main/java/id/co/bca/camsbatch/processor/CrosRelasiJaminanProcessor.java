package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosRelasiJaminanInput;
import id.co.bca.camsbatch.domain.model.CrosRelasiJaminan;

public class CrosRelasiJaminanProcessor implements ItemProcessor<CrosRelasiJaminanInput, CrosRelasiJaminan>{

    @Override
    public CrosRelasiJaminan process(CrosRelasiJaminanInput input) throws Exception {
        CrosRelasiJaminan processedEntity = new CrosRelasiJaminan();

        return processedEntity;
    }
    
}
