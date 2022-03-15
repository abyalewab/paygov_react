import React from 'react';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import Timer from 'app/modules/Timer/timermd';
import { shallow } from 'enzyme';

let container;
beforeEach(() => {
  container = document.createElement('div');
  document.body.appendChild(container);

  const mock = jest.fn();
});

afterEach(() => {
  document.body.removeChild(container);
  container = null;
});

it('Can render timer', () => {
  const test = document.createElement('div');
  act(() => {
    ReactDOM.render(
      <>
        <BrowserRouter />
        <Timer />
        <BrowserRouter />
      </>,
      test
    );

    ReactDOM.unmountComponentAtNode(test);
  });
});

it('Can test setTimeout correctly ', () => {
  jest.useFakeTimers();
  // const component = shallow(<PgConfirmation/>);
  // expect(setTimeout).toHaveBeenCalledTimes(1);
  // jest.advanceTimersByTime(1000);
  // expect(component.state('time')).toBe(9);

  // const instance = wrapper.instance();
  // return instance.componentDidMount();
});
