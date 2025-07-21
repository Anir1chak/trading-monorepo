import React, { useState } from "react";
import axios from "axios";

export default function Analytics() {
  const [snapshot, setSnapshot] = useState({ average: 0, min: 0, max: 0, count: 0 });
  const [volumeParams, setVolumeParams] = useState({ from: "00:00", to: "23:59" });
  const [volume, setVolume] = useState(0);
  const [message, setMessage] = useState("");

  const fetchSnapshot = () => {
    axios.get(`${process.env.REACT_APP_API_URL}/api/analytics/snapshot`)
      .then(res => setSnapshot(res.data))
      .catch(err => setMessage("Error fetching snapshot"));
  };

  const fetchVolume = () => {
    axios.get(`${process.env.REACT_APP_API_URL}/api/analytics/volume`, { params: volumeParams })
      .then(res => setVolume(res.data))
      .catch(err => setMessage("Error fetching volume"));
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Analytics Dashboard</h2>

      <div className="mb-6">
        <h3 className="font-semibold">Rolling Snapshot (Last 5 mins)</h3>
        <button onClick={fetchSnapshot} className="mt-2 px-3 py-1 bg-blue-500 text-white rounded">
          Refresh Snapshot
        </button>
        <div className="mt-2">
          <p>Count: {snapshot.count}</p>
          <p>Average Price: {snapshot.average}</p>
          <p>Min Price: {snapshot.min}</p>
          <p>Max Price: {snapshot.max}</p>
        </div>
      </div>

      <div>
        <h3 className="font-semibold">Volume Query</h3>
        <label className="block mt-2">
          From (HH:mm):
          <input
            type="time"
            value={volumeParams.from}
            onChange={e => setVolumeParams({ ...volumeParams, from: e.target.value })}
            className="ml-2 border rounded p-1"
          />
        </label>
        <label className="block mt-2">
          To (HH:mm):
          <input
            type="time"
            value={volumeParams.to}
            onChange={e => setVolumeParams({ ...volumeParams, to: e.target.value })}
            className="ml-2 border rounded p-1"
          />
        </label>
        <button onClick={fetchVolume} className="mt-2 px-3 py-1 bg-green-500 text-white rounded">
          Fetch Volume
        </button>
        <div className="mt-2">
          <p>Total Volume: {volume}</p>
        </div>
      </div>

      {message && <p className="mt-4 text-red-600">{message}</p>}
    </div>
  );
}