package com.listeners.rest;

public class testDto {
	String name;
	Integer age;
	String school;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	@Override
	public String toString() {
		return "testDto [name=" + name + ", age=" + age + ", school=" + school + "]";
	}
	
}

