import axios from "axios";
import React, { useEffect, useState } from "react";

const Dashboard = (props) => {
  const [user, setUser] = useState({});
  const [notification, setNotification] = useState([]);
  useEffect(() => {
    axios({
      url: "/user/api/v1/login",
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      data: JSON.stringify({ username: "testUser", password: "test" })
    })
      .then((res) => {
        console.log("success", res.data);
        setUser({ ...res.data });
        let source = null;
        source = new EventSource(
          "/notification/api/v1/notification/user/" + res.data.id
        );
        source.addEventListener("notification", function (event) {
          var data = event.data;
          console.log("Event data : ", data);
          setNotification([...notification, data]);
        });
        source.onerror = (err) => console.log("Error", err);
      })
      .catch((err) => console.log("error", err));
  }, []);
  return (
    <React.Fragment>
      <h2>User Dashboard</h2>
      <h3>User data</h3>
      <p>{JSON.stringify(user)}</p>
      <h3>Notifications</h3>
      {notification.map((notification) => (
        <p>{JSON.stringify(notification)}</p>
      ))}
    </React.Fragment>
  );
};

export default Dashboard;