package FlightFinder.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "seats")
@Setter
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String row;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private int seatNumber;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private boolean isOccupied;

    @ElementCollection(targetClass = SeatFeature.class)
    // creates a separate table seat_features to store seat attributes.
    @CollectionTable(name = "seat_features", joinColumns = @JoinColumn(name = "seat_id"))
    @Enumerated(EnumType.STRING) // stores enum values as readable text instead of numbers.
    @Column(name = "feature")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Set<SeatFeature> features;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference
    private Flight flight;
}
