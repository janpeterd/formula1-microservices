import GpResponse from "@/dto/gpResponse";
import axios from "axios";

const baseUrl = "http://localhost:8082/api/gp";

export default class GpApi {
  static async get() {
    return axios.get(baseUrl);
  }
  static async getbyid(code: string) {
    return axios.get<GpResponse[]>(baseUrl + "/by-id/" + code);
  }
}
