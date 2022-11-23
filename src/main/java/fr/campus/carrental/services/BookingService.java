package fr.campus.carrental.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.campus.carrental.dao.BookingDao;
import fr.campus.carrental.model.Booking;
import org.campus.carrental.ICar;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
// vérifications sur conducteur (âge, permis)
// chevaux et âge


    //private String carMicroServiceUrl = "http://preprod1.letsco.ovh:60080/ms-3-sim-cars/cars";
    private String carMicroServiceUrl = "http://192.168.1.86:8080/cars";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private BookingDao bookingDao;

    public List<ICar> findAll() {
        List<ICar> allVehicles = this.restTemplate.getForObject(this.carMicroServiceUrl+"/list", List.class);
        return allVehicles;
    }

    public List<ICar> listAllAvailableVehiclesForGivenPeriod(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        List<ICar> allVehicles = this.restTemplate.getForObject(this.carMicroServiceUrl+"/list", List.class);
        List<ICar> availableVehicles = new ArrayList<>();
        for(ICar vehicle : allVehicles){
            if(checkIfVehicleIsAvailableInGivenDateInterval(vehicle.getId(), startDate, endDate)){
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public boolean checkIfVehicleIsAvailableInGivenDateInterval(int vehicleId, String startDate, String endDate) throws ParseException {
        List<Booking> simultaneousBookings = findByDateInterval(startDate, endDate);
        for(Booking booking : simultaneousBookings){
            if(booking.getVehicleId() == vehicleId){
                return false;
            }
        }
        return true;
    }

    public List<Booking> findByDateInterval(String startDate, String endDate) throws ParseException {
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
}
