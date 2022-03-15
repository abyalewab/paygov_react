import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Paygov from './paygov';
import PaygovDetail from './paygov-detail';
import PaygovUpdate from './paygov-update';
import PaygovDeleteDialog from './paygov-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaygovUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaygovUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaygovDetail} />
      <ErrorBoundaryRoute path={match.url} component={Paygov} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaygovDeleteDialog} />
  </>
);

export default Routes;
