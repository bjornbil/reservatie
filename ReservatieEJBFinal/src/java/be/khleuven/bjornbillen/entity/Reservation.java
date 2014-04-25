/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bjorn
 */
@Entity
@Table(name = "RESERVATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.rID = :id"),
    @NamedQuery(name = "Reservation.findByDate", query = "SELECT r FROM Reservation r WHERE r.rDate = :date"),
    @NamedQuery(name = "Reservation.findByName", query = "SELECT r FROM Reservation r WHERE r.rName = :name"),
})
public class Reservation implements Serializable {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")  
    private Integer rID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME")
    private String rName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESDATE")
    private String rDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTHOUR")
    private String rStartHour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STOPHOUR")
    private String rStopHour;
    @NotNull
    @ManyToOne(cascade=CascadeType.ALL)
    private Tafel rTable;
    
    private String tableDescr;

    public Reservation() {
    }
    
    public Reservation(Integer sID, String sName, String sDate, String sStartHour, String sStopHour, Tafel sTable){
        this.rID = sID;
        this.rName = sName;
        this.rDate = sDate;
        this.rStartHour = sStartHour;
        this.rStopHour = sStopHour;
        this.rTable = sTable;
        this.tableDescr = rTable.getTDescription();
    }

    public String getRStartHour() {
        return rStartHour;
    }

    public String getRStopHour() {
        return rStopHour;
    }
    
    public Integer getRID() {
        return rID;
    }

    public String getRName() {
        return rName;
    }

    public String getRDate() {
        return rDate;
    }
    
    public void setRID(Integer id){
        this.rID = id;
    }
    
    public void setRName(String name){
        this.rName = name;
    }
    
    public void setRDate(String date){
        this.rDate = date;
    }
    
    public void setStartHour(String start){
        this.rStartHour = start;
    }
    
    public void setStopHour(String stop){
        this.rStopHour = stop;
    }
    
    public void setTable(Tafel table){
        this.rTable = table;
    }
    
    public Tafel getTable(){
        return rTable;
    }
    
    public String getRTable(){
        return getTable().getTDescription();
    }    
    

    
    
}
