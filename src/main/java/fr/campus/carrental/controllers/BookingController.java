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
    public Booking addBooking(@RequestBody Booking newBooking){
        bookingDao.save(newBooking);
        return newBooking;
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

    // Modifier une réservation
    @PutMapping("/booking/update")
    // l'id sera dans le corps de la requête.
    // avec save(), le dao recherchera l'instance à l'id indiqué et le remplacera par la nouvelle instance
    public Booking updateBooking(@RequestBody Booking newBooking){
        bookingDao.save(newBooking);
        return newBooking;
    }

    // Supprimer une réservation
    @DeleteMapping("booking/delete/{bookingId}")
    public void deleteBooking(@PathVariable int bookingId){
        Optional<Booking> target = bookingDao.findById(bookingId);
        target.ifPresent(bookingDao::delete);
    }

    // Tester l'âge de l'utilisateur
    @GetMapping("/bookings/checkage/{vehicleId}")
    // Il faut préciser à quel header on passe le token ("Authorization") car il y a plusieurs type différents de header
    public boolean checkIfUserIsOldEnoughForCarPower(@RequestHeader("Authorization") String UserToken, @PathVariable int vehicleId){
       return this.bookingService.checkIfUserIsOldEnoughForCarPower(UserToken, vehicleId);
    }

    // Afficher toutes les réservations pour un interval de dates donné
    @GetMapping("/bookings/dateinterval/{startDate}/{endDate}")
    public List<Booking> findBookingsByDateInterval(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return this.bookingService.findBookingsByDateInterval(startDate, endDate);
    }

    @GetMapping("/bookings/availability/{vehicleId}/{startDate}/{endDate}")
    public boolean checkIfVehicleIsAvailableInGivenDateInterval(@PathVariable int vehicleId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return this.bookingService.checkIfVehicleIsAvailableInGivenDateInterval(vehicleId, startDate, endDate);
    }

    @GetMapping("/booking/{startDate}/{endDate}")
    public List<ICar> listAllAvailableVehiclesForGivenPeriod(@PathVariable String startDate, @PathVariable String endDate) throws ParseException, IOException {
        return this.bookingService.listAllAvailableVehiclesForGivenPeriod(startDate, endDate);
    }

}
