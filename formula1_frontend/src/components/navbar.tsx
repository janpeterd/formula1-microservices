import { Link } from "react-router-dom"
import Logo from "../assets/logo.svg"
function Navbar() {
  return (
    <div className="h-24 flex items-center justify-between p-4 border-b border-neutral-400 mb-8 bg-accent text-white font-f1">
      <Link to="/" className="h-full flex items-center">
        <div className="h-full">
          <img src={Logo} alt="Logo" className="h-full w-auto p-1" />
        </div>
        <span className="font-bold text-3xl italic">Grand-Prix Microservices</span>
      </Link>
      <ul className="flex gap-x-4">
        <li><Link to="/grand-prix">View Grand Prix</Link></li>
        <li><Link to="/grand-prix-add">Add Grand Prix</Link></li>
        <li><Link to="/drivers">Driver Overview</Link></li>
      </ul>
    </div >
  )

}

export default Navbar
