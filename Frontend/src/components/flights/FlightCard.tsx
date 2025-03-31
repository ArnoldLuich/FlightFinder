import { FlightCardProps } from '../../types/types';

// FlightCard component displays flight details in a card format.
// Clicking the card triggers the onClick handler.
export default function FlightCard({ flight, onClick }: FlightCardProps) {
  return (
    <div className="card mb-3" onClick={onClick} style={{ cursor: 'pointer' }}>
      <div className="card-body d-flex justify-content-between align-items-center">
        <h5 className="mb-0">{flight.flightNumber}</h5>

        <div>{flight.startLocation} → {flight.destination}</div>

        <div className="text-muted">
          {new Date(flight.departureDate).toLocaleDateString()} • {flight.departureTime.slice(0, 5)}
        </div>

        <span className="h4 text-primary">€{flight.price.toFixed(2)}</span>
      </div>
    </div>
  );
}