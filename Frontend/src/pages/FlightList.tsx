import { useEffect, useState } from 'react';
import FlightCard from '../components/flights/FlightCard';
import SeatOverlay from '../components/seats/SeatOverlay';
import { Flight } from '../types/types';
import Spinner from 'react-bootstrap/Spinner';
import Alert from 'react-bootstrap/Alert';
import { fetchFlights } from '../services/flightService';
import FlightFilter from '../components/flights/FlightFilter';

export default function FlightList() {
  const [flights, setFlights] = useState<Flight[]>([]);
  const [selectedFlight, setSelectedFlight] = useState<Flight | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filters, setFilters] = useState<{
    startLocation?: string;
    destination?: string;
    departureDate?: string;
    departureTime?: string;
    minPrice?: number;
    maxPrice?: number;
  }>({});

  // Fetch flights whenever filters change
  useEffect(() => {
    const loadFlights = async () => {
      try {
        const data = await fetchFlights(filters);
        setFlights(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to load flights');
      } finally {
        setLoading(false);
      }
    };

    loadFlights();
  }, [filters]);

  // Show loading spinner when data is being fetched
  if (loading) return (
    <div className="text-center mt-5">
      <Spinner animation="border" role="status">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    </div>
  );

  // Display error message if there was an issue fetching the data
  if (error) return (
    <Alert variant="danger" className="mt-4">
      Error loading flights: {error}
    </Alert>
  );

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Available Flights</h1>

      {/* Flight filter component */}
      <FlightFilter onFilterChange={setFilters} />

      {/* Flight cards list */}
      <div className="flight-list">
        {flights.map((flight) => (
          <FlightCard
            key={flight.id}
            flight={flight}
            onClick={() => setSelectedFlight(flight)}
          />
        ))}
      </div>

      {/* Seat selection overlay for the selected flight */}
      <SeatOverlay
        key={selectedFlight?.id}
        flight={selectedFlight}
        show={!!selectedFlight}
        onHide={() => setSelectedFlight(null)}
      />
    </div>
  );
}