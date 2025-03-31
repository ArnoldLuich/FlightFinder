package FlightFinder.Backend.controller;

import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;
import FlightFinder.Backend.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/seat-recommendations")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    /**
     * Endpoint to get seat recommendations based on the flight ID, required number of seats, and desired seat features.
     *
     * @param flightId The ID of the flight for which seat recommendations are needed.
     * @param numSeatsRequired The number of seats the user wants to reserve.
     * @param desiredFeatures Optional list of desired seat features (e.g., window seat, more legroom).
     * @return List of recommended seats for the given flight.
     */
    @GetMapping("/{flightId}/")
    public List<Seat> getSeatRecommendations(
            @PathVariable Long flightId,
            @RequestParam int numSeatsRequired,
            @RequestParam(required = false) List<SeatFeature> desiredFeatures
    ) {
        return seatService.getSeatRecommendations(flightId, numSeatsRequired, desiredFeatures);
    }
}
