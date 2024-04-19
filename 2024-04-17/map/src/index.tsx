import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
declare global {
  interface Window {
    naver: any;
  }
}

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(<App />);