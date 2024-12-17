import { ChevronRight } from "lucide-react";
import { Button } from "./ui/button";
import { Link } from "react-router-dom";

function HomePage() {
  return (
    <div className="flex justify-center items-center w-full min-h-[75vh] bg-[url('/home.jpg')] bg-no-repeat bg-cover bg-center">
      <div className="w-full h-full backdrop-brightness-50 backdrop-saturate-150">
        <div className="flex flex-col justify-center w-full h-lvh px-48">
          <h1 className="font-f1 text-5xl font-bold italic text-left text-white">Grand Prix Mircoservices</h1>
          <p className="text-white text-xl py-4">
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
