import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ReactDOM from "react-dom/client";
import { Navigate } from "react-router-dom";
import App from "./App.tsx";
import "./index.css";
import Dashboard from "./pages/dashboard/Dashboard.tsx";
import Layout from "./layout/Layout.tsx";
import ErrorPage from "./pages/error/ErrorPage.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <Navigate to="/app" replace />,
      },
      {
        path: "/app",
        element: <App />,
      },
      {
        path: "/dashboard",
        element: <Dashboard />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  // <React.StrictMode>
  <RouterProvider router={router} />
  // </React.StrictMode>
);
