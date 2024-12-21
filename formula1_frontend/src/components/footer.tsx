
import Logo from "../assets/logo.svg";


function Footer() {
  const year = new Date().getFullYear();
  return (
    <div className="flex items-center justify-between p-4 h-24 bg-dark text-white font-f1">
      <div className="flex justify-center items-center h-full">
        <img src={Logo} alt="logo" className="h-full w-auto p-1" />
        <h2 className="text-lg md:text-3xl font-bold italic">Grand Prix Microservices&#8482;</h2>
      </div>
      <p className="text-neutral-300 font-thin text-xs">&copy;  2023 - {year}</p>
    </div>
  )
}

export default Footer;
