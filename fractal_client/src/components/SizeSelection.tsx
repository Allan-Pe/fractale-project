import { useState } from "react";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import { Box, Button } from "@mui/material";
import { getJulia } from "../services/service";

export const SizeSelection = ({
  generateOriginalFractal,
  generateFractalWithCustomTP,
  handleSaveFractal,
  generateCustomSizeFractal,
  easterEgg,
  generateCurrentImgWithJulia,
  undo,
  redo,
}: any) => {
  const [fractalSize, setFractalSize] = useState<string>("");

  const handleChangeWidth = (event: SelectChangeEvent) => {
    if ((event.target.value as string) == "100") {
      easterEgg("EG");
    }

    setFractalSize(event.target.value as string);
  };

  const handleCustomFractal = () => {
    generateCustomSizeFractal(fractalSize);
  };

  const handleJulia = async () => {
    await getJulia();
    generateCurrentImgWithJulia();
  };

  return (
    <Box sx={{ margin: "1rem" }}>
      <Button
        sx={{
          color: "white",
          backgroundColor: "#373330",
          marginBottom: "1rem",
        }}
        onClick={() => handleJulia()}
      >
        Julia
      </Button>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignContent: "center",
          flexDirection: "row",
        }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            flexDirection: "column",
            margin: "1rem",
            borderColor: "white",
            border: "solid",
            padding: "2rem",
            borderRadius: "1rem",
            borderWidth: "0.05rem",
          }}
        >
          <InputLabel sx={{ color: "white", marginBottom: "1rem" }}>
            Select fractal size
          </InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={fractalSize}
            label="fractal size"
            onChange={handleChangeWidth}
            sx={{ color: "white", backgroundColor: "#373330" }}
          >
            <MenuItem value={100}>100 x 100</MenuItem>
            <MenuItem value={500}>500 x 500</MenuItem>
            <MenuItem value={700}>700 x 700</MenuItem>
            <MenuItem value={1000}>1000 x 1000</MenuItem>
            <MenuItem value={2000}>2000 x 2000</MenuItem>
          </Select>
          <Button
            sx={{
              marginTop: "1rem",
              color: "white",
              backgroundColor: "#373330",
            }}
            onClick={() => handleCustomFractal()}
          >
            Generate custom size fractal
          </Button>
        </Box>
      </Box>
      <Box
        sx={{
          marginTop: "2rem",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
        }}
      >
        <Button
          sx={{
            color: "white",
            backgroundColor: "#373330",
            width: "150px",
            marginBottom: "1rem",
          }}
          onClick={() => undo()}
        >
          Undo
        </Button>
        <Button
          sx={{
            color: "white",
            backgroundColor: "#373330",
            width: "150px",
            marginBottom: "1rem",
          }}
          onClick={() => redo()}
        >
          Redo
        </Button>
      </Box>
      <Box
        className="genericFlexClass"
        sx={{ margin: "2rem", marginTop: "3rem" }}
      >
        <Button
          sx={{
            color: "white",
            backgroundColor: "#373330",
            marginBottom: "1rem",
          }}
          onClick={() => generateOriginalFractal()}
        >
          Generate brand new fractal with standard thread pool
        </Button>
        <Button
          sx={{ color: "white", backgroundColor: "#373330" }}
          onClick={() => generateFractalWithCustomTP()}
        >
          Generate current fractal with custom thread pool
        </Button>
      </Box>
      <Box className="genericFlexClass" sx={{ marginTop: "2rem" }}>
        <Button
          sx={{ color: "white", backgroundColor: "#373330" }}
          onClick={() => handleSaveFractal()}
        >
          Save this fractal
        </Button>
      </Box>
    </Box>
  );
};
