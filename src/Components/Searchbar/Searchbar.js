import React from 'react';
import classes from './Searchbar.module.css';

const searchbar = (props) => (
  
      <input onChange={props.changed} className={classes.Searchbar} type="text" placeholder="Search"/>

);

export default searchbar;