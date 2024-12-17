import { BrowserRouter } from "react-router-dom"
import "./index.css"

import Main from "./components/main";
import Navbar from "./components/navbar";
import Footer from "./components/footer";
import { RecoilRoot } from "recoil";

function App() {
  return (
    <BrowserRouter>
      <RecoilRoot>
        <div className="min-h-screen">
          <Navbar />
          <Main />
          <Footer />
        </div>
      </RecoilRoot>
    </BrowserRouter>
  )
}
export default App;
