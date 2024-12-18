import DriverResponse from "@/dto/driverResponse";
import axios from "axios";
import { API_URL } from "./utils";

const endpoint = "drivers";

export default class DriverApi {
  static async get() {
    return axios.get<DriverResponse[]>(`${API_URL}/${endpoint}`);
  }
}
