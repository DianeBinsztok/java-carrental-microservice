package fr.campus.carrental.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Booking{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    public int getUserId() {
        return this.userId;
    }
    public int getVehicleId() {
        return this.vehicleId;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }

    // JPA repository peut avoir besoin de setters pour créer des clés étrangères (comme userId et vehicleId)
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
