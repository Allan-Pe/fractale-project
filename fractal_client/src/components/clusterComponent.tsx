import { Box } from "@mui/material";
import angel from "../assets/angel.png";
import tibzz from "../assets/tibzz.jpg";
import yannis from "../assets/yannis.png";
import allan from "../assets/allan.png";
import "./clusterComponent.css";

export const ClusterComponent = () => {
  return (
    <Box
      sx={{
        position: "absolute",
        top: "32%",
        left: "7%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
      }}
    >
      <Box>
        <Box
          className="spin"
          component="img"
          sx={{
            borderRadius: 10,
            marginRight: "15rem",
          }}
          alt=""
          src={angel}
        />
        <Box
          className="spin"
          component="img"
          sx={{
            borderRadius: 10,
          }}
          alt=""
          src={tibzz}
        />
      </Box>
      <Box sx={{ marginTop: "15rem" }}>
        <Box
          className="spin"
          component="img"
          sx={{
            borderRadius: 10,
            marginRight: "15rem",
          }}
          alt=""
          src={allan}
        />
        <Box
          className="spin"
          component="img"
          sx={{
            borderRadius: 10,
          }}
          alt=""
          src={yannis}
        />
      </Box>
    </Box>
  );
};
