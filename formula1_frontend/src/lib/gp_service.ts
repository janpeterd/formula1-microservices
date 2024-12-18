import GpResponse from "@/dto/gpResponse";
import GpRequest from "@/dto/gpRequest";
import axios from "axios";
import { API_URL } from "./utils";
import Cookies from "js-cookie";

const endpoint = "gps";
const idToken = Cookies.get("idToken");

export default class GpApi {
  static async get() {
    return axios.get<GpResponse[]>(`${API_URL}/${endpoint}`);
  }
  static async getByCode(gpCode: string) {
    return axios.get<GpResponse>(`${API_URL}/gp/${gpCode}`);
  }

  static async post(payload: GpRequest) {
    return axios.post(`${API_URL}/${endpoint}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
  static async put(gpCode: string, payload: GpRequest) {
    await axios.put(`${API_URL}/${endpoint}/${gpCode}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
  static async delete(gpCode: string) {
    await axios.delete(`${API_URL}/${endpoint}?gpCode=${gpCode}`, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
}
