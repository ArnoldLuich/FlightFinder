// Define the types of seat features available
export type SeatFeature = 'WINDOW_SEAT' | 'MORE_LEGROOM' | 'CLOSE_TO_EXIT';

// Represents a single seat on a flight
export interface Seat {
  id: number;
  row: string;
  seatNumber: number;
  isOccupied: boolean;
  features: SeatFeature[];
}

// Represents a flight with details and available seats
export interface Flight {
  id: number;
  flightNumber: string;
  startLocation: string;
  destination: string;
  departureDate: string;
  departureTime: string;
  price: number;
  seats: Seat[];
}

// Mapping of seat features to their corresponding emoji
export const featureEmojis: Record<SeatFeature, string> = {
  WINDOW_SEAT: '🪟',
  MORE_LEGROOM: '🦵',
  CLOSE_TO_EXIT: '🚪',
};

// Props for the flight filter component, used to filter available flights
export interface FlightFilterProps {
  filters: {
    startLocation?: string;
    destination?: string;
    departureDate?: string;
    departureTime?: string;
    minPrice?: number;
    maxPrice?: number;
  };
  onFilterChange: (filters: {
    startLocation?: string;
    destination?: string;
    departureDate?: string;
    departureTime?: string;
    minPrice?: number;
    maxPrice?: number;
  }) => void;
}

// Props for a flight card component, used to display flight information
export interface FlightCardProps {
  flight: Flight;
  onClick: () => void;
}

// Props for the seat filters component, used to filter seats based on requirements
export interface SeatFiltersProps {
  numSeatsRequired: number;
  desiredFeatures: SeatFeature[];
  onNumSeatsChange: (num: number) => void;
  onFeaturesChange: (features: SeatFeature[]) => void;
}

// Props for the seat overlay component, used to display seat details in a modal or overlay
export interface SeatOverlayProps {
  flight: Flight | null;
  show: boolean;
  onHide: () => void;
}