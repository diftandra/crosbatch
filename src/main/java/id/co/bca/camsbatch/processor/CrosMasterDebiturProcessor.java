package id.co.bca.camsbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import id.co.bca.camsbatch.dto.CrosMasterDebiturInput;
import id.co.bca.camsbatch.model.CrosMasterDebitur;
import id.co.bca.camsbatch.shared.DataParserUtils;

public class CrosMasterDebiturProcessor implements ItemProcessor<CrosMasterDebiturInput, CrosMasterDebitur> {

    @Override
    public CrosMasterDebitur process(CrosMasterDebiturInput crosMasterDebiturInput) throws Exception {
        CrosMasterDebitur processedEntity = new CrosMasterDebitur();

        // Convert date fields
        processedEntity.setTglData(DataParserUtils.parseDate(crosMasterDebiturInput.getTglData()));
        processedEntity.setTglMenjadiNasabah(DataParserUtils.parseDate(crosMasterDebiturInput.getTglMenjadiNasabah()));
        processedEntity.setTglMenjadiDebitur(DataParserUtils.parseDate(crosMasterDebiturInput.getTglMenjadiDebitur()));

        // Convert integer fields with proper error handling
        processedEntity.setCustLnCurrKey(DataParserUtils.parseInteger(crosMasterDebiturInput.getCustLnCurrKey()));
        processedEntity.setCollectKey(DataParserUtils.parseInteger(crosMasterDebiturInput.getCollectKey()));
        processedEntity.setAcctRegionKey(DataParserUtils.parseInteger(crosMasterDebiturInput.getAcctRegionKey()));
        processedEntity.setAcctKcuKey(DataParserUtils.parseInteger(crosMasterDebiturInput.getAcctKcuKey()));
        processedEntity.setFlagHb(DataParserUtils.parseInteger(crosMasterDebiturInput.getFlagHb()));
        processedEntity.setHariTunggakan(DataParserUtils.parseInteger(crosMasterDebiturInput.getHariTunggakan()));

        // Convert BigDecimal fields
        processedEntity.setBalanceIdr(DataParserUtils.parseBigDecimal(crosMasterDebiturInput.getBalanceIdr()));

        // Copy string fields directly
        processedEntity.setCin(crosMasterDebiturInput.getCin());
        processedEntity.setNamaDebitur(crosMasterDebiturInput.getNamaDebitur());
        processedEntity.setNoGrup(crosMasterDebiturInput.getNoGrup());
        processedEntity.setNamaGrup(crosMasterDebiturInput.getNamaGrup());
        processedEntity.setSektorEkonomi(crosMasterDebiturInput.getSektorEkonomi());
        processedEntity.setKategoriDebitur(crosMasterDebiturInput.getKategoriDebitur());
        processedEntity.setKodeKanwil(crosMasterDebiturInput.getKodeKanwil());
        processedEntity.setKodeKcu(crosMasterDebiturInput.getKodeKcu());
        processedEntity.setKategoriKredit(crosMasterDebiturInput.getKategoriKredit());

        // Set default values
        processedEntity.setIsActive(true);

        return processedEntity;
    }

    //  /**
    //  * Parse a date object that might be a string from the flat file
    //  */
    // private Date parseDate(Object dateField) throws ParseException {
    //     if (dateField == null) {
    //         return null;
    //     }

    //     // If it's already a Date, return it
    //     if (dateField instanceof Date) {
    //         return (Date) dateField;
    //     }

    //     // If it's a String, parse it
    //     if (dateField instanceof String) {
    //         String dateStr = (String) dateField;
    //         if (dateStr.trim().isEmpty()) {
    //             return null;
    //         }

    //         try {
    //             return dateFormatter.parse(dateStr);
    //         } catch (ParseException e) {
    //             // Log the error and either return null or throw
    //             System.err.println("Error parsing date: " + dateStr);
    //             throw e;
    //         }
    //     }

    //     return null;
    // }

    //  /**
    //  * Parse an integer value that might be a string from the flat file
    //  */
    // private Integer parseInteger(Object intField) {
    //     if (intField == null) {
    //         return null;
    //     }

    //     // If it's already an Integer, return it
    //     if (intField instanceof Integer) {
    //         return (Integer) intField;
    //     }

    //     // If it's a String, parse it
    //     if (intField instanceof String) {
    //         String intStr = (String) intField;
    //         if (intStr.trim().isEmpty()) {
    //             return null;
    //         }

    //         try {
    //             return Integer.parseInt(intStr.trim());
    //         } catch (NumberFormatException e) {
    //             System.err.println("Error parsing integer: " + intStr + " - " + e.getMessage());
    //             return null; // Or handle the error as appropriate
    //         }
    //     }

    //     return null;
    // }

    // /**
    //  * Parse a BigDecimal value that might be a string from the flat file
    //  */
    // private BigDecimal parseBigDecimal(Object decimalField) {
    //     if (decimalField == null) {
    //         return null;
    //     }

    //     // If it's already a BigDecimal, return it
    //     if (decimalField instanceof BigDecimal) {
    //         return (BigDecimal) decimalField;
    //     }

    //     // If it's a String, parse it
    //     if (decimalField instanceof String) {
    //         String decimalStr = (String) decimalField;
    //         if (decimalStr.trim().isEmpty()) {
    //             return null;
    //         }

    //         try {
    //             return new BigDecimal(decimalStr.trim());
    //         } catch (NumberFormatException e) {
    //             System.err.println("Error parsing decimal: " + decimalStr + " - " + e.getMessage());
    //             return null; // Or handle the error as appropriate
    //         }
    //     }

    //     return null;
    // }

}
