package fr.campus.carrental.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookingController {

    @GetMapping("/bookings")
    public String GetAllBookings(){
        return "test booking";
    }
}
