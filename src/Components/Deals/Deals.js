import React from "react";

import Deal from "./Deal/Deal";
import classes from "./Deals.module.css";

const Deals = props => {
  const deals = props.items
    .sort(function(a, b) {
      return b.date - a.date;
    })
    .map((item, index) => {
      return (
        <Deal click={() => props.clicked(item.url)} key={index} title={item.title} url={item.url} date={item.date} />
      );
    });

  return <div className={classes.Deals}>{deals}</div>;
};

export default Deals;
