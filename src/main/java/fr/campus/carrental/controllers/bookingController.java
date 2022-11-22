package fr.campus.carrental.controllers;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.model.Booking;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class bookingController {

    private BookingDao bookingDao;

    public bookingController(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @GetMapping("/bookings")
    public List<Booking> GetAllBookings(){
        return bookingDao.findAll();
    }
    @GetMapping("/booking/{id}")
    public Optional<Booking> bookingDetail(@PathVariable int id){
        return bookingDao.findById(id);
    }

    @PostMapping(value = "/bookings")
    // @RequestBody demande à Spring de convertir le corps de la requête HTTP en JSON
    // La requête en JSON sera convertie en objet Car
    public void addBooking(@RequestBody Booking newBooking){
        bookingDao.save(newBooking);
    }

    @GetMapping("/bookings/user/{userId}")
    public List<Booking> listByUserId(@PathVariable int userId){
        return bookingDao.findByUserId(userId);
    }
    @GetMapping("/bookings/vehicle/{vehicleId}")
    public List<Booking> listByVehicleId(@PathVariable int vehicleId){
        return bookingDao.findByVehicleId(vehicleId);
    }
//    @GetMapping("/bookings/dateinterval{startDate}-{EndDate}")
//    public List<Booking> findByDateInterval(@PathVariable Date startDate, @PathVariable Date EndDate){
//        return bookingDao.findByDateInterval(startDate, EndDate);
//    }
}
