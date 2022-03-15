import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import PgConfirmation from 'app/modules/pg-confirmation/pg-confirmation';

// jest.mock("axios");
// const mockedAxios = axios as jest.Mocked<typeof axios>;

let container;
let localStore;
let wrapper;
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

describe('getUserInfo', () => {
  beforeEach(() => {
    window.sessionStorage.clear();
    jest.restoreAllMocks();
    window.sessionStorage.setItem(
      'user',
      JSON.stringify({ cik: '123#', ccc: '123456', paymentAmount: 100, name: 'Abyalew', email: 'abyalewab@gmail.com', phone: '0932198133' })
    );
    wrapper = shallow(<PgConfirmation />);
  });
  it('Should get user info from session storage', () => {
    const getItemSpy = jest.spyOn(window.sessionStorage, 'getItem');
  });

  it('Should match the snapshot', () => {
    expect(wrapper).toMatchSnapshot();
  });

  describe('when API call is successful', () => {
    it('should get token', () => {
      const data = {
        token: 'EC-4IJFJFNJNFI45U8J',
      };

      const mock = new MockAdapter(axios);
      mock.onGet('api/paypal').reply(200, data);

      const instance = wrapper.instance();
      return instance.getPaypalUrl();
    });
  });

  describe('when API call is successful', () => {
    it('should post payment amount', () => {
      const data = {
        userData: jest.spyOn(window.sessionStorage, 'getItem'),
      };

      const mock = new MockAdapter(axios);
      mock.onPost('api/paymentAmountApi').reply(200, data);

      const instance = wrapper.instance();
      return instance.postPaymentAmount();
    });
  });

  describe('setInterval', () => {
    it('should test timer correctly ', () => {
      jest.useFakeTimers();
      const component = shallow(<PgConfirmation />);
      expect(component.state('time')).toBe(10);
      jest.advanceTimersByTime(1000);
      expect(component.state('time')).toBe(9);

      const instance = wrapper.instance();
      return instance.componentDidMount();

      // const window.location.href;
      // const utils = jest.createMockFromModule('app/modules/pg-confirmation/pg-confirmation').default;
      // const instance = wrapper.instance();
      // expect(utils.clearInterval.mock).toHaveBeenCalledWith(expect.any(Number));
    });
  });

  /* describe("postPaymentAmount", () => {

          beforeEach(()=>{
            wrapper=shallow(<PgConfirmation />);
          });

      describe("when API call is successful", () => {
        it("Should post payment amount ", () => {

          const data = jest.spyOn(window.sessionStorage, 'getItem');
          mockedAxios.post.mockResolvedValueOnce(data);
          // const result = await wrapper.instance().postPaymentAmount();

        });
      });

      describe("when API call fails", () => {
        it("should return error message", () => {

           const message = "Network Error";
           mockedAxios.post.mockRejectedValueOnce(new Error(message));
           // const result = await wrapper.instance().postPaymentAmount();
        });
      });
    });*/

  /* describe("when API call is successful", () => {
it('should render a proper table data', () => {
const getItemSpy = jest.spyOn(window.sessionStorage, 'getItem');

const axios = require("axios");
const MockAdapter = require("axios-mock-adapter");
const mock = new MockAdapter(axios);

mock.onPost("/api/postPaymentAmount").reply(200);

});
});


describe("when API call is successful", () => {
it('should render a proper table data', () => {
const axios = require("axios");
const MockAdapter = require("axios-mock-adapter");
const mock = new MockAdapter(axios);

mock.onGet("/api/paypal").reply(200, {token:"EC-4FNJ4JNJ4NJohn"});

});
});
*/

  /* describe("getPaypalUrl", () => {

              beforeEach(()=>{
                wrapper=shallow(<PgConfirmation />);
              });

                describe("when API call is successful", () => {
                it("should get express checkout token ", () => {


                  const mock = new MockAdapter(axios);
                  mock.onGet('/path/to/api').reply(200, response.data);
                  const component = mount(<SalesPlanTable />);

                  const data = "EC-BJHHJGHJGHJGHJ";
                  mockedAxios.get.mockResolvedValueOnce(data);
                  // const result = await wrapper.instance().postPaymentAmount();

                });
                });

                describe("when API call fails", () => {
                  it("should return error message", () => {

                     const message = "Network Error";
                     mockedAxios.get.mockRejectedValueOnce(new Error(message));
                     // const result = await wrapper.instance().postPaymentAmount();
                  });
                });
          });


        describe("ComponentDidMount", () =>{
              it('checks componentDidMount is called', () => {
                  const spy = jest.spyOn(PgConfirmation.prototype, 'componentDidMount');
                  wrapper = mount(<PgConfirmation/>);
                  expect(spy).toHaveBeenCalled();
                })

           });*/
});
