/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bakum
 */
@Entity
@Table(name = "MEASURE_UNIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeasureUnit.findAll", query = "SELECT m FROM MeasureUnit m"),
    @NamedQuery(name = "MeasureUnit.findById", query = "SELECT m FROM MeasureUnit m WHERE m.id = :id"),
    @NamedQuery(name = "MeasureUnit.findByFullname", query = "SELECT m FROM MeasureUnit m WHERE m.fullname = :fullname"),
    @NamedQuery(name = "MeasureUnit.findByCode", query = "SELECT m FROM MeasureUnit m WHERE m.code = :code"),
    @NamedQuery(name = "MeasureUnit.findByDeleted", query = "SELECT m FROM MeasureUnit m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "MeasureUnit.findByVersion", query = "SELECT m FROM MeasureUnit m WHERE m.version = :version")})
public class MeasureUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FULLNAME")
    private String fullname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CODE")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELETED")
    private short deleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @OneToMany(mappedBy = "edIzm")
    private Collection<LastPrices> lastPricesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measureId")
    private Collection<OrdersTpUslugi> ordersTpUslugiCollection;

    public MeasureUnit() {
    }

    public MeasureUnit(String id) {
        this.id = id;
    }

    public MeasureUnit(String id, String fullname, String code, short deleted, Date version) {
        this.id = id;
        this.fullname = fullname;
        this.code = code;
        this.deleted = deleted;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<LastPrices> getLastPricesCollection() {
        return lastPricesCollection;
    }

    public void setLastPricesCollection(Collection<LastPrices> lastPricesCollection) {
        this.lastPricesCollection = lastPricesCollection;
    }

    @XmlTransient
    public Collection<OrdersTpUslugi> getOrdersTpUslugiCollection() {
        return ordersTpUslugiCollection;
    }

    public void setOrdersTpUslugiCollection(Collection<OrdersTpUslugi> ordersTpUslugiCollection) {
        this.ordersTpUslugiCollection = ordersTpUslugiCollection;
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
        if (!(object instanceof MeasureUnit)) {
            return false;
        }
        MeasureUnit other = (MeasureUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.MeasureUnit[ id=" + id + " ]";
    }
    
}
