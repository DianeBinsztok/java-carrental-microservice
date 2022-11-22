package fr.campus.carrental.dao;

import fr.campus.carrental.model.Booking;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BookingDaoImpl implements BookingDao{
    public List<Booking> bookings = new ArrayList<>();



    public BookingDaoImpl() throws ParseException {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        this.bookings.add(new Booking(1, 1, 5, formatter.parse("2022-12-20"), formatter.parse("2022-12-28")));
        this.bookings.add(new Booking(2, 19, 3, formatter.parse("2022-12-20"),  formatter.parse("2022-12-27")));
        this.bookings.add(new Booking(3, 4, 11, formatter.parse("2022-12-23"), formatter.parse("2022-12-26")));

    }

    @Override
    public List<Booking> findAll() {
        return bookings;
    }

    @Override
    public Booking findById(int id) {
        return null;
    }

    @Override
    public List<Booking> findByUser(int userId) {
        return null;
    }

    @Override
    public List<Booking> findByVehicle(int vehicleId) {
        return null;
    }

    @Override
    public List<Booking> findByDateInterval(Date startDate, Date EndDate) {
        return null;
    }
}
