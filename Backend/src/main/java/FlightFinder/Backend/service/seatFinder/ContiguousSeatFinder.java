package FlightFinder.Backend.service.seatFinder;

import FlightFinder.Backend.model.Seat;
import FlightFinder.Backend.model.SeatFeature;

import java.util.*;

public class ContiguousSeatFinder {

    /**
     * Finds the best contiguous group of seats that match the given number of seats and desired features.
     *
     * @param availableSeats List of all available seats.
     * @param numSeats Number of seats required.
     * @param features Desired seat features for prioritization.
     * @return The best contiguous group of seats or an empty list if no valid group is found.
     */
    public static List<Seat> findBestContiguousSeatGroup(List<Seat> availableSeats, int numSeats, List<SeatFeature> features) {
        // Group available seats by their row.
        Map<String, List<Seat>> seatsByRow = new HashMap<>();
        for (Seat seat : availableSeats) {
            seatsByRow.computeIfAbsent(seat.getRow(), r -> new ArrayList<>()).add(seat);
        }

        // Find all valid contiguous seat groups within each row.w.
        List<List<Seat>> allGroupsSameRow = findContiguousGroups(seatsByRow, numSeats);

        // SSelect the best seat group based on the given features.
        return selectBestGroup(allGroupsSameRow, features);
    }

    /**
     * Finds all valid contiguous groups of seats in each row.
     *
     * @param seatsByRow Map of row names to lists of available seats in that row.
     * @param numSeats Number of seats required in a group.
     * @return A list of valid contiguous seat groups.
     */
    private static List<List<Seat>> findContiguousGroups(Map<String, List<Seat>> seatsByRow, int numSeats) {
        List<List<Seat>> allGroupsSameRow = new ArrayList<>();
        for (Map.Entry<String, List<Seat>> entry : seatsByRow.entrySet()) {
            List<Seat> rowSeats = entry.getValue();

            // Ensure there are enough seats in the row to form a group.
            if (rowSeats.size() >= numSeats) {
                rowSeats.sort(Comparator.comparingInt(Seat::getSeatNumber));

                // Find all contiguous groups of the required size.
                for (int i = 0; i <= rowSeats.size() - numSeats; i++) {
                    List<Seat> group = rowSeats.subList(i, i + numSeats);

                    if (isContiguousGroup(group, numSeats)) {
                        allGroupsSameRow.add(new ArrayList<>(group));
                    }
                }
            }
        }
        return allGroupsSameRow;
    }

    /**
     * Checks if a given group of seats is contiguous.
     *
     * @param group The group of seats to check.
     * @param numSeats The expected number of seats in the group.
     * @return True if the seats form a contiguous group, false otherwise.
     */
    private static boolean isContiguousGroup(List<Seat> group, int numSeats) {
        if (group.isEmpty() || group.size() != numSeats) {
            return false; // A valid group must contain exactly the required number of seats.
        }

        int firstSeat = group.get(0).getSeatNumber();
        int lastSeat = group.get(numSeats - 1).getSeatNumber();

        // A contiguous group must have no gaps between the first and last seat numbers.
        return (lastSeat - firstSeat) == (numSeats - 1);
    }

    /**
     * Selects the best seat group from the available contiguous seat groups based on the given features.
     *
     * @param allGroupsSameRow List of valid contiguous seat groups.
     * @param features Desired seat features for prioritization.
     * @return The best seat group, or an empty list if no suitable group is found.
     */
    private static List<Seat> selectBestGroup(List<List<Seat>> allGroupsSameRow, List<SeatFeature> features) {
        if (allGroupsSameRow.isEmpty()) {
            return new ArrayList<>();
        }

        Comparator<List<Seat>> groupComparator = SeatFinderUtils.createGroupComparator(features);
        return allGroupsSameRow.stream().min(groupComparator).orElse(new ArrayList<>());
    }
}
