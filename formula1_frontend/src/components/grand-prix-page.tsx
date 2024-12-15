import GrandPrixCarousel from "./gp-carousel"
import GrandPrixDetails from "./gp-details"
import GpApi from "@/lib/gp_service";
import GpResponse from "@/dto/gpResponse";
import { useState, useEffect } from "react";
import { motion } from "motion/react";

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


  const handleGpSelect = (gp: GpResponse) => {
    setSelectedGp(gp); // Update selected GP when carousel item is clicked
  };


  return (
    <div className="flex flex-col justify-center gap-y-12">
      <div className="w-[90%] mx-auto">
        <GrandPrixCarousel gps={gps} onGpSelect={handleGpSelect} />
      </div>
      {selectedGp &&
        <motion.div
          key={selectedGp.gpCode} // Ensure a unique key for animation
          initial={{ opacity: 0, x: -50 }} // Start hidden and shifted
          animate={{ opacity: 1, x: 0 }} // Animate to visible and centered
          exit={{ opacity: 0, x: 50 }} // Animate out with a shift
          transition={{ duration: 0.5 }} // Smooth transition
        >
          < GrandPrixDetails gp={selectedGp} />
        </motion.div>
      }
    </div>
  )
}
export default GrandPrixPage
