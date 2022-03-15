package com.mycompany.myapp.web.rest;

import com.ingenico.connect.gateway.sdk.java.Client;
import com.ingenico.connect.gateway.sdk.java.CommunicatorConfiguration;
import com.ingenico.connect.gateway.sdk.java.Factory;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.Address;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.AmountOfMoney;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutRequest;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutResponse;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.definitions.HostedCheckoutSpecificInput;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Customer;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Order;
import com.mycompany.myapp.domain.Paygov;
import com.mycompany.myapp.repository.PaygovRepository;
import com.mycompany.myapp.util.Configuration;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.paypal.exception.*;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.sdk.exceptions.PayPalException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import liquibase.exception.InvalidChangeDefinitionException;
import liquibase.parser.core.ParsedNodeException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Paygov}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaygovResource {

    private final Logger log = LoggerFactory.getLogger(PaygovResource.class);

    private static final String ENTITY_NAME = "paygov";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaygovRepository paygovRepository;

    public PaygovResource(PaygovRepository paygovRepository) {
        this.paygovRepository = paygovRepository;
    }

    @Valid
    Paygov payAmount;

    /**
     * {@code POST  /paygovs} : Create a new paygov.
     *
     * @param paygov the paygov to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paygov, or with status {@code 400 (Bad Request)} if the paygov has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/paygovs")
    public ResponseEntity<Paygov> createPaygov(@Valid @RequestBody Paygov paygov) throws URISyntaxException {
        log.debug("REST request to save Paygov : {}", paygov);
        if (paygov.getId() != null) {
            throw new BadRequestAlertException("A new paygov cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paygov result = paygovRepository.save(paygov);
        return ResponseEntity
            .created(new URI("/api/paygovs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paygovs/:id} : Updates an existing paygov.
     *
     * @param id the id of the paygov to save.
     * @param paygov the paygov to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paygov,
     * or with status {@code 400 (Bad Request)} if the paygov is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paygov couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paygovs/{id}")
    public ResponseEntity<Paygov> updatePaygov(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Paygov paygov
    ) throws URISyntaxException {
        log.debug("REST request to update Paygov : {}, {}", id, paygov);
        if (paygov.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paygov.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paygovRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Paygov result = paygovRepository.save(paygov);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paygov.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paygovs/:id} : Partial updates given fields of an existing paygov, field will ignore if it is null
     *
     * @param id the id of the paygov to save.
     * @param paygov the paygov to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paygov,
     * or with status {@code 400 (Bad Request)} if the paygov is not valid,
     * or with status {@code 404 (Not Found)} if the paygov is not found,
     * or with status {@code 500 (Internal Server Error)} if the paygov couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paygovs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paygov> partialUpdatePaygov(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Paygov paygov
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paygov partially : {}, {}", id, paygov);
        if (paygov.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paygov.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paygovRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paygov> result = paygovRepository
            .findById(paygov.getId())
            .map(existingPaygov -> {
                if (paygov.getCik() != null) {
                    existingPaygov.setCik(paygov.getCik());
                }
                if (paygov.getCcc() != null) {
                    existingPaygov.setCcc(paygov.getCcc());
                }
                if (paygov.getPaymentAmount() != null) {
                    existingPaygov.setPaymentAmount(paygov.getPaymentAmount());
                }
                if (paygov.getName() != null) {
                    existingPaygov.setName(paygov.getName());
                }
                if (paygov.getEmail() != null) {
                    existingPaygov.setEmail(paygov.getEmail());
                }
                if (paygov.getPhone() != null) {
                    existingPaygov.setPhone(paygov.getPhone());
                }

                return existingPaygov;
            })
            .map(paygovRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paygov.getId().toString())
        );
    }

    /**
     * {@code GET  /paygovs} : get all the paygovs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paygovs in body.
     */
    @GetMapping("/paygovs")
    public List<Paygov> getAllPaygovs() {
        log.debug("REST request to get all Paygovs");
        return paygovRepository.findAll();
    }

    /**
     * {@code GET  /paygovs/:id} : get the "id" paygov.
     *
     * @param id the id of the paygov to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paygov, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paygovs/{id}")
    public ResponseEntity<Paygov> getPaygov(@PathVariable Long id) {
        log.debug("REST request to get Paygov : {}", id);
        Optional<Paygov> paygov = paygovRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paygov);
    }

    /**
     * {@code DELETE  /paygovs/:id} : delete the "id" paygov.
     *
     * @param id the id of the paygov to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paygovs/{id}")
    public ResponseEntity<Void> deletePaygov(@PathVariable Long id) {
        log.debug("REST request to delete Paygov : {}", id);
        paygovRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/paymentAmountApi")
    public @ResponseBody String getPayment(@Valid @RequestBody Paygov paymentValue) throws URISyntaxException {
        this.payAmount = paymentValue;
        return "paypal payment amount from API call";
    }

    SetExpressCheckoutResponseType setExpressCheckoutResponse;
    String token;

    @GetMapping("/paypal")
    public String setExpressCheckout()
        throws PayPalException, ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        Long PayerId = 5l;
        String paymentAmount = this.payAmount.getPaymentAmount().toString();
        //String returnURL = "https://paygov-gh.herokuapp.com/payment-save";
        //String cancelURL = "https://paygov-gh.herokuapp.com/";

        String returnURL = "http://localhost:9000/payment-save";
        String cancelURL = "http://localhost:9000";

        PaymentActionCodeType paymentAction = PaymentActionCodeType.SALE;
        CurrencyCodeType currencyCode = CurrencyCodeType.EUR;

        Map<String, String> configurationMap = Configuration.getAcctAndConfig();
        // Creating service wrapper object to make an API call by loading configuration map.
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

        //construct the request
        SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
        setExpressCheckoutReq.setVersion("63.0");

        //construct the details for the request
        SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();

        PaymentDetailsType paymentDetails = new PaymentDetailsType();
        paymentDetails.setOrderDescription("PayGov integration with paypal");
        paymentDetails.setInvoiceID("INVOICE-" + Math.random());
        BasicAmountType orderTotal = new BasicAmountType();
        orderTotal.setValue(paymentAmount);
        orderTotal.setCurrencyID(currencyCode);
        paymentDetails.setOrderTotal(orderTotal);
        paymentDetails.setPaymentAction(paymentAction);
        details.setPaymentDetails(Arrays.asList(new PaymentDetailsType[] { paymentDetails }));
        details.setReturnURL(returnURL);
        details.setCancelURL(cancelURL);
        details.setCustom(PayerId.toString());

        setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);

        SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
        expressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutReq);

        setExpressCheckoutResponse = service.setExpressCheckout(expressCheckoutReq);
        token = setExpressCheckoutResponse.getToken();

        return JSONObject.quote(setExpressCheckoutResponse.getToken());
    }

    @GetMapping("/paypalDoEC")
    public String startDoExpresResponse()
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, PayPalException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        try {
            doExpressResponse(getExpressCheckoutDetails(setExpressCheckoutResponse.getToken()));
        } catch (PayPalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return JSONObject.quote(getExpressCheckoutDetails(setExpressCheckoutResponse.getToken()).getCheckoutStatus());
    }

    public GetExpressCheckoutDetailsResponseDetailsType getExpressCheckoutDetails(String token)
        throws PayPalException, ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        // CallerServices caller = new CallerServices();

        // APIProfile profile = ...;
        Map<String, String> configurationMap = Configuration.getAcctAndConfig();
        // Creating service wrapper object to make an API call by loading configuration
        // map.
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

        GetExpressCheckoutDetailsReq grequest = new GetExpressCheckoutDetailsReq();
        GetExpressCheckoutDetailsRequestType pprequest = new GetExpressCheckoutDetailsRequestType();
        pprequest.setVersion("63.0");
        // pprequest.setToken(token);

        grequest.setGetExpressCheckoutDetailsRequest(new GetExpressCheckoutDetailsRequestType(token));
        GetExpressCheckoutDetailsResponseType ppresponse = service.getExpressCheckoutDetails(grequest);

        ppresponse.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID();
        ppresponse.getGetExpressCheckoutDetailsResponseDetails().getToken();
        ppresponse.getAck();
        ppresponse.getGetExpressCheckoutDetailsResponseDetails();

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");

        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID() + " payerID");

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");

        System.out.println(ppresponse.getAck() + " ack");

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");

        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getToken() + " TOKEN");

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails() + " PAYMENTDETAILS");

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getPaymentInfo() + " PAYMENTINFO");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getBillingAddress() + " BILLINGADRESS");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println(ppresponse.getGetExpressCheckoutDetailsResponseDetails().getCheckoutStatus() + " CHECKOUTSTATUS");

        // doExpressResponse(ppresponse.getGetExpressCheckoutDetailsResponseDetails());
        return ppresponse.getGetExpressCheckoutDetailsResponseDetails();
    }

    public String doExpressResponse(GetExpressCheckoutDetailsResponseDetailsType response)
        throws PayPalException, ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        // CallerServices caller = new CallerServices();
        // doExpressResponse(ppresponse.getGetExpressCheckoutDetailsResponseDetails());
        // APIProfile profile = ...;
        // APIProfile profile = ...;
        Map<String, String> configurationMap = Configuration.getAcctAndConfig();
        // Creating service wrapper object to make an API call by loading configuration
        // map.
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

        DoExpressCheckoutPaymentRequestType pprequest = new DoExpressCheckoutPaymentRequestType();
        pprequest.setVersion("63.0");

        // DoExpressCheckoutPaymentResponseType ppresponse= new
        // DoExpressCheckoutPaymentResponseType();

        DoExpressCheckoutPaymentRequestDetailsType paymentDetailsRequestType = new DoExpressCheckoutPaymentRequestDetailsType();

        paymentDetailsRequestType.setPaymentDetails(response.getPaymentDetails());
        paymentDetailsRequestType.setToken(response.getToken());
        //PayerInfoType payerInfo = response.getPayerInfo();
        paymentDetailsRequestType.setPayerID(response.getPayerInfo().getPayerID());
        paymentDetailsRequestType.setPaymentAction(PaymentActionCodeType.SALE);
        pprequest.setDoExpressCheckoutPaymentRequestDetails(paymentDetailsRequestType);
        // DoExpressCheckoutPaymentReq
        // paymentDetailsRequestType.setPaymentDetails(response.getPaymentDetails());
        // pprequest.setDoExpressCheckoutPaymentRequestDetails(paymentDetailsRequestType);
        DoExpressCheckoutPaymentReq payRequest1 = new DoExpressCheckoutPaymentReq();
        payRequest1.setDoExpressCheckoutPaymentRequest(pprequest);
        DoExpressCheckoutPaymentResponseType ppresponse = service.doExpressCheckoutPayment(payRequest1);

        System.out.println(ppresponse.getAck() + " PAYMENT");
        return ppresponse.getAck() + " PAYMENT";
    }

    @GetMapping("/paymentSB")
    public CreateHostedCheckoutResponse getRedirectUrl() throws URISyntaxException, IOException {
        Client client = getClient();
        try {
            HostedCheckoutSpecificInput hostedCheckoutSpecificInput = new HostedCheckoutSpecificInput();
            hostedCheckoutSpecificInput.setLocale("en_GB");
            hostedCheckoutSpecificInput.setVariant("100");
            hostedCheckoutSpecificInput.setReturnUrl("http://localhost:9000/payment-save");
            hostedCheckoutSpecificInput.setShowResultPage(false);

            AmountOfMoney amountOfMoney = new AmountOfMoney();
            amountOfMoney.setAmount(payAmount.getPaymentAmount().longValue());
            amountOfMoney.setCurrencyCode("USD");

            Address billingAddress = new Address();
            billingAddress.setCountryCode("US");

            Customer customer = new Customer();
            customer.setBillingAddress(billingAddress);
            customer.setMerchantCustomerId("1234");

            Order order = new Order();
            order.setAmountOfMoney(amountOfMoney);
            order.setCustomer(customer);

            CreateHostedCheckoutRequest body = new CreateHostedCheckoutRequest();
            body.setHostedCheckoutSpecificInput(hostedCheckoutSpecificInput);
            body.setOrder(order);

            CreateHostedCheckoutResponse response = ((Client) client).merchant("1089").hostedcheckouts().create(body);
            System.out.println(response.getPartialRedirectUrl());
            return response;
        } finally {
            client.close();
        }
    }

    String apiKeyId = "c8e19539478a1b05";
    String secretApiKey = "IOqNecY6Wc/1SHa7po3/+AKWn1klsHibb6z7Fwls0FI=";

    private com.ingenico.connect.gateway.sdk.java.Client getClient() throws URISyntaxException {
        String apiKeyId = System.getProperty("apiKeyId", this.apiKeyId);
        String secretApiKey = System.getProperty("secretApiKey", this.secretApiKey);

        URL propertiesUrl = getClass().getResource("/hostedpaymentpage.properties");
        CommunicatorConfiguration configuration = Factory.createConfiguration(propertiesUrl.toURI(), apiKeyId, secretApiKey);
        return Factory.createClient(configuration);
    }
}
