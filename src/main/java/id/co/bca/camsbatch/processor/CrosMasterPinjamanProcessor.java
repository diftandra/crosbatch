package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosMasterPinjamanInput;
import id.co.bca.camsbatch.domain.model.CrosMasterPinjaman;

public class CrosMasterPinjamanProcessor implements ItemProcessor<CrosMasterPinjamanInput, CrosMasterPinjaman> {
    
    @Override
    public CrosMasterPinjaman process(CrosMasterPinjamanInput input) throws Exception {
        CrosMasterPinjaman processedEntity = new CrosMasterPinjaman();

        return processedEntity;
    }

}
