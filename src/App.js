import React, {Component} from 'react';
import './App.css';
import DealsPage from './Containers/DealsPage/DealsPage';
import Aux from './hoc/Auxilliary/aux';

class App extends Component {
  render () {
    return (
      <Aux>
       <DealsPage/>
      </Aux>
      );
  }
}

export default App;
