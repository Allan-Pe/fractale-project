import { Box, Typography } from "@mui/material";

const Dashboard = () => {
  return (
    <Box>
      <Box>
        <Typography variant="h2">Dasboard</Typography>
        <Typography sx={{ marginTop: "4rem" }}>
          Data will be displayed here
        </Typography>
      </Box>
    </Box>
  );
};

export default Dashboard;
