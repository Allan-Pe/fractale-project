import axios from "axios";

// Make a request for a user with a given ID
export const apiCall = async () => {
  try {
    const response: Blob = await axios.get(
      "http://localhost:8080/generatefractal"
    );

    console.log(Blob);

    return new Blob([response], { type: "image/jpeg" });
  } catch (error) {
    // handle error
    console.log(error);
  }
};
