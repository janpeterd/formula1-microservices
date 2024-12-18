import DriverCard from "./driver-card";
import GrandPrixStat from "./gp-stat";
import GpResponse from "@/dto/gpResponse";

function GrandPrixDetails({ gp }: { gp: GpResponse }) {

  return (
    <div className="my-10">
      <h1 className="font-f1 font-bold text-3xl italic py-1">{gp.name} - {gp.country}</h1>
      <div className="flex flex-wrap justify-between items-center border-accent border-r-[12px] border-t-[12px] rounded-tr-2xl p-6 md:p-14 mb-10 gap-x-10 gap-y-4">
        <div className="max-h-[500px] lg:max-w-[550px] w-full lg:w-1/2 mx-auto" >
          <img src={`http://localhost:8084/${gp.trackImageUrl}`} alt={gp.name} className="h-auto w-full object-contain rounded-xl" />
        </div>
        <div className="lg:flex-grow grid grid-cols-1 w-full md:grid-rows-2 md:w-auto md:grid-flow-col gap-4">
          <GrandPrixStat label="Distance in meters" stat={gp.distanceMeters.toString()} />
          <GrandPrixStat label="Country" stat={gp.country} />
          <GrandPrixStat label="Laps" stat={gp.laps.toString()} />
          <GrandPrixStat label="Race date" stat={gp.raceDate.toString()} />
        </div>
        <div className="p-3">
          <h2 className="font-f1 text-3xl py-6">Description</h2>
          <p className="leading-7 [&:not(:first-child)]:mt-6">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum ullam neque sit magnam quis ipsam dignissimos repellat accusamus, eveniet cumque optio aperiam saepe. Ipsam harum asperiores, quo et similique excepturi.
          </p>
        </div>
      </div>
      <div className="w-full h-6 bg-line-pattern bg-repeat my-16"></div>
      <div className="flex gap-12 justify-center">
        <div className="p-3">
          <h2 className="font-f1 text-5xl py-6 text-center">Results</h2>
          <div className="flex flex-col justify-center items-center gap-y-6">
            <div>
              <span className="text-2xl font-f1 text-bold">Winner</span>
              <DriverCard driver={gp.winningDriver} />
            </div>
            <div className="flex justify-center items-center gap-x-4 lg:gap-x-48 flex-wrap md:flex-nowrap">
              <div>
                <span className="text-xl font-f1 text-bold">Second place</span>
                <DriverCard driver={gp.secondDriver} />
              </div>
              <div>
                <span className="text-xl font-f1 text-bold">Third place</span>
                <DriverCard driver={gp.thirdDriver} />
              </div>
            </div>
          </div>
        </div>
        <div className="hidden md:block text-5xl text-center w-1/3 bg-line-pattern" />
      </div>
    </div>
  )

}

export default GrandPrixDetails;
