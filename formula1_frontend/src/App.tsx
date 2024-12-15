import { BrowserRouter } from "react-router-dom"
import "./index.css"

import Main from "./components/main";
import Navbar from "./components/navbar";
import Footer from "./components/footer";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Main />
      <Footer />
    </BrowserRouter>
  )
}
export default App;
