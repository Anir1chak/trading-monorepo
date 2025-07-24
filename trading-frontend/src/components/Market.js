// src/components/Market.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const Market = () => {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ name: "", quantity: "", price: "" });
  const [buyData, setBuyData] = useState({ itemNumber: "", buyQuantity: "" });
  const [message, setMessage] = useState("");

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_URL}/api/market/list`)
      .then((response) => setItems(response.data))
      .catch((error) => console.error("Error fetching market items", error));
  }, []);

  const handleAddItem = (e) => {
    e.preventDefault();
    const storedUser = localStorage.getItem("user");
    if (!storedUser) {
      setMessage("User not logged in!");
      return;
    }
    const user = JSON.parse(storedUser);
    const ownerId = JSON.parse(user.id);

    axios
      .post(`${process.env.REACT_APP_API_URL}/api/market/add`, null, {
        params: {
          name: newItem.name,
          quantity: newItem.quantity,
          price: newItem.price,
          ownerId: ownerId,
        },
      })
      .then((response) => {
        setMessage(response.data);
        return axios.get(`${process.env.REACT_APP_API_URL}/api/market/list`);
      })
      .then((response) => setItems(response.data))
      .catch((error) => {
        console.error("Error adding item", error);
        setMessage("Failed to add item");
      });
  };

  const handleBuyItem = (e) => {
    e.preventDefault();
    axios
      .post(`${process.env.REACT_APP_API_URL}/api/market/buy`, null, {
        params: {
          itemNumber: buyData.itemNumber,
          buyQuantity: buyData.buyQuantity,
        },
      })
      .then((response) => {
        setMessage(response.data);
        return axios.get(`${process.env.REACT_APP_API_URL}/api/market/list`);
      })
      .then((response) => setItems(response.data))
      .catch((error) => console.error("Error buying item", error));
  };

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-4">
      <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-2xl font-bold text-center mb-6">Marketplace</h2>

        <div className="mb-8">
          <h3 className="text-lg font-semibold mb-4">Add New Item</h3>
          <form onSubmit={handleAddItem} className="space-y-4">
            <input
              type="text"
              placeholder="Item Name"
              required
              value={newItem.name}
              onChange={(e) => setNewItem({ ...newItem, name: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="number"
              placeholder="Quantity"
              required
              value={newItem.quantity}
              onChange={(e) => setNewItem({ ...newItem, quantity: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="number"
              step="0.01"
              placeholder="Price"
              required
              value={newItem.price}
              onChange={(e) => setNewItem({ ...newItem, price: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-md"
            />
            <button
              type="submit"
              className="w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition"
            >
              Add Item
            </button>
          </form>
        </div>

        <div className="mb-8">
          <h3 className="text-lg font-semibold mb-4">Buy an Item</h3>
          <form onSubmit={handleBuyItem} className="space-y-4">
            <input
              type="number"
              placeholder="Item Number"
              required
              value={buyData.itemNumber}
              onChange={(e) => setBuyData({ ...buyData, itemNumber: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="number"
              placeholder="Quantity to Buy"
              required
              value={buyData.buyQuantity}
              onChange={(e) => setBuyData({ ...buyData, buyQuantity: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-md"
            />
            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition"
            >
              Buy Item
            </button>
          </form>
        </div>

        {message && (
          <p className="text-center text-sm text-gray-600 mb-6">{message}</p>
        )}

        <h3 className="text-lg font-semibold mb-2">Available Market Items</h3>
        <ul className="space-y-2">
          {items.map((item) => (
            <li
              key={item.number}
              className="px-4 py-2 bg-gray-50 border rounded-md text-sm"
            >
              <strong>{item.number}</strong>: {item.name} — Qty: {item.quantity} — Price: ${item.price}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Market;
