/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.db;


import be.khleuven.bjornbillen.entity.Reservation;
import be.khleuven.bjornbillen.entity.Tafel;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;

/**
 *
 * @author Bjorn
 */
@Remote
public interface ReservationSystemRemote {

    public List<Reservation> getReservations();
    public List<Tafel> getTables();
    public void addReservation(Reservation r);
    public void editReservation(Reservation r);
    public Reservation getReservation(Integer id);
    public Tafel getTable(Integer id);
    public void deleteReservation(Integer id);
    public void addTable(Tafel t);
    public void editTable(Tafel t);
    public void deleteTable(Integer id);   
}
