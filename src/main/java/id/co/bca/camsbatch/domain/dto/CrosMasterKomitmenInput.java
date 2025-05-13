package id.co.bca.camsbatch.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrosMasterKomitmenInput {
    
    private String tglData;
    private String custLnCurrKey;
    private String acctLnCurrKey;
    private String commCurrKey;
    private String productSubTypeKey;
    private String facLnSegKey;
    private String collectKey;
    private String acctRegionKey;
    private String acctKcuKey;
    private String kodeKanwil;
    private String kodeKcu;
    private String noRekeningPinjaman;
    private String noKomitmen;
    private String tglBukaKomitmen;
    private String tglTutupKomitmen;
    private String kategoriKredit;
    private String tipeProduk;
    private String kodeProduk;
    private String namaProduk;
    private String mataUang;
    private String plafondOcur;
    private String plafondIdr;
    private String balanceOcur;
    private String balanceIdr;
    private String bungaOcur;
    private String bungaIdr;
    private String dendaOcur;
    private String dendaIdr;
    private String balanceOcurHb;
    private String balanceIdrHb;
    private String bungaOcurHb;
    private String bungaIdrHb;
    private String dendaOcurHb;
    private String dendaIdrHb;
    private String tglHb;
    private String balanceOcurHt;
    private String balanceIdrHt;
    private String bungaOcurHt;
    private String bungaIdrHt;
    private String dendaOcurHt;
    private String dendaIdrHt;
    private String tglHt;
    private String ppap;
    private String posisiDataPpap;
    private String ckpn;
    private String posisiDataCkpn;
    private String hariTunggakan;
    private String isRestrukturisasi;
    private String kodeRestrukturisasi;
    private String flagHb;

}
