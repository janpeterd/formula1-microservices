import GpResponse from "@/dto/gpResponse";
import { API_URL } from "@/lib/utils";

function GrandPrixCard({ gpResponse }: { gpResponse: GpResponse }) {
  return (
    <div className="relative flex flex-col justify-center items-center group hover:-translate-y-1 transition-all">
      <img src={`${API_URL}/${gpResponse.imageUrl}`} alt={gpResponse.name} className="h-full w-full object-cover rounded-lg cursor-pointer aspect-[16/9]" />
      <div className="absolute bottom-0 left-0 right-0 top-0 bg-black/50 group-hover:bg-black/30 group-hover:saturate-200 py-4 group-hover:backdrop-blur-sm flex justify-center items-center rounded-lg">
        <h1 className="text-3xl font-f1 text-center text-white font-bold ">{gpResponse.name}</h1>
      </div>
    </div>
  )
}

export default GrandPrixCard;
