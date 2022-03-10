package com.curso.hellospring.beans;

import com.curso.hellospring.dto.TestDTO1;
import com.curso.hellospring.dto.TestDTO2;

public class SimpleBean {
	
	
	private TestDTO1 testDto1;
	
	private TestDTO2 testDto2;
	
	public SimpleBean(TestDTO1 dto1, TestDTO2 dto2) {
		System.out.println("Invocando al constructor con DTO1: "+dto1);
		System.out.println("Invocando al constructor con DTO2: "+dto2);
	}

	public TestDTO2 getTestDto2() {
		return testDto2;
	}

	public void setTestDto2(TestDTO2 testDto2) {
		this.testDto2 = testDto2;
	}

	@Override
	public String toString() {
		return "SimpleBean [testDto1=" + testDto1 + ", testDto2=" + testDto2 + "]";
	}
}
