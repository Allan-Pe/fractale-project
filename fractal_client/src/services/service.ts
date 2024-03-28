import axios from "axios";

// Make a request for a user with a given ID
export const generateFractal = async () => {
  try {
    const response: any = await axios.get(
      "http://localhost:8080/generatefractal",
      { responseType: "arraybuffer" }
    );

    const responseBlob = new Blob([response.data], { type: "image/jpeg" });
    return responseBlob;
  } catch (error) {
    // handle error
    console.log(error);
  }
};

export const updateFractalPosition = async (direction: string) => {
  const config = {
    body: {
      direction
    }
  }

  try {
    await axios.post("http://localhost:8080/generatefractal", config)
    .then(response => {
      const responseBlob = new Blob([response.data], { type: "image/jpeg" });
      console.log(responseBlob)
    })

  } catch (error) {
    
  }
}
