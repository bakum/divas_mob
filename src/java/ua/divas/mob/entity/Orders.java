/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o where o.deleted = 0 and o.statusId = :statusid and o.zamerId = :zamerid ORDER BY o.dat DESC"),
    @NamedQuery(name = "Orders.findAllForAdmin", query = "SELECT o FROM Orders o where o.deleted = 0 ORDER BY o.dat DESC"),
    @NamedQuery(name = "Orders.findAllForDispatch", query = "SELECT o FROM Orders o where o.deleted = 0 and o.statusId in (:name1, :name2, :name3) ORDER BY o.dat DESC"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByDat", query = "SELECT o FROM Orders o WHERE o.dat = :dat"),
    @NamedQuery(name = "Orders.findByNum", query = "SELECT o FROM Orders o WHERE o.num = :num"),
    @NamedQuery(name = "Orders.findByKurs", query = "SELECT o FROM Orders o WHERE o.kurs = :kurs"),
    @NamedQuery(name = "Orders.findByKratnost", query = "SELECT o FROM Orders o WHERE o.kratnost = :kratnost"),
    @NamedQuery(name = "Orders.findByDiscription", query = "SELECT o FROM Orders o WHERE o.discription = :discription"),
    @NamedQuery(name = "Orders.findByDeleted", query = "SELECT o FROM Orders o WHERE o.deleted = :deleted"),
    @NamedQuery(name = "Orders.findByVersion", query = "SELECT o FROM Orders o WHERE o.version = :version"),
    @NamedQuery(name = "Orders.findByPosted", query = "SELECT o FROM Orders o WHERE o.posted = :posted")})
public class Orders implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NUM")
    private String num;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "KURS")
    private BigDecimal kurs;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KRATNOST")
    private long kratnost;
    @Size(max = 2000)
    @Column(name = "DISCRIPTION")
    private String discription;
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
    @Column(name = "POSTED")
    private short posted;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "ACTIVITIES_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TypeOfActivities activitiesId;
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private OrderStatus statusId;
    @JoinColumn(name = "KONTRAG_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Kontragents kontragId;
    @JoinColumn(name = "ZAMER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Kontragents zamerId;
    @JoinColumn(name = "KASSA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Kassa kassaId;
    @JoinColumn(name = "FIRM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Firms firmId;
    @JoinColumn(name = "DIVISION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Divisions divisionId;
    @JoinColumn(name = "CURR_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Currency currId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<OrdersTpOplaty> ordersTpOplatyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<OrdersTpUslugi> ordersTpUslugiCollection;

    public Orders() {
    }

    public Orders(String id) {
        this.id = id;
    }

    public Orders(String id, Date dat, String num, BigDecimal kurs, long kratnost, short deleted, Date version, short posted) {
        this.id = id;
        this.dat = dat;
        this.num = num;
        this.kurs = kurs;
        this.kratnost = kratnost;
        this.deleted = deleted;
        this.version = version;
        this.posted = posted;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public BigDecimal getKurs() {
        return kurs;
    }

    public void setKurs(BigDecimal kurs) {
        this.kurs = kurs;
    }

    public long getKratnost() {
        return kratnost;
    }

    public void setKratnost(long kratnost) {
        this.kratnost = kratnost;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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

    public short getPosted() {
        return posted;
    }

    public void setPosted(short posted) {
        this.posted = posted;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public TypeOfActivities getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(TypeOfActivities activitiesId) {
        this.activitiesId = activitiesId;
    }

    public OrderStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(OrderStatus statusId) {
        this.statusId = statusId;
    }

    public Kontragents getKontragId() {
        return kontragId;
    }

    public void setKontragId(Kontragents kontragId) {
        this.kontragId = kontragId;
    }

    public Kontragents getZamerId() {
        return zamerId;
    }

    public void setZamerId(Kontragents zamerId) {
        this.zamerId = zamerId;
    }

    public Kassa getKassaId() {
        return kassaId;
    }

    public void setKassaId(Kassa kassaId) {
        this.kassaId = kassaId;
    }

    public Firms getFirmId() {
        return firmId;
    }

    public void setFirmId(Firms firmId) {
        this.firmId = firmId;
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

    @XmlTransient
    public Collection<OrdersTpOplaty> getOrdersTpOplatyCollection() {
        return ordersTpOplatyCollection;
    }

    public void setOrdersTpOplatyCollection(Collection<OrdersTpOplaty> ordersTpOplatyCollection) {
        this.ordersTpOplatyCollection = ordersTpOplatyCollection;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.Orders[ id=" + id + " ]";
    }
    
}
