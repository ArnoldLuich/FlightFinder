package FlightFinder.Backend.service;

import FlightFinder.Backend.model.Flight;
import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;
import FlightFinder.Backend.repository.FlightRepository;
import FlightFinder.Backend.service.seatFinder.ContiguousSeatFinder;
import FlightFinder.Backend.service.seatFinder.MixedSeatFinder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatServiceImpl implements SeatService {

    private final FlightRepository flightRepository;

    public SeatServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * Retrieves seat recommendations for a given flight.
     *
     * @param flightId         The ID of the flight.
     * @param numSeatsRequired The number of seats required.
     * @param desiredFeatures  The list of desired seat features (can be empty).
     * @return A list of recommended seats.
     */
    @Override
    public List<Seat> getSeatRecommendations(Long flightId, int numSeatsRequired, List<SeatFeature> desiredFeatures) {
        // Return an empty list if the number of seats required is invalid.
        if (numSeatsRequired < 1) {
            return Collections.emptyList();
        }

        // Ensure desiredFeatures is never null
        List<SeatFeature> nonNullDesiredFeatures = (desiredFeatures != null) ? desiredFeatures
                : Collections.emptyList();

        // Fetch the flight and find the best seat group if the flight exists.
        return flightRepository.findById(flightId)
                .map(flight -> {
                    List<Seat> availableSeats = getAvailableSeats(flight);
                    if (availableSeats.size() < numSeatsRequired) {
                        return Collections.<Seat>emptyList();
                    }
                    return findBestSeatGroup(availableSeats, numSeatsRequired, nonNullDesiredFeatures);
                })
                .orElse(Collections.emptyList());
    }

    /**
     * Retrieves the list of available (unoccupied) seats for a given flight.
     *
     * @param flight The flight object.
     * @return A list of available seats.
     */
    private List<Seat> getAvailableSeats(Flight flight) {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : flight.getSeats()) {
            if (!seat.isOccupied()) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    /**
     * Determines the best group of seats based on availability and seat features.
     *
     * @param availableSeats List of available seats.
     * @param numSeats       Number of seats required.
     * @param features       Desired seat features.
     * @return A list of selected best seats.
     */
    private List<Seat> findBestSeatGroup(List<Seat> availableSeats, int numSeats, List<SeatFeature> features) {
        // Attempt to find a contiguous seat group first.
        List<Seat> bestSeats = ContiguousSeatFinder.findBestContiguousSeatGroup(availableSeats, numSeats, features);
        if (!bestSeats.isEmpty()) {
            return bestSeats;
        }

        // If no contiguous group is found, fall back to a mixed seat group.
        bestSeats = MixedSeatFinder.findBestMixedSeatGroup(availableSeats, numSeats, features);
        return bestSeats.isEmpty() ? Collections.emptyList() : bestSeats;
    }
}