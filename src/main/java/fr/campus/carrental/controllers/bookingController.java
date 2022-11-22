package fr.campus.carrental.controllers;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.dao.BookingDaoImpl;
import fr.campus.carrental.model.Booking;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class bookingController {

    private BookingDao bookingDao;

    public bookingController() throws ParseException {
        this(new BookingDaoImpl());
    }
    public bookingController(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @GetMapping("/bookings")
    public List<Booking> GetAllBookings(){
        return bookingDao.findAll();
    }
}
