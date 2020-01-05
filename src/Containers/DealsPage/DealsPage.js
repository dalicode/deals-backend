import React, { Component } from "react";

import Aux from "../../hoc/Aux/aux";
import Deals from "../../Components/Deals/Deals";
import axios from "axios";
import Background from "../../UI/Background/Background";

class DealsPage extends Component {
  state = {
    deals: []
  };

  componentDidMount() {
    axios.get("http://localhost:8080/bapcsales").then(resp => {
      let newDeals = [...resp.data.deals, ...this.state.deals];
      this.setState({ deals: newDeals });
    });
    axios.get("http://localhost:8080/gamedeals").then(resp => {
      let newDeals = [...resp.data.deals, ...this.state.deals];
      this.setState({ deals: newDeals });
    });
    axios.get("http://localhost:8080/redflagdeals").then(resp => {
      let newDeals = [...resp.data.deals, ...this.state.deals];
      this.setState({ deals: newDeals });
    });
  }

  openExternalUrl = (url) => {
    window.open(url);
  }

  render() {
    return (
      <Aux>
      <Background/>
        <div>Search bar </div>
        <Deals items={this.state.deals} clicked={this.openExternalUrl}/>
        <div>Navigation controls</div>
      </Aux>
    );
  }
}

export default DealsPage;
