import DriverResponse from "@/dto/driverResponse";
import GrandPrixStat from "./gp-stat";
import TeamInfoSmall from "./team-info-small";
import { Skeleton } from "./ui/skeleton";
import { useState } from "react";
import { API_URL } from "@/lib/utils";

function DriverCard({ driver }: { driver: DriverResponse }) {
  const [imageUrlHasError, setImageUrlHasError] = useState<boolean>(false);
  return (
    <div>
      <div key={driver.driverCode} className="relative w-96 h-[350px] border-accent border-r-[12px] border-t-[12px] rounded-tr-2xl p-2 flex flex-col gap-y-4 justify-center items-center font-f1 group overflow-hidden hover:-translate-y-1 transition-all">
        <div className="text-2xl absolute bottom-2 left-0 right-0 z-20 p-2 text-white bg-black/80 mx-2 w-auto font-bold text-center rounded-b-lg group-hover:underline">{driver.firstName} {driver.lastName}</div>
        {
          imageUrlHasError
            ?
            <Skeleton className="w-full h-full bg-line-pattern" />
            :
            <img src={`${API_URL}/${driver.imageUrl}`} alt="driver image" className="rounded-lg w-full h-full object-cover" onError={() => setImageUrlHasError(true)} />
        }
      </div >
      <GrandPrixStat label="Country" stat={driver.country} />
      <GrandPrixStat label="Points this season" stat={driver.seasonPoints.toString()} />
      <TeamInfoSmall teamCode={driver.teamCode} />
    </div>
  )
}

export default DriverCard;
