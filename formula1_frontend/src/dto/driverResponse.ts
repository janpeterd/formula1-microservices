import TeamResponse from "./teamResponse";

interface DriverResponse {
  driverCode: string;
  teamCode: TeamResponse;
  firstName: string;
  lastName: string;
  country: string;
  seasonPoints: number;
  imageUrl: string;

  toString(): string;
}

export default DriverResponse;
