package fr.campus.carrental.controllers;

import fr.campus.carrental.model.Booking;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    // Ma réponse doit être:
    // - Sous forme de liste
    // - chaque booking est une instance de Booking
    // - statut : 200
    public void getAllBookings() {
        List<Object> bookingsList = restTemplate.getForObject("/bookings", List.class);
        ResponseEntity<List> response = restTemplate.getForEntity("/bookings", List.class);
        int responseCode = response.getStatusCodeValue();

        assertThat(responseCode == 200);
        assertThat(bookingsList instanceof ArrayList);
        for(Object booking : bookingsList){
            assertThat(booking instanceof Booking);
        }

    }

    @Test
    // Ma réponse doit:
    // - être une instance de Booking
    // - avoir l'id 1
    // - statut : 200
    public void getBookingById() {
        Booking booking = restTemplate.getForObject("/booking/1", Booking.class);
        ResponseEntity<Object> response = restTemplate.getForEntity("/booking/1", Object.class);
        int responseCode = response.getStatusCodeValue();

        assertThat(responseCode == 200);
        assertThat(booking instanceof Booking);
        assertThat(booking.getId() == 1);
    }

    @Test
    // Je ne peux pas afficher de réservation pour un id qui n'existe pas
    // Ma réponse doit être une 404
    public void cantGetBookingThatDoesntExist() {
        ResponseEntity<Booking> response = restTemplate.getForEntity("/booking/x", Booking.class);
        int responseCode = response.getStatusCodeValue();
        assertThat(responseCode == 404);
    }

    @Test
    // Ma réponse doit:
    // - être une instance de List
    // - composée d'instance de Booking
    // - avoir l'id 1
    // - statut : 200
    public void getBookingsByCarId(){
        ResponseEntity<List> response =  restTemplate.getForEntity("/bookings/vehicle/2", List.class);
        int responseCode = response.getStatusCodeValue();
        List<Booking> bookingsByCar =  restTemplate.getForObject("/bookings/vehicle/2", List.class);

        assertThat(responseCode == 200);
        assertThat(bookingsByCar instanceof ArrayList);
        for(Object booking : response.getBody()){
            assertThat(booking instanceof Booking);
        }
        for(Booking booking : bookingsByCar){
            assertThat(booking.getVehicleId() == 2);
        }
    }

    @Test
    // Je ne peux pas afficher de réservations pour un véhicule qui n'existe pas
    // Ma réponse doit être une 404
    public void cantGetBookingsForCarThatDoesntExist(){
        ResponseEntity<Object> response =  restTemplate.getForEntity("/bookings/vehicle/x", Object.class);
        int responseCode = response.getStatusCodeValue();
        assertThat(responseCode == 404);
    }

    @Test
    // La création d'une nouvelle réservation me renvoie ma réservation avec les bons attributs
    public void addBookingReturnsNewBooking() throws ParseException {
        // 1 - Je créer une nouvelle instance de booking
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse("2023-10-12");
        Date end = format.parse("2023-10-19");
        Booking newBooking =  restTemplate.postForObject("/bookings/", new Booking(9, 5, 18,start , end), Booking.class );
        // 2 - Je vérifie que je reçois ma nouvelle instance de Booking
        assertThat(newBooking.getId() == 9);
        assertThat(newBooking.getUserId() == 5);
        assertThat(newBooking.getVehicleId() == 18);
        assertThat(newBooking.getStartDate() instanceof Date);
        assertThat(newBooking.getStartDate().toString().equals("2023-10-12"));
        assertThat(newBooking.getEndDate() instanceof Date);
        assertThat(newBooking.getEndDate().toString().equals("2023-10-19"));
    }

//    @Test
//    // La modification d'une réservation me renvoie ma réservation avec ses nouveaux attributs
//    public void updateBookingReturnsModifiedBooking() throws ParseException {
//        // 1 - Je créer une nouvelle instance de booking
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date start = format.parse("2023-10-12");
//        Date end = format.parse("2023-10-19");
//        Booking newBooking =  restTemplate.put("/booking/update", new Booking(9, 5, 18,start , end), Booking.class );
//        // 2 - Je vérifie que je reçois ma nouvelle instance de Booking
//        assertThat(newBooking.getId() == 9);
//        assertThat(newBooking.getUserId() == 5);
//        assertThat(newBooking.getVehicleId() == 18);
//        assertThat(newBooking.getStartDate() instanceof Date);
//        assertThat(newBooking.getStartDate().toString().equals("2023-10-12"));
//        assertThat(newBooking.getEndDate() instanceof Date);
//        assertThat(newBooking.getEndDate().toString().equals("2023-10-19"));
//    }
}
