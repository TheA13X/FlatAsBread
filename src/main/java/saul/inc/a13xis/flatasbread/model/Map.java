/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saul.inc.a13xis.flatasbread.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexis
 */
@Entity
@Table(name = "map")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Map.findAll", query = "SELECT m FROM Map m"),
    @NamedQuery(name = "Map.findByName", query = "SELECT m FROM Map m WHERE m.name = :name"),
    @NamedQuery(name = "Map.findByEdit", query = "SELECT m FROM Map m WHERE m.edit = :edit"),
    @NamedQuery(name = "Map.findByVersion", query = "SELECT m FROM Map m WHERE m.version = :version"),
    @NamedQuery(name = "Map.findByLocX", query = "SELECT m FROM Map m WHERE m.locX = :locX"),
    @NamedQuery(name = "Map.findByLocY", query = "SELECT m FROM Map m WHERE m.locY = :locY"),
    @NamedQuery(name = "Map.findByLocZ", query = "SELECT m FROM Map m WHERE m.locZ = :locZ")})
public class Map implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "edit")
    private Boolean edit;
    @Column(name = "version")
    private String version;
    @Basic(optional = false)
    @Column(name = "locX")
    private float locX;
    @Basic(optional = false)
    @Column(name = "locY")
    private float locY;
    @Basic(optional = false)
    @Column(name = "locZ")
    private float locZ;
    public Map() {
    }

    public Map(String name) {
        this.name = name;
    }

    public Map(String name, float locX, float locY, float locZ) {
        this.name = name;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public float getLocX() {
        return locX;
    }

    public void setLocX(float locX) {
        this.locX = locX;
    }

    public float getLocY() {
        return locY;
    }

    public void setLocY(float locY) {
        this.locY = locY;
    }

    public float getLocZ() {
        return locZ;
    }

    public void setLocZ(float locZ) {
        this.locZ = locZ;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Map)) {
            return false;
        }
        Map other = (Map) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Map[ name=" + name + " ]";
    }
    
}
