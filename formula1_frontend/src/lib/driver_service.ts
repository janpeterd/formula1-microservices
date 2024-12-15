import DriverResponse from "@/dto/driverResponse";
import axios from "axios";

const baseUrl = "http://localhost:8081/api/driver";

export default class DriverApi {
  static async get() {
    return axios.get<DriverResponse[]>(baseUrl);
  }
  static async getbyid(code: string) {
    return axios.get<DriverResponse[]>(baseUrl + "/by-id/" + code);
  }
}
