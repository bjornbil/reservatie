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
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="RESID")
    private int rID;
    @Column(name = "NAME")
    private String rName;
    @Column(name = "RESDATE")
    private String rDate;
    @Column(name = "STARTHOUR")
    private String rStartHour;
    @Column(name = "STOPHOUR")
    private String rStopHour;
    @ManyToOne
    @JoinColumn(name="TABLE_ID")
    private Tafel rTable;

    public Reservation() {
    }
    
    public Reservation(String sName, String sDate, String sStartHour, String sStopHour, Tafel sTable){
        this.rName = sName;
        this.rDate = sDate;
        this.rStartHour = sStartHour;
        this.rStopHour = sStopHour;
        this.rTable = sTable;
    }

    public String getRStartHour() {
        return rStartHour;
    }

    public String getRStopHour() {
        return rStopHour;
    }
    
    public int getRID() {
        return rID;
    }

    public String getRName() {
        return rName;
    }

    public String getRDate() {
        return rDate;
    }
    
    public void setRID(int id){
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
