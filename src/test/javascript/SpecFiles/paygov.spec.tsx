import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import Paygov from 'src/main/webapp/app/modules/paygov/paygov';
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

  it('should match the snapshot', () => {
    expect(wrapper).toMatchSnapshot();
  });

  describe('onChangeCik', () => {
    it('Should call setState on Cik', () => {
      const mockEvent = {
        target: {
          name: 'ccc',
          value: 'test',
        },
      };
      const expected = {
        cik: 'test',
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
        ccc: '123456',
      };
      wrapper.instance().onChangeCik(mockEvent);
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
        paymentAmount: '100',
      };
      wrapper.instance().onChangeCik(mockEvent);
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
        name: 'Abyalew Yeshitla',
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
        cik: 'abyalewab@gmail.com',
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
          value: '00932198133',
        },
      };
      const expected = {
        phone: '0932198133',
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
});
