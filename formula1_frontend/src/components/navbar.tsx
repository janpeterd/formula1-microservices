import { Link, NavLink } from "react-router-dom"
import Logo from "../assets/logo.svg"
import GoogleSignInButton from "./google-sign-in"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "./ui/dropdown-menu";
import { Menu } from "lucide-react";
function Navbar() {

  return (
    <div>
      <div className="h-24 flex items-center justify-between p-4 bg-accent text-white font-f1">
        <Link to="/" className="h-full flex items-center">
          <div className="h-full">
            <img src={Logo} alt="Logo" className="h-full w-auto p-1" />
          </div>
          <span className="font-bold text-3xl italic">Grand-Prix Microservices</span>
        </Link>

        <div className="block md:hidden">
          <DropdownMenu>
            <DropdownMenuTrigger>
              <Menu />
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              <DropdownMenuLabel>Pages</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem >
                <NavLink to="/grand-prix" className={({ isActive }) => isActive ? "underline font-bold" : undefined}>Grand Prix</NavLink>
              </DropdownMenuItem>
              <DropdownMenuItem >
                <NavLink to="/drivers" className={({ isActive }) => isActive ? "underline font-bold" : undefined} >Drivers</NavLink>
              </DropdownMenuItem>
              <DropdownMenuLabel>My Account</DropdownMenuLabel>
              <GoogleSignInButton renderButtonElId="navbarSignInButton" />
              <DropdownMenuSeparator />
              <DropdownMenuItem >Log Out</DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuLabel>Admin</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem>
                <Link to="/grand-prix-add">Add Grand Prix</Link>
              </DropdownMenuItem>
              <DropdownMenuItem>
                <Link to="/gp-admin">Manage Grand Prix</Link>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
          <div>
          </div>
        </div>

        <div className="hidden md:flex gap-x-4 justify-center items-center">
          <NavLink to="/grand-prix" className={({ isActive }) => isActive ? "underline font-bold" : undefined}>Grand Prix</NavLink>
          <NavLink to="/drivers" className={({ isActive }) => isActive ? "underline font-bold" : undefined} >Drivers</NavLink>
          <GoogleSignInButton renderButtonElId="navbarSignInButton" />
        </div>
      </div>
    </div>
  )
}

export default Navbar
