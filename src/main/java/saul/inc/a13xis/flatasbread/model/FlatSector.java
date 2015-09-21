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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "FlatSector")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "FlatSector.findAll", query = "SELECT f FROM FlatSector f"),
		@NamedQuery(name = "FlatSector.findById", query = "SELECT f FROM FlatSector f WHERE f.id = :id"),
		@NamedQuery(name = "FlatSector.findByName", query = "SELECT f FROM FlatSector f WHERE f.name = :name"),
		@NamedQuery(name = "FlatSector.findByOwner", query = "SELECT f FROM FlatSector f WHERE f.owner = :owner"),
		@NamedQuery(name = "FlatSector.findBySecX", query = "SELECT f FROM FlatSector f WHERE f.secX = :secX"),
		@NamedQuery(name = "FlatSector.findBySecY", query = "SELECT f FROM FlatSector f WHERE f.secY = :secY"),
		@NamedQuery(name = "FlatSector.findByBiome", query = "SELECT f FROM FlatSector f WHERE f.biome = :biome"),
		@NamedQuery(name = "FlatSector.findByGenerated", query = "SELECT f FROM FlatSector f WHERE f.generated = :generated") })
public class FlatSector implements Serializable {
	private static final long	serialVersionUID	= 666L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int					id;
	@Column(name = "name")
	private String				name;
	@Column(name = "owner")
	private String				owner;
	@Column(name = "secX")
	private int					secX;
	@Column(name = "secY")
	private int					secY;
	@Basic(optional = false)
	@Column(name = "biome")
	private String				biome;
	@Basic(optional = false)
	@Column(name = "generated")
	private boolean				generated;
	
	public FlatSector() {
	}
	
	public FlatSector(int id) {
		this.id = id;
	}
	
	public FlatSector(int id, String biome, boolean generated) {
		this.id = id;
		this.biome = biome;
		this.generated = generated;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public int getSecX() {
		return secX;
	}
	
	public void setSecX(int secX) {
		this.secX = secX;
	}
	
	public int getSecY() {
		return secY;
	}
	
	public void setSecY(int secY) {
		this.secY = secY;
	}
	
	public String getBiome() {
		return biome;
	}
	
	public void setBiome(String biome) {
		this.biome = biome;
	}
	
	public boolean isGenerated() {
		return generated;
	}
	
	public void setGenerated(boolean isGenerated) {
		this.generated = isGenerated;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof FlatSector)) {
			return false;
		}
		FlatSector other = (FlatSector) object;
		if (this.id != -1 && this.id != other.id) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "FlatSector[ id=" + id + " ]";
	}
	
}
