import React, { Component } from "react";

import Deals from "../../Components/Deals/Deals";
import axios from "axios";
import Background from "../../UI/Background/Background";
import Searchbar from "../../Components/Searchbar/Searchbar";
import classes from "./DealsPage.module.css";

class DealsPage extends Component {
  state = {
    deals: [],
    search: ""
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

  handleChange = (ev) => {
    this.setState({search:ev.target.value});
  }

  render() {
    return (
      <div className={classes.Dealspage}>
        <Background/>
        <Searchbar changed={this.handleChange}/>
        <Deals items={this.state.deals} search={this.state.search} clicked={this.openExternalUrl}/>
        <div>Navigation controls</div>
      </div>
    );
  }
}

export default DealsPage;
