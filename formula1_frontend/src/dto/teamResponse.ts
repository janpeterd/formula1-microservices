import DriverResponse from "./driverResponse";

interface TeamResponse {
  teamCode: string;
  name: string;
  points: number;
  drivers: [DriverResponse];
  imageUrl: string;
  logoUrl: string;
}

export default TeamResponse;
