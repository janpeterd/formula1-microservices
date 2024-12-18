import TeamResponse from "@/dto/teamResponse";
import axios from "axios";
import { API_URL } from "./utils";

const endpoint = "teams";

export default class TeamApi {
  static async get() {
    return axios.get<TeamResponse[]>(`${API_URL}/${endpoint}`);
  }
  static async getByCode(teamCode: string) {
    return axios.get<TeamResponse>(`${API_URL}/team/${teamCode}`);
  }
}
