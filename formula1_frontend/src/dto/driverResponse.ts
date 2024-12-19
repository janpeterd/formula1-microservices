interface DriverResponse {
  driverCode: string;
  teamCode: string;
  firstName: string;
  lastName: string;
  country: string;
  seasonPoints: number;
  imageUrl: string;

  toString(): string;
}

export default DriverResponse;
