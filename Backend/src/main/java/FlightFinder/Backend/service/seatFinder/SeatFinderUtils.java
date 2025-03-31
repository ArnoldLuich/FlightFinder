package FlightFinder.Backend.service.seatFinder;

import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;

import java.util.Comparator;
import java.util.List;

public class SeatFinderUtils {

    /**
     * Creates a comparator for seat groups based on feature score, row, and seat
     * number.
     *
     * Groups with higher feature scores are preferred. If scores are equal, groups
     * in earlier rows
     * are chosen. If rows are the same, groups with smaller seat numbers are
     * prioritized.
     *
     * @param features The list of desired seat features.
     * @return A comparator for sorting seat groups.
     */
    public static Comparator<List<Seat>> createGroupComparator(List<SeatFeature> features) {
        return Comparator
                .comparingInt((List<Seat> group) -> calculateFeatureScore(group, features))
                .reversed() // Higher scores are better, so reverse the order
                .thenComparing(group -> group.get(0).getRow()) // Prefer groups in earlier rows
                .thenComparingInt(group -> group.get(0).getSeatNumber()); // Prefer groups with smaller seat numbers
    }

    /**
     * Calculates a feature score for a seat group.
     *
     * The score is determined by counting how many desired features are present
     * in the given group of seats.
     *
     * @param group           The list of seats in the group.
     * @param desiredFeatures The list of features to match.
     * @return The total number of desired features found in the group.
     */
    private static int calculateFeatureScore(List<Seat> group, List<SeatFeature> desiredFeatures) {
        return (int) group.stream()
                .flatMap(seat -> seat.getFeatures().stream()) // Collect all seat features
                .filter(desiredFeatures::contains) // Keep only the desired features
                .count(); // Count the matching features
    }
}
