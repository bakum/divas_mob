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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bakum
 */
@Entity
@Table(name = "WLS_SETTINGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WlsSettings.findAll", query = "SELECT w FROM WlsSettings w"),
    @NamedQuery(name = "WlsSettings.findById", query = "SELECT w FROM WlsSettings w WHERE w.id = :id"),
    @NamedQuery(name = "WlsSettings.findByPort", query = "SELECT w FROM WlsSettings w WHERE w.port = :port"),
    @NamedQuery(name = "WlsSettings.findByUsername", query = "SELECT w FROM WlsSettings w WHERE w.username = :username"),
    @NamedQuery(name = "WlsSettings.findByPassword", query = "SELECT w FROM WlsSettings w WHERE w.password = :password"),
    @NamedQuery(name = "WlsSettings.findByHost", query = "SELECT w FROM WlsSettings w WHERE w.host = :host")})
public class WlsSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "PORT")
    private String port;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "HOST")
    private String host;

    public WlsSettings() {
    }

    public WlsSettings(String id) {
        this.id = id;
    }

    public WlsSettings(String id, String port, String username, String password, String host) {
        this.id = id;
        this.port = port;
        this.username = username;
        this.password = password;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
        if (!(object instanceof WlsSettings)) {
            return false;
        }
        WlsSettings other = (WlsSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ua.divas.mob.entity.WlsSettings[ id=" + id + " ]";
    }
    
}
