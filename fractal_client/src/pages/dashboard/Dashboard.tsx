import { useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import { apiCall } from "../../services/service";

const Dashboard = () => {
  const [fractalImg, setFractalImg] = useState<string>();

  const testGet = async () => {
    try {
      const response: any = await apiCall();

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
            height: 500,
            width: 700,
            maxHeight: { xs: 233, md: 167 },
            maxWidth: { xs: 350, md: 250 },
          }}
          alt=""
          src={fractalImg}
        />
      </Box>
    </Box>
  );
};

export default Dashboard;
