import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useLocation,
  useNavigate,
} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import BookRide from "./components/BookRide";
import RideHistory from "./components/RideHistory";
import DriverRides from "./components/DriverRides";
import DriverEarnings from "./components/DriverEarnings";
import "./App.css";

function Navigation() {
  useLocation();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const userData = localStorage.getItem("user");
  const user = userData ? JSON.parse(userData) : null;
  const isAuthenticated = !!token;

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/login");
  };

  return (
    <nav className="top-nav">
      {isAuthenticated ? (
        <>
          <Link to="/dashboard">Dashboard</Link>
          <span className="nav-spacer">|</span>
          <button className="link-button" onClick={handleLogout}>
            Logout
          </button>
          {user && (
            <span className="nav-user">
              {user.firstName} ({user.role})
            </span>
          )}
        </>
      ) : (
        <>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
        </>
      )}
    </nav>
  );
}

function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <div>
            <h1 className="App-title">Ride Sharing App</h1>
            <p className="App-subtitle">
              Modern ride booking, payments, notifications, and admin controls.
            </p>
          </div>
          <Navigation />
        </header>
        <main>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/book-ride" element={<BookRide />} />
            <Route path="/ride-history" element={<RideHistory />} />
            <Route path="/driver-rides" element={<DriverRides />} />
            <Route path="/driver-earnings" element={<DriverEarnings />} />
            <Route path="/" element={<Login />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
