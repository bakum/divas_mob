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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LAST_PRICES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LastPrices.findAll", query = "SELECT l FROM LastPrices l"),
    @NamedQuery(name = "LastPrices.findByNom", query = "SELECT l FROM LastPrices l WHERE l.nomId = :nomid"),
    @NamedQuery(name = "LastPrices.findById", query = "SELECT l FROM LastPrices l WHERE l.id = :id"),
    @NamedQuery(name = "LastPrices.findByPeriod", query = "SELECT l FROM LastPrices l WHERE l.period = :period"),
    @NamedQuery(name = "LastPrices.findByPriceUsl", query = "SELECT l FROM LastPrices l WHERE l.priceUsl = :priceUsl"),
    @NamedQuery(name = "LastPrices.findByPriceGoods", query = "SELECT l FROM LastPrices l WHERE l.priceGoods = :priceGoods")})
public class LastPrices implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERIOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date period;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE_USL")
    private BigDecimal priceUsl;
    @Column(name = "PRICE_GOODS")
    private BigDecimal priceGoods;
    @JoinColumn(name = "NOM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Nomenklatura nomId;
    @JoinColumn(name = "ED_IZM", referencedColumnName = "ID")
    @ManyToOne
    private MeasureUnit edIzm;

    public LastPrices() {
    }

    public LastPrices(String id) {
        this.id = id;
    }

    public LastPrices(String id, Date period, BigDecimal priceUsl) {
        this.id = id;
        this.period = period;
        this.priceUsl = priceUsl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
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

    public Nomenklatura getNomId() {
        return nomId;
    }

    public void setNomId(Nomenklatura nomId) {
        this.nomId = nomId;
    }

    public MeasureUnit getEdIzm() {
        return edIzm;
    }

    public void setEdIzm(MeasureUnit edIzm) {
        this.edIzm = edIzm;
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
        if (!(object instanceof LastPrices)) {
            return false;
        }
        LastPrices other = (LastPrices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.LastPrices[ id=" + id + " ]";
    }
    
}
