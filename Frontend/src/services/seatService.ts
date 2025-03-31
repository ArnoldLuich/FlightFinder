import axios from 'axios';
import qs from 'qs';
import { SeatFeature } from '../types/types';

// Function to get seat recommendations based on flight, required seats, and desired features
export const seatService = {
  getSeatRecommendations: async (
    flightId: number,
    numSeatsRequired: number,
    desiredFeatures: SeatFeature[]
  ) => {
    const response = await axios.get(
      `http://localhost:8080/seat-recommendations/${flightId}/`,
      {
        params: {
          numSeatsRequired,
          desiredFeatures: desiredFeatures.length ? desiredFeatures : undefined,
        },
        paramsSerializer: params => qs.stringify(params, { arrayFormat: 'repeat' }),
      }
    );

    // Return the seat IDs from the response
    return response.data.map((seat: any) => seat.id);
  }
};