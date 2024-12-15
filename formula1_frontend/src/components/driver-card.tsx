import DriverResponse from "@/dto/driverResponse";

function DriverCard({ driver }: { driver: DriverResponse }) {
  return (
    <div key={driver.driverCode} className="relative w-96 h-[350px] border-accent border-r-[12px] border-t-[12px] rounded-tr-2xl p-2 flex flex-col gap-y-4 justify-center items-center font-f1 group overflow-hidden hover:scale-105 transition-all">
      <div className="text-2xl absolute bottom-2 left-0 right-0 z-20 p-2 text-white bg-black/80 mx-2 w-auto font-bold text-center rounded-b-lg group-hover:underline">{driver.firstName} {driver.lastName}</div>
      <img src={`http://localhost:8084/${driver.imageUrl}`} alt="driver image" className="rounded-lg w-full h-full object-cover" />
    </div >
  )
}

export default DriverCard;
