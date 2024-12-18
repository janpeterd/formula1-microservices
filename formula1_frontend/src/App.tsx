import { BrowserRouter } from "react-router-dom"
import "./index.css"

import Main from "./components/main";
import Navbar from "./components/navbar";
import Footer from "./components/footer";
import { RecoilRoot } from "recoil";
import { Toaster } from "./components/ui/toaster";

function App() {
  return (
    <BrowserRouter>
      <RecoilRoot>
        <div className="flex flex-col justify-between min-h-screen">
          <Navbar />
          <Main />
          <Toaster />
          <Footer />
        </div>
      </RecoilRoot>
    </BrowserRouter>
  )
}
export default App;
