import { useEffect, useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import { generateFractal, updateFractalPosition } from "../../services/service";

const Dashboard = () => {
  const [fractalImg, setFractalImg] = useState<string>();

  const testGet = async () => {
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
  
    const updateFractalResponse: any = await updateFractalPosition(direction)
    if (!(updateFractalResponse instanceof Blob)) {
      throw new Error("Response is not a Blob.");
    }
    const url = URL.createObjectURL(updateFractalResponse);
    console.log(url);
    setFractalImg(url);
  }

  useEffect(() =>{
    const handleKeyDown = (event: any) => {
      switch (event.key){
        case 'd':
          sendMovement("right");
          break;
        case 'q':
          sendMovement("left");
          break;
        case 'z':
          sendMovement("up");
          break;
        case 's':
          sendMovement("down")
          break;
        case 'a':
          sendMovement("zoomin")
          break;
        case 'e':
          sendMovement("zoomout")
          break
        default:
          break;

      }
    }

    document.addEventListener('keydown', handleKeyDown);

    return () => {
      document.removeEventListener('keydown', handleKeyDown)
    };

  }, []);


  return (
    <Box>
      <Box>
        <Typography variant="h2">Dasboard</Typography>
        <Typography sx={{ marginTop: "4rem" }}>
          Data will be displayed here
        </Typography>
        <Button onClick={() => testGet()}>TEST</Button>
        <Box
          component="img"
          sx={{
            height: "90%",
            width: "90%",
            // maxHeight: { xs: 233, md: 167 },
            // maxWidth: { xs: 350, md: 250 },
          }}
          alt=""
          src={fractalImg}
        />
      </Box>
    </Box>
  );
};

export default Dashboard;
