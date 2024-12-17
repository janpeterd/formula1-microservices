import axios from "axios";
import { API_URL } from "./utils";
import Cookies from "js-cookie";
import ImageRequest from "@/dto/imageRequest";

const endpoint = "images";
const idToken = Cookies.get("idToken");

export default class ImageApi {
  static async post(payload: ImageRequest) {
    return axios.post(`${API_URL}/${endpoint}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
  }
}
