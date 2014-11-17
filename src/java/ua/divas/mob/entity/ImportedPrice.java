/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bakum
 */
@Entity
@Table(name = "IMPORTED_PRICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImportedPrice.findAll", query = "SELECT i FROM ImportedPrice i"),
    @NamedQuery(name = "ImportedPrice.findById", query = "SELECT i FROM ImportedPrice i WHERE i.id = :id"),
    @NamedQuery(name = "ImportedPrice.findByDat", query = "SELECT i FROM ImportedPrice i WHERE i.dat = :dat"),
    @NamedQuery(name = "ImportedPrice.findByArtikul", query = "SELECT i FROM ImportedPrice i WHERE i.artikul = :artikul"),
    @NamedQuery(name = "ImportedPrice.findByGroups", query = "SELECT i FROM ImportedPrice i WHERE i.groups = :groups"),
    @NamedQuery(name = "ImportedPrice.findByNomName", query = "SELECT i FROM ImportedPrice i WHERE i.nomName = :nomName"),
    @NamedQuery(name = "ImportedPrice.findByEdIzm", query = "SELECT i FROM ImportedPrice i WHERE i.edIzm = :edIzm"),
    @NamedQuery(name = "ImportedPrice.findByPriceUsl", query = "SELECT i FROM ImportedPrice i WHERE i.priceUsl = :priceUsl"),
    @NamedQuery(name = "ImportedPrice.findByPriceGoods", query = "SELECT i FROM ImportedPrice i WHERE i.priceGoods = :priceGoods"),
    @NamedQuery(name = "ImportedPrice.findByVersion", query = "SELECT i FROM ImportedPrice i WHERE i.version = :version")})
public class ImportedPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dat;
    @Size(max = 50)
    @Column(name = "ARTIKUL")
    private String artikul;
    @Size(max = 50)
    @Column(name = "GROUPS")
    private String groups;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "NOM_NAME")
    private String nomName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ED_IZM")
    private String edIzm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE_USL")
    private BigDecimal priceUsl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE_GOODS")
    private BigDecimal priceGoods;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    public ImportedPrice() {
    }

    public ImportedPrice(String id) {
        this.id = id;
    }

    public ImportedPrice(String id, Date dat, String nomName, String edIzm, BigDecimal priceUsl, BigDecimal priceGoods, Date version) {
        this.id = id;
        this.dat = dat;
        this.nomName = nomName;
        this.edIzm = edIzm;
        this.priceUsl = priceUsl;
        this.priceGoods = priceGoods;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getNomName() {
        return nomName;
    }

    public void setNomName(String nomName) {
        this.nomName = nomName;
    }

    public String getEdIzm() {
        return edIzm;
    }

    public void setEdIzm(String edIzm) {
        this.edIzm = edIzm;
    }

    public BigDecimal getPriceUsl() {
        return priceUsl;
    }

    public void setPriceUsl(BigDecimal priceUsl) {
        this.priceUsl = priceUsl;
    }

    public BigDecimal getPriceGoods() {
        return priceGoods;
    }

    public void setPriceGoods(BigDecimal priceGoods) {
        this.priceGoods = priceGoods;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImportedPrice)) {
            return false;
        }
        ImportedPrice other = (ImportedPrice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.ImportedPrice[ id=" + id + " ]";
    }
    
}
