import React, { useEffect, useState } from "react";
import axios from "axios";

const RideHistory = () => {
  const [rides, setRides] = useState([]);

  useEffect(() => {
    const fetchRides = async () => {
      try {
        const token = localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user"));

        const response = await axios.get(
          `http://localhost:8080/api/rides/rider/${user.userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );

        setRides(response.data);
      } catch (error) {
        console.error("Failed to fetch rides", error);
      }
    };

    fetchRides();
  }, []);

  return (
    <div className="ride-history-container">
      <h2>Ride History</h2>
      {rides.length === 0 ? (
        <p>No rides found.</p>
      ) : (
        <ul>
          {rides.map((ride) => (
            <li key={ride.rideId}>
              <p>
                From: {ride.pickupLocation} To: {ride.dropoffLocation}
              </p>
              <p>Status: {ride.status}</p>
              <p>Fare: ${ride.fare}</p>
              <p>Date: {new Date(ride.createdAt).toLocaleDateString()}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RideHistory;
