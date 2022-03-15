import React, { Component } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { useForm } from 'react-hook-form';
import './paygov.scss';
import ReactDOM from 'react-dom';

import PaymentSave from 'app/modules/payment-save/payment-save';
import { useState, useEffect } from 'react';
import { getEntity, updateEntity, createEntity, reset } from './paygov.reducer';
import { IPaygov } from 'app/shared/model/paygov.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Switch, Route } from 'react-router-dom';
import IdleTimer from 'react-idle-timer';
import PropTypes from 'prop-types';
import { Redirect } from 'react-router-dom';
import moment from 'moment';
import PageNotAvailable from 'app/modules/page-idle/page-idle';
import IdleTimeOutModal from 'app/modules/paygov/idle-modal';

interface AbcState {
  cik: string;
  ccc: string;
  paymentAmount: string;
  name: string;
  phone: string;
  email: string;
  timeout: any;
  isTimedOut: boolean;
  userLoggedIn: boolean;
  hours: any;
}

export default class Paygov extends React.Component<any, any> {
  userData;
  idleTimer;
  onAction;
  onActive;
  onIdle;
  timerId = null;

  constructor(props) {
    super(props);

    this.onChangeCik = this.onChangeCik.bind(this);
    this.onChangeCcc = this.onChangeCcc.bind(this);
    this.onChangePaymentAmount = this.onChangePaymentAmount.bind(this);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePhone = this.onChangePhone.bind(this);

    this.onSubmit = this.onSubmit.bind(this);

    this.idleTimer = null;
    this.onAction = this._onAction.bind(this);
    this.onActive = this._onActive.bind(this);
    this.onIdle = this._onIdle.bind(this);
    this.handleClose = this.handleClose.bind(this);

    this.state = {
      cik: '',
      ccc: '',
      paymentAmount: '',
      name: '',
      email: '',
      phone: '',

      timeout: 0,
      showModal: false,
      userLoggedIn: false,
      isTimedOut: false,
      hours: moment().hour(),
    };
  }

  // Form Events

  onChangeCik(e) {
    this.setState({
      cik: e.target.value,
    });
  }

  onChangeCcc(e) {
    this.setState({
      ccc: e.target.value,
    });
  }

  onChangePaymentAmount(e) {
    this.setState({
      paymentAmount: e.target.value,
    });
  }
  onChangeName(e) {
    this.setState({
      name: e.target.value,
    });
  }

  onChangeEmail(e) {
    this.setState({
      email: e.target.value,
    });
  }

  onChangePhone(e) {
    this.setState({
      phone: e.target.value,
    });
  }

  onSubmit(e) {
    this.userData = JSON.parse(sessionStorage.getItem('user'));
    e.preventDefault();

    this.setState({
      cik: this.userData.cik,
      ccc: this.userData.ccc,
      paymentAmount: this.userData.paymentAmount,
      name: this.userData.name,
      email: this.userData.email,
      phone: this.userData.phone,
    });
  }

  // React Life Cycle
  componentDidMount() {
    this.userData = JSON.parse(sessionStorage.getItem('user'));

    if (sessionStorage.getItem('user')) {
      this.setState({
        cik: this.userData.cik,
        ccc: this.userData.ccc,
        paymentAmount: this.userData.paymentAmount,
        name: this.userData.name,
        email: this.userData.email,
        phone: this.userData.phone,
      });
    } else {
      this.setState({
        cik: '',
        ccc: '',
        paymentAmount: '',
        name: '',
        email: '',
        phone: '',
      });
    }
  }

  UNSAFE_componentWillUpdate(nextProps, nextState) {
    sessionStorage.setItem('user', JSON.stringify(nextState));
  }

  remove = () => {
    sessionStorage.removeItem('user');
    this.setState({
      cik: '',
      ccc: '',
      paymentAmount: '',
      name: '',
      email: '',
      phone: '',
    });
  };

  isValid(): any {
    const cikPattern = /^[0-9]+[A-Za-z0-9]+[@#$%^&*()!]/;
    const cccPattern = /^[0-9]/;
    const patternPaymentAmount = /^[0-9]/;
    const emailPattern = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
    const phonePattern = /^[0-9]/;

    if (
      !this.state.cik ||
      !cikPattern.exec(this.state.cik) ||
      !cccPattern.exec(this.state.ccc) ||
      this.state.ccc.length < 6 ||
      !patternPaymentAmount.exec(this.state.paymentAmount) ||
      !emailPattern.exec(this.state.email) ||
      !phonePattern.exec(this.state.phone) ||
      this.state.phone.length !== 10
    ) {
      return true;
    } else {
      return false;
    }
  }

  _onAction(e) {
    console.log('user did something', e);
    this.setState({ isTimedOut: false });
  }

  _onActive(e) {
    console.log('user is active', e);
    this.setState({ isTimedOut: false });
  }

  _onIdle(e) {
    const begin = moment('11:30AM', 'hh:mma');
    const end = moment('11:50AM', 'hh:mma');
    this.setState({ hours: moment() });

    console.log('user is idle', e);
    const isTimedOut = this.state.isTimedOut;
    const hours = this.state.hours;

    if (hours.isAfter(begin) && hours.isBefore(end) && isTimedOut) {
      this.setState({ showModal: true });

      setInterval(() => {
        this.setState({ showModal: false });
        ReactDOM.render(<PageNotAvailable />, document.getElementById('root'));
      }, 10000);
    } else {
      this.idleTimer.reset();
      this.setState({ hours: moment() });
      this.setState({ isTimedOut: true });
    }
  }

  handleClose() {
    this.setState({ showModal: false });
  }

  render() {
    return (
      <>
        <IdleTimer
          ref={ref => {
            this.idleTimer = ref;
          }}
          element={document}
          onActive={this.onActive}
          onIdle={this.onIdle}
          onAction={this.onAction}
          debounce={250}
          timeout={this.state.timeout}
        />

        <IdleTimeOutModal showModal={this.state.showModal} handleClose={this.handleClose} />

        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <img src="content/images/paygov-logo.png" alt="Logo" />
        </div>

        <div className="wrapper" id="fm">
          <div className="cont">
            <Row className="justify-content-center">
              <Col md="3">
                <h3>Add Payment</h3>
              </Col>
            </Row>
            <Row className="justify-content-center">
              <Col md="3">
                <ValidatedForm onSubmit={this.onSubmit}>
                  <ValidatedField
                    value={this.state.cik}
                    onChange={this.onChangeCik}
                    label="Cik"
                    id="pay-cik"
                    name="cik"
                    data-cy="cik"
                    type="text"
                    validate={{
                      required: { value: true, message: 'This field is required.' },
                      pattern: { value: /^[0-9]+[@#$%^&*()!]/, message: 'This field must include number and special character' },
                    }}
                  />
                  <ValidatedField
                    value={this.state.ccc}
                    onChange={this.onChangeCcc}
                    label="Ccc"
                    id="pay-ccc"
                    name="ccc"
                    data-cy="ccc"
                    type="text"
                    validate={{
                      required: { value: true, message: 'This field is required.' },
                      min: { value: 100000, message: 'This field should be at least 6.' },
                      validate: v => isNumber(v) || 'This field should be a number.',
                    }}
                  />
                  <ValidatedField
                    value={this.state.paymentAmount}
                    onChange={this.onChangePaymentAmount}
                    label="Payment Amount"
                    id="pay-paymentAmount"
                    name="paymentAmount"
                    data-cy="paymentAmount"
                    type="text"
                    validate={{
                      required: { value: true, message: 'This field is required.' },
                      validate: v => isNumber(v) || 'This field should be a number.',
                    }}
                  />
                  <ValidatedField
                    value={this.state.name}
                    onChange={this.onChangeName}
                    label="Name"
                    id="pay-name"
                    name="name"
                    data-cy="name"
                    type="text"
                    validate={{
                      required: { value: true, message: 'This field is required.' },
                    }}
                  />
                  <ValidatedField
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    label="Email"
                    id="pay-email"
                    name="email"
                    data-cy="email"
                    type="email"
                    validate={{
                      required: { value: true, message: 'This field is required.' },
                      pattern: { value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i, message: 'Please enter valid email' },
                    }}
                  />
                  <ValidatedField
                    value={this.state.phone}
                    onChange={this.onChangePhone}
                    label="Phone"
                    id="pay-phone"
                    name="phone"
                    data-cy="phone"
                    type="text"
                    validate={{
                      required: { value: true, message: 'This field is required.' },

                      pattern: { value: /^[0-9]{10}$/i, message: ' This field must be only 10 digit. ' },

                      validate: v => isNumber(v) || 'This field should be a number.',
                    }}
                  />
                  <Button type="reset" onClick={this.remove}>
                    <span>Cancel</span>
                  </Button>
                  &nbsp;
                  <Button type="submit" color="primary" tag={Link} to="/pg-confirmation" disabled={this.isValid()}>
                    <span>&nbsp;Next&nbsp;&nbsp;</span>
                  </Button>
                </ValidatedForm>
              </Col>
            </Row>
          </div>
        </div>
      </>
    );
  }
}
