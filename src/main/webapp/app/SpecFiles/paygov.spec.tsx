import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import Paygov from 'app/modules/paygov/paygov';
import moment from 'moment';

configure({
  adapter: new Adapter(),
});

describe('paygov form ', () => {
  let wrapper;
  let mockSubmit;
  beforeEach(() => {
    mockSubmit = jest.fn();
    wrapper = shallow(<Paygov submit={mockSubmit} />);
  });

  it('Should match the snapshot', () => {
    expect(wrapper).toMatchSnapshot();
  });

  describe('onChangeCik', () => {
    it('Should call setState on Cik', () => {
      const mockEvent = {
        target: {
          name: 'cik',
          value: '123#',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '',
        paymentAmount: '',
        name: '',
        email: '',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangeCik(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  describe('onChangeCcc', () => {
    it('Should call setState on Ccc', () => {
      const mockEvent = {
        target: {
          name: 'ccc',
          value: '123456',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '',
        name: '',
        email: '',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangeCcc(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  describe('onChangePaymentAmount', () => {
    it('Should call setState on PaymentAmount', () => {
      const mockEvent = {
        target: {
          name: 'paymentAmount',
          value: '100',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '100',
        name: '',
        email: '',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangePaymentAmount(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  describe('onChangeName', () => {
    it('Should call setState on Name', () => {
      const mockEvent = {
        target: {
          name: 'name',
          value: 'Abyalew Yeshitla',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '100',
        name: 'Abyalew Yeshitla',
        email: '',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangeName(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  describe('onChangeEmail', () => {
    it('Should call setState on Email', () => {
      const mockEvent = {
        target: {
          name: 'email',
          value: 'abyalewab@gmail.com',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '100',
        name: 'Abyalew Yeshitla',
        email: 'abyalewab@gmail.com',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangeEmail(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  describe('onChangePhone', () => {
    it('Should call setState on Phone', () => {
      const mockEvent = {
        target: {
          name: 'phone',
          value: '0932198133',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '100',
        name: 'Abyalew Yeshitla',
        email: 'abyalewab@gmail.com',
        phone: '0932198133',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onChangePhone(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });

  let e;
  it('checks if _onAction(e) sets correctly', () => {
    wrapper.instance()._onAction(e);
    expect(wrapper.instance().state.isTimedOut).toEqual(false);
  });

  it('checks if _onActive(e)  sets correctly', () => {
    wrapper.instance()._onActive(e);
    expect(wrapper.instance().state.isTimedOut).toEqual(false);
  });

  it('Checks if remove() sets form values to null correctly', () => {
    wrapper.instance().remove();
    expect(wrapper.instance().state.cik).toEqual('');
    expect(wrapper.instance().state.ccc).toEqual('');
    expect(wrapper.instance().state.paymentAmount).toEqual('');
    expect(wrapper.instance().state.name).toEqual('');
    expect(wrapper.instance().state.email).toEqual('');
    expect(wrapper.instance().state.phone).toEqual('');
  });

  it('Checks if form validation isValid() works correctly', () => {
    wrapper.instance().isValid();
    expect(wrapper.instance().state.isTimedOut).toEqual(false);
  });

  describe('onSubmit()', () => {
    it('Should submit', () => {
      const mockEvent = {
        target: {
          name: 'ccc',
          value: '123456',
        },
      };
      const expected = {
        cik: '123#',
        ccc: '123456',
        paymentAmount: '',
        name: '',
        email: '',
        phone: '',
        timeout: 0,
        hours: moment().hour(),
        showModal: false,
        userLoggedIn: false,
        isTimedOut: false,
      };
      wrapper.instance().onSubmit(mockEvent);
      expect(wrapper.state()).toEqual(expected);
    });
  });
});
