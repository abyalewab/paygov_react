import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './paygov.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaygovDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paygovEntity = useAppSelector(state => state.paygov.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paygovDetailsHeading">Paygov</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paygovEntity.id}</dd>
          <dt>
            <span id="cik">Cik</span>
          </dt>
          <dd>{paygovEntity.cik}</dd>
          <dt>
            <span id="ccc">Ccc</span>
          </dt>
          <dd>{paygovEntity.ccc}</dd>
          <dt>
            <span id="paymentAmount">Payment Amount</span>
          </dt>
          <dd>{paygovEntity.paymentAmount}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{paygovEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{paygovEntity.email}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{paygovEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/paygov" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paygov/${paygovEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaygovDetail;
