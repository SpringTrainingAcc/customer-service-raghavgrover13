package com.customer.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customer.ms.constants.CustomerConstants;
import com.customer.ms.dao.CustomerDAO;
import com.customer.ms.model.Customer;
import com.customer.ms.model.CustomerM;
import com.customer.ms.service.CustomerService;

@RestController
public class CustomController {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/hello")
	public String hello() {
		return "Greetings from Spring Boot 1.0";
	}

	/* Request Method to get all Customers */
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Customer> getCustomers() {
		List<Customer> list = customerDAO.getAllCustomers();
		return list;
	}

	/* Request Method to get Customer based on Customer ID */
	@RequestMapping(value = "/customers/{cusId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Customer getCustomer(@PathVariable("cusId") String cusId) {
		return customerDAO.getCustomer(cusId);
	}

	/* Request Method to add a new customer */
	@RequestMapping(value = "/customers", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Customer addCustomer(@RequestBody Customer customer) {

		return customerDAO.addCustom(customer);
	}

	/* Request Method to delete a single customer */
	@RequestMapping(value = "/customersdelete/{cusId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteCustomer(@PathVariable("cusId") String cusId) {
		return customerDAO.delCustom(cusId);
	}

	/* Request Method to update a single customer's name and address */
	@RequestMapping(value = "/customersupdate", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Customer updateCustomers(@RequestBody Customer customer) {
		return customerDAO.updateCustomer(customer);
	}
	
	/*
	 * Mongo Customer Request Mappings
	 */
	
	
	@RequestMapping(value= "/mongoCustomers", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<CustomerM> getMongoCustomers()
	{
	
		List <CustomerM> list= customerService.findAll();
		return list;
	}
	
	@RequestMapping(value= "/mongoCustomer/{cusId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public CustomerM getMongoCustomers(@PathVariable("cusId") String cusId)
	{
	
		return customerService.findById(cusId);
	}
	

	@RequestMapping(value = "/mongoCustomer", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerM addMongoCustomer(@RequestBody CustomerM customerM) {

		return customerService.addCustomer(customerM);
	}
	
	@RequestMapping(value = "/mongoCustomerUpdate", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerM updateMongoCustomer(@RequestBody CustomerM customerM) {

		return customerService.updateCustomer(customerM);
	}
	
	@RequestMapping(value= "/mongoCustomerDelete/{cusId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteMongoCustomer(@PathVariable("cusId") String cusId) {

		return CustomerConstants.successDeleteMessage+ " "+ customerService.deleteCustomer(cusId);
	}

}
