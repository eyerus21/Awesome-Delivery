import PropTypes from "prop-types";
import { connect } from "react-redux";

import RestaurantDashboard from "../restaurant/dashboard";
import CustomerDashboard from "../customer/dashboard";
import DriverDashboard from "../driver/dashboard";

const Dashboard = (props) => {
  const { loggedInuser } = props.auth;
  return (
    <div>
      {/* {loggedInuser && loggedInuser.role === "restaurant" ? (
        <RestaurantDashboard />
      ) : loggedInuser && loggedInuser.role === "customer" ?(
        <CustomerDashboard />
      ) : ( <DriverDashboard/> )} */}
    </div>
  );
};

Dashboard.propTypes = {
  auth: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  errors: state.errors,
});

export default connect(mapStateToProps, {})(Dashboard);