package id.co.bca.camsbatch.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "cros", name = "cros_master_debitur")
public class CrosMasterDebitur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCrosMasterDebitur;

    private Date tglData;
    private Integer custLnCurrKey;
    private Integer collectKey;
    private Integer acctRegionKey;
    private Integer acctKcuKey;
    private String cin;
    private String namaDebitur;
    private Date tglMenjadiNasabah;
    private Date tglMenjadiDebitur;
    private String noGrup;
    private String namaGrup;
    private String sektorEkonomi;
    private String kategoriDebitur;
    private String kodeKanwil;
    private String kodeKcu;
    private String kategoriKredit;
    private Integer flagHb;
    private BigDecimal balanceIdr;
    private Integer hariTunggakan;
    private Boolean isActive;
    
}
