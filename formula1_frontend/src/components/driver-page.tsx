import { useState, useEffect } from "react";
import DriverResponse from "@/dto/driverResponse";
import DriverCard from "./driver-card";
import DriverApi from "@/lib/driver_service";

function DriverPage() {
  const [drivers, setDrivers] = useState<DriverResponse[]>([]);

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await DriverApi.get();
        setDrivers(response.data);
      } catch (err) {
        console.error("Failed to fetch drivers from API: ", err);
      }
    };

    fetchDrivers();
  }, []);


  return (
    <div>
      <h1 className="font-f1 text-4xl my-4 font-bold text-center">F1 Drivers</h1>
      <hr className="w-full my-12" />
      <div className="flex flex-wrap justify-center items-center gap-8">
        {drivers.map((driver) => (
          <DriverCard key={driver.driverCode} driver={driver} />
        ))}
      </div>
    </div>
  )


}

export default DriverPage;
