// src/components/Login.js
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/login", { username, password })
  .then(response => {
    if (response.data.loginSuccessful) {
      localStorage.setItem("user", JSON.stringify(response.data.user));
      setMessage("Login successful");
      navigate("/market");
    } else {
      setMessage("Invalid username or password");
    }
  })
  .catch(error => {
    console.error("There was an error!", error);
    setMessage("An error occurred");
  });
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input type="text" placeholder="Username" required
          value={username} onChange={e => setUsername(e.target.value)} />
        <br />
        <input type="password" placeholder="Password" required
          value={password} onChange={e => setPassword(e.target.value)} />
        <br />
        <button type="submit">Login</button>
      </form>
      <p>{message}</p>
    </div>
  );
};

export default Login;
