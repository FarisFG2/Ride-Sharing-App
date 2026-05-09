import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const BookRide = () => {
  const [rideData, setRideData] = useState({
    pickupLocation: "",
    dropoffLocation: "",
    fare: "",
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setRideData({
      ...rideData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const user = JSON.parse(localStorage.getItem("user"));

      await axios.post(
        "http://localhost:8080/api/rides",
        {
          ...rideData,
          riderId: user.userId,
          fare: parseFloat(rideData.fare),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      alert("Ride booked successfully!");
      navigate("/dashboard");
    } catch (error) {
      alert("Failed to book ride");
    }
  };

  return (
    <div className="book-ride-container">
      <h2>Book a Ride</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Pickup Location:</label>
          <input
            type="text"
            name="pickupLocation"
            value={rideData.pickupLocation}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Drop-off Location:</label>
          <input
            type="text"
            name="dropoffLocation"
            value={rideData.dropoffLocation}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Fare ($):</label>
          <input
            type="number"
            name="fare"
            value={rideData.fare}
            onChange={handleChange}
            min="0"
            step="0.01"
            required
          />
        </div>
        <button type="submit">Book Ride</button>
      </form>
    </div>
  );
};

export default BookRide;
