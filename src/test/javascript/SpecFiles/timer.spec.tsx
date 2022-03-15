import React from 'react';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import Timer from 'src/main/webapp/app/modules/Timer/timer';

let container;
beforeEach(() => {
  container = document.createElement('div');
  document.body.appendChild(container);
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
