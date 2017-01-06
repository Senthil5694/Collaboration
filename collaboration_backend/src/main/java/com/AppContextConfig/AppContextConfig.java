package com.AppContextConfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.Dao.UserDao;
import com.DaoImpl.UserDaoImpl;
import com.Model.Blog;
import com.Model.Forum;
import com.Model.Friend;
import com.Model.Job;
import com.Model.UserDetails;

@EnableWebMvc
@Configuration
@ComponentScan("com")
@EnableTransactionManagement
public class AppContextConfig {

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("senthil");
	    dataSource.setPassword("vasan");
	    Properties connectionProperties = new Properties();
		connectionProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		connectionProperties.setProperty("hibernate.show_sql", "true");
		connectionProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle11gDialect");
		connectionProperties.setProperty("hibernate.format_sql", "true");
		connectionProperties.setProperty("hibernate.jdbc.use_get_generated_keys", "true");
		//ConnectionProperties.setProperty("hibernate.default_schema", "");
		dataSource.setConnectionProperties(connectionProperties);
		return dataSource;
	   
	}
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		// properties.put("hibernate.dialect",
		// "org.hibernate.dialect.MySQLDialect");
		return properties;
	}
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    sessionBuilder.addProperties(getHibernateProperties());
	    
	    sessionBuilder.addAnnotatedClasses(UserDetails.class);
	    sessionBuilder.addAnnotatedClasses(Blog.class);
	    sessionBuilder.addAnnotatedClasses(Friend.class);
	    sessionBuilder.addAnnotatedClasses(Forum.class);
	    return sessionBuilder.buildSessionFactory();
	}
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
	        SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
	            sessionFactory);
	 
	    return transactionManager;
	}
	
	

}
