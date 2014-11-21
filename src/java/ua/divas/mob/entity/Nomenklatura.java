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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "NOMENKLATURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nomenklatura.findAll", query = "SELECT n FROM Nomenklatura n"),
    @NamedQuery(name = "Nomenklatura.findByParent", query = "SELECT n FROM Nomenklatura n WHERE n.parentId = :parentid and n.isGroup = 1"),
    @NamedQuery(name = "Nomenklatura.findByParentGr", query = "SELECT n FROM Nomenklatura n WHERE n.parentId = :parentid and n.isGroup = 0"),
    @NamedQuery(name = "Nomenklatura.findById", query = "SELECT n FROM Nomenklatura n WHERE n.id = :id"),
    @NamedQuery(name = "Nomenklatura.findByFullname", query = "SELECT n FROM Nomenklatura n WHERE UPPER(n.fullname) = UPPER(:fullname)"),
    @NamedQuery(name = "Nomenklatura.findByDeleted", query = "SELECT n FROM Nomenklatura n WHERE n.deleted = :deleted"),
    @NamedQuery(name = "Nomenklatura.findByIsGroup", query = "SELECT n FROM Nomenklatura n WHERE n.isGroup = :isGroup"),
    @NamedQuery(name = "Nomenklatura.findByUsluga", query = "SELECT n FROM Nomenklatura n WHERE n.usluga = :usluga"),
    @NamedQuery(name = "Nomenklatura.findByPredefined", query = "SELECT n FROM Nomenklatura n WHERE n.predefined = :predefined"),
    @NamedQuery(name = "Nomenklatura.findByVersion", query = "SELECT n FROM Nomenklatura n WHERE n.version = :version"),
    @NamedQuery(name = "Nomenklatura.findByArtikul", query = "SELECT n FROM Nomenklatura n WHERE n.artikul = :artikul")})
public class Nomenklatura implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomId")
    private Collection<LastPrices> lastPricesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainUsluga")
    private Collection<UserSettings> userSettingsCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "FULLNAME")
    private String fullname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELETED")
    private short deleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_GROUP")
    private short isGroup;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USLUGA")
    private short usluga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREDEFINED")
    private short predefined;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Size(max = 50)
    @Column(name = "ARTIKUL")
    private String artikul;
    @OneToMany(mappedBy = "parentId")
    private Collection<Nomenklatura> nomenklaturaCollection;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Nomenklatura parentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomId")
    private Collection<OrdersTpUslugi> ordersTpUslugiCollection;

    public Nomenklatura() {
    }

    public Nomenklatura(String id) {
        this.id = id;
    }

    public Nomenklatura(String id, String fullname, short deleted, short isGroup, short usluga, short predefined, Date version) {
        this.id = id;
        this.fullname = fullname;
        this.deleted = deleted;
        this.isGroup = isGroup;
        this.usluga = usluga;
        this.predefined = predefined;
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

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public short getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(short isGroup) {
        this.isGroup = isGroup;
    }

    public short getUsluga() {
        return usluga;
    }

    public void setUsluga(short usluga) {
        this.usluga = usluga;
    }

    public short getPredefined() {
        return predefined;
    }

    public void setPredefined(short predefined) {
        this.predefined = predefined;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    @XmlTransient
    public Collection<Nomenklatura> getNomenklaturaCollection() {
        return nomenklaturaCollection;
    }

    public void setNomenklaturaCollection(Collection<Nomenklatura> nomenklaturaCollection) {
        this.nomenklaturaCollection = nomenklaturaCollection;
    }

    public Nomenklatura getParentId() {
        return parentId;
    }

    public void setParentId(Nomenklatura parentId) {
        this.parentId = parentId;
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
        if (!(object instanceof Nomenklatura)) {
            return false;
        }
        Nomenklatura other = (Nomenklatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.Nomenklatura[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
        return userSettingsCollection;
    }

    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
        this.userSettingsCollection = userSettingsCollection;
    }

    @XmlTransient
    public Collection<LastPrices> getLastPricesCollection() {
        return lastPricesCollection;
    }

    public void setLastPricesCollection(Collection<LastPrices> lastPricesCollection) {
        this.lastPricesCollection = lastPricesCollection;
    }
    
}
