import { Link, NavLink } from "react-router-dom"
import Logo from "../assets/logo.svg"
import GoogleSignInButton from "./google-sign-in"
function Navbar() {
  return (
    <div className="h-24 flex items-center justify-between p-4 bg-accent text-white font-f1">
      <Link to="/" className="h-full flex items-center">
        <div className="h-full">
          <img src={Logo} alt="Logo" className="h-full w-auto p-1" />
        </div>
        <span className="font-bold text-3xl italic">Grand-Prix Microservices</span>
      </Link>
      <div className="flex gap-x-4 justify-center items-center">
        <NavLink to="/grand-prix" className={({ isActive }) => isActive ? "underline font-bold" : undefined}>Grand Prix</NavLink>
        <NavLink to="/drivers" className={({ isActive }) => isActive ? "underline font-bold" : undefined} >Drivers</NavLink>
        <GoogleSignInButton renderButtonElId="navbarSignInButton" />
      </div>
    </div >
  )

}

export default Navbar
