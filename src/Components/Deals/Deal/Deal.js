import React from 'react';

import classes from './Deal.module.css';

const deal = (props) => {
    var d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCSeconds(props.date);
    d = d.toDateString();

    return (
        <div onClick={props.click} className={classes.Deal}>
            <p>{props.title}</p>
            {/* <p>{props.url}</p> */}
            <p>{d}</p>
        </div>
    );
}

export default deal;