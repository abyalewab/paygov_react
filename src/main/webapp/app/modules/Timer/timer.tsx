import * as React from 'react';
import { render } from 'react-dom';

function Timer() {
  const [counter, setCounter] = React.useState(10);

  React.useEffect(() => {
    counter > 0 && setTimeout(() => setCounter(counter - 1), 1000);
  }, [counter]);

  return (
    <div className="App">
      <div style={{ color: 'red' }}>
        <strong>{counter} </strong> seconds are left
      </div>
    </div>
  );
}

export default Timer;
