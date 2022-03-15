import React, { useEffect } from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';

import Paygov from 'app/modules/paygov/paygov';
import IdleTimeOutModal from 'app/modules/paygov/idle-modal';
import App from 'app/modules/modal/modal';
import PaymentSave from 'app/modules/payment-save/payment-save';
import Timer from 'app/modules/Timer/timer';
import PageNotAvailable from 'app/modules/page-idle/page-idle';
import PgNotAvailable from 'app/modules/page-idle/pg-idle';
import PgConfirmation from 'app/modules/pg-confirmation/pg-confirmation';
import Example from 'app/modules/md/md';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import moment from 'moment';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading...</div>,
});

const begin = moment('11:30AM', 'hh:mma');
const end = moment('11:50AM', 'hh:mma');
const hours = moment();

const Routes = props => {
  if (hours.isAfter(begin) && hours.isBefore(end)) {
    return (
      <div className="view-routes">
        <PageNotAvailable />
      </div>
    );
  } else {
    return (
      <div className="view-routes">
        <Switch>
          <ErrorBoundaryRoute path="/login" component={Login} />
          <ErrorBoundaryRoute path="/logout" component={Logout} />
          <ErrorBoundaryRoute path="/account/register" component={Register} />
          <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
          <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
          <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
          <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
          <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
          <ErrorBoundaryRoute path="/paygovr" component={Paygov} />
          <ErrorBoundaryRoute path="/" exact component={Paygov} />
          <ErrorBoundaryRoute path="/pgtimer" component={Timer} />
          <ErrorBoundaryRoute path="/pg-confirmation" component={PgConfirmation} />
          <ErrorBoundaryRoute path="/payment-save" component={PaymentSave} />
          <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} />
          <ErrorBoundaryRoute component={PageNotFound} />
        </Switch>
      </div>
    );
  }
};

export default Routes;
