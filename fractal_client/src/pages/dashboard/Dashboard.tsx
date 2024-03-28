import { useEffect, useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import { generateFractal, updateFractalPosition, saveFractal } from "../../services/service";
import CircularProgress from "@mui/material/CircularProgress";

const Dashboard = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [fractalImg, setFractalImg] = useState<string>();

  const generateStartFractal = async () => {
    try {
      const response: any = await generateFractal();

      if (!(response instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const url = URL.createObjectURL(response);
      console.log(url);
      setFractalImg(url);

      // Optionally, revoke the URL to free up memory when not needed anymore
      // URL.revokeObjectURL(url);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const sendMovement = async (direction: string) => {
    setIsLoading(true);
    try {
      const updateFractalResponse: any = await updateFractalPosition(direction);

      if (!(updateFractalResponse instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const newUrl = URL.createObjectURL(updateFractalResponse);
      setIsLoading(false);
      setFractalImg(newUrl);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    const handleKeyDown = (event: any) => {
      switch (event.key) {
        case "d":
          sendMovement("right");
          break;
        case "q":
          sendMovement("left");
          break;
        case "z":
          sendMovement("up");
          break;
        case "s":
          sendMovement("down");
          break;
        case "a":
          sendMovement("zoomin");
          break;
        case "e":
          sendMovement("zoomout");
          break;
        default:
          break;
      }
    };

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  useEffect(() => {
    generateStartFractal();
  }, []);

  return (
    <Box>
      <Box 
      sx={{
        display:"flex",
        flexDirection: "column"
      }}>
        <Typography variant="h2">Fractal Multiplex</Typography>
        <Typography sx={{ marginTop: "4rem" }}>navigate to infinity</Typography>
        {isLoading ? (
          <Box>
            <CircularProgress />
          </Box>
        ) : (
          <Box
            component="img"
            sx={{
              height: "80vh",
              width: "80vh",
              // maxHeight: { xs: 233, md: 167 },
              // maxWidth: { xs: 350, md: 250 },
            }}
            alt=""
            src={fractalImg}
          />
        )}
        <Button onClick={() => saveFractal()}>Save me</Button>
      </Box>
    </Box>
  );
};

export default Dashboard;
