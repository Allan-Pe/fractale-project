import { useEffect, useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import {
  generateFractal,
  updateFractalPosition,
  saveFractal,
  generateFractalWithCustomPool,
} from "../../services/service";
import CircularProgress from "@mui/material/CircularProgress";
import { FractalProperties } from "../../services/interfaces";
import StatsScreen from "../../components/Monitoring";
import "./Dashboard.css";
import { SizeSelection } from "../../components/SizeSelection";
import { ClusterComponent } from "../../components/clusterComponent";

const Dashboard = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [fractalImg, setFractalImg] = useState<string>("");
  const [easterEggSt, setEasterEggSt] = useState<string>("");
  const [displayEE, setDisplayEE] = useState<boolean>(false);
  const [movementDistanceCorrector, setMovementDistanceCorrector] =
    useState<number>(1.0);
  const [fractalProperties, setFractalProperties] = useState<FractalProperties>(
    {
      centerX: 0.0,
      centerY: 0.0,
      scale: 4.0,
      width: 1000,
      height: 1000,
      maxIterations: 1000,
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
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleSaveFractal = () => {
    if (easterEggSt === "EG") {
      setDisplayEE(true);
    }

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
    const movementFactor = 0.2;

    setFractalProperties((prevFractalProperties) => {
      const newFractalProperties = {
        ...prevFractalProperties,
      };

      switch (value) {
        case "d":
          newFractalProperties.centerX -=
            movementDistanceCorrector * movementFactor;
          break;
        case "q":
          newFractalProperties.centerX +=
            movementDistanceCorrector * movementFactor;
          break;
        case "z":
          newFractalProperties.centerY +=
            movementDistanceCorrector * movementFactor;
          break;
        case "s":
          newFractalProperties.centerY -=
            movementDistanceCorrector * movementFactor;
          break;
        case "a":
          setMovementDistanceCorrector((prevValue) => prevValue / 1.2);
          newFractalProperties.scale /= 1.2;
          break;
        case "e":
          setMovementDistanceCorrector((prevValue) => prevValue * 1.2);
          newFractalProperties.scale *= 1.2;
          break;
        default:
          break;
      }

      return newFractalProperties;
    });
  };

  const generateFractalWithCustomTP = async () => {
    try {
      const response: any = await generateFractalWithCustomPool(
        fractalProperties
      );

      if (!(response instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const url = URL.createObjectURL(response);
      setFractalImg(url);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const generateOriginalFractal = async () => {
    setIsLoading(true);
    const originalFractal: FractalProperties = {
      centerX: 0.0,
      centerY: 0.0,
      scale: 4.0,
      width: 1000,
      height: 1000,
      maxIterations: 1000,
    };

    try {
      const updateFractalResponse: any = await updateFractalPosition(
        originalFractal
      );

      if (!(updateFractalResponse instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const newUrl = URL.createObjectURL(updateFractalResponse);
      setIsLoading(false);
      setFractalImg(newUrl);
      setFractalProperties(originalFractal);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const undo = async () => {
    try {
      const updateFractalResponse: any = await undoRequest(
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

  const redo = async () => {
    try {
      const updateFractalResponse: any = await redoRequest(
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


  const generateCustomSizeFractal = async (size: string) => {
    setIsLoading(true);

    const customSizeFractalProperties: FractalProperties = {
      centerX: 0.0,
      centerY: 0.0,
      scale: 4.0,
      width: parseInt(size),
      height: parseInt(size),
      maxIterations: 1000,
    };

    try {
      const updateFractalResponse: any = await updateFractalPosition(
        customSizeFractalProperties
      );

      if (!(updateFractalResponse instanceof Blob)) {
        throw new Error("Response is not a Blob.");
      }

      const newUrl = URL.createObjectURL(updateFractalResponse);
      setIsLoading(false);
      setFractalImg(newUrl);
      setFractalProperties(customSizeFractalProperties);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const easterEgg = (value: string) => {
    setEasterEggSt(value);
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

  useEffect(() => {
    let intervalId: number;

    const handleGamepadInput = () => {
      const gamepads = navigator.getGamepads();
      const gamepad = gamepads[0];

      if (gamepad!.buttons[0].pressed === true) {
        calculateNewFractalProperties("a");
      }

      if (gamepad!.buttons[2].pressed === true) {
        calculateNewFractalProperties("e");
      }

      if (gamepad!.buttons[12].pressed === true) {
        calculateNewFractalProperties("s");
      }

      if (gamepad!.buttons[13].pressed === true) {
        calculateNewFractalProperties("z");
      }

      if (gamepad!.buttons[14].pressed === true) {
        calculateNewFractalProperties("d");
      }

      if (gamepad!.buttons[15].pressed === true) {
        calculateNewFractalProperties("q");
      }
    };

    const startCheckingGamepadInput = () => {
      intervalId = setInterval(handleGamepadInput, 100);
    };

    window.addEventListener("gamepadconnected", startCheckingGamepadInput);

    return () => {
      window.removeEventListener("gamepadconnected", startCheckingGamepadInput);
      clearInterval(intervalId);
    };
  }, []);

  return (
    <Box sx={{ position: "relative" }}>
      {/* <StatsScreen /> */}
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
        }}
      >
        <Typography variant="h3">Fractal Multiplex</Typography>
        <Typography
          variant="h4"
          sx={{ marginTop: "1rem", marginBottom: "4rem" }}
        >
          Navigate to infinity
        </Typography>

        {isLoading ? (
          <Box>
            <CircularProgress />
          </Box>
        ) : (
          <Box className="image-container">
            <Box
              component="img"
              sx={{
                height: "80vh",
                width: "80vh",
                borderRadius: 10,
              }}
              alt=""
              src={fractalImg}
            />
            <Box>
              <SizeSelection
                generateOriginalFractal={generateOriginalFractal}
                generateFractalWithCustomTP={generateFractalWithCustomTP}
                handleSaveFractal={handleSaveFractal}
                generateCustomSizeFractal={generateCustomSizeFractal}
                easterEgg={easterEgg}
                undo={undo}
                redo={redo}
              />
            </Box>
          </Box>
        )}
      </Box>
      {displayEE === true ? <ClusterComponent /> : ""}
    </Box>
  );
};

export default Dashboard;
