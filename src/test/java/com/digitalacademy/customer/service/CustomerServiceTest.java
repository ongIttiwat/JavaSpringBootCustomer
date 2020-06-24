package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import static com.digitalacademy.customer.support.CustomerSupportTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    public void testGetAllCustomer() {

        List<Customer> customerList = getCustomerList();
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("Ryan", resp.get(0).getFirstName());
        assertEquals("Giggs", resp.get(0).getLastName());
        assertEquals("66888888888", resp.get(0).getPhoneNo());
        assertEquals("scb@gmail.com", resp.get(0).getEmail());
        assertEquals(23, resp.get(0).getAge().intValue());

        assertEquals(2, resp.get(1).getId().intValue());
        assertEquals("David", resp.get(1).getFirstName());
        assertEquals("Blackham", resp.get(1).getLastName());
        assertEquals("66888888888", resp.get(1).getPhoneNo());
        assertEquals("dbb@gmail.com", resp.get(1).getEmail());
        assertEquals(40, resp.get(1).getAge().intValue());

    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById(){
        Long reqParam = 1L;

        Customer customer = getCustomer();

        when(customerRepository.findAllById(reqParam)).thenReturn(customer);
        Customer resp = customerService.getCustomerById(reqParam);

        assertEquals(1, resp.getId().intValue());
        assertEquals("Ryan", resp.getFirstName());
        assertEquals("Giggs", resp.getLastName());
        assertEquals("66888888888", resp.getPhoneNo());
        assertEquals("scb@gmail.com", resp.getEmail());
        assertEquals(23, resp.getAge().intValue());
    }



    @DisplayName("Test get customer by name should return customer")
    @Test
    void testGetCustomerByName(){
        String reqParam = "Ryan";

        List<Customer> customerList = getCustomerList();
        when(customerRepository.findAllByFirstName(reqParam)).thenReturn(customerList);
        List<Customer> resp = customerService.getCustomerByFirstName(reqParam);

        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("Ryan", resp.get(0).getFirstName());
        assertEquals("Giggs", resp.get(0).getLastName());
        assertEquals("66888888888", resp.get(0).getPhoneNo());
        assertEquals("scb@gmail.com", resp.get(0).getEmail());
        assertEquals(23, resp.get(0).getAge().intValue());


    }


    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer() {
        Customer customerReq = getNewCustomer();

        Customer customerReturn = getNewCustomer();

        when(customerRepository.save(customerReq)).thenReturn(customerReturn);
        Customer resp = customerService.createCustomer(customerReq);

        assertEquals(1, resp.getId().intValue());
        assertEquals("New Name", resp.getFirstName());
        assertEquals("New LastName", resp.getLastName());
        assertEquals("66888888888", resp.getPhoneNo());
        assertEquals("new@new.com", resp.getEmail());
        assertEquals(15, resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer(){
        Long reqId = 2L;
        Customer reqCustomer = getNoonCustomer();

        Customer oldCustomer = getOldNoonCustomer();
        when(customerRepository.findAllById(reqId)).thenReturn(oldCustomer);
        when(customerRepository.save(reqCustomer)).thenReturn(reqCustomer);

        Customer resp = customerService.updateCustomer(reqId, reqCustomer);
        assertEquals(2, resp.getId().intValue());
        assertEquals("Noon", resp.getFirstName());
        assertEquals("Bow", resp.getLastName());
        assertEquals("66888888888", resp.getPhoneNo());
        assertEquals("bow@bow.com", resp.getEmail());
        assertEquals(5, resp.getAge().intValue());
    }

    @DisplayName("Test update customer name should return null")
    @Test
    void testUpdateCustomerFail(){
        Long reqId = 2L;
        Customer reqCustomer = getNoonCustomer();

        Customer oldCustomer = null;

        when(customerRepository.findAllById(reqId)).thenReturn(oldCustomer);
        when(customerRepository.save(reqCustomer)).thenReturn(null);

        Customer resp = customerService.updateCustomer(reqId, reqCustomer);

        assertEquals(null, resp);
    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer(){
        Long reqId = 2L;
        doNothing().when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(true, resp);
        assertTrue(resp);
    }

    @DisplayName("Test delete customer fail should return false")
    @Test
    void testDeleteCustomerFail(){
        Long reqId = 2L;
        doThrow(EmptyResultDataAccessException.class)
                .when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(false, resp);
        assertFalse(resp);
    }
}
