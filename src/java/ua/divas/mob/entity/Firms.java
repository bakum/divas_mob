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
@Table(name = "FIRMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firms.findAll", query = "SELECT f FROM Firms f"),
    @NamedQuery(name = "Firms.findById", query = "SELECT f FROM Firms f WHERE f.id = :id"),
    @NamedQuery(name = "Firms.findByFullname", query = "SELECT f FROM Firms f WHERE f.fullname = :fullname"),
    @NamedQuery(name = "Firms.findByPrintname", query = "SELECT f FROM Firms f WHERE f.printname = :printname"),
    @NamedQuery(name = "Firms.findByUrFiz", query = "SELECT f FROM Firms f WHERE f.urFiz = :urFiz"),
    @NamedQuery(name = "Firms.findByInn", query = "SELECT f FROM Firms f WHERE f.inn = :inn"),
    @NamedQuery(name = "Firms.findByOkpo", query = "SELECT f FROM Firms f WHERE f.okpo = :okpo"),
    @NamedQuery(name = "Firms.findByVersion", query = "SELECT f FROM Firms f WHERE f.version = :version"),
    @NamedQuery(name = "Firms.findByDeleted", query = "SELECT f FROM Firms f WHERE f.deleted = :deleted"),
    @NamedQuery(name = "Firms.findByPredefined", query = "SELECT f FROM Firms f WHERE f.predefined = :predefined")})
public class Firms implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firmaId")
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
    @Size(min = 1, max = 200)
    @Column(name = "PRINTNAME")
    private String printname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UR_FIZ")
    private short urFiz;
    @Size(max = 50)
    @Column(name = "INN")
    private String inn;
    @Size(max = 50)
    @Column(name = "OKPO")
    private String okpo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELETED")
    private short deleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREDEFINED")
    private short predefined;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firmId")
    private Collection<Orders> ordersCollection;

    public Firms() {
    }

    public Firms(String id) {
        this.id = id;
    }

    public Firms(String id, String fullname, String printname, short urFiz, Date version, short deleted, short predefined) {
        this.id = id;
        this.fullname = fullname;
        this.printname = printname;
        this.urFiz = urFiz;
        this.version = version;
        this.deleted = deleted;
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

    public String getPrintname() {
        return printname;
    }

    public void setPrintname(String printname) {
        this.printname = printname;
    }

    public short getUrFiz() {
        return urFiz;
    }

    public void setUrFiz(short urFiz) {
        this.urFiz = urFiz;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firms)) {
            return false;
        }
        Firms other = (Firms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.Firms[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
        return userSettingsCollection;
    }

    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
        this.userSettingsCollection = userSettingsCollection;
    }
    
}
