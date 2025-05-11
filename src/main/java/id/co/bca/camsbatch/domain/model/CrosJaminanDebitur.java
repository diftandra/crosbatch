package id.co.bca.camsbatch.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "cros", name = "cros_jaminan_debitur")
public class CrosJaminanDebitur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCrosJaminanDebitur;

    private Date tglData;
    private Integer custLnCurrKey;
    private Integer collCurrKey;
    private Integer collCurrencyKey;
    private String noCollateral;
    private String tipeJaminan;
    private String mataUang;
    @Column(length = 450)
    private String deskripsiJaminan;
    private BigDecimal nilaiPasarOcur;
    private BigDecimal nilaiPasarIdr;
    private BigDecimal nilaiTaksasiOcur;
    private BigDecimal nilaiTaksasiIdr;
    private BigDecimal nilaiHakTanggunganOcur;
    private BigDecimal nilaiHakTanggunganIdr;
    private BigDecimal nilaiLikuidasiOcur;
    private BigDecimal nilaiLikuidasiIdr;
    private String solidNonSolid;
    private Date tglBap;
    private Boolean isActive;
    
}
