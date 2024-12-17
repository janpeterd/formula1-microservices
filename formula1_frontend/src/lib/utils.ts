import DriverResponse from "@/dto/driverResponse";
import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export const API_URL = "http://localhost:8083";

export function getDriverFullName(driver: DriverResponse) {
  return `${driver.firstName} ${driver.lastName}`;
}
