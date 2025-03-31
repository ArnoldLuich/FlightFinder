package FlightFinder.Backend.service;

import FlightFinder.Backend.model.Flight;
import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;
import FlightFinder.Backend.repository.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * Gets a list of flights based on the provided filters.
     *
     * @param startLocation Starting location for the flight.
     * @param destination   Destination of the flight.
     * @param departureDate Date the flight departs.
     * @param departureTime Time the flight departs.
     * @param minPrice      Minimum price of the flight.
     * @param maxPrice      Maximum price of the flight.
     * @return List of flights that match the provided filters.
     */
    @Override
    public List<Flight> getFlightsWithFilters(
            String startLocation,
            String destination,
            LocalDate departureDate,
            LocalTime departureTime,
            Double minPrice,
            Double maxPrice) {
        Specification<Flight> spec = Specification.where(null);

        if (startLocation != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("startLocation")),
                    "%" + startLocation.toLowerCase() + "%"));
        }

        if (destination != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("destination")),
                    "%" + destination.toLowerCase() + "%"));
        }

        if (departureDate != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("departureDate"), departureDate));
        }

        if (departureTime != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("departureTime"), departureTime));
        }

        if (minPrice != null || maxPrice != null) {
            spec = spec.and((root, query, cb) -> {
                if (minPrice != null && maxPrice != null) {
                    return cb.between(root.get("price"), minPrice, maxPrice);
                } else if (minPrice != null) {
                    return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
                } else {
                    return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
                }
            });
        }

        return flightRepository.findAll(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> getSeatsByFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));
        return flight.getSeats();
    }

    /**
     * Adds a new random flight to the system.
     */
    @Override
    public void addFlight() {
        List<String> cities = Arrays.asList("Liberty City", "Vice City", "Los Santos", "San Fierro", "Las Venturas");

        Flight flight = new Flight();

        // Randomize flight number (using UUID for uniqueness)
        flight.setFlightNumber("AA" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        // Randomize start and destination locations
        Collections.shuffle(cities); // Shuffle the list to randomize start and destination
        flight.setStartLocation(cities.get(0));
        flight.setDestination(cities.get(1));

        // Randomize departure date and time
        LocalDate departureDate = LocalDate.of(2025, ThreadLocalRandom.current().nextInt(1, 13),
                ThreadLocalRandom.current().nextInt(1, 29));
        LocalTime departureTime = LocalTime.of(ThreadLocalRandom.current().nextInt(0, 24),
                ThreadLocalRandom.current().nextInt(0, 60));
        flight.setDepartureDate(departureDate);
        flight.setDepartureTime(departureTime);

        // Randomize price between $100 and $500
        double price = ThreadLocalRandom.current().nextDouble(100, 500);
        flight.setPrice(price);

        List<Seat> seats = new ArrayList<>();

        // Define airplane seat configuration
        int numRows = 6;
        String[] seatColumns = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };

        // Loop through columns first, then rows to arrange seats left to right
        for (String column : seatColumns) {
            for (int row = 1; row <= numRows; row++) {
                Seat seat = new Seat();
                seat.setRow(column);
                seat.setSeatNumber(row);

                // Randomly decide if the seat is occupied (50% chance)
                seat.setOccupied(ThreadLocalRandom.current().nextBoolean());

                // Assign seat features based on position
                Set<SeatFeature> features = new HashSet<>();
                if ((row == 1 || row == 6) &&
                        !column.equals("C") &&
                        !column.equals("F") &&
                        !column.equals("I") &&
                        !column.equals("L")) {
                    features.add(SeatFeature.WINDOW_SEAT); // Window seats
                }

                if (column.equals("A") || column.equals("B") || column.equals("C") || column.equals("D")) {
                    features.add(SeatFeature.MORE_LEGROOM);
                }

                if (row == 3 || row == 4) {
                    features.add(SeatFeature.CLOSE_TO_EXIT);
                }

                seat.setFeatures(features);
                seat.setFlight(flight); // Associate seat with the flight
                seats.add(seat);
            }
        }

        // Assign all seats to the flight
        flight.setSeats(seats);

        // Save the flight (CascadeType.ALL ensures seats are saved too)
        flightRepository.save(flight);
    }
}
