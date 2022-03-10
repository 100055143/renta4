package com.curso.hellospring.beans;

public class HelloBean {
	 	String propiedad;
	    
	 	public static HelloBean crearBean() {
	 		System.out.println("Método de factoría");
	 		return new HelloBean();
	 	}
	 	
	    public void printHello()
	    {
	        System.out.println("Hello World desde "+propiedad);
	    }

		public String getPropiedad() {
			return propiedad;
		}

		public void setPropiedad(String propiedad) {
			this.propiedad = propiedad;
		}
}
