package com.customer.ms.model;

public class Customer {

	private String cusId;
	private String cusName;
	private String address;

	public Customer(String cusId, String cusName, String address) {
		this.cusId = cusId;
		this.cusName = cusName;
		this.address = address;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
