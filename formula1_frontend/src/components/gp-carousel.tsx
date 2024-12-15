import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import GrandPrixCard from "./gp-card";
import GpResponse from "@/dto/gpResponse";

function GrandPrixCarousel({ gps }: { gps: GpResponse[] }) {
  return (
    <Carousel className="w-full h-full">
      <CarouselContent className="-ml-2 md:-ml-4">
        {gps.map((gp) => (
          <CarouselItem key={gp.gpCode} className="pl-1 md:basis-1/2 lg:basis-1/3">
            <GrandPrixCard gpResponse={gp}></GrandPrixCard>
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselNext />
      <CarouselPrevious />
    </Carousel>
  )
}

export default GrandPrixCarousel;
