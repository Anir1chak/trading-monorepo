// src/components/Market.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const Market = () => {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ name: "", quantity: "", price: "" });
  const [buyData, setBuyData] = useState({ itemNumber: "", buyQuantity: "" });
  const [message, setMessage] = useState("");

 // Fetch market items on component mount
  useEffect(() => {
    axios.get("http://localhost:8080/api/market/list")
      .then(response => setItems(response.data))
      .catch(error => console.error("Error fetching market items", error));
  }, []);

  const handleAddItem = (e) => {
    e.preventDefault();

    // Retrieve the user data from localStorage
    const storedUser = localStorage.getItem("user");
    if (!storedUser) {
      setMessage("User not logged in!");
      return;
    }
    const user = JSON.parse(storedUser);
    const ownerId = JSON.parse(user.id); // Use the logged-in user's ID
    console.log(typeof(ownerId));
    axios.post("http://localhost:8080/api/market/add", null, { 
      params: { 
        name: newItem.name,
        quantity: newItem.quantity,
        price: newItem.price,
        ownerId: ownerId
      }
    })
      .then(response => {
        setMessage(response.data);
        // Refresh the market list after adding
        return axios.get("http://localhost:8080/api/market/list");
      })
      .then(response => setItems(response.data))
      .catch(error => {
        console.error("Error adding item", error);
        setMessage("Failed to add item");
      });
  };

  const handleBuyItem = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/market/buy", null, {
      params: { 
        itemNumber: buyData.itemNumber,
        buyQuantity: buyData.buyQuantity
      }
    })
      .then(response => {
        setMessage(response.data);
        // Refresh the market list after purchase
        return axios.get("http://localhost:8080/api/market/list");
      })
      .then(response => setItems(response.data))
      .catch(error => console.error("Error buying item", error));
  };

  return (
    <div>
      <h2>Market</h2>
      <h3>Add New Item</h3>
      <form onSubmit={handleAddItem}>
        <input type="text" placeholder="Item Name" required
          value={newItem.name}
          onChange={e => setNewItem({ ...newItem, name: e.target.value })} />
        <br />
        <input type="number" placeholder="Quantity" required
          value={newItem.quantity}
          onChange={e => setNewItem({ ...newItem, quantity: e.target.value })} />
        <br />
        <input type="number" step="0.01" placeholder="Price" required
          value={newItem.price}
          onChange={e => setNewItem({ ...newItem, price: e.target.value })} />
        <br />
        <button type="submit">Add Item</button>
      </form>

      <h3>Buy an Item</h3>
      <form onSubmit={handleBuyItem}>
        <input type="number" placeholder="Item Number" required
          value={buyData.itemNumber}
          onChange={e => setBuyData({ ...buyData, itemNumber: e.target.value })} />
        <br />
        <input type="number" placeholder="Quantity to Buy" required
          value={buyData.buyQuantity}
          onChange={e => setBuyData({ ...buyData, buyQuantity: e.target.value })} />
        <br />
        <button type="submit">Buy Item</button>
      </form>

      <p>{message}</p>
      
      <h3>Market Items</h3>
      <ul>
        {items.map(item => (
          <li key={item.number}>
            {item.number}: {item.name} - Qty: {item.quantity} - Price: ${item.price}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Market;
