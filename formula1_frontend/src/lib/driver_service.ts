import DriverResponse from "@/dto/driverResponse";
import axios from "axios";
//import Cookies from "js-cookie";
import { API_URL } from "./utils";

const endpoint = "drivers";
//const idToken = Cookies.get("idToken");

export default class DriverApi {
  //static async get() {
  //  return axios.get<DriverResponse[]>(`${API_URL}/${endpoint}`, {
  //    httpsAgent: false, // Disable HTTPS
  //    withCredentials: false,
  //    headers: {
  //      Authorization: `Bearer ${idToken}`,
  //    },
  //  });
  //}
  static async get() {
    return axios.get<DriverResponse[]>(`${API_URL}/${endpoint}`);
  }
}
