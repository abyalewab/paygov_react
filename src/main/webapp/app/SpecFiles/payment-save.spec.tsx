import React from 'react';
import { shallow, mount } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import PaymentSave from 'app/modules/payment-save/payment-save';
import moment from 'moment';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

// jest.mock("axios");
// const mockedAxios = axios as jest.Mocked<typeof axios>;

let container;
let localStore;
const localStorageMock = (() => {
  let store = {};

  return {
    getItem(key) {
      return store[key] || null;
    },
    setItem(key, value) {
      store[key] = value.toString();
    },
    removeItem(key) {
      delete store[key];
    },
    clear() {
      store = {};
    },
  };
})();

Object.defineProperty(window, 'sessionStorage', {
  value: localStorageMock,
});

configure({
  adapter: new Adapter(),
});

describe('Payment save page ', () => {
  let wrapper;
  let mockSubmit;
  beforeEach(() => {
    mockSubmit = jest.fn();
    wrapper = shallow(<PaymentSave />);
  });

  it('Should match the snapshot', () => {
    expect(wrapper).toMatchSnapshot();
  });

  describe('When API call is successful', () => {
    it('should save user information', () => {
      const data = {
        userData: jest.spyOn(window.sessionStorage, 'getItem'),
      };

      const mock = new MockAdapter(axios);
      mock.onPost('api/paygovs').reply(200, data);

      const saveData = jest.fn();
      jest.spyOn(PaymentSave.prototype, saveData()).mockImplementation(saveData);
      expect(saveData).toHaveBeenCalled();
    });
  });

  /* describe('when API call is successful', ()=>{

                  it('should save user information', () => {

                      const data = {
                        userData : jest.spyOn(window.sessionStorage, 'removeItem'),
                      };

                      expect()
                      const instance = wrapper.instance();
                      return instance.remove();
                  });
      });*/

  /* describe("when API call is successful", () => {
        it("should post user information to database ", () => {
          const data = jest.spyOn(window.sessionStorage, 'getItem');
          mockedAxios.post.mockResolvedValueOnce(data);
          // const result = await component.saveData();
          console.log(data);
        });
      });

      describe("when API call fails", () => {
        it("should return error message", () => {

           const message = "Network Error";
           mockedAxios.post.mockRejectedValueOnce(new Error(message));
           // const result = await component.saveData();
        });
      });*/

  /* it("Checks if session remove works correctly ", () => {
            wrapper.instance().remove() = jest.fn();;
            const getItemSpy = jest.spyOn(window.sessionStorage, 'getItem');
            expect(getItemSpy).toEqual(null);
      });*/

  /* describe("when API call is successful", () => {
      it('should render a proper table data', () => {
      const getItemSpy = jest.spyOn(window.sessionStorage, 'getItem');

      const axios = require("axios");
      const MockAdapter = require("axios-mock-adapter");
      const mock = new MockAdapter(axios);

      mock.onPost("api/paygovs").reply(201);

      });
      });*/
});
