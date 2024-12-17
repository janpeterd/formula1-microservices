import TeamResponse from "@/dto/teamResponse";
//import Cookies from "js-cookie";
import axios from "axios";
import { API_URL } from "./utils";

const endpoint = "teams";
//const idToken = Cookies.get("idToken");

export default class TeamApi {
  //static async get() {
  //  return axios.get<TeamResponse[]>(`${API_URL}/${endpoint}`, {
  //    headers: {
  //      Authorization: `Bearer ${idToken}`,
  //    },
  //  });
  //}
  static async get() {
    return axios.get<TeamResponse[]>(`${API_URL}/${endpoint}`);
  }
}
