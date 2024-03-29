import { useState } from "react";
import { Box, Typography, Button } from "@mui/material";
const StatsScreen = ({
  timeTotal = 10,
  timeAverage = 10,
  timeAverageTask = 10,
  numberIterations = 10,
}) => {
  const [showStats, setShowStats] = useState(true);

  const handleClose = () => {
    setShowStats(false);
  };
  const handleOpen = () => {
    setShowStats(true);
  };
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
              marginRight: "10px"
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
          <Typography>Time total : {timeTotal} ms</Typography>
          <Typography>Average time : {timeAverage} ms</Typography>
          <Typography>Average task time : {timeAverageTask} ms</Typography>
          <Typography>Number iterations : {numberIterations}</Typography>
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
