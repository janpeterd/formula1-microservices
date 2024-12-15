
import Logo from "../assets/logo.svg";


function Footer() {
  const year = new Date().getFullYear();
  return (
    <div className="flex items-center justify-between p-8 h-36 border-t border-neutral-200 bg-dark text-white font-f1 mt-12">

      <div className="flex justify-center items-center h-full">
        <img src={Logo} alt="logo" className="h-full w-auto" />
        <h2 className="text-xl font-bold italc">Grand Prix Microservices&#8482;</h2>
      </div>
      <p className="text-neutral-300 font-thin text-sm">&copy;  2023 - {year}</p>
    </div>
  )
}

export default Footer;
