import { useState, useEffect } from "react";
import DriverResponse from "@/dto/driverResponse";
import DriverApi from "@/lib/driver_service";

function DriverSelect() {
  const [drivers, setDrivers] = useState<DriverResponse[]>([]);
  const [selectedDriver, setSelectedDriver] = useState<DriverResponse>();

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

    <>
      <div className="fixed top-0 left-0 right-0 bottom-0 bg-black/40 backdrop-blur-md flex-col justify-center items-center z-20" >
        <h2 className="absolute top-0 left-0 right-0 text-3xl font-f1 font-bold text-center mt-36">Select a driver</h2>
        <div className="my-52 flex flex-wrap justify-center gap-8 items-center overflow-y-auto max-h-[70vh]">
          {drivers.map((driver) => (
            <div className="relative w-[350px] h-[350px]">
              <img src={`http://localhost:8084${driver.imageUrl}`} alt={`${driver.firstName} ${driver.lastName}`} className="w-full h-full object-cover rounded-lg" />
              <div className="absolute left-0 right-0 bottom-0 bg-black/20 backdrop-blur-md py-4 border-b-0 flex justify-center items-center rounded-b-lg">
                <h2 className="font-f1 text-2xl font-bold italic text-white z-50">{driver.firstName} {driver.lastName}</h2>
              </div>
            </div>
          ))}
        </div>
      </div >
    </>
  )


}

export default DriverSelect
{/* TODO: search */ }
