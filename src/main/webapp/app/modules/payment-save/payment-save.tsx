import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps, Route, Router } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IPaygov } from 'app/shared/model/paygov.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import './payment-save.scss';
import ReactDOM from 'react-dom';

import Modal from 'react-bootstrap/Modal';
import ModalBody from 'react-bootstrap/Modal';
import ModalTitle from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/Modal';
import ModalFooter from 'react-bootstrap/Modal';

function PaymentSave() {
  const [show, setShow] = useState(false);
  const [show2, setShow2] = useState(false);
  const userData = JSON.parse(sessionStorage.getItem('user'));

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    doExpressCheckout();
  }, []);

  const doExpressCheckout = () => {
    const request = 'api/paypalDoEC';
    axios.get(request).then(res => {
      const dataDoEC = JSON.stringify(res.data);
      const doEC = JSON.parse(dataDoEC);
      console.log(doEC);
    });
  };

  function remove() {
    sessionStorage.removeItem('user');
  }

  function saveData() {
    axios({
      method: 'post',
      url: 'api/paygovs',
      data: userData,
    })
      .then(function (response) {
        console.log(response);
        if (response.status === 201 && response.statusText === 'Created') {
          setShow(true);
          remove();
        }
      })
      .catch(function (error) {
        console.log(error);
        setShow2(true);
      });
  }

  return (
    <div>
      <br />
      <br />
      <br />
      <br />
      <br />
      <h2 className="pg">
        <b>PayGov</b>
      </h2>
      <br />

      <div className="bd">
        <br />
        <br />
        <h4 id="save">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Almost done, press complete button to finalize payment process</h4>
        <br />
        <br />
        <Button tag={Link} to="/paygovr" type="submit" className="center">
          <FontAwesomeIcon icon="ban" />
          &nbsp;&nbsp;
          <span>Cancel</span>
        </Button>
        &nbsp;&nbsp;&nbsp;
        <Button type="submit" className="center" onClick={saveData} color="primary">
          <FontAwesomeIcon icon="save" />
          &nbsp;&nbsp;
          <span>Complete</span>
        </Button>
        <br />
        <br />
        <br />
      </div>
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />

      <div>
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton className="successModal">
            <Modal.Title>Confirmation Message</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <h6>Payment process completed successfully !</h6>
          </Modal.Body>
          <Modal.Footer>
            <Button tag={Link} to="/paygovr" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </div>

      <div>
        <Modal show={show2} onHide={handleClose}>
          <Modal.Header closeButton className="errorModal">
            <Modal.Title>Error Message</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <h6>Unable to complete payment process !</h6>
          </Modal.Body>
          <Modal.Footer>
            <Button tag={Link} to="/paygovr" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}

export default PaymentSave;

/*

import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps, Route, Router } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IPaygov } from 'app/shared/model/paygov.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import "./payment-save.scss"
import ReactDOM from 'react-dom';

import Modal from 'react-bootstrap/Modal'
import ModalBody from 'react-bootstrap/Modal'
import ModalTitle from 'react-bootstrap/Modal'
import ModalHeader from 'react-bootstrap/Modal'
import ModalFooter from 'react-bootstrap/Modal'

interface AbcState {
  cik : string;
  ccc : string;
  paymentAmount : string;
  name: string;
  phone: string;
  email: string;

}

export default class PgConfirmation extends React.Component<any, any> {

    userData;
    url;
    ppUrl;
    state = {
    time : 10,
    loading : true,
    payres:{},
    cok:"",
    // ppUrl:""
    }
    timerId = null;



    constructor(props) {
        super(props);
        this.userData = JSON.parse(sessionStorage.getItem('user'));
        // this.getPaypalUrl();
        this.postPaymentAmount();
    }



    getPaypalUrl() {
    axios.get('api/paypal')
         .then(res => {
            this.url =JSON.stringify(res.data);
            this.ppUrl = JSON.parse(this.url);
            this.setState({
            this.state.ppUrl: JSON.parse(this.url)
            })
            console.log(this.ppUrl);
         });

    }


    postPaymentAmount() {
        const data = JSON.parse(sessionStorage.getItem('user'));
        axios.post('api/paymentAmountApi', data).then(res => console.log(res)); // .catch(err => console.log(err));
        console.log(data);
    }

    componentDidMount() {
          this.timerId = setInterval(() => {
          this.setState((prevState) => ({ time: this.state.time -1 }));

          if(this.state.time===0){
            clearInterval(this.timerId);
            if(this.ppUrl!=null){
            window.location.href = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+this.ppUrl
            }
          }

          }, 1000);
    }

  render() {
        return (

            <div className="bdd">
                <h2>
                  <span>PayGov</span>
                </h2><br/>

                <div className="timer">
                    <h5 className="ctr"> You are going to be redirected </h5>
                    <h5 className="ctr"> to the payment page only </h5>
                    <h5 className="ctr"> <Timer/> </h5>

                </div><br/>

                <div className="form-group">
                  <strong><span>Cik : </span></strong>
                  <span>{this.userData.cik}</span>
                </div>
                <br/>
                <div className="form-group">
                  <strong><span>Ccc  : </span></strong>
                  <span>{this.userData.ccc}</span>
                </div>
                <br/>
                <div className="form-group">
                  <strong><span>Payment Amount : </span></strong>
                  <span>{this.userData.paymentAmount}</span>
                </div>
                <br/>
                <div className="form-group">
                  <strong><span>Name : </span></strong>
                  <span>{this.userData.name}</span>
                </div>
                <br/>
                <div className="form-group">
                  <b><span>Email : </span></b>
                  <span>{this.userData.email}</span>
                </div>
                <br/>
                <div className="form-group">
                  <b><span>Phone : </span></b>
                  <span>{this.userData.phone}</span>
                </div>
                <br/>

                <div>
                  <br/>
                   <Button className="btn btn-primary" tag={Link} to="/paygovr">
                   <FontAwesomeIcon icon="arrow-left" />
                     &nbsp;&nbsp;
                     Back
                     &nbsp;&nbsp;
                   </Button>
                </div>
            </div>
        )
        }

}



*/
