# Fractal Multiplex

## Overview

Fractal Multiplex is an interactive fractal generator designed to visualize and explore complex mathematical structures in real time. The application supports:

- Julia sets
- Mandelbrot set (default)

It is built with a modern architecture combining:

- A **Ktor backend (Kotlin)** responsible for fractal computation
- A **Vite frontend (JavaScript)** for fast and responsive rendering

---

## Requirements

Before running the project, ensure the following tools are installed:

- Java (JDK 17 or higher)
- Node.js (v18 or higher recommended)
- npm
- A Unix-based terminal (Linux/macOS) or Git Bash on Windows

---

## Getting Started

### Make the script executable (if needed)

```bash
chmod +x start-all.sh
./start-all.sh
```

## Fractal Generation Application

## What the `start-all.sh` script does

The `start-all.sh` script fully automates the project startup:

- Checks that the `backend` and `frontend` folders exist
- Makes the Gradle wrapper executable if needed
- Starts the Ktor backend server
- Installs frontend dependencies if necessary (`npm install`)
- Launches the Vite development server

## Access to the application

Once the script is running:

- The backend runs in the background
- The frontend is available at:

[http://localhost:5173](http://localhost:5173)

## Features

## Fractal generation

The application generates fractals dynamically on the server side. You can choose between:

- Mandelbrot (default): complex stability regions
- Julia: patterns generated from complex numbers

## Adjustable resolution

- High resolution → more detail
- Low resolution → faster rendering

## Navigation history

- Previous → go back in history
- Next → move forward in history

## Keyboard controls

- Z → up
- Q → left
- S → down
- D → right
- A → zoom in
- E → zoom out

## Stopping the application

To stop the application:

CTRL + C

The script automatically stops the backend and cleans up processes.
