package com.customer.ms.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.customer.ms.constants.CustomerConstants;
import com.customer.ms.model.Customer;

@Repository
public class CustomerDAO {

	private static Map<String, Customer> cusMap = new HashMap<String, Customer>();

	static {
		initCus();
	}

	private static void initCus() {
		Customer cus1 = new Customer("C01", "Smith", "NY");
		Customer cus2 = new Customer("C02", "Smith", "CT");
		Customer cus3 = new Customer("C03", "Smith", "CA");

		cusMap.put(cus1.getCusId(), cus1);
		cusMap.put(cus2.getCusId(), cus2);
		cusMap.put(cus3.getCusId(), cus3);

	}

	public Customer getCustomer(String cusId) {
		return cusMap.get(cusId);
	}

	public List<Customer> getAllCustomers() {
		Collection<Customer> c = cusMap.values();
		List<Customer> list = new ArrayList<Customer>();
		list.addAll(c);
		return list;
	}

	public Customer addCustom(Customer cus) {
		cusMap.put(cus.getCusId(), cus);
		return cus;
	}

	public String delCustom(String cusId) {
		cusMap.remove(cusId, cusMap.get(cusId));
		return CustomerConstants.successDeleteMessage + " " + cusId;
	}

	public Customer updateCustomer(Customer cus) {
		cusMap.put(cus.getCusId(), cus);
		return cus;
	}

}
