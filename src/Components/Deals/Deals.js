import React from "react";

import Deal from "./Deal/Deal";
import classes from "./Deals.module.css";

const Deals = props => {
  const deals = props.items
    .sort(function(a, b) {
      return b.date - a.date;
    })
    .map((item, index) => {
      if (
        item.title.toLowerCase().includes(props.search) ||
        props.search === ""
      ) {
        return (
          <Deal
            click={() => props.clicked(item.url)}
            key={index}
            title={item.title}
            url={item.url}
            date={item.date}
          />
        );
      }
      return null;
    });

  return <div className={classes.Deals}>{deals}</div>;
};

export default Deals;
