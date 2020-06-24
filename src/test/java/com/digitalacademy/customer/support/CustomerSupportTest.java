package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {

    public static List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("Giggs");
        customer.setPhoneNo("66888888888");
        customer.setEmail("scb@gmail.com");
        customer.setAge(23);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("David");
        customer.setLastName("Blackham");
        customer.setPhoneNo("66888888888");
        customer.setEmail("dbb@gmail.com");
        customer.setAge(40);
        customerList.add(customer);

        return customerList;
    }

    public static Customer getCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("Giggs");
        customer.setPhoneNo("66888888888");
        customer.setEmail("scb@gmail.com");
        customer.setAge(23);
        return customer;
    }

    public static Customer getNewCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("New Name");
        customer.setLastName("New LastName");
        customer.setPhoneNo("66888888888");
        customer.setEmail("new@new.com");
        customer.setAge(15);
        return customer;
    }

    public static Customer getNoonCustomer(){
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("Noon");
        customer.setLastName("Bow");
        customer.setPhoneNo("66888888888");
        customer.setEmail("bow@bow.com");
        customer.setAge(5);
        return customer;
    }

    public static Customer getOldNoonCustomer() {
        Customer oldCustomer = new Customer();
        oldCustomer.setFirstName("OldNoon");
        oldCustomer.setLastName("OldBow");
        oldCustomer.setPhoneNo("66888888888");
        oldCustomer.setEmail("old@old.com");
        oldCustomer.setAge(20);

        return oldCustomer;
    }

}
