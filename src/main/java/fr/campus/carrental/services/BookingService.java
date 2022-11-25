package fr.campus.carrental.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.model.Booking;
import org.campus.carrental.ICar;
import org.campus.carrental.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingService {
// vérifications sur conducteur (âge, permis)
// chevaux et âge


    //private String carMicroServiceUrl = "http://preprod1.letsco.ovh:60080/ms-3-sim-cars/cars";
    private String carMicroServiceUrl = "http://192.168.1.86:8080/cars";
    private String userMicroServiceUrl = "http://192.168.1.6:8080/user";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BookingDao bookingDao;

    // Requêter tous les véhicules
    public List<ICar> findAll() {
        List<ICar> allVehicles = this.restTemplate.getForObject(this.carMicroServiceUrl+"/list", List.class);
        return allVehicles;
    }

    // Lister tous les véhicules disponibles pour une période donnée
    public List<ICar> listAllAvailableVehiclesForGivenPeriod(@PathVariable String startDate, @PathVariable String endDate) throws ParseException, IOException {

        // Je récupère la réponse de l'API sous forme de String (JSON) et je la change en liste d'instances de ICar
        ResponseEntity<String> responseVehicles = this.restTemplate.getForEntity(this.carMicroServiceUrl+"/list", String.class);
        ObjectMapper filterVehicles = new ObjectMapper();
        List<ICar> allVehicles = filterVehicles.readValue(responseVehicles.getBody(), new TypeReference<List<ICar>>(){});
        // fin du traitement


        List<ICar> availableVehicles = new ArrayList<ICar>();

        assert allVehicles != null;
        for(ICar vehicle : allVehicles){
            if(checkIfVehicleIsAvailableInGivenDateInterval(vehicle.getId(), startDate, endDate)){
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    // Vérifier la disponibilité d'un véhicule dans une période donnée
    public boolean checkIfVehicleIsAvailableInGivenDateInterval(int vehicleId, String startDate, String endDate) throws ParseException {
        List<Booking> simultaneousBookings = findBookingsByDateInterval(startDate, endDate);
        for(Booking booking : simultaneousBookings){
            if(booking.getVehicleId() == vehicleId){
                return false;
            }
        }
        return true;
    }

    // Afficher toutes les réservations sur une période donnée
    public List<Booking> findBookingsByDateInterval(String startDate, String endDate) throws ParseException {
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

    // Vérifier que le conducteur a l'âge requis pour la puissance du véhicule
    public boolean checkIfUserIsOldEnoughForCarPower(String UserToken, int vehicleId){
        // Je donne un id de user en dur pour l'instant. il sera remplacé par "me"
        IUser user = this.restTemplate.getForObject(this.userMicroServiceUrl+"/1",  IUser.class);
        ICar car = this.restTemplate.getForObject(this.carMicroServiceUrl+"/"+vehicleId,  ICar.class);
        int usersAge = user.getAge();
        int carPower = car.getFiscalPower();
        System.out.println("usersAge => "+ usersAge);
        System.out.println("carPower => "+ carPower);
        if(usersAge < 18){
            return false;
        } else if ( usersAge < 21) {
            if(carPower>=8){
                return false;
            }
        }else if( usersAge < 25){
            if(carPower>=13){
                return false;
            }
        }
         return true;
    }

}
