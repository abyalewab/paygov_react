import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Timer from 'app/modules/Timer/timermd';
import PageNotAvailable from 'app/modules/page-idle/page-idle';
import ReactDOM from 'react-dom';
import './paygov.scss';
export const IdleTimeOutModal = ({ showModal, handleClose }) => {
  function PageUnavailable(): any {
    ReactDOM.render(<PageNotAvailable />, document.getElementById('root'));
  }

  return (
    <div>
      <Modal show={showModal}>
        <Modal.Header className="headerModal">
          <Modal.Title>Warning Message</Modal.Title>
        </Modal.Header>

        <Modal.Body className="bodyModal">
          <h5>Sorry the system is not available </h5>
        </Modal.Body>
        <Modal.Body className="bodyModal">
          <h5>You are going to leave the page now </h5>
        </Modal.Body>
        <Modal.Body className="bodyModal">
          <h5>
            <Timer />
          </h5>
        </Modal.Body>

        <Modal.Footer>
          <Button onClick={PageUnavailable}>Close</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default IdleTimeOutModal;
