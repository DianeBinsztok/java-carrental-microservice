package fr.campus.carrental.controllers;
//import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.model.Booking;
import fr.campus.carrental.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.campus.carrental.ICar;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingDao bookingDao;

    // Lister toutes les réservations
    @GetMapping("/bookings")
    public List<Booking> GetAllBookings(){
        return bookingDao.findAll();
    }
    @GetMapping("/cars")
    public List<ICar> GetAllVehicles() throws JsonProcessingException {
        return bookingService.findAll();
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
        return this.bookingService.findByDateInterval(startDate, endDate);
    }

    @GetMapping("/bookings/availability/{vehicleId}/{startDate}/{endDate}")
    public boolean checkIfVehicleIsAvailableInGivenDateInterval(@PathVariable int vehicleId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return this.bookingService.checkIfVehicleIsAvailableInGivenDateInterval(vehicleId, startDate, endDate);
    }

    @GetMapping("/booking/{startDate}/{endDate}")
    public List<ICar> listAllAvailableVehiclesForGivenPeriod(@PathVariable String startDate, @PathVariable String endDate) throws ParseException, IOException {
        System.out.println("1 - Contrôleur - j'entre dans la méthode");
        return this.bookingService.listAllAvailableVehiclesForGivenPeriod(startDate, endDate);
    }

}
