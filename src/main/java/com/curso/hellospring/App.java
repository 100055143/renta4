package com.curso.hellospring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.curso.hellospring.beans.HelloBean;

/**
 * Hello world!
 *
 */
public class App 
{
           public static void main(String[] args) {
     
                  ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
                  ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml", "applicationContext.xml"});
                  
                  //HelloBean myBean = (HelloBean) beanFactory.getBean("HelloBean");
                  HelloBean myBean = beanFactory.getBean("HelloBean", HelloBean.class);
                  myBean.printHello();

     
                  /*
                  1. posible incluir instrucciones "import" en alguno de los ficheros xml cargados en el ApplicationContext.
                   <import resource="services.xml"/>
                   <import resource="daos.xml"/>
                  
                  2. Un bean puede ser etiquetado con un alias de la siguiente forma:
                   <alias name="beanName" alias="aliasName"/>
                   Posteriormente, dicho bean puede ser referencia u obtenido del contexto de Spring utilizando el alias asignado.
                  
                  3. Es posible instanciar beans partiendo de un método estático de factoría.
                   <bean  id="testService"
                   		class="mipaquete.TestService"
                  	    factory-method="createInstance"/>
                  
                  	public class TestService {
                   		private static TestService testService = new TestService();
                  		private TestService() {}
                  		public static TestService createInstance() {
                  			return testService;
                  		}
                 	}
                 	
                 	4. Es posible instanciar beans partiendo de un método NO estático de factoría.
                 	
                 	<!-- the factory bean, which contains a method called createInstance() -->
						<bean id="serviceLocator" class="examples.DefaultServiceLocator">
 							<!-- Dependencias y propiedades -->
						</bean>
						<!-- the bean to be created via the factory bean -->
						<bean 	id="clientService"
 								factory-bean="serviceLocator"
 								factory-method="createClientServiceInstance" />

						public class DefaultServiceLocator {
 							private static ClientService clientService = new ClientServiceImpl();
 							private DefaultServiceLocator() {}
 							public ClientService createClientServiceInstance() {
 								return clientService;
 							}
						}
					
					5. Una clase de factoría puede declarar más de un método de factoría, sin embargo para utilizar cada uno de ellos es necesario crear una referencia de bean independiente.
					
					<bean id="serviceLocator" class="examples.DefaultServiceLocator">
 						<!-- dependencias -->
					</bean>
					<bean id="clientService"
 						factory-bean="serviceLocator"
 						factory-method="createClientServiceInstance"/>
					<bean 	id="accountService"
 							factory-bean="serviceLocator"
 							factory-method="createAccountServiceInstance"/>
 							
 							
					public class DefaultServiceLocator {
						private static ClientService clientService = new ClientServiceImpl();
						private static AccountService accountService = new AccountServiceImpl();
						private DefaultServiceLocator() {}
						public ClientService createClientServiceInstance() {
							return clientService;
						}
						public AccountService createAccountServiceInstance() {
							return accountService;
						}
					} 							
                 */
        }
     
    }