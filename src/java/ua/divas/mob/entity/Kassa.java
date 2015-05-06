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
@Table(name = "KASSA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kassa.findAll", query = "SELECT k FROM Kassa k"),
    @NamedQuery(name = "Kassa.findBySettings", query = "SELECT k FROM Kassa k WHERE k.id IN (SELECT s.kassaId.id FROM KassaSettings s WHERE s.userId = :user_id)"),
    @NamedQuery(name = "Kassa.findById", query = "SELECT k FROM Kassa k WHERE k.id = :id"),
    @NamedQuery(name = "Kassa.findByFullname", query = "SELECT k FROM Kassa k WHERE k.fullname = :fullname"),
    @NamedQuery(name = "Kassa.findByIsGroup", query = "SELECT k FROM Kassa k WHERE k.isGroup = :isGroup"),
    @NamedQuery(name = "Kassa.findByDeleted", query = "SELECT k FROM Kassa k WHERE k.deleted = :deleted"),
    @NamedQuery(name = "Kassa.findByVersion", query = "SELECT k FROM Kassa k WHERE k.version = :version"),
    @NamedQuery(name = "Kassa.findByPredefined", query = "SELECT k FROM Kassa k WHERE k.predefined = :predefined"),
    @NamedQuery(name = "Kassa.findByFirmaId", query = "SELECT k FROM Kassa k WHERE k.firmaId = :firmaId")})
public class Kassa implements Serializable {
    @OneToMany(mappedBy = "kassaId")
    private Collection<OrdersTpOplaty> ordersTpOplatyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kassaId")
    private Collection<KassaSettings> kassaSettingsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kassaId")
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
    @Size(min = 1, max = 100)
    @Column(name = "FULLNAME")
    private String fullname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_GROUP")
    private short isGroup;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELETED")
    private short deleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREDEFINED")
    private short predefined;
    @Size(max = 50)
    @Column(name = "FIRMA_ID")
    private String firmaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kassaId")
    private Collection<Orders> ordersCollection;
    @OneToMany(mappedBy = "parentId")
    private Collection<Kassa> kassaCollection;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Kassa parentId;
    @JoinColumn(name = "DIVISION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Divisions divisionId;
    @JoinColumn(name = "CURR_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Currency currId;

    public Kassa() {
    }

    public Kassa(String id) {
        this.id = id;
    }

    public Kassa(String id, String fullname, short isGroup, short deleted, Date version, short predefined) {
        this.id = id;
        this.fullname = fullname;
        this.isGroup = isGroup;
        this.deleted = deleted;
        this.version = version;
        this.predefined = predefined;
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

    public short getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(short isGroup) {
        this.isGroup = isGroup;
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

    public short getPredefined() {
        return predefined;
    }

    public void setPredefined(short predefined) {
        this.predefined = predefined;
    }

    public String getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(String firmaId) {
        this.firmaId = firmaId;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @XmlTransient
    public Collection<Kassa> getKassaCollection() {
        return kassaCollection;
    }

    public void setKassaCollection(Collection<Kassa> kassaCollection) {
        this.kassaCollection = kassaCollection;
    }

    public Kassa getParentId() {
        return parentId;
    }

    public void setParentId(Kassa parentId) {
        this.parentId = parentId;
    }

    public Divisions getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Divisions divisionId) {
        this.divisionId = divisionId;
    }

    public Currency getCurrId() {
        return currId;
    }

    public void setCurrId(Currency currId) {
        this.currId = currId;
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
        if (!(object instanceof Kassa)) {
            return false;
        }
        Kassa other = (Kassa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fullname;
    }

    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
        return userSettingsCollection;
    }

    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
        this.userSettingsCollection = userSettingsCollection;
    }

    @XmlTransient
    public Collection<KassaSettings> getKassaSettingsCollection() {
        return kassaSettingsCollection;
    }

    public void setKassaSettingsCollection(Collection<KassaSettings> kassaSettingsCollection) {
        this.kassaSettingsCollection = kassaSettingsCollection;
    }

    @XmlTransient
    public Collection<OrdersTpOplaty> getOrdersTpOplatyCollection() {
        return ordersTpOplatyCollection;
    }

    public void setOrdersTpOplatyCollection(Collection<OrdersTpOplaty> ordersTpOplatyCollection) {
        this.ordersTpOplatyCollection = ordersTpOplatyCollection;
    }
    
}
