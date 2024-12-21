import { useState, useEffect } from "react";
import DriverResponse from "@/dto/driverResponse";
import DriverCard from "./driver-card";
import DriverApi from "@/lib/driver_service";
import { Input } from "./ui/input";
import { Search } from "lucide-react";
import { motion } from "motion/react";

function DriverPage() {
  const [drivers, setDrivers] = useState<DriverResponse[]>([]);
  const [driverSearch, setDriverSearch] = useState<string>("");

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
    <div className="my-12">
      <h1 className="font-f1 text-5xl my-4 font-bold text-center">F1 Drivers</h1>
      <hr className="w-full my-12" />
      <div className="flex justify-center items-center gap-x-4 my-6 mx-40 bg-bg_accent p-2 rounded-lg">
        <Search />
        <Input placeholder="Search on driver name" onChange={e => { setDriverSearch(e.target.value) }}></Input>
      </div>
      <div className="flex flex-wrap justify-center items-center gap-8">
        {drivers.filter((d) => `${d.firstName} ${d.lastName}`.toLowerCase().includes(driverSearch.toLowerCase())).map((driver) => (

          <motion.div className="w-96" initial={{ opacity: 0, y: 50 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.5 }} >
            <DriverCard key={driver.driverCode} driver={driver} />
          </motion.div>
        ))}
      </div>
    </div >
  )


}

export default DriverPage;
