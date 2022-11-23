package fr.campus.carrental.controllers;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @GetMapping("/bookings/dateinterval/{startDate}/{endDate}")
    public List<Booking> findByDateInterval(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        List <Booking> result = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);
        List<Booking> bookingsStartingWithin = bookingDao.findAllByStartDateBetween(start, end);
        List<Booking> bookingsEndingWithin = bookingDao.findAllByEndDateBetween(start, end);
        result.addAll(bookingsStartingWithin);
        result.addAll(bookingsEndingWithin);
        return result;
    }

    @GetMapping("/bookings/availability/{vehicleId}/{startDate}/{endDate}")
    public boolean checkIfVehicleIsAvailableInGivenDateInterval(@PathVariable int vehicleId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        boolean available = true;
        List<Booking> simultaneousBookings = findByDateInterval(startDate, endDate);
        for(Booking booking : simultaneousBookings){
            if(booking.getVehicleId() == vehicleId){
                available = false;
            }
        }
        return available;
    }

}
