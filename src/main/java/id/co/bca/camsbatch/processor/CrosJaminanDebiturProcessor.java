package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosJaminanDebiturInput;
import id.co.bca.camsbatch.domain.model.CrosJaminanDebitur;
import id.co.bca.camsbatch.utility.DataParserUtils;

public class CrosJaminanDebiturProcessor implements ItemProcessor<CrosJaminanDebiturInput, CrosJaminanDebitur> {
    
    @Override
    public CrosJaminanDebitur process(CrosJaminanDebiturInput crosJaminanDebiturInput) throws Exception {

        CrosJaminanDebitur processedEntity = new CrosJaminanDebitur();

        // Convert date fields
        processedEntity.setTglData(DataParserUtils.parseDate(crosJaminanDebiturInput.getTglData()));
        processedEntity.setTglBap(DataParserUtils.parseDate(crosJaminanDebiturInput.getTglBap()));
        
        // Convert integer fields with proper error handling
        processedEntity.setCustLnCurrKey(DataParserUtils.parseInteger(crosJaminanDebiturInput.getCustLnCurrKey()));
        processedEntity.setCollCurrKey(DataParserUtils.parseInteger(crosJaminanDebiturInput.getCollCurrKey()));
        processedEntity.setCollCurrencyKey(DataParserUtils.parseInteger(crosJaminanDebiturInput.getCollCurrencyKey()));

        // Convert BigDecimal fields
        processedEntity.setNilaiPasarOcur(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiPasarOcur()));
        processedEntity.setNilaiPasarIdr(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiPasarIdr()));
        processedEntity.setNilaiTaksasiOcur(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiTaksasiOcur()));
        processedEntity.setNilaiTaksasiIdr(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiTaksasiIdr()));
        processedEntity.setNilaiHakTanggunganOcur(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiHakTanggunganOcur()));
        processedEntity.setNilaiHakTanggunganIdr(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiHakTanggunganIdr()));
        processedEntity.setNilaiLikuidasiOcur(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiLikuidasiOcur()));
        processedEntity.setNilaiLikuidasiIdr(DataParserUtils.parseBigDecimal(crosJaminanDebiturInput.getNilaiLikuidasiIdr()));

        // Copy string fields directly
        processedEntity.setNoCollateral(crosJaminanDebiturInput.getNoCollateral());
        processedEntity.setTipeJaminan(crosJaminanDebiturInput.getTipeJaminan());
        processedEntity.setMataUang(crosJaminanDebiturInput.getMataUang());
        processedEntity.setDeskripsiJaminan(crosJaminanDebiturInput.getDeskripsiJaminan()); 
        processedEntity.setSolidNonSolid(crosJaminanDebiturInput.getSolidNonSolid());

        // Set default values
        processedEntity.setIsActive(true);

        return processedEntity;
    }

}
