import React, { useEffect, useState } from "react";
import axios from "axios";

const DriverRides = () => {
  const [availableRides, setAvailableRides] = useState([]);
  const [myRides, setMyRides] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAvailableRides();
    fetchMyRides();
  }, []);

  const fetchAvailableRides = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(
        "http://localhost:8080/api/rides/available",
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setAvailableRides(response.data);
    } catch (error) {
      console.error("Failed to fetch available rides", error);
    }
  };

  const fetchMyRides = async () => {
    try {
      const token = localStorage.getItem("token");
      const user = JSON.parse(localStorage.getItem("user"));
      const response = await axios.get(
        `http://localhost:8080/api/rides/driver/${user.userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setMyRides(response.data);
      setLoading(false);
    } catch (error) {
      console.error("Failed to fetch my rides", error);
      setLoading(false);
    }
  };

  const acceptRide = async (rideId) => {
    try {
      const token = localStorage.getItem("token");
      const user = JSON.parse(localStorage.getItem("user"));

      await axios.put(
        `http://localhost:8080/api/rides/${rideId}/accept?driverId=${user.userId}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      alert("Ride accepted successfully!");
      fetchAvailableRides(); // Refresh available rides
      fetchMyRides(); // Refresh my rides
    } catch (error) {
      console.error("Failed to accept ride", error);
      alert("Failed to accept ride");
    }
  };

  const updateRideStatus = async (rideId, status) => {
    try {
      const token = localStorage.getItem("token");

      await axios.put(
        `http://localhost:8080/api/rides/${rideId}/status?status=${status}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      alert(`Ride status updated to ${status}`);
      fetchMyRides(); // Refresh my rides
    } catch (error) {
      console.error("Failed to update ride status", error);
      alert("Failed to update ride status");
    }
  };

  const createPayment = async (ride) => {
    try {
      const token = localStorage.getItem("token");

      const paymentData = {
        rideId: ride.rideId,
        driverId: ride.driverId,
        amount: ride.fare,
        paymentMethod: "CARD", // Default payment method
      };

      await axios.post("http://localhost:8080/api/payments", paymentData, {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("Payment created for completed ride");
    } catch (error) {
      console.error("Failed to create payment", error);
      alert("Ride completed but payment creation failed");
    }
  };

  if (loading) return <div className="page-card">Loading...</div>;

  return (
    <div className="page-card">
      <h2>Driver Dashboard</h2>

      <section className="driver-section">
        <h3>Available Rides</h3>
        {availableRides.length === 0 ? (
          <p>No available rides at the moment.</p>
        ) : (
          <div className="rides-list">
            {availableRides.map((ride) => (
              <div key={ride.rideId} className="ride-card">
                <div className="ride-info">
                  <p>
                    <strong>From:</strong> {ride.pickupLocation}
                  </p>
                  <p>
                    <strong>To:</strong> {ride.dropoffLocation}
                  </p>
                  <p>
                    <strong>Fare:</strong> ${ride.fare?.toFixed(2)}
                  </p>
                  <p>
                    <strong>Requested:</strong>{" "}
                    {new Date(ride.createdAt).toLocaleString()}
                  </p>
                </div>
                <button
                  className="nav-button accept-button"
                  onClick={() => acceptRide(ride.rideId)}
                >
                  Accept Ride
                </button>
              </div>
            ))}
          </div>
        )}
      </section>

      <section className="driver-section">
        <h3>My Rides</h3>
        {myRides.length === 0 ? (
          <p>No rides assigned yet.</p>
        ) : (
          <div className="rides-list">
            {myRides.map((ride) => (
              <div key={ride.rideId} className="ride-card">
                <div className="ride-info">
                  <p>
                    <strong>From:</strong> {ride.pickupLocation}
                  </p>
                  <p>
                    <strong>To:</strong> {ride.dropoffLocation}
                  </p>
                  <p>
                    <strong>Fare:</strong> ${ride.fare?.toFixed(2)}
                  </p>
                  <p>
                    <strong>Status:</strong> {ride.status}
                  </p>
                  <p>
                    <strong>Date:</strong>{" "}
                    {new Date(ride.createdAt).toLocaleString()}
                  </p>
                </div>
                <div className="ride-actions">
                  {ride.status === "ACCEPTED" && (
                    <button
                      className="nav-button status-button"
                      onClick={() =>
                        updateRideStatus(ride.rideId, "IN_PROGRESS")
                      }
                    >
                      Start Ride
                    </button>
                  )}
                  {ride.status === "IN_PROGRESS" && (
                    <button
                      className="nav-button status-button"
                      onClick={() => updateRideStatus(ride.rideId, "COMPLETED")}
                    >
                      Complete Ride
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </section>
    </div>
  );
};

export default DriverRides;
