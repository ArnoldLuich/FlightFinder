import axios from 'axios';
import { Flight } from '../types/types';

// Function to fetch flights from the API with optional filter parameters
export const fetchFlights = async (params?: Record<string, any>): Promise<Flight[]> => {
  try {
    const { data } = await axios.get<Flight[]>('http://localhost:8080/flight/filter', { params });
    return data;
  } catch (error) {
    const errorMessage = axios.isAxiosError(error) && error.response?.data?.message || 'An unexpected error occurred';
    throw new Error(errorMessage);
  }
};