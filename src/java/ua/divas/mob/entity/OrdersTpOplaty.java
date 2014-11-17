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
@Table(name = "ORDERS_TP_OPLATY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersTpOplaty.findAll", query = "SELECT o FROM OrdersTpOplaty o"),
    @NamedQuery(name = "OrdersTpOplaty.findById", query = "SELECT o FROM OrdersTpOplaty o WHERE o.id = :id"),
    @NamedQuery(name = "OrdersTpOplaty.findByDat", query = "SELECT o FROM OrdersTpOplaty o WHERE o.dat = :dat"),
    @NamedQuery(name = "OrdersTpOplaty.findBySum", query = "SELECT o FROM OrdersTpOplaty o WHERE o.sum = :sum"),
    @NamedQuery(name = "OrdersTpOplaty.findByComments", query = "SELECT o FROM OrdersTpOplaty o WHERE o.comments = :comments")})
public class OrdersTpOplaty implements Serializable {
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUM")
    private BigDecimal sum;
    @Size(max = 1000)
    @Column(name = "COMMENTS")
    private String comments;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Orders orderId;

    public OrdersTpOplaty() {
    }

    public OrdersTpOplaty(String id) {
        this.id = id;
    }

    public OrdersTpOplaty(String id, Date dat, BigDecimal sum) {
        this.id = id;
        this.dat = dat;
        this.sum = sum;
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
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
        if (!(object instanceof OrdersTpOplaty)) {
            return false;
        }
        OrdersTpOplaty other = (OrdersTpOplaty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.OrdersTpOplaty[ id=" + id + " ]";
    }
    
}
