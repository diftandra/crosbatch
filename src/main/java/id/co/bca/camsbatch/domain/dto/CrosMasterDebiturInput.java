package id.co.bca.camsbatch.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrosMasterDebiturInput {
    
    private String tglData;
    private String custLnCurrKey;
    private String collectKey;
    private String acctRegionKey;
    private String acctKcuKey;
    private String cin;
    private String namaDebitur;
    private String tglMenjadiNasabah;
    private String tglMenjadiDebitur;
    private String noGrup;
    private String namaGrup;
    private String sektorEkonomi;
    private String kategoriDebitur;
    private String kodeKanwil;
    private String kodeKcu;
    private String kategoriKredit;
    private String flagHb;
    private String balanceIdr;
    private String hariTunggakan;

}
