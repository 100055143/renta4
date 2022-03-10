package com.curso.hellospring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.curso.hellospring.beans.HelloBean;
import com.curso.hellospring.beans.config.SimpleConfigBean;

public class AppAnnotations {
	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleConfigBean.class);
		HelloBean helloBean = ctx.getBean(HelloBean.class);
		helloBean.printHello();

//        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext3.xml");
//        HelloBean helloBean = beanFactory.getBean("helloBean", HelloBean.class);
//        System.out.println(helloBean);	
		
		/*
		 * 1. La anotación @Autowired permite inyectar propiedades por tipo o nombre.
		 * 
		 * @Autowired
		 * private TestDTO1 testDto;
		 * 
		 *  2. En el caso de conflicto por tipo en la anotación Autowired, se puede utilizar @Primary para indicar el Bean que tiene prioridad
		 *  
				@Bean(name="helloBean") 
				@Primary
				public HelloBean helloBean1(){
					return new HelloBean();
				}
		 * 
				@Bean(name="helloBean") 
				public HelloBean helloBean2(){
					return new HelloBean();
				}
				
				@Autowired
				private HelloBean helloBean;
				
			3. Con @Qualifier se tendrá mucho más control sobre el proceso de auto-wiring.
			
				@Autowired
				@Qualifier("helloBean2")
				private HelloBean helloBean;
				
			4. @Resource es otra alternativa a @Autowired. La principal diferencia entre ambas anotaciones es que @Autowired funciona basado en el tipo del bean, mientras que Resource lo hace sobre sobre el nombre. @Autowired está disponible en SPring, @Resource en la JSR 330.
			
				@Resource
				private HelloBean helloBean;
			
			5. Las anotaciones @PostConstruct and @PreDestroy también están disponibles en la JSR 330 y permiten controlar el proceso de creación y destrucción de beans.
		
		 


			6. 	JSR 330 (Inject) vs Spring
				Spring 				javax.inject.* 			javax.inject restrictions / comments
				@Autowired 			@Inject 				@Inject has no 'required' attribute; can be used with Java 8’s Optional instead.
				@Component 			@Named 					Just a way to identify named components. Not as flexible as @Component
				@Scope("singleton") @Singleton 				The JSR-330 default scope is like Spring’s prototype. However, in order to keep it consistent with Spring’s general defaults, 
															a JSR-330 bean declared in the Spring container is a singleton by default. 
															In order to use a scope other than singleton, you should use Spring’s @Scope annotation. 
															javax.inject also provides a @Scope annotation. Nevertheless, this one is only intended to be used for creating your own annotations.
				@Qualifier 			@Qualifier / @Named 	javax.inject.Qualifier is just a meta-annotation for building custom qualifiers. Concrete String qualifiers (like Spring’s @Qualifier with a value)
															can be associated through javax.inject.Named.

		 	
		 	7. EL contexto AnnotationConfigApplicationContext soporta varias características interesantes, por ejemplo:
		 			* Registro de múltiples clases marcadas con la anotación @ComponentScan.
		 			ApplicationContext ctx = new AnnotationConfigApplicationContext(MyServiceImpl.class,
						Dependency1.class, Dependency2.class);
					MyService myService = ctx.getBean(MyService.class);
					myService.doStuff();
					
					* Registro con el método "register".
					AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
					ctx.register(AppConfig.class, OtherConfig.class);
					ctx.register(AdditionalConfig.class);
					ctx.refresh();
					MyService myService = ctx.getBean(MyService.class);
					myService.doStuff();
					
					* Scan de paquetes en busca de beans sin usar @ComponentScan
					AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
					ctx.scan("com.acme");
					ctx.refresh();
					MyService myService = ctx.getBean(MyService.class);

		 	8. 	La anotación @Bean es equivalente al elemento <bean /> por lo tanto tiene algunas propiedades comunes.
				public class Foo {
					public void init() {
					// initialization logic
					}
				}
				
				public class Bar {
					public void cleanup() {
					// destruction logic
					}
				}
				
				@Configuration
				public class AppConfig {
					@Bean(initMethod = "init")
					public Foo foo() {
						return new Foo();
					}
				
					@Bean(destroyMethod = "cleanup")
					public Bar bar() {
						return new Bar();
					}
				}		
				
				 * Un bean puede tener múltiples nombres, esto es conocido como "aliasing":

				@Bean(name = { "dataSource", "subsystemA-dataSource", "subsystemB-dataSource" })
				public DataSource dataSource() {
					// instantiate, configure and return DataSource bean...
				}
			
			9. Cuando se cuenta con varias clases de configuración, es posible importarlas en una clase concreta con @Import
				@Configuration
				public class ConfigA {
					@Bean
					public A a() {
						return new A();
					}
				}
				@Configuration
				@Import(ConfigA.class)
				public class ConfigB {
					@Bean
					public B b() {
						return new B();
					}
				}
				
				ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);
				A a = ctx.getBean(A.class);
				B b = ctx.getBean(B.class);				 

		 	10. En algunos casos es conveniente combinar anotaciones y XML, dado que las anotaciones no sustituyen el 100% de las funcionalidades disponibles en los XML.
		 	
		 	@Configuration
			public class AppConfig {
			@Autowired
			private DataSource dataSource;

				@Bean
				public AccountRepository accountRepository() {
					return new JdbcAccountRepository(dataSource);
				}
	
				@Bean
				public TransferService transferService() {
					return new TransferService(accountRepository());
				}
			}
			
			system-test-config.xml:
				<beans>
					<!-- enable processing of annotations such as @Autowired and @Configuration -->
					<context:annotation-config/>
					<context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>
					<bean class="com.acme.AppConfig"/>
					<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource">
						<property name="url" value="${jdbc.url}"/>
						<property name="username" value="${jdbc.username}"/>
						<property name="password" value="${jdbc.password}"/>
					</bean>
				</beans>
			
			
			jdbc.properties:
			jdbc.url=jdbc:hsqldb:hsql://localhost/xdb
			jdbc.username=sa
			jdbc.password=
			
			public static void main(String[] args) {
				ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/curso/hellospring/system-test-config.xml");
				TransferService transferService = ctx.getBean(TransferService.class);
			}			
			
			
			11. En el caso anterior, el fichero XML es el que se carga en la aplicación y gracias a <context:annotation-config/> se puede cargar la clase con @Configuration y sus beans.
			Es posible hacerlo justo al reves, es decir, partiendo de una clase de configuración cargar un fichero XML, para ello se utiliza la anotación @ImportResource.

			@Configuration
			@ImportResource("classpath:/com/curso/hellospring/properties-config.xml")
			public class AppConfig {
				
				@Value("${jdbc.url}")
				private String url;
				
				@Value("${jdbc.username}")
				private String username;
				
				@Value("${jdbc.password}")
				private String password;
				
				@Bean
				public DataSource dataSource() {
					return new DriverManagerDataSource(url, username, password);
				}
			}
			
			properties-config.xml
			<beans>
				<context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>
			</beans>
			
			jdbc.properties
			jdbc.url=jdbc:hsqldb:hsql://localhost/xdb
			jdbc.username=sa
			jdbc.password=
			
			public static void main(String[] args) {
				ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
				TransferService transferService = ctx.getBean(TransferService.class);
			// ...
			}			
			
			12. Es posible establecer perfiles a grupos de beans y posteriormente utilizarlos desde el contexto. Por ejemplo:
			
			
			@Configuration
			@Profile("dev")
			public class AppConfig1 {
				@Bean
				.....
				@Bean
				.....
				
			}			
			
			@Configuration
			@Profile("pro")
			public class AppConfig2 {
				@Bean
				.....
				@Bean
				.....
				
			}	
			
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.getEnvironment().setActiveProfiles("dev");
			ctx.register(AppConfig1.class, AppConfig2.class);
			ctx.refresh();
			
			También es posible habilitar múltiples perfiles.
			ctx.getEnvironment().setActiveProfiles("profile1", "profile2");
			
			13. Para inyectar ficheros de propiedades en la configuración de un proyecto es posible utilizar la anotación @PropertySource
			
			@Configuration
			@PropertySource("classpath:/com/curso/app.properties")
			public class AppConfig {
				@Autowired
				Environment env;
				
				@Bean
				public TestBean testBean() {
					TestBean testBean = new TestBean();
					testBean.setName(env.getProperty("testbean.name"));
					return testBean;
				}
			}
		 * 
		 * */
	}
}
