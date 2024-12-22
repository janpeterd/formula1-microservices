import axios from "axios";
import { API_URL } from "./utils";
import Cookies from "js-cookie";
import ImageRequest from "@/dto/imageRequest";

const endpoint = "images";

export default class ImageApi {
  static async post(payload: ImageRequest) {
    const idToken = Cookies.get("idToken");
    return axios.post(`${API_URL}/${endpoint}`, payload, {
      headers: {
        Authorization: `Bearer ${idToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
  }
}
