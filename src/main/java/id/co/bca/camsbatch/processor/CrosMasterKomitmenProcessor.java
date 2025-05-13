package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.domain.dto.CrosMasterKomitmenInput;
import id.co.bca.camsbatch.domain.model.CrosMasterKomitmen;
import id.co.bca.camsbatch.utility.DataParserUtils;

public class CrosMasterKomitmenProcessor implements ItemProcessor<CrosMasterKomitmenInput, CrosMasterKomitmen>{
    
    @Override
    public CrosMasterKomitmen process(CrosMasterKomitmenInput input) throws Exception {
        CrosMasterKomitmen processedEntity = new CrosMasterKomitmen();

        // Convert date fields
        processedEntity.setTglData(DataParserUtils.parseDate(input.getTglData()));
        processedEntity.setTglBukaKomitmen(DataParserUtils.parseDate(input.getTglBukaKomitmen()));
        processedEntity.setTglTutupKomitmen(DataParserUtils.parseDate(input.getTglTutupKomitmen()));
        processedEntity.setTglHb(DataParserUtils.parseDate(input.getTglHb()));
        processedEntity.setTglHt(DataParserUtils.parseDate(input.getTglHt()));
        processedEntity.setPosisiDataPpap(DataParserUtils.parseDate(input.getPosisiDataPpap()));
        processedEntity.setPosisiDataCkpn(DataParserUtils.parseDate(input.getPosisiDataCkpn()));

        // Convert integer fields with proper error handling
        processedEntity.setCustLnCurrKey(DataParserUtils.parseInteger(input.getCustLnCurrKey()));
        processedEntity.setAcctLnCurrKey(DataParserUtils.parseInteger(input.getAcctLnCurrKey()));
        processedEntity.setCommCurrKey(DataParserUtils.parseInteger(input.getCommCurrKey()));
        processedEntity.setProductSubTypeKey(DataParserUtils.parseInteger(input.getProductSubTypeKey()));
        processedEntity.setFacLnSegKey(DataParserUtils.parseInteger(input.getFacLnSegKey()));
        processedEntity.setCollectKey(DataParserUtils.parseInteger(input.getCollectKey()));
        processedEntity.setAcctRegionKey(DataParserUtils.parseInteger(input.getAcctRegionKey()));
        processedEntity.setAcctKcuKey(DataParserUtils.parseInteger(input.getAcctKcuKey()));
        processedEntity.setHariTunggakan(DataParserUtils.parseInteger(input.getHariTunggakan()));
        processedEntity.setIsRestrukturisasi(DataParserUtils.parseInteger(input.getIsRestrukturisasi()));
        processedEntity.setFlagHb(DataParserUtils.parseInteger(input.getFlagHb()));

        // Convert BigDecimal fields
        processedEntity.setPlafondOcur(DataParserUtils.parseBigDecimal(input.getPlafondOcur()));
        processedEntity.setPlafondIdr(DataParserUtils.parseBigDecimal(input.getPlafondIdr()));
        processedEntity.setBalanceOcur(DataParserUtils.parseBigDecimal(input.getBalanceOcur()));
        processedEntity.setBalanceIdr(DataParserUtils.parseBigDecimal(input.getBalanceIdr()));
        processedEntity.setBungaOcur(DataParserUtils.parseBigDecimal(input.getBungaOcur()));
        processedEntity.setBungaIdr(DataParserUtils.parseBigDecimal(input.getBungaIdr()));
        processedEntity.setDendaOcur(DataParserUtils.parseBigDecimal(input.getDendaOcur()));
        processedEntity.setDendaIdr(DataParserUtils.parseBigDecimal(input.getDendaIdr()));
        processedEntity.setBalanceOcurHb(DataParserUtils.parseBigDecimal(input.getBalanceOcurHb()));
        processedEntity.setBalanceIdrHb(DataParserUtils.parseBigDecimal(input.getBalanceIdrHb()));
        processedEntity.setBungaOcurHb(DataParserUtils.parseBigDecimal(input.getBungaOcurHb()));
        processedEntity.setBungaIdrHb(DataParserUtils.parseBigDecimal(input.getBungaIdrHb()));
        processedEntity.setDendaOcurHb(DataParserUtils.parseBigDecimal(input.getDendaOcurHb()));
        processedEntity.setDendaIdrHb(DataParserUtils.parseBigDecimal(input.getDendaIdrHb()));
        processedEntity.setBalanceOcurHt(DataParserUtils.parseBigDecimal(input.getBalanceOcurHt()));
        processedEntity.setBalanceIdrHt(DataParserUtils.parseBigDecimal(input.getBalanceIdrHt()));
        processedEntity.setBungaOcurHt(DataParserUtils.parseBigDecimal(input.getBungaOcurHt()));
        processedEntity.setBungaIdrHt(DataParserUtils.parseBigDecimal(input.getBungaIdrHt()));
        processedEntity.setDendaOcurHt(DataParserUtils.parseBigDecimal(input.getDendaOcurHt()));
        processedEntity.setDendaIdrHt(DataParserUtils.parseBigDecimal(input.getDendaIdrHt()));
        processedEntity.setPpap(DataParserUtils.parseBigDecimal(input.getPpap()));
        processedEntity.setCkpn(DataParserUtils.parseBigDecimal(input.getCkpn()));

        // Copy string fields directly
        processedEntity.setKodeKanwil(input.getKodeKanwil());
        processedEntity.setKodeKcu(input.getKodeKcu());
        processedEntity.setNoRekeningPinjaman(input.getNoRekeningPinjaman());
        processedEntity.setNoKomitmen(input.getNoKomitmen());
        processedEntity.setKategoriKredit(input.getKategoriKredit());
        processedEntity.setTipeProduk(input.getTipeProduk());
        processedEntity.setKodeProduk(input.getKodeProduk());
        processedEntity.setNamaProduk(input.getNamaProduk());
        processedEntity.setMataUang(input.getMataUang());
        processedEntity.setKodeRestrukturisasi(input.getKodeRestrukturisasi());
        
        // Set default values
        processedEntity.setIsActive(true);

        return processedEntity;
    }

}
