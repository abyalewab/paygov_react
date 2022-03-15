package com.mycompany.myapp.web.rest;

import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Paygov;
import com.mycompany.myapp.repository.PaygovRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import junit.framework.Assert;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import urn.ebay.apis.eBLBaseComponents.GetExpressCheckoutDetailsResponseDetailsType;

/**
 * Integration tests for the {@link PaygovResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaygovResourceMyTest {

    private static final String DEFAULT_CIK = "AAAAAAAAAA";
    private static final Integer DEFAULT_CCC = 1;
    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String DEFAULT_PHONE = "AAAAAAAAAA";

    private static final String ENTITY_API_URL = "/api/paygovs";
    private static final String ENTITY_API_URL_PAYMENT = "/api/paymentAmountApi";
    private static final String ENTITY_API_URL_PAYPAL = "/api/paypal";
    private static final String ENTITY_API_URL_PAYPAL_DOEC = "/api/paypalDoEC";
    private static final String ENTITY_API_URL_WORLDLINE = "/api/paymentSB";

    @Autowired
    private PaygovRepository paygovRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaygovMockMvc;

    private Paygov paygov;

    public static Paygov createEntity(EntityManager em) {
        Paygov paygov = new Paygov()
            .cik(DEFAULT_CIK)
            .ccc(DEFAULT_CCC)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return paygov;
    }

    @BeforeEach
    public void initTest() {
        paygov = createEntity(em);
    }

    @Test
    @Transactional
    void createPaygov() throws Exception {
        int databaseSizeBeforeCreate = paygovRepository.findAll().size();
        // Create the Paygov
        restPaygovMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov)))
            .andExpect(status().isCreated());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeCreate + 1);

        Paygov testPaygov = paygovList.get(paygovList.size() - 1);
        assertThat(testPaygov.getCik()).isEqualTo(DEFAULT_CIK);
        assertThat(testPaygov.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaygov.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaygov.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaygov.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaygov.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        MvcResult mvcResult = restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL_PAYMENT).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Transactional
    void paypal() throws Exception {
        getPayment();

        MvcResult mvcResult = restPaygovMockMvc.perform(get(ENTITY_API_URL_PAYPAL)).andExpect(status().isOk()).andReturn();

        String tokenContent = mvcResult.getResponse().getContentAsString();
        assertNotNull(tokenContent);

        System.out.println("Returned Token From SetExpressCheckout API = " + tokenContent);
    }

    @Test
    @Transactional
    void startDoExpressResponse() throws Exception {
        getPayment();
        paypal();
        MvcResult mvcResult = restPaygovMockMvc.perform(get(ENTITY_API_URL_PAYPAL_DOEC)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    @Transactional
    void getRedirectUrl() throws Exception {
        getPayment();

        MvcResult mvcResult = restPaygovMockMvc.perform(get(ENTITY_API_URL_WORLDLINE)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
}
