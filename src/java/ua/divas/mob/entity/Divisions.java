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
@Table(name = "DIVISIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Divisions.findAll", query = "SELECT d FROM Divisions d"),
    @NamedQuery(name = "Divisions.findById", query = "SELECT d FROM Divisions d WHERE d.id = :id"),
    @NamedQuery(name = "Divisions.findByFullname", query = "SELECT d FROM Divisions d WHERE d.fullname = :fullname"),
    @NamedQuery(name = "Divisions.findByDeleted", query = "SELECT d FROM Divisions d WHERE d.deleted = :deleted"),
    @NamedQuery(name = "Divisions.findByIsGroup", query = "SELECT d FROM Divisions d WHERE d.isGroup = :isGroup"),
    @NamedQuery(name = "Divisions.findByVersion", query = "SELECT d FROM Divisions d WHERE d.version = :version"),
    @NamedQuery(name = "Divisions.findByPredefined", query = "SELECT d FROM Divisions d WHERE d.predefined = :predefined")})
public class Divisions implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionId")
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
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREDEFINED")
    private short predefined;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionId")
    private Collection<Orders> ordersCollection;
    @OneToMany(mappedBy = "divisionId")
    private Collection<Kassa> kassaCollection;
    @OneToMany(mappedBy = "parentId")
    private Collection<Divisions> divisionsCollection;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Divisions parentId;

    public Divisions() {
    }

    public Divisions(String id) {
        this.id = id;
    }

    public Divisions(String id, String fullname, short deleted, short isGroup, Date version, short predefined) {
        this.id = id;
        this.fullname = fullname;
        this.deleted = deleted;
        this.isGroup = isGroup;
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

    @XmlTransient
    public Collection<Divisions> getDivisionsCollection() {
        return divisionsCollection;
    }

    public void setDivisionsCollection(Collection<Divisions> divisionsCollection) {
        this.divisionsCollection = divisionsCollection;
    }

    public Divisions getParentId() {
        return parentId;
    }

    public void setParentId(Divisions parentId) {
        this.parentId = parentId;
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
        if (!(object instanceof Divisions)) {
            return false;
        }
        Divisions other = (Divisions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.Divisions[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
        return userSettingsCollection;
    }

    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
        this.userSettingsCollection = userSettingsCollection;
    }
    
}
