#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACK_DIR="$ROOT_DIR/FractalMultiplex"
FRONT_DIR="$ROOT_DIR/fractal_client"
BACK_PORT=8080

# ─── Directory checks ─────────────────────────────────────────────────────────

if [[ ! -d "$BACK_DIR" ]]; then
  echo "Backend directory not found: $BACK_DIR"
  exit 1
fi

if [[ ! -d "$FRONT_DIR" ]]; then
  echo "Frontend directory not found: $FRONT_DIR"
  exit 1
fi

# ─── Kill any leftover processes ──────────────────────────────────────────────

echo "Cleaning up old processes..."
pkill -9 -f "gradlew" 2>/dev/null || true
pkill -9 -f "java"    2>/dev/null || true
pkill -9 -f "vite"    2>/dev/null || true
pkill -9 -f "node"    2>/dev/null || true
lsof -ti:$BACK_PORT | xargs kill -9 2>/dev/null || true
lsof -ti:5173        | xargs kill -9 2>/dev/null || true

# Stop any running Gradle daemons gracefully
if [[ -x "$BACK_DIR/gradlew" ]]; then
  "$BACK_DIR/gradlew" --stop 2>/dev/null || true
fi

sleep 1

# ─── Ensure gradlew is executable ─────────────────────────────────────────────

if [[ ! -x "$BACK_DIR/gradlew" ]]; then
  chmod +x "$BACK_DIR/gradlew"
fi

# ─── Cleanup on EXIT / CTRL+C ─────────────────────────────────────────────────

cleanup() {
  echo ""
  echo "Shutting down all processes..."
  kill "$BACK_PID" 2>/dev/null || true
  pkill -9 -f "gradlew" 2>/dev/null || true
  pkill -9 -f "java"    2>/dev/null || true
  pkill -9 -f "vite"    2>/dev/null || true
  lsof -ti:$BACK_PORT | xargs kill -9 2>/dev/null || true
  lsof -ti:5173        | xargs kill -9 2>/dev/null || true
  echo "All processes stopped. Backend logs available at: $ROOT_DIR/backend.log"
}
trap cleanup EXIT INT TERM

# ─── Start backend ────────────────────────────────────────────────────────────

echo "Starting backend (Ktor) on port $BACK_PORT..."
(
  cd "$BACK_DIR"
  ./gradlew run --no-daemon
) > "$ROOT_DIR/backend.log" 2>&1 &
BACK_PID=$!

# ─── Wait for backend to be ready ─────────────────────────────────────────────

echo "Waiting for backend..."
TIMEOUT=90
ELAPSED=0

until curl -s "http://localhost:$BACK_PORT" > /dev/null 2>&1; do
  # Check that the backend process is still alive
  if ! kill -0 "$BACK_PID" 2>/dev/null; then
    echo ""
    echo "Backend crashed. See backend.log:"
    tail -30 "$ROOT_DIR/backend.log"
    exit 1
  fi

  if (( ELAPSED >= TIMEOUT )); then
    echo ""
    echo "Backend not available after ${TIMEOUT}s. See backend.log:"
    tail -30 "$ROOT_DIR/backend.log"
    exit 1
  fi

  printf "."
  sleep 1
  (( ELAPSED++ ))
done

echo ""
echo "Backend ready in ${ELAPSED}s."

# ─── Install frontend dependencies ────────────────────────────────────────────

echo "Installing frontend dependencies..."
(cd "$FRONT_DIR" && npm install)

# ─── Start frontend ───────────────────────────────────────────────────────────

echo "Starting frontend (Vite)..."
echo ""
echo "  Backend  -> http://localhost:$BACK_PORT"
echo "  Frontend -> http://localhost:5173"
echo ""
echo "  Backend logs: tail -f $ROOT_DIR/backend.log"
echo ""

cd "$FRONT_DIR"
npm run dev