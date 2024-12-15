import DriverResponse from "./driverResponse";
import TeamResponse from "./teamResponse";

interface GpResponse {
  gpCode: string;
  name: string;
  country: string;
  city: string;
  distanceMeters: number;
  laps: number;
  winningDriver: DriverResponse;
  secondDriver: DriverResponse;
  thirdDriver: DriverResponse;
  winningTeam: TeamResponse;
  raceDate: Date;
  imageUrl: string;
}

export default GpResponse;
