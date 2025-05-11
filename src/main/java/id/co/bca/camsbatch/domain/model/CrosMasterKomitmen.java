package id.co.bca.camsbatch.model;

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
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "cros", name = "cros_master_komitmen")
public class CrosMasterKomitmen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date tglData;
    private Integer custLnCurrKey;
    private Integer acctLnCurrKey;
    private Integer commCurrKey;
    private Integer productSubTypeKey;
    private Integer facLnSegKey;
    private Integer collectKey;
    private Integer acctRegionKey;
    private Integer acctKcuKey;
    private String kodeKanwil;
    private String kodeKcu;
    private String noRekeningPinjaman;
    private String noKomitmen;
    private Date tglBukaKomitmen;
    private Date tglTutupKomitmen;
    private String kategoriKredit;
    private String tipeProduk;
    private String kodeProduk;
    private String namaProduk;
    private String mataUang;
    private BigDecimal plafondOcur;
    private BigDecimal plafondIdr;
    private BigDecimal balanceOcur;
    private BigDecimal balanceIdr;
    private BigDecimal bungaOcur;
    private BigDecimal bungaIdr;
    private BigDecimal dendaOcur;
    private BigDecimal dendaIdr;
    private BigDecimal balanceOcurHb;
    private BigDecimal balanceIdrHb;
    private BigDecimal bungaOcurHb;
    private BigDecimal bungaIdrHb;
    private BigDecimal dendaOcurHb;
    private BigDecimal dendaIdrHb;
    private Date tglHb;
    private BigDecimal balanceOcurHt;
    private BigDecimal balanceIdrHt;
    private BigDecimal bungaOcurHt;
    private BigDecimal bungaIdrHt;
    private BigDecimal dendaOcurHt;
    private BigDecimal dendaIdrHt;
    private Date tglHt;
    private BigDecimal ppap;
    private Date posisiDataPpap;
    private BigDecimal ckpn;
    private Date posisiDataCkpn;
    private Integer hariTunggakan;
    private Integer isRestrukturisasi;
    private String kodeRestrukturisasi;
    private Integer flagHb;
    private Boolean isActive;

}
