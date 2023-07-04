package com.zoho.ecommerce;

class User {
	private int id;
	private String name;
	private String password;
	private long mobile_no;
	private int age;
	private String gender;
	private String address;

	User(int id, String name, String password, long mobile_no, int age, String gender, String address) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mobile_no = mobile_no;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public long getMobileNo() {
		return mobile_no;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getAddress() {
		return address;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMobileNo(long mobileNo) {
		this.mobile_no = mobileNo;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
