import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import GrandPrixCard from "./gp-card";
import GpResponse from "@/dto/gpResponse";
import { Skeleton } from "./ui/skeleton";
type GrandPrixCarouselProps = {
  gps: GpResponse[];
  onGpSelect: (gp: GpResponse) => void;
};


function GrandPrixCarousel({ gps, onGpSelect }: GrandPrixCarouselProps) {
  return (
    <Carousel className="w-full h-full">
      <CarouselContent className="-ml-2 md:-ml-4">
        {gps.map((gp) => (
          <CarouselItem
            key={gp.gpCode}
            className="pl-1 md:basis-1/2 lg:basis-1/3 cursor-pointer"
            onClick={() => onGpSelect(gp)} // Notify parent component
          >
            {gp ?
              <GrandPrixCard gpResponse={gp}></GrandPrixCard>
              :
              <Skeleton className="w-[100px] h-[20px] rounded-full" />
            }
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselNext />
      <CarouselPrevious />
    </Carousel>
  )
}

export default GrandPrixCarousel;
