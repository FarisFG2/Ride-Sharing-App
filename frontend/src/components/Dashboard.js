import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Dashboard = () => {
  const [user, setUser] = useState(null);
  const [users, setUsers] = useState([]);
  const [rides, setRides] = useState([]);
  const [adminError, setAdminError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const userData = localStorage.getItem("user");
    if (!token || !userData) {
      navigate("/login");
    } else {
      const parsedUser = JSON.parse(userData);
      setUser(parsedUser);
      if (parsedUser.role === "ADMIN") {
        fetchAdminData(token);
      }
    }
  }, [navigate]);

  const fetchAdminData = async (token) => {
    try {
      const [usersResponse, ridesResponse] = await Promise.all([
        axios.get("http://localhost:8080/api/users", {
          headers: { Authorization: `Bearer ${token}` },
        }),
        axios.get("http://localhost:8080/api/rides", {
          headers: { Authorization: `Bearer ${token}` },
        }),
      ]);
      setUsers(usersResponse.data || []);
      setRides(ridesResponse.data || []);
    } catch (error) {
      console.error("Failed to load admin data", error);
      setAdminError("Unable to load admin controls at the moment.");
    }
  };

  const handleDeleteUser = async (userId) => {
    const token = localStorage.getItem("token");
    try {
      await axios.delete(`http://localhost:8080/api/users/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsers(users.filter((userItem) => userItem.userId !== userId));
    } catch (error) {
      console.error("Failed to delete user", error);
      setAdminError("Could not delete user. Please try again.");
    }
  };

  const handleDeleteRide = async (rideId) => {
    const token = localStorage.getItem("token");
    try {
      await axios.delete(`http://localhost:8080/api/rides/${rideId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setRides(rides.filter((ride) => ride.rideId !== rideId));
    } catch (error) {
      console.error("Failed to delete ride", error);
      setAdminError("Could not delete ride. Please try again.");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/login");
  };

  if (!user) return <div className="page-card">Loading...</div>;

  return (
    <div className="page-card">
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          gap: "16px",
          flexWrap: "wrap",
        }}
      >
        <div>
          <h2>Welcome back, {user.firstName}!</h2>
          <p className="status-chip">Role: {user.role}</p>
        </div>
        <button onClick={handleLogout}>Logout</button>
      </div>

      {user.role === "ADMIN" && (
        <div className="admin-panel">
          <h3>Admin Dashboard</h3>
          {adminError && <p className="error-message">{adminError}</p>}

          <section className="admin-section">
            <h4>Users</h4>
            {users.length === 0 ? (
              <p>No users recorded.</p>
            ) : (
              <table className="admin-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((userItem) => (
                    <tr key={userItem.userId}>
                      <td>{userItem.userId}</td>
                      <td>{userItem.email}</td>
                      <td>{userItem.role}</td>
                      <td>
                        <button
                          className="nav-button"
                          onClick={() => handleDeleteUser(userItem.userId)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </section>

          <section className="admin-section">
            <h4>Rides</h4>
            {rides.length === 0 ? (
              <p>No rides recorded.</p>
            ) : (
              <table className="admin-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Rider</th>
                    <th>Driver</th>
                    <th>Status</th>
                    <th>Fare</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {rides.map((ride) => (
                    <tr key={ride.rideId}>
                      <td>{ride.rideId}</td>
                      <td>{ride.riderId}</td>
                      <td>{ride.driverId || "Unassigned"}</td>
                      <td>{ride.status}</td>
                      <td>${ride.fare?.toFixed(2)}</td>
                      <td>
                        <button
                          className="nav-button"
                          onClick={() => handleDeleteRide(ride.rideId)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </section>
        </div>
      )}

      <div className="action-grid">
        {user.role === "RIDER" && (
          <>
            <div className="action-card">
              <h3>Book a Ride</h3>
              <p>Find the best route and request a ride with one click.</p>
              <button
                onClick={() => navigate("/book-ride")}
                className="nav-button"
              >
                Start Booking
              </button>
            </div>
            <div className="action-card">
              <h3>Ride History</h3>
              <p>Review past trips, fares, and completed rides.</p>
              <button
                onClick={() => navigate("/ride-history")}
                className="nav-button"
              >
                View History
              </button>
            </div>
          </>
        )}

        {user.role === "DRIVER" && (
          <>
            <div className="action-card">
              <h3>Available Rides</h3>
              <p>Accept new ride requests and manage active trips.</p>
              <button
                onClick={() => navigate("/driver-rides")}
                className="nav-button"
              >
                View Available Rides
              </button>
            </div>
            <div className="action-card">
              <h3>Earnings</h3>
              <p>Track earnings and completed ride performance.</p>
              <button
                onClick={() => navigate("/driver-earnings")}
                className="nav-button"
              >
                View Earnings
              </button>
            </div>
          </>
        )}

        {user.role === "ADMIN" && (
          <>
            <div className="action-card">
              <h3>User Management</h3>
              <p>Manage riders, drivers, and system access controls.</p>
              <button className="nav-button">Manage Users</button>
            </div>
            <div className="action-card">
              <h3>Platform Health</h3>
              <p>Monitor all rides and remove incorrect entries.</p>
              <button className="nav-button">View All Rides</button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
