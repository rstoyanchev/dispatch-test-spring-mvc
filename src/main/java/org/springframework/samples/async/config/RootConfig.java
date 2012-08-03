package org.springframework.samples.async.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.samples.async.service.Customer;
import org.springframework.samples.async.service.CustomerService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {CustomerService.class})
public class RootConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @SuppressWarnings("deprecation")
	@Bean
    public SessionFactory sessionFactory() {

        Properties props = new Properties();
        props.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
        props.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES , "customers.sql");
        props.put(org.hibernate.cfg.Environment.DIALECT, HSQLDialect.class.getName());
        props.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");

        return new LocalSessionFactoryBuilder(dataSource())
	        	.addAnnotatedClasses(Customer.class)
	        	.addProperties(props)
	        	.buildSessionFactory();
    }

  @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().build();
    }

}
