import DriverCard from "./driver-card";
import GrandPrixStat from "./gp-stat";
import GpResponse from "@/dto/gpResponse";

function GrandPrixDetails({ gp }: { gp: GpResponse }) {

  return (
    <div className="bg-[url()]">
      <h1 className="font-f1 font-bold text-3xl italic py-1">{gp.name} - {gp.country}</h1>
      <div className="flex flex-wrap justify-between items-center border-accent border-r-[12px] border-t-[12px] rounded-tr-2xl p-6 md:p-14 mb-10">
        <div className="h-[500px] w-full lg:w-1/2" >
          <img src={`http://localhost:8084/${gp.trackImageUrl}`} alt={gp.name} className="h-full w-auto object-contain" />
        </div>
        <div className="lg:flex-grow grid grid-cols-1 w-full md:grid-rows-2 md:w-auto md:grid-flow-col gap-4">
          <GrandPrixStat label="Distance in meters" stat={gp.distanceMeters.toString()} />
          <GrandPrixStat label="Country" stat={gp.country} />
          <GrandPrixStat label="Laps" stat={gp.laps.toString()} />
          <GrandPrixStat label="Race date" stat={gp.raceDate.toString()} />
        </div>
      </div>
      <div className="w-full h-6 bg-line-pattern bg-repeat my-16"></div>
      <div className="flex flex-wrap md:flex-nowrap justify-center">
        <div className="p-3">
          <h2 className="font-f1 text-2xl py-6">Description</h2>
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum ullam neque sit magnam quis ipsam dignissimos repellat accusamus, eveniet cumque optio aperiam saepe. Ipsam harum asperiores, quo et similique excepturi.
          </p>
        </div>
        <div className="p-3">
          <h2 className="font-f1 text-2xl py-6">Results</h2>
          <div>
            <span className="text-xl font-f1 text-bold">Winner</span>
            <DriverCard driver={gp.winningDriver} />
          </div>
          <div>
            <span className="text-xl font-f1 text-bold">Second place</span>
            <DriverCard driver={gp.secondDriver} />
          </div>
          <div>
            <span className="text-xl font-f1 text-bold">Third place</span>
            <DriverCard driver={gp.thirdDriver} />
          </div>
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum ullam neque sit magnam quis ipsam dignissimos repellat accusamus, eveniet cumque optio aperiam saepe. Ipsam harum asperiores, quo et similique excepturi.
          </p>
        </div>
      </div>
    </div>
  )

}

export default GrandPrixDetails;
