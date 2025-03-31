package FlightFinder.Backend.service.seatFinder;

import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MixedSeatFinder {
    /**
     * Finds the best mixed seat group considering both row spread and seat features.
     *
     * @param availableSeats List of all available seats.
     * @param numSeats Number of seats required.
     * @param features Desired seat features for prioritization.
     * @return The best group of mixed seats or an empty list if no valid group is found.
     */
    public static List<Seat> findBestMixedSeatGroup(List<Seat> availableSeats, int numSeats, List<SeatFeature> features){
        // Sort the seats by row and seat number.
        List<Seat> sortedSeats = sortSeats(availableSeats);

        // Generate candidate groups.
        List<List<Seat>> candidateGroups = generateCandidateGroups(sortedSeats, numSeats);

        // Select the best candidate group based on spread and features.
        return selectBestCandidateGroup(candidateGroups, features);

    }

    /**
     * Sorts the available seats first by row, then by seat number.
     *
     * @param availableSeats List of available seats.
     * @return A sorted list of seats.
     */
    private static List<Seat> sortSeats(List<Seat> availableSeats) {
        List<Seat> sortedSeats = new ArrayList<>(availableSeats);
        sortedSeats.sort(Comparator.comparing(Seat::getRow)
                .thenComparingInt(Seat::getSeatNumber));
        return sortedSeats;
    }

    /**
     * Generates potential seat groups.
     *
     * @param sortedSeats List of seats sorted by row and seat number.
     * @param numSeats Number of seats required in a group.
     * @return A list of candidate seat groups.
     */
    private static List<List<Seat>> generateCandidateGroups(List<Seat> sortedSeats, int numSeats) {
        List<List<Seat>> candidateGroups = new ArrayList<>();

        for (int i = 0; i <= sortedSeats.size() - numSeats; i++) {
            List<Seat> group = new ArrayList<>(sortedSeats.subList(i, i + numSeats));
            candidateGroups.add(group);
        }

        return candidateGroups;
    }

    /**
     * Selects the best candidate group based on row spread and seat features.
     *
     * @param candidateGroups List of candidate seat groups.
     * @param features Desired seat features for prioritization.
     * @return The best candidate seat group, or an empty list if no suitable group is found.
     */
    private static List<Seat> selectBestCandidateGroup(List<List<Seat>> candidateGroups, List<SeatFeature> features) {
        Comparator<List<Seat>> crossRowComparator = Comparator
                .comparingInt(MixedSeatFinder::calculateSpread) // Prioritize groups with minimal spread.
                .thenComparing(SeatFinderUtils.createGroupComparator(features)); // Further refine based on seat features.

        return candidateGroups.stream()
                .min(crossRowComparator)
                .orElse(Collections.emptyList());
    }

    /**
     * Calculates a "spread" metric for a group of seats.
     *
     * The spread is defined as the sum of:
     * - The row spread: difference between the max and min row (using ASCII values of row letters).
     * - The seat spread: difference between the max and min seat numbers.
     * A smaller spread indicates that the seats are closer together.
     *
     * @param group The group of seats to calculate the spread for.
     * @return The calculated spread value.
     */
    private static int calculateSpread(List<Seat> group) {
        int minRow = group.stream()
                .mapToInt(seat -> seat.getRow().charAt(0)) // Convert row letters to ASCII values
                .min().orElse(0);
        int maxRow = group.stream()
                .mapToInt(seat -> seat.getRow().charAt(0))
                .max().orElse(0);
        int rowSpread = maxRow - minRow;

        int minSeat = group.stream()
                .mapToInt(Seat::getSeatNumber)
                .min().orElse(0);
        int maxSeat = group.stream()
                .mapToInt(Seat::getSeatNumber)
                .max().orElse(0);
        int seatSpread = maxSeat - minSeat;

        return rowSpread + seatSpread; // Total spread is the sum of row and seat spreads
    }
}