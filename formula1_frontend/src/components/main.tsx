import GrandPrixForm from "./gp-form";
import "../index.css"
import DriverPage from "./driver-page";
import { Route, Routes } from "react-router-dom";
import GrandPrixPage from "./grand-prix-page";
import Secure from "./secure";
import GrandPrixAdminPage from "./gp-admin";
import HomePage from "./home-page";

function Main() {
  return (
    <div>
      <Routes>
        <Route path={"/"} element={<HomePage />} />
        <Route path={"/grand-prix"} element={
          <div className="container mx-auto mt-8">
            <GrandPrixPage />
          </div>
        } />
        <Route path={"/drivers"} element={
          <div className="container mx-auto mt-8"><DriverPage /></div>
        } />
        <Route path={"/gp-admin"} element={
          <div className="mt-8">
            <Secure>
              <GrandPrixAdminPage />
            </Secure>
          </div>
        } />
        <Route
          path="/grand-prix-add/:gpCode?"
          element={
            <div className="container mx-auto my-8">
              <Secure>
                <GrandPrixForm />
              </Secure>
            </div>
          }
        />
      </Routes>
    </div>
  )
}

export default Main;
