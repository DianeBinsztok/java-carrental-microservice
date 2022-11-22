package fr.campus.carrental.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private int userId;
    private int vehicleId;
    private Date startDate;
    private Date endDate;

    public Booking(){}

    public Booking(int id, int userId, int vehicleId, Date startDate, Date endDate){
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int getId() {
        return this.id;
    }
    public int getUser() {
        return this.userId;
    }
    public int getVehicle() {
        return this.vehicleId;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
}
