package fr.campus.carrental.dao;

import fr.campus.carrental.model.Booking;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDao {
    List<Booking> findAll();
    Booking findById(int id);
    List<Booking> findByUser(int userId);
    List<Booking> findByVehicle(int vehicleId);
    List<Booking> findByDateInterval(Date startDate, Date EndDate);
}
