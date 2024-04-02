import axios from "axios";
import { FractalProperties } from "./interfaces";

// Make a request for a user with a given ID
export const generateFractal = async (fractalProperties: FractalProperties) => {
  try {
    const response: any = await axios.post(
      "http://localhost:8080/generatenewfractal",
      fractalProperties,
      { responseType: "arraybuffer" }
    );

    const responseBlob = new Blob([response.data], { type: "image/jpeg" });
    return responseBlob;
  } catch (error) {
    console.log(error);
  }
};

export const updateFractalPosition = async (
  fractalProperties: FractalProperties
) => {
  try {
    const response: any = await axios.post(
      "http://localhost:8080/updatefractal",
      fractalProperties,
      { responseType: "arraybuffer" }
    );

    const responseBlob = new Blob([response.data], { type: "image/jpeg" });
    return responseBlob;
  } catch (error) {}
};

export const saveFractal = async (fractalProperties: FractalProperties) => {
  try {
    await axios.post("http://localhost:8080/savefractal", fractalProperties);
  } catch (error) {}
};

export const getstats = async () => {
  try {
    const response = await axios.get("http://localhost:8080/getstats");
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const generateFractalWithCustomPool = async (
  fractalProperties: FractalProperties
) => {
  try {
    const response: any = await axios.post(
      "http://localhost:8080/generatenewfractalthreadpool",
      fractalProperties,
      { responseType: "arraybuffer" }
    );

    const responseBlob = new Blob([response.data], { type: "image/jpeg" });
    return responseBlob;
  } catch (error) {
    console.log(error);
  }
};
