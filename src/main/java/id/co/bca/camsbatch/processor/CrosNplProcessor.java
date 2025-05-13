package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosNplInput;
import id.co.bca.camsbatch.domain.model.CrosNpl;

public class CrosNplProcessor implements ItemProcessor<CrosNplInput, CrosNpl>{

    @Override
    public CrosNpl process(CrosNplInput input) throws Exception {
        CrosNpl processedEntity = new CrosNpl();

        return processedEntity;
    }
    
}
