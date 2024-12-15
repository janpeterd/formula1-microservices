import GrandPrixCarousel from "./gp-carousel"
import GrandPrixDetails from "./gp-details"
import GpApi from "@/lib/gp_service";
import GpResponse from "@/dto/gpResponse";
import { useState, useEffect } from "react";

function GrandPrixPage() {
  const [gps, setGps] = useState<GpResponse[]>([]);
  const [selectedGp, setSelectedGp] = useState<GpResponse>();

  useEffect(() => {
    const fetchGps = async () => {
      try {
        const response = await GpApi.get();
        setGps(response.data);
        setSelectedGp(response.data[0])

      } catch (err) {
        console.error("Failed to fetch gps from API: ", err);
      }
    };

    fetchGps();
  }, []);

  return (
    <div className="flex flex-col justify-center gap-y-12">
      <div className="w-[90%] mx-auto">
        <GrandPrixCarousel gps={gps} />
      </div>
      {selectedGp &&
        < GrandPrixDetails gp={selectedGp} />
      }
    </div>
  )
}
export default GrandPrixPage
