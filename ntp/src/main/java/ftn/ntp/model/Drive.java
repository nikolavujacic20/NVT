package ftn.ntp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@Entity
@Table(name = "rides")
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private String origin;
    private String destination;

    @Enumerated(EnumType.STRING)
    private RideStatus status; // RideStatus is an enum [ORDERED, IN_PROGRESS, FINISHED, CANCELLED]

    private java.time.LocalDateTime startTime;
    private java.time.LocalDateTime endTime;
    private Double estimatedCost;
    private Double actualCost;

    @ManyToOne
    @JoinColumn(name = "driverId", referencedColumnName = "userId")
    private RegularUser driver;

    @ManyToOne
    @JoinColumn(name = "passengerId", referencedColumnName = "userId")
    private RegularUser passenger;

}