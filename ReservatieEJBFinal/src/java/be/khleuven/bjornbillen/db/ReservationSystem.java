/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.db;

import be.khleuven.bjornbillen.entity.Reservation;
import be.khleuven.bjornbillen.entity.Tafel;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bjorn
 */
@Stateless
@LocalBean
public class ReservationSystem implements ReservationSystemRemote {
      
    @PersistenceContext
    private EntityManager manager;
    
    public ReservationSystem(){
       
    }
    @Override
    public List<Reservation> getReservations() {
    return manager.createQuery("Select r from Reservation r",Reservation.class).getResultList();
    }

    @Override
    public void addReservation(Reservation r) {
    manager.persist(r);
    }

    @Override
    public void editReservation(Reservation r) {
    manager.merge(r);
    }

    @Override
    public void deleteReservation(Integer id) {
    manager.remove(getReservation(id));
    }

    @Override
    public void addTable(Tafel t) {
    manager.persist(t);
    }

    @Override
    public void editTable(Tafel t) {
    manager.merge(t);
    }

    @Override
    public void deleteTable(Integer id) {
    manager.remove(getTable(id));
    }
    
    @Override
    public List<Tafel> getTables(){
    return manager.createQuery("Select t from Tafel t",Tafel.class).getResultList();
    }

    @Override
    public Reservation getReservation(Integer id) {
        return manager.find(Reservation.class, id);
    }

    @Override
    public Tafel getTable(Integer id) {
        return manager.find(Tafel.class,id);
    }

    @Override
    public Tafel getTable(String descr) {
        Tafel t = manager.createQuery("Select t from Tafel t WHERE t.tDescription = :descr",Tafel.class).getSingleResult();
        return t;
    }
    
}
