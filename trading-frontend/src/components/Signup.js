// src/components/Signup.js
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
//const navigate = useNavigate();

const Signup = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
 // const history = useHistory();
 const navigate = useNavigate();
  const handleSignup = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/signup", { username, password })
      .then(response => {
        setMessage(response.data);
        if (response.data === "User created successfully") {
          navigate("/login");
        }
      })
      .catch(error => console.error("There was an error!", error));
  };

  return (
    <div>
      <h2>Signup</h2>
      <form onSubmit={handleSignup}>
        <input type="text" placeholder="Username" required
          value={username} onChange={e => setUsername(e.target.value)} />
        <br />
        <input type="password" placeholder="Password" required
          value={password} onChange={e => setPassword(e.target.value)} />
        <br />
        <button type="submit">Signup</button>
      </form>
      <p>{message}</p>
    </div>
  );
};

export default Signup;
