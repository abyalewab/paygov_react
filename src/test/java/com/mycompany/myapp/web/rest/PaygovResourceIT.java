package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaygovResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaygovResourceIT {

    private static final String DEFAULT_CIK = "AAAAAAAAAA";
    private static final String UPDATED_CIK = "BBBBBBBBBB";

    private static final Integer DEFAULT_CCC = 1;
    private static final Integer UPDATED_CCC = 2;

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paygovs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaygovRepository paygovRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaygovMockMvc;

    private Paygov paygov;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
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

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paygov createUpdatedEntity(EntityManager em) {
        Paygov paygov = new Paygov()
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
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
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
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
    void createPaygovWithExistingId() throws Exception {
        // Create the Paygov with an existing ID
        paygov.setId(1L);

        int databaseSizeBeforeCreate = paygovRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCikIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setCik(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCccIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setCcc(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setPaymentAmount(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setName(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setEmail(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygovRepository.findAll().size();
        // set the field null
        paygov.setPhone(null);

        // Create the Paygov, which fails.

        restPaygovMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaygovs() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        // Get all the paygovList
        restPaygovMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paygov.getId().intValue())))
            .andExpect(jsonPath("$.[*].cik").value(hasItem(DEFAULT_CIK)))
            .andExpect(jsonPath("$.[*].ccc").value(hasItem(DEFAULT_CCC)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getPaygov() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        // Get the paygov
        restPaygovMockMvc
            .perform(get(ENTITY_API_URL_ID, paygov.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paygov.getId().intValue()))
            .andExpect(jsonPath("$.cik").value(DEFAULT_CIK))
            .andExpect(jsonPath("$.ccc").value(DEFAULT_CCC))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingPaygov() throws Exception {
        // Get the paygov
        restPaygovMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaygov() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();

        // Update the paygov
        Paygov updatedPaygov = paygovRepository.findById(paygov.getId()).get();
        // Disconnect from session so that the updates on updatedPaygov are not directly saved in db
        em.detach(updatedPaygov);
        updatedPaygov
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restPaygovMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaygov.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaygov))
            )
            .andExpect(status().isOk());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
        Paygov testPaygov = paygovList.get(paygovList.size() - 1);
        assertThat(testPaygov.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaygov.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaygov.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaygov.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygov.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygov.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paygov.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaygovWithPatch() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();

        // Update the paygov using partial update
        Paygov partialUpdatedPaygov = new Paygov();
        partialUpdatedPaygov.setId(paygov.getId());

        partialUpdatedPaygov.name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restPaygovMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaygov.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaygov))
            )
            .andExpect(status().isOk());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
        Paygov testPaygov = paygovList.get(paygovList.size() - 1);
        assertThat(testPaygov.getCik()).isEqualTo(DEFAULT_CIK);
        assertThat(testPaygov.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaygov.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaygov.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygov.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygov.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdatePaygovWithPatch() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();

        // Update the paygov using partial update
        Paygov partialUpdatedPaygov = new Paygov();
        partialUpdatedPaygov.setId(paygov.getId());

        partialUpdatedPaygov
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restPaygovMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaygov.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaygov))
            )
            .andExpect(status().isOk());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
        Paygov testPaygov = paygovList.get(paygovList.size() - 1);
        assertThat(testPaygov.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaygov.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaygov.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaygov.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygov.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygov.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paygov.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaygov() throws Exception {
        int databaseSizeBeforeUpdate = paygovRepository.findAll().size();
        paygov.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygovMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygov))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paygov in the database
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaygov() throws Exception {
        // Initialize the database
        paygovRepository.saveAndFlush(paygov);

        int databaseSizeBeforeDelete = paygovRepository.findAll().size();

        // Delete the paygov
        restPaygovMockMvc
            .perform(delete(ENTITY_API_URL_ID, paygov.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paygov> paygovList = paygovRepository.findAll();
        assertThat(paygovList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
