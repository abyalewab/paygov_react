import React, { Component } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button, Row, Col, FormText } from 'reactstrap';
import { useState, useEffect } from 'react';
import axios from 'axios';
import './pg-confirm.scss';
import { render } from 'react-dom';
import Timer from 'app/modules/Timer/timer';

interface AbcState {
  cik: string;
  ccc: string;
  paymentAmount: string;
  name: string;
  phone: string;
  email: string;
}

export default class PgConfirmation extends React.Component<any, any> {
  userData;
  url;
  ppUrl;
  state = {
    time: 10,
    loading: true,
    payres: {},
    cok: '',
    ppUrl: '',
  };
  timerId = null;

  constructor(props) {
    super(props);
    this.userData = JSON.parse(sessionStorage.getItem('user'));
    this.getPaypalUrl();
    this.postPaymentAmount();
  }

  getPaypalUrl() {
    axios.get('api/paypal').then(res => {
      this.url = JSON.stringify(res.data);
      const { ppUrl } = JSON.parse(this.url);
      this.setState({
        ppUrl,
      });
      console.log(ppUrl);
    });
  }

  postPaymentAmount() {
    const data = JSON.parse(sessionStorage.getItem('user'));
    axios.post('api/paymentAmountApi', data).then(res => console.log(res)); // .catch(err => console.log(err));
    console.log(data);
  }

  componentDidMount() {
    this.timerId = setInterval(() => {
      this.setState(prevState => ({ time: this.state.time - 1 }));

      if (this.state.time === 0) {
        clearInterval(this.timerId);
        if (this.ppUrl != null) {
          window.location.href = 'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=' + this.state.ppUrl;
        }
      }
    }, 1000);
  }

  render() {
    return (
      <div className="bdd">
        <h2>
          <span>PayGov</span>
        </h2>
        <br />

        <div className="timer">
          <h5 className="ctr"> You are going to be redirected </h5>
          <h5 className="ctr"> to the payment page only </h5>
          <h5 className="ctr">
            {' '}
            <Timer />{' '}
          </h5>
        </div>
        <br />

        <div className="form-group">
          <strong>
            <span>Cik : </span>
          </strong>
          <span>{this.userData.cik}</span>
        </div>
        <br />
        <div className="form-group">
          <strong>
            <span>Ccc : </span>
          </strong>
          <span>{this.userData.ccc}</span>
        </div>
        <br />
        <div className="form-group">
          <strong>
            <span>Payment Amount : </span>
          </strong>
          <span>{this.userData.paymentAmount}</span>
        </div>
        <br />
        <div className="form-group">
          <strong>
            <span>Name : </span>
          </strong>
          <span>{this.userData.name}</span>
        </div>
        <br />
        <div className="form-group">
          <b>
            <span>Email : </span>
          </b>
          <span>{this.userData.email}</span>
        </div>
        <br />
        <div className="form-group">
          <b>
            <span>Phone : </span>
          </b>
          <span>{this.userData.phone}</span>
        </div>
        <br />

        <div>
          <br />
          <Button className="btn btn-primary" tag={Link} to="/paygovr">
            <FontAwesomeIcon icon="arrow-left" />
            &nbsp;&nbsp; Back &nbsp;&nbsp;
          </Button>
        </div>
      </div>
    );
  }
}
