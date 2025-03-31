package FlightFinder.Backend.service;

import FlightFinder.Backend.model.Flight;
import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getFlightsWithFilters(String startLocation,
                                       String destination,
                                       LocalDate departureDate,
                                       LocalTime departureTime,
                                       Double minPrice,
                                       Double maxPrice);

    void addFlight();

    void delete(Long id);
}
