package com.curso.hellospring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.curso.hellospring.beans.SimpleBean;

public class AppDI {

	public static void main(String[] args) {
	     
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext2.xml");
        SimpleBean simpleBean = beanFactory.getBean("simpleBean", SimpleBean.class);
        System.out.println(simpleBean);
        
        /*
         
         1. Es posible enviar valores concretos a los beans por medio de parámetros en los constructores.
         public class ExampleBean {
			private int numero;
			private String cadena;
			@ConstructorProperties({"numero", "cadena"})
			public ExampleBean(int numero, String cadena) {
				this.numero = numero;
				this.cadena = cadena;
			}
		}
		
		
		<bean id="exampleBean" class="paquete.ExampleBean">
			<constructor-arg type="int" value="7500000"/>
			<constructor-arg type="java.lang.String" value="42"/>
		</bean>
         
         2. Por otro lado, el argumento "index" permite controlar el orden en el que Spring enviará los parámetros al constructor.
         
		<bean id="exampleBean" class="paquete.ExampleBean">
			<constructor-arg index="0", type="int" value="75000"/>
			<constructor-arg index="1", type="java.lang.String" value="hola"/>
		</bean>
        
         3. La DI basada en setters se puede realizar con el elemento <property> y la propiedad "ref". Por ejemplo:
         
          <bean id="exampleBean" class="examples.ExampleBean">
			<!-- setter injection using the nested ref element -->
			<property name="beanOne">
				<ref bean="anotherExampleBean"/>
			</property>
			<!-- setter injection using the neater ref attribute -->
			<property name="beanTwo" ref="yetAnotherBean"/>
			<property name="integerProperty" value="1"/>
		</bean>
		<bean id="anotherExampleBean" class="examples.AnotherBean"/>
		<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
			
		
		4. En Spring la DI también puede basarse en constructores.
			<bean id="simpleBean2" class="com.curso.hellospring.SimpleBean2">
				<constructor-arg>
					<ref bean="dto1"/>
				</constructor-arg>
				<constructor-arg ref="dto2"/>
				<constructor-arg type="int" value="1"/>
			</bean>
			<bean id="dto1" class="com.curso.hellospring.dto.TestDTO1"/>
			<bean id="dto2" class="com.curso.hellospring.dto.TestDTO2"/>
			
		5. En lugar de usar un constructor, es posible invocar a un método static de factoría que retorne una instancia del objeto.
		<bean id="simpleBean2" class="com.curso.hellospring.SimpleBean2" factory-method="createInstance">
			<constructor-arg ref="dto1"/>
			<constructor-arg ref="dto2"/>
			<constructor-arg value="1"/>
		</bean>
		<bean id="dto1" class="com.curso.hellospring.dto.TestDTO1"/>
		<bean id="dto2" class="com.curso.hellospring.dto.TestDTO2"/>
        
        Los argumentos son envados en el mismo orden de aparición al método estático "createInstance"
        
        
		6. Es posible utilizar p-namespace para simplificar el uso de <property/>
		
		<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
			<!-- results in a setDriverClassName(String) call -->
			<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
			<property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
			<property name="username" value="root"/>
			<property name="password" value="root"/>
		</bean>
		
		
		<beans xmlns="http://www.springframework.org/schema/beans"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				xmlns:p="http://www.springframework.org/schema/p"
				xsi:schemaLocation="http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans.xsd">
			<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close"
				p:driverClassName="com.mysql.jdbc.Driver"
				p:url="jdbc:mysql://localhost:3306/mydb"
				p:username="root"
				p:password="root"/>
		</beans>
		
		7. Utilizando PropertyPlaceholderConfigurer es posible obtener un objeto "java.util.Properties" partiendo de un listado.
		<bean id="mappings" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
			<!-- typed as a java.util.Properties -->
			<property name="properties">
				<value>
					jdbc.driver.className=com.mysql.jdbc.Driver
					jdbc.url=jdbc:mysql://localhost:3306/mydb
				</value>
			</property>
		</bean>
		
		
		8. Es posible declarar Propiedades, Listas, Sets y Mapas.
		
		<bean id="simpleBean2" class="com.curso.hellospring.SimpleBean2">
			<!-- setEmails(java.util.Properties) call -->
			<property name="emails">
				<props>
					<prop key="administrator">administrator@example.org</prop>
					<prop key="support">support@example.org</prop>
					<prop key="development">development@example.org</prop>
				</props>
			</property>

			<!-- setSimpleList(java.util.List) call -->
			<property name="simpleList">
				<list>
					<value>a list element followed by a reference</value>
					<ref bean="dto1" />
				</list>
			</property>
		
			<!-- setSimpleMap(java.util.Map) call -->
			<property name="someMap">
				<map>
					<entry key="entrada" value="cadena"/>
					<entry key ="ref" value-ref="dto2"/>
				</map>
			</property>
		
			<!-- setSimpleSet(java.util.Set) call -->
			<property name="simpleSet">
				<set>
					<value>Cadena simple</value>
					<ref bean="myDataSource" />
				</set>
			</property>
		</bean>		
		
		
		9. Es posible hacer un "merge" de propiedades.
		
		<beans>
				<bean id="parent" abstract="true" class="com.curso.hellospring.SimpleBeanAbstract">
					<property name="emails">
						<props>
							<prop key="administrator">administrator@example.com</prop>
							<prop key="support">support@example.com</prop>
						</props>
					</property>
				</bean>
				
				<bean id="child" parent="parent" class="com.curso.hellospring.SimpleBean">
					<property name="emails">
						<props merge="true">
							<prop key="sales">sales@example.com</prop>
							<prop key="support">support@example.co.uk</prop>
						</props>
					</property>
				</bean>
		<beans>		
		
		
		10. Es posible crear beans que se creen "perezosamente".
		
		<bean id="lazy" class="com.foo.ExpensiveToCreateBean" lazy-init="true"/>
		<bean name="not.lazy" class="com.foo.AnotherBean"/>
		
		<beans default-lazy-init="true">
			<!-- no beans will be pre-instantiated... -->
		</beans>
		
		11. Para controlar el ciclo de vida de un bean se pueden implementar métodos de inicialización y destrucción.
		
		<bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>
		public class ExampleBean {
				public void init() {
					// inicialización del bean
				}
		}		
		
		<bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="cleanup"/>			
		public class ExampleBean {
			public void cleanup() {
				//Destrucción del bean
			}
		}	
		
		12. Para controlar el ciclo de vida de todos los beans a nivel global:		
		
		<beans default-init-method="init" default-destroy-method="init">
			<bean ...... />
			<bean ...... />
		</beans>
		
		
		13. Herencia de beans en SPring 
		
		   <bean id = "helloWorld" class = "com.HelloWorld">
      			<property name = "message1" value = "Hello World!"/>
      			<property name = "message2" value = "Hello again!"/>
   			</bean>

   			<bean id ="helloIndia" class = "com.HelloSpain" parent = "helloWorld">
      			<property name = "message1" value = "Hello Madrid!"/>
      			<property name = "message3" value = "Hiiii!"/>
   			</bean>
   			
   			La clase HelloWorld debe contener los atributos "message1" y "message2".
   			La clase HelloSpain debe contener los atributos "message1", "message2" y "message3".
   			
   			En Spring es posible definir "plantillas de beans". No es necesario que la plantilla tenga el atributo "class" y debe ser marcada con el atributo "abstract" con un valor igual a "true"
   			

		   <bean id = "helloWorld" abstract = "true">
      			<property name = "message1" value = "Hello World!"/>
      			<property name = "message2" value = "Hello again!"/>
      			<property name = "message3" value = "bye!"/>
   			</bean>

   			<bean id ="helloIndia" class = "com.HelloSpain" parent = "helloWorld">
      			<property name = "message1" value = "Hello Madrid!"/>
      			<property name = "message3" value = "Hiiii!"/>
   			</bean>

			Todas las configuraciones son heredadas del bean padre, excepto:
			Dependencias con "depends on", modo del bean "singleton", "prototype" u otro, lazy init.
			
		14. Autowire en Spring por nombre o por tipo:
		
		
			<bean id="coche" class="com.Coche" autowired="byName">
			</bean>

			<bean id="marca" class="com.Marca">
			</bean>
			
			En la clase com.Coche existirá un atributo llamado "marca" del tipo "com.Marca".
			
			<bean id="coche" class="com.Coche" autowired="byType">
			</bean>

			<bean id="marca" class="com.Marca">
			</bean>
			
			En la clase com.Coche existirá un atributo del tipo "com.Marca" y se inyectará automaticamente con el bean con id "marca".
		* */
        
	}
}
