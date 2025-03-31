import { useState } from 'react';
import { Modal, Badge, Button, Alert } from 'react-bootstrap';
import { SeatFeature, SeatOverlayProps, featureEmojis } from '../../types/types';
import SeatFilters from './SeatFilters';
import { seatService } from '../../services/seatService';

export default function SeatOverlay({ flight, show, onHide }: SeatOverlayProps) {
  const [selectedSeats, setSelectedSeats] = useState<string[]>([]);
  const [numSeatsRequired, setNumSeatsRequired] = useState(1);
  const [desiredFeatures, setDesiredFeatures] = useState<SeatFeature[]>([]);
  const [showNoSeatsModal, setShowNoSeatsModal] = useState(false);

  // Fetch seat recommendations based on user-selected filters
  const applyFilters = async () => {
    if (!flight) return;

    try {
      const seatIds = await seatService.getSeatRecommendations(flight.id, numSeatsRequired, desiredFeatures);
      setSelectedSeats(seatIds.map(String));

      if (seatIds.length === 0) {
        setShowNoSeatsModal(true);
      }
    } catch (error) {
      console.error('Error fetching recommendations:', error);
      setSelectedSeats([]);
      setShowNoSeatsModal(true);
    }
  };

  // Toggle seat selection
  const toggleSeat = (seatId: string) => {
    setSelectedSeats(prev =>
      prev.includes(seatId) ? prev.filter(id => id !== seatId) : [...prev, seatId]
    );
  };

  // If no flight data is available, don't render anything
  if (!flight) return null;

  return (
    <>
      <Modal show={show} onHide={onHide} size="lg" centered>
        <Modal.Header closeButton>
          <Modal.Title>{flight.flightNumber} - Seat Selection</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          {/* Total price display */}
          <div className="mb-3">
            <h4 className="text-end">
              Total Price: <span className="text-primary">
                ${(flight.price * selectedSeats.length).toFixed(2)}
              </span>
            </h4>
          </div>

          {/* Flight details */}
          <div className="mb-3">
            <h5>{flight.startLocation} → {flight.destination}</h5>
            <small className="text-muted">
              Departure: {new Date(flight.departureDate).toLocaleDateString()} • {flight.departureTime.slice(0, 5)}
            </small>
          </div>

          {/* Seat filter selection */}
          <SeatFilters
            numSeatsRequired={numSeatsRequired}
            desiredFeatures={desiredFeatures}
            onNumSeatsChange={setNumSeatsRequired}
            onFeaturesChange={setDesiredFeatures}
          />

          {/* Apply Filters button */}
          <Button variant="primary" onClick={applyFilters} className="w-100 mb-3">
            Apply Filters
          </Button>

          {/* Seat selection grid */}
          <div className="row g-2">
            {flight.seats.map(seat => {
              const seatId = seat.id.toString();
              const isSelected = selectedSeats.includes(seatId);
              const isOccupied = seat.isOccupied;

              return (
                <div className="col-2" key={seatId}>
                  <div
                    className={`card ${isOccupied ? 'bg-secondary text-muted' : isSelected ? 'bg-primary text-white' : 'bg-light'}`}
                    style={{ cursor: isOccupied ? 'not-allowed' : 'pointer' }}
                    onClick={() => !isOccupied && toggleSeat(seatId)}
                  >
                    <div className="card-body py-2">
                      <div className="d-flex justify-content-between align-items-center">
                        <span className="fw-medium">{seat.row}{seat.seatNumber.toString().padStart(2, '0')}</span>
                        <div className="d-flex gap-1">
                          {seat.features.map(feature => (
                            <Badge key={feature} bg="light" text="dark" title={feature.replace('_', ' ')}>
                              {featureEmojis[feature]}
                            </Badge>
                          ))}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </Modal.Body>
      </Modal>

      {/* No seats found modal */}
      <Modal show={showNoSeatsModal} onHide={() => setShowNoSeatsModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>No Seats Available</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Alert variant="warning">No seats match your selected filters. Please adjust your criteria and try again.</Alert>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowNoSeatsModal(false)}>Close</Button>
        </Modal.Footer>
      </Modal>

    </>
  );
}