import React from 'react';
import ReactDOM from 'react-dom';
import { render, fireEvent } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { BrowserRouter as Router, Route, Link, BrowserRouter } from 'react-router-dom';
import PageNotAvailable from 'app/modules/page-idle/page-idle';

let container;
beforeEach(() => {
  container = document.createElement('div');
  document.body.appendChild(container);
});

afterEach(() => {
  document.body.removeChild(container);
  container = null;
});

it(' can render and update a counter', () => {
  const test = document.createElement('div');

  act(() => {
    ReactDOM.render(
      <>
        <BrowserRouter />
        <PageNotAvailable />
        <BrowserRouter />
      </>,
      test
    );

    ReactDOM.unmountComponentAtNode(test);
  });
});
