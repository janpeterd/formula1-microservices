import DriverResponse from "@/dto/driverResponse";
import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export const API_URL = "http://api.microservices.formula1";
//export const API_URL = import.meta.env.PROD
//  ? "http://api.microservices.formula1"
//  : "http://127.0.0.1:8083";

export function getDriverFullName(driver: DriverResponse) {
  return `${driver.firstName} ${driver.lastName}`;
}

export async function urlToFile(url: string) {
  const split_url = url.split("/");
  const fileName = split_url[split_url.length - 1];
  const response = await fetch(url);
  const blob = await response.blob();
  const mimeType = blob.type;
  return new File([blob], fileName, { type: mimeType });
}

export function fileObjectToDataUrl(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    // Define the onload event for FileReader
    reader.onload = () => {
      resolve(reader.result as string); // Resolve the Promise with the Data URL
    };

    // Define the onerror event for FileReader
    reader.onerror = (error) => {
      reject(error); // Reject the Promise if an error occurs
    };

    // Read the file as a data URL (base64)
    reader.readAsDataURL(file);
  });
}
