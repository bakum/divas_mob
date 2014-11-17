/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bakum
 */
@Entity
@Table(name = "USER_SETTINGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserSettings.findByUser", query = "SELECT u FROM UserSettings u WHERE u.userId = :userid"),
    @NamedQuery(name = "UserSettings.findAll", query = "SELECT u FROM UserSettings u"),
    @NamedQuery(name = "UserSettings.findById", query = "SELECT u FROM UserSettings u WHERE u.id = :id")})
public class UserSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "ACTIVITIES_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TypeOfActivities activitiesId;
    @JoinColumn(name = "MAIN_USLUGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Nomenklatura mainUsluga;
    @JoinColumn(name = "KASSA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Kassa kassaId;
    @JoinColumn(name = "FIRMA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Firms firmaId;
    @JoinColumn(name = "DIVISION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Divisions divisionId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Currency currencyId;

    public UserSettings() {
    }

    public UserSettings(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Nomenklatura getMainUsluga() {
        return mainUsluga;
    }

    public void setMainUsluga(Nomenklatura mainUsluga) {
        this.mainUsluga = mainUsluga;
    }

    public Kassa getKassaId() {
        return kassaId;
    }

    public void setKassaId(Kassa kassaId) {
        this.kassaId = kassaId;
    }

    public Firms getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(Firms firmaId) {
        this.firmaId = firmaId;
    }

    public Divisions getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Divisions divisionId) {
        this.divisionId = divisionId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
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
        if (!(object instanceof UserSettings)) {
            return false;
        }
        UserSettings other = (UserSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.UserSettings[ id=" + id + " ]";
    }
    
}
