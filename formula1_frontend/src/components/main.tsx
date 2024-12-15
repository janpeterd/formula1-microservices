import GrandPrixForm from "./gp-form";
import "../index.css"
import DriverPage from "./driver-page";
import { Route, Routes } from "react-router-dom";
import GrandPrixPage from "./grand-prix-page";

function Main() {
  return (
    <div className="container mx-auto px-4">
      <Routes>
        <Route path={"/"} element={<GrandPrixPage />} />
        <Route path={"/grand-prix"} element={<GrandPrixPage />} />
        <Route path={"/drivers"} element={<DriverPage />} />
        <Route path={"/grand-prix-add"} element={<GrandPrixForm />} />
      </Routes>

    </div>
  )
}

export default Main;
