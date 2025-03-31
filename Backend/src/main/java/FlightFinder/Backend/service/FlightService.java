package FlightFinder.Backend.service;

import FlightFinder.Backend.model.Flight;
import FlightFinder.Backend.model.Seat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FlightService {
    List<Flight> getFlightsWithFilters(String startLocation,
            String destination,
            LocalDate departureDate,
            LocalTime departureTime,
            Double minPrice,
            Double maxPrice);

    List<Seat> getSeatsByFlight(Long id);

    void addFlight();
}
