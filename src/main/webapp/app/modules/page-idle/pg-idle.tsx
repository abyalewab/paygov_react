import React, { useState, useEffect } from 'react';
import './page-idle.scss';
function PageNotAvailable() {
  return (
    <div>
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <div className="pg">
        <h2>
          <b>Message</b>
        </h2>
        <br />
      </div>

      <div className="main">
        <br />
        <br />
        <br />
        <br />

        <div className="fnt">
          <h4>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Sorry, the page is not available right now !
          </h4>
        </div>
        <br />
        <br />
        <br />
        <br />
      </div>
    </div>
  );
}

export default PageNotAvailable;
