/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bjorn
 */
@Entity
@Table(name = "RESERVATION", uniqueConstraints={@UniqueConstraint(columnNames={"RESID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.rID = :id"),
    @NamedQuery(name = "Reservation.findByDate", query = "SELECT r FROM Reservation r WHERE r.rDate = :date"),
    @NamedQuery(name = "Reservation.findByName", query = "SELECT r FROM Reservation r WHERE r.rName = :name"),
})
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="RESID")
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
    @ManyToOne
    private Tafel rTable;

 
    private String tableDescr = "";

    public Reservation() {
    }
    
    public Reservation(String sName, String sDate, String sStartHour, String sStopHour, Tafel sTable){
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
    
    public String getTableDescr() {
        return tableDescr;
    }

    public void setTableDescr(String tableDescr) {
        this.tableDescr = tableDescr;
    }
    

    
    
}
