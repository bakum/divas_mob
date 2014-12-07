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
@Table(name = "ORDER_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderStatus.findAll", query = "SELECT o FROM OrderStatus o"),
    @NamedQuery(name = "OrderStatus.findById", query = "SELECT o FROM OrderStatus o WHERE o.id = :id"),
    @NamedQuery(name = "OrderStatus.findByVersion", query = "SELECT o FROM OrderStatus o WHERE o.version = :version"),
    @NamedQuery(name = "OrderStatus.findByName", query = "SELECT o FROM OrderStatus o WHERE o.name = :name"),
    @NamedQuery(name = "OrderStatus.findByNameForZamer", query = "SELECT o FROM OrderStatus o WHERE UPPER(o.name) IN (:name1,:name2,:name3,:name4)"),
    @NamedQuery(name = "OrderStatus.findByFullname", query = "SELECT o FROM OrderStatus o WHERE o.fullname = :fullname")})
public class OrderStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "FULLNAME")
    private String fullname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
    private Collection<Orders> ordersCollection;

    public OrderStatus() {
    }

    public OrderStatus(String id) {
        this.id = id;
    }

    public OrderStatus(String id, Date version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
        if (!(object instanceof OrderStatus)) {
            return false;
        }
        OrderStatus other = (OrderStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.OrderStatus[ id=" + id + " ]";
    }
    
}
