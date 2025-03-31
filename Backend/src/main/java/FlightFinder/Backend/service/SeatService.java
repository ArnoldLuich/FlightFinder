package FlightFinder.Backend.service;


import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;

import java.util.*;


public interface SeatService {
    List<Seat> getSeatRecommendations(Long flightId, int numSeatsRequired, List<SeatFeature> desiredFeatures);
}
