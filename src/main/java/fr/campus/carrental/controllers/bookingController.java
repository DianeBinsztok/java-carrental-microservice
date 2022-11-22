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

    // Lister toutes les réservations
    @GetMapping("/bookings")
    public List<Booking> GetAllBookings(){
        return bookingDao.findAll();
    }

    // Consulter une réservation si elle existe
    @GetMapping("/booking/{id}")
    public Optional<Booking> bookingDetail(@PathVariable int id){
        return bookingDao.findById(id);
    }

    // Enregistrer une réservation si elle existe
    @PostMapping(value = "/bookings")
    // @RequestBody demande à Spring de convertir le corps de la requête HTTP en JSON
    // La requête en JSON sera convertie en objet Car
    public void addBooking(@RequestBody Booking newBooking){
        bookingDao.save(newBooking);
    }

    // Lister les réservations par client
    @GetMapping("/bookings/user/{userId}")
    public List<Booking> listByUserId(@PathVariable int userId){
        return bookingDao.findByUserId(userId);
    }

    // Lister les réservations par véhicule
    @GetMapping("/bookings/vehicle/{vehicleId}")
    public List<Booking> listByVehicleId(@PathVariable int vehicleId){
        return bookingDao.findByVehicleId(vehicleId);
    }

    @PutMapping("/booking/update")
    // l'id sera dans le corps de la requête.
    // avec save(), le dao recherchera l'instance à l'id indiqué et le remplacera par la nouvelle instance
    public void updateBooking(@RequestBody Booking newBooking){
        bookingDao.save(newBooking);
    }

    @DeleteMapping("booking/delete/{bookingId}")
    public void deleteBooking(@PathVariable int bookingId){
        Optional<Booking> target = bookingDao.findById(bookingId);
        target.ifPresent(bookingDao::delete);
    }

    // Lister les réservations par interval de dates
//    @GetMapping("/bookings/dateinterval{startDate}-{EndDate}")
//    public List<Booking> findByDateInterval(@PathVariable Date startDate, @PathVariable Date EndDate){
//        return bookingDao.findByDateInterval(startDate, EndDate);
//    }
}
