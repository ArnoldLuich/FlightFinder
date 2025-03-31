import { useState } from 'react';
import { Form, Button, Row, Col, Alert } from 'react-bootstrap';
import { FlightFilterProps } from '../../types/types';

// FlightFilter component allows users to filter flights
export default function FlightFilter({ onFilterChange }: FlightFilterProps) {
  const [startLocation, setStartLocation] = useState('');
  const [destination, setDestination] = useState('');
  const [departureDate, setDepartureDate] = useState('');
  const [departureTime, setDepartureTime] = useState('');
  const [minPrice, setMinPrice] = useState<number | undefined>();
  const [maxPrice, setMaxPrice] = useState<number | undefined>();
  const [error, setError] = useState<string | null>(null);

  // Handles form submission to apply filters
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    // Ensure that minPrice is not greater than maxPrice
    if (minPrice !== undefined && maxPrice !== undefined && minPrice > maxPrice) {
      setError('Min price cannot be greater than max price.');
      return;
    }
    setError(null);

    // Pass filter values to the parent component
    onFilterChange({
      startLocation: startLocation || undefined,
      destination: destination || undefined,
      departureDate: departureDate || undefined,
      departureTime: departureTime || undefined,
      minPrice,
      maxPrice,
    });
  };

  // Resets all filter inputs
  const handleReset = () => {
    setStartLocation('');
    setDestination('');
    setDepartureDate('');
    setDepartureTime('');
    setMinPrice(undefined);
    setMaxPrice(undefined);
    setError(null);
    onFilterChange({});
  };

  return (
    <Form onSubmit={handleSubmit} className="mb-4">
      {error && <Alert variant="danger">{error}</Alert>}
      <Row className="g-2">
        {/* Start Location Input */}
        <Col md={3}>
          <Form.Group controlId="startLocation">
            <Form.Label>From</Form.Label>
            <Form.Control
              type="text"
              placeholder="Departure city"
              value={startLocation}
              onChange={(e) => setStartLocation(e.target.value)}
            />
          </Form.Group>
        </Col>

        {/* Destination Input */}
        <Col md={3}>
          <Form.Group controlId="destination">
            <Form.Label>To</Form.Label>
            <Form.Control
              type="text"
              placeholder="Destination city"
              value={destination}
              onChange={(e) => setDestination(e.target.value)}
            />
          </Form.Group>
        </Col>

        {/* Departure Date Input */}
        <Col md={2}>
          <Form.Group controlId="departureDate">
            <Form.Label>Date</Form.Label>
            <Form.Control
              type="date"
              value={departureDate}
              onChange={(e) => setDepartureDate(e.target.value)}
            />
          </Form.Group>
        </Col>

        {/* Departure Time Input */}
        <Col md={2}>
          <Form.Group controlId="departureTime">
            <Form.Label>Time</Form.Label>
            <Form.Control
              type="time"
              value={departureTime}
              onChange={(e) => setDepartureTime(e.target.value)}
            />
          </Form.Group>
        </Col>

        {/* Minimum Price Input */}
        <Col md={2}>
          <Form.Group controlId="minPrice">
            <Form.Label>Min Price</Form.Label>
            <Form.Control
              type="number"
              placeholder="€ Min"
              value={minPrice ?? ''}
              onChange={(e) =>
                setMinPrice(e.target.value === '' ? undefined : Number(e.target.value))
              }
            />
          </Form.Group>
        </Col>

        {/* Maximum Price Input */}
        <Col md={2}>
          <Form.Group controlId="maxPrice">
            <Form.Label>Max Price</Form.Label>
            <Form.Control
              type="number"
              placeholder="€ Max"
              value={maxPrice ?? ''}
              onChange={(e) =>
                setMaxPrice(e.target.value === '' ? undefined : Number(e.target.value))
              }
            />
          </Form.Group>
        </Col>
      </Row>

      {/* Action Buttons: Reset and Apply Filters */}
      <div className="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
        <Button variant="secondary" onClick={handleReset}>
          Reset Filters
        </Button>
        <Button variant="primary" type="submit">
          Apply Filters
        </Button>
      </div>
    </Form>
  );
}