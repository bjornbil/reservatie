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
public class Tafel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAFELID")
    private int tID;
    @Column(name = "DESCRIPTION")
    private String tDescription;
    @Column(name = "PLACES")
    private Integer tPlaces;
    @OneToMany(mappedBy="rTable")
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

    
    public int getTID() {
        return tID;
    }

    public void setTID(int tID) {
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
    
    @Override
    public boolean equals(Object o){
        boolean equals = false;
        Tafel t = null;
        if (o instanceof Tafel){
        t = (Tafel) o;
        if (t.tID == this.tID){
        equals = true;
        }
        }
        return equals;
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.tID;
        return hash;
    }
    
    
}
