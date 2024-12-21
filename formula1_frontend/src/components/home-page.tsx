import { ChevronRight } from "lucide-react";
import { Button } from "./ui/button";
import { Link } from "react-router-dom";

function HomePage() {
  return (
    <div className="absolute top-24 bottom-24 flex justify-center items-center w-full bg-[url('/home.jpg')] bg-no-repeat bg-cover bg-center">
      <div className="w-full h-full backdrop-brightness-50 backdrop-saturate-150">
        <div className="flex flex-col justify-center items-center md:items-stretch w-full h-full max-h-screen px-6 md:px-48">
          <h1 className="text-center md:text-left font-f1 text-5xl font-bold italic text-white">Grand Prix Microservices</h1>
          <p className="text-white text-xl py-4 ms-4 text-center md:text-left">
            Explore Grand Prix data and discover your favorite drivers!
          </p>
          <Button asChild className="max-w-[140px] my-8 bg-accent font-bold">
            <Link to="/grand-prix">
              Get Started
              <ChevronRight />
            </Link>
          </Button>
        </div>
      </div>
    </div>
  )
}

export default HomePage;
