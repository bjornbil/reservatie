/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bjorn
 */
@Entity
@Table(name = "TAFEL", uniqueConstraints={@UniqueConstraint(columnNames={"TABLEID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tafel.findAll", query = "SELECT t FROM Tafel t"),
    @NamedQuery(name = "Tafel.findById", query = "SELECT t FROM Tafel t WHERE t.tID = :id"),
})
public class Tafel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TABLEID")
    private Integer tID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPTION")
    private String tDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PLACES")
    private Integer tPlaces;
    @OneToMany
    @JoinTable(name="RES_TAFEL", joinColumns={@JoinColumn(name="TAFEL_ID", referencedColumnName="TAFELID")}, inverseJoinColumns={@JoinColumn(name="RES_ID", referencedColumnName="RESID")})
    private List<Reservation> reservations;

  
    
    public Tafel() {
    
    }
    
    public Tafel(String sDescription, Integer places){
       setTDescription(sDescription);
       setTPlaces(places);
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    
    public Integer getTID() {
        return tID;
    }

    public void setTID(Integer tID) {
        this.tID = tID;
    }

    public String getTDescription() {
        return tDescription;
    }

    public void setTDescription(String tDescription) {
        this.tDescription = tDescription;
    }

    public Integer getTPlaces() {
        return tPlaces;
    }

    public void setTPlaces(Integer tPlaces) {
        this.tPlaces = tPlaces;
    }
}
