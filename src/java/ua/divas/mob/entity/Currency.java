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
@Table(name = "CURRENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
    @NamedQuery(name = "Currency.findById", query = "SELECT c FROM Currency c WHERE c.id = :id"),
    @NamedQuery(name = "Currency.findByIsGroup", query = "SELECT c FROM Currency c WHERE c.isGroup = :isGroup"),
    @NamedQuery(name = "Currency.findByParentId", query = "SELECT c FROM Currency c WHERE c.parentId = :parentId"),
    @NamedQuery(name = "Currency.findByDeleted", query = "SELECT c FROM Currency c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "Currency.findByFullname", query = "SELECT c FROM Currency c WHERE c.fullname = :fullname"),
    @NamedQuery(name = "Currency.findByCode", query = "SELECT c FROM Currency c WHERE c.code = :code"),
    @NamedQuery(name = "Currency.findByNamefull", query = "SELECT c FROM Currency c WHERE c.namefull = :namefull"),
    @NamedQuery(name = "Currency.findByVersion", query = "SELECT c FROM Currency c WHERE c.version = :version"),
    @NamedQuery(name = "Currency.findByPredefined", query = "SELECT c FROM Currency c WHERE c.predefined = :predefined"),
    @NamedQuery(name = "Currency.findByIdXml", query = "SELECT c FROM Currency c WHERE c.idXml = :idXml")})
public class Currency implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId")
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
    @Column(name = "IS_GROUP")
    private short isGroup;
    @Size(max = 50)
    @Column(name = "PARENT_ID")
    private String parentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELETED")
    private short deleted;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "FULLNAME")
    private String fullname;
    @Size(max = 3)
    @Column(name = "CODE")
    private String code;
    @Size(max = 50)
    @Column(name = "NAMEFULL")
    private String namefull;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREDEFINED")
    private short predefined;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ID_XML")
    private String idXml;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currId")
    private Collection<Orders> ordersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currId")
    private Collection<Kassa> kassaCollection;

    public Currency() {
    }

    public Currency(String id) {
        this.id = id;
    }

    public Currency(String id, short isGroup, short deleted, String fullname, Date version, short predefined, String idXml) {
        this.id = id;
        this.isGroup = isGroup;
        this.deleted = deleted;
        this.fullname = fullname;
        this.version = version;
        this.predefined = predefined;
        this.idXml = idXml;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(short isGroup) {
        this.isGroup = isGroup;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
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

    public String getNamefull() {
        return namefull;
    }

    public void setNamefull(String namefull) {
        this.namefull = namefull;
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

    public String getIdXml() {
        return idXml;
    }

    public void setIdXml(String idXml) {
        this.idXml = idXml;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.Currency[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
        return userSettingsCollection;
    }

    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
        this.userSettingsCollection = userSettingsCollection;
    }
    
}
