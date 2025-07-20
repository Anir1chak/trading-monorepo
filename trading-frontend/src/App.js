// src/App.js
import React from "react";
import { BrowserRouter , Routes, Route, Switch, Link } from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Market from "./components/Market";
import Analytics from "./components/Analytics";


//import { BrowserRouter,  Route, Link } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/login">Login</Link> |{" "}
        <Link to="/signup">Signup</Link> |{" "}
        <Link to="/market">Market</Link>
         <Link to="/analytics">Analytics</Link>
      </nav>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/market" element={<Market />} />
                  <Route path="/analytics" element={<Analytics />} />

        <Route path="/" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
