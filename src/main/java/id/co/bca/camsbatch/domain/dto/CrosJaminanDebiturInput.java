package id.co.bca.camsbatch.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrosJaminanDebiturInput {
    
    private String tglData;
    private String custLnCurrKey;
    private String collCurrKey;
    private String collCurrencyKey;
    private String noCollateral;
    private String tipeJaminan;
    private String mataUang;
    private String deskripsiJaminan;
    private String nilaiPasarOcur;
    private String nilaiPasarIdr;
    private String nilaiTaksasiOcur;
    private String nilaiTaksasiIdr;
    private String nilaiHakTanggunganOcur;
    private String nilaiHakTanggunganIdr;
    private String nilaiLikuidasiOcur;
    private String nilaiLikuidasiIdr;
    private String solidNonSolid;
    private String tglBap;
}
