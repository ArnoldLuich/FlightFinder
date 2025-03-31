package FlightFinder.Backend.controller;

import FlightFinder.Backend.model.Flight;
import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;
import FlightFinder.Backend.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Endpoint to get flights based on various filter criteria.
     *
     * @param startLocation Optional start location of the flight.
     * @param destination Optional destination of the flight.
     * @param departureDate Optional departure date of the flight.
     * @param departureTime Optional departure time of the flight.
     * @param minPrice Optional minimum price of the flight.
     * @param maxPrice Optional maximum price of the flight.
     * @return List of flights that match the provided filters.
     */
    @GetMapping("/filter")
    public List<Flight> getFlightsByFilter(
            @RequestParam(required = false) String startLocation,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime departureTime,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return flightService.getFlightsWithFilters(
                startLocation,
                destination,
                departureDate,
                departureTime,
                minPrice,
                maxPrice
        );
    }

    /**
     * Endpoint to add a new random flight to the system.
     */
    @GetMapping("/add")
    public void addFlight() {
        flightService.addFlight();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightService.delete(id);
    }
}