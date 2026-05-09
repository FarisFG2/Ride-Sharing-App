import React, { useEffect, useState } from "react";
import axios from "axios";

const DriverEarnings = () => {
  const [payments, setPayments] = useState([]);
  const [totalEarnings, setTotalEarnings] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchEarnings();
  }, []);

  const fetchEarnings = async () => {
    try {
      const token = localStorage.getItem("token");
      const user = JSON.parse(localStorage.getItem("user"));

      const response = await axios.get(
        `http://localhost:8080/api/payments/driver/${user.userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      const paymentData = response.data;
      setPayments(paymentData);

      // Calculate total earnings from completed payments
      const total = paymentData
        .filter((payment) => payment.status === "COMPLETED")
        .reduce((sum, payment) => sum + payment.amount, 0);

      setTotalEarnings(total);
      setLoading(false);
    } catch (error) {
      console.error("Failed to fetch earnings", error);
      setLoading(false);
    }
  };

  if (loading) return <div className="page-card">Loading...</div>;

  return (
    <div className="page-card">
      <h2>Driver Earnings</h2>

      <div className="earnings-summary">
        <div className="summary-card">
          <h3>Total Earnings</h3>
          <p className="earnings-amount">${totalEarnings.toFixed(2)}</p>
        </div>
        <div className="summary-card">
          <h3>Total Payments</h3>
          <p className="payments-count">{payments.length}</p>
        </div>
      </div>

      <section className="earnings-section">
        <h3>Payment History</h3>
        {payments.length === 0 ? (
          <p>No payments received yet.</p>
        ) : (
          <table className="earnings-table">
            <thead>
              <tr>
                <th>Payment ID</th>
                <th>Ride ID</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {payments.map((payment) => (
                <tr key={payment.paymentId}>
                  <td>{payment.paymentId}</td>
                  <td>{payment.rideId}</td>
                  <td>${payment.amount?.toFixed(2)}</td>
                  <td>
                    <span className={`status-${payment.status.toLowerCase()}`}>
                      {payment.status}
                    </span>
                  </td>
                  <td>{new Date(payment.createdAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </div>
  );
};

export default DriverEarnings;
