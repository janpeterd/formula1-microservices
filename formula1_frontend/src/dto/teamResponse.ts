import DriverResponse from "./driverResponse";

interface TeamResponse {
  teamCode: string;
  name: string;
  points: number;
  drivers: [DriverResponse];
}

export default TeamResponse;
