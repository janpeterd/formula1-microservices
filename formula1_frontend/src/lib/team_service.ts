import TeamResponse from "@/dto/teamResponse";
import axios from "axios";

const baseUrl = "http://localhost:8080/api/team";

export default class TeamApi {
  static async get() {
    return axios.get<TeamResponse[]>(baseUrl);
  }
}
