import * as React from 'react';
import axios from 'axios';
import { render } from 'react-dom';

function Timer() {
  const [counter, setCounter] = React.useState(10);

  React.useEffect(() => {
    counter > 0 && setTimeout(() => setCounter(counter - 1), 1000);
  }, [counter]);

  return (
    <div className="App">
      <div style={{ color: 'brown' }}>
        Only <strong>{counter} </strong> seconds are remaining !!!
      </div>
    </div>
  );
}

export default Timer;
