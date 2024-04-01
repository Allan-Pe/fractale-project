import { useEffect, useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import {
  generateFractal,
  updateFractalPosition,
  saveFractal,
} from "../../services/service";
import CircularProgress from "@mui/material/CircularProgress";
import { FractalProperties } from "../../services/interfaces";

const Dashboard = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [fractalImg, setFractalImg] = useState<string>();
  const [movementDistanceCorrector, setMovementDistanceCorrector] =
    useState<number>(1.0);
  const [fractalProperties, setFractalProperties] = useState<FractalProperties>(
    {
      centerX: 0.0,
      centerY: 0.0,
      scale: 4.0,
      width: 100,
      height: 100,
      maxIterations: 100,
    }
  );

  const generateStartFractal = async () => {
    try {
      const response: any = await generateFractal(fractalProperties);

      if (!(response instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const url = URL.createObjectURL(response);
      setFractalImg(url);

      // Optionally, revoke the URL to free up memory when not needed anymore
      // URL.revokeObjectURL(url);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleSaveFractal = () => {
    saveFractal(fractalProperties);
  };

  const setFractalPosition = async () => {
    setIsLoading(true);

    try {
      const updateFractalResponse: any = await updateFractalPosition(
        fractalProperties
      );

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

  const calculateNewFractalProperties = (value: string) => {
    setFractalProperties((prevFractalProperties) => {
      const newFractalProperties = {
        ...prevFractalProperties,
      };

      switch (value) {
        case "d":
          newFractalProperties.centerX -= movementDistanceCorrector;
          break;
        case "q":
          newFractalProperties.centerX += movementDistanceCorrector;
          break;
        case "z":
          newFractalProperties.centerY += movementDistanceCorrector;
          break;
        case "s":
          newFractalProperties.centerY -= movementDistanceCorrector;
          break;
        case "a":
          setMovementDistanceCorrector((prevValue) => prevValue / 2);
          newFractalProperties.scale /= 2;
          break;
        case "e":
          setMovementDistanceCorrector((prevValue) => prevValue * 2);
          newFractalProperties.scale *= 2;
          break;
        default:
          break;
      }

      return newFractalProperties;
    });
  };

  useEffect(() => {
    const handleKeyDown = (event: any) => {
      calculateNewFractalProperties(event.key);
    };
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  useEffect(() => {
    setFractalPosition();
  }, [fractalProperties]);

  useEffect(() => {
    generateStartFractal();
  }, []);

  return (
    <Box>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
        }}
      >
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
            }}
            alt=""
            src={fractalImg}
          />
        )}
        <Button onClick={() => handleSaveFractal()}>Save me</Button>
      </Box>
    </Box>
  );
};

export default Dashboard;
