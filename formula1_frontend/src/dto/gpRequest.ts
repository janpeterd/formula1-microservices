interface GpRequest {
  name: string;
  country: string;
  city: string;
  distanceMeters: number;
  laps: number;
  winningDriverCode: string;
  secondDriverCode: string;
  thirdDriverCode: string;
  winningTeamCode: string;
  raceDate: Date;
  imageUrl: string;
  trackImageUrl: string;
}

export default GpRequest;
