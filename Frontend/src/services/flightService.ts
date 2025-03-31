import axios from "axios";
import { Flight, Seat } from "../types/types";

export const flightService = {
  async fetchFlights(params?: Record<string, any>): Promise<Flight[]> {
    try {
      const { data } = await axios.get<Flight[]>("http://localhost:8080/flight/filter", { params });
      return data;
    } catch (error) {
      throw new Error(
        axios.isAxiosError(error) && error.response?.data?.message ? error.response.data.message : "An unexpected error occurred"
      );
    }
  },

  async fetchSeatsByFlight(flightId: number): Promise<Seat[]> {
    try {
      const { data } = await axios.get<Seat[]>(`http://localhost:8080/flight/${flightId}/seats`);
      return data;
    } catch (error) {
      throw new Error(
        axios.isAxiosError(error) && error.response?.data?.message ? error.response.data.message : "An unexpected error occurred"
      );
    }
  },

  async addFlight(): Promise<void> {
    try {
      await axios.get("http://localhost:8080/flight/add");
    } catch (error) {
      throw new Error(
        axios.isAxiosError(error) && error.response?.data?.message ? error.response.data.message : "An unexpected error occurred"
      );
    }
  },
};