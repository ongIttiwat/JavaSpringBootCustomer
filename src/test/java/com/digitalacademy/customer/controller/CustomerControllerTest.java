package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    public static final String urlCustomerList = "/customer/List/";

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception {
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jasonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals( "1", jasonArray.getJSONObject(0).get("id").toString());
        assertEquals( "Ryan", jasonArray.getJSONObject(0).get("firstName").toString());
        assertEquals( "Giggs", jasonArray.getJSONObject(0).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals( "scb@gmail.com", jasonArray.getJSONObject(0).get("email").toString());
        assertEquals( 23, jasonArray.getJSONObject(0).get("age"));

        assertEquals( "2", jasonArray.getJSONObject(1).get("id").toString());
        assertEquals( "David", jasonArray.getJSONObject(1).get("firstName").toString());
        assertEquals( "Blackham", jasonArray.getJSONObject(1).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals( "dbb@gmail.com", jasonArray.getJSONObject(1).get("email").toString());
        assertEquals( 40, jasonArray.getJSONObject(1).get("age"));
    }

    public static final String urlCustomer = "/customer/";

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById() throws Exception {
        Long reqId = 2L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getNoonCustomer());
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "Id/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals( "2", jsonObject.get("id").toString());
        assertEquals( "Noon", jsonObject.get("firstName").toString());
        assertEquals( "Bow", jsonObject.get("lastName").toString());
        assertEquals( "66888888888", jsonObject.get("phoneNo").toString());
        assertEquals( "bow@bow.com", jsonObject.get("email").toString());
        assertEquals( 5, jsonObject.get("age"));
    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        Long reqId = 15L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "Id/" + reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("Test get customer by name should return customer list")
    @Test
    void testGetCustomerByName() throws Exception {

        String reqParams ="Ryan";
        List<Customer> resCustomer = CustomerSupportTest.getCustomerList();
        resCustomer.get(1).setFirstName("Ryan");

        when(customerService.getCustomerByFirstName(reqParams))
                .thenReturn(resCustomer);

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "Name/" + reqParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();


        JSONArray jasonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals( "1", jasonArray.getJSONObject(0).get("id").toString());
        assertEquals( "Ryan", jasonArray.getJSONObject(0).get("firstName").toString());
        assertEquals( "Giggs", jasonArray.getJSONObject(0).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals( "scb@gmail.com", jasonArray.getJSONObject(0).get("email").toString());
        assertEquals( 23, jasonArray.getJSONObject(0).get("age"));

        assertEquals( "2", jasonArray.getJSONObject(1).get("id").toString());
        assertEquals( "Ryan", jasonArray.getJSONObject(1).get("firstName").toString());
        assertEquals( "Blackham", jasonArray.getJSONObject(1).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals( "dbb@gmail.com", jasonArray.getJSONObject(1).get("email").toString());
        assertEquals( 40, jasonArray.getJSONObject(1).get("age"));
    }


    @DisplayName("Test get customer by name should return customer list")
    @Test
    void testGetCustomerByNameNotFound() throws Exception {

        String reqParams ="fall";
        List<Customer> resCustomer = null;

        when(customerService.getCustomerByFirstName(reqParams))
                .thenReturn(resCustomer);

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "Name/" + reqParams))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("Test delete customer should success")
    @Test
    void testDeleteCustomer() throws Exception {
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "" + reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(customerService, times(1)).deleteById(reqId);
    }

    @DisplayName("Test delete customer should not found")
    @Test
    void testDeleteCustomerShouldNotFound() throws Exception {
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "" + reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).deleteById(reqId);

    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getCustomer();
        Long reqId = 1L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer))
                .thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals( "1", jsonObject.get("id").toString());
        assertEquals( "New Name", jsonObject.get("firstName").toString());
        assertEquals( "New LastName", jsonObject.get("lastName").toString());
        assertEquals( "66888888888", jsonObject.get("phoneNo").toString());
        assertEquals( "new@new.com", jsonObject.get("email").toString());
        assertEquals( 15, jsonObject.get("age"));
    }

    @DisplayName("Test update customer should return not found")
    @Test
    void testUpdateCustomerNotFound() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getCustomer();
        Long reqId = 1L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isNotFound())
                .andReturn();

    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals( "1", jsonObject.get("id").toString());
        assertEquals( "Ryan", jsonObject.get("firstName").toString());
        assertEquals( "Giggs", jsonObject.get("lastName").toString());
        assertEquals( "66888888888", jsonObject.get("phoneNo").toString());
        assertEquals( "scb@gmail.com", jsonObject.get("email").toString());
        assertEquals( 23, jsonObject.get("age"));


    }

    @DisplayName("Test create customer with empty name should return error")
    @Test
    void testCreateCustomerWithEmptyName() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

}