package fr.campus.carrental.dao;

import fr.campus.carrental.model.Booking;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {
    List<Booking> findAll();
    Optional<Booking> findById(int id);
    List<Booking> findByUserId(int userId);
    List<Booking> findByVehicleId(int vehicleId);
    List<Booking> findAllByEndDateBetween(Date startDate, Date endDate);
    List<Booking> findAllByStartDateBetween(Date startDate, Date endDate);

}
