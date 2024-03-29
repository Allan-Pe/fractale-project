import { useState } from "react";
import { Box, Typography, Button } from "@mui/material";
import { getstats } from "../services/service";
const StatsScreen = ({
}) => {
  const [showStats, setShowStats] = useState(true);
  const [stats, setStats] = useState(0);

  const handleClose = () => {
    setShowStats(false);
  };
  const handleOpen = () => {
    setShowStats(true);
  };
  
    const getData = async () => {
      const response = getstats();
      setStats(await response);
    };
    setTimeout(getData, 200)

  return (
    <>
      {showStats ? (
        <Box
          sx={{
            position: "fixed",
            top: "20px",
            right: "20px",
            padding: "1rem",
            backgroundColor: "rgba(255, 255, 255, 0.7)",
            borderRadius: "8px",
            zIndex: 999,
          }}
        >
          <span
            onClick={handleClose}
            style={{
              cursor: "pointer",
              position: "absolute",
              top: "5px",
              right: "5px",
              fontSize: "1.2rem",
              marginRight: "10px",
            }}
          >
            &times;
          </span>
          <Typography
            sx={{
              fontSize: 20,
            }}
          >
            Statistics
          </Typography>
          <Typography>Time total : {stats.timeImage} ms</Typography>
          <Typography>Average time : {stats.averageTimeImage} ms</Typography>
          <Typography>Average task time : {stats.averageTimeTask} ms</Typography>
          <Typography>Number iterations : {stats.iteration}</Typography>
        </Box>
      ) : (
        <Button
          onClick={handleOpen}
          sx={{
            position: "fixed",
            top: "20px",
            right: "20px",
            backgroundColor: "rgba(255, 255, 255, 0.7)",
            borderRadius: "8px",
            zIndex: 999,
          }}
        >
          Open Stats
        </Button>
      )}
    </>
  );
};

export default StatsScreen;
