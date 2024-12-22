import GpResponse from "@/dto/gpResponse";
import GpRequest from "@/dto/gpRequest";
import axios from "axios";
import { API_URL } from "./utils";
import Cookies from "js-cookie";

const endpoint = "gps";

export default class GpApi {
  static async get() {
    return axios.get<GpResponse[]>(`${API_URL}/${endpoint}`);
  }
  static async getByCode(gpCode: string) {
    return axios.get<GpResponse>(`${API_URL}/gp/${gpCode}`);
  }

  static async post(payload: GpRequest) {
    const idToken = Cookies.get("idToken");
    return axios.post(`${API_URL}/${endpoint}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
  static async put(gpCode: string, payload: GpRequest) {
    const idToken = Cookies.get("idToken");
    await axios.put(`${API_URL}/${endpoint}/${gpCode}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
  static async delete(gpCode: string) {
    const idToken = Cookies.get("idToken");
    await axios.delete(`${API_URL}/${endpoint}?gpCode=${gpCode}`, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    });
  }
}
