package com.curso.hellospring.beans;

public class DefaultServiceLocator {
		public HelloBean createClientServiceInstance() {
			System.out.println("Factory Bean Method");
			return new HelloBean();
		}
}
