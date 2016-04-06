package org.wlfek.push;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// application.properties 보다 우선시 됨.
@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages="org.wlfek.push", entityManagerFactoryRef="emf")
public class JPAConfig {
 
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    	em.setDataSource(dataSource());	
		em.setPackagesToScan("org.wlfek");
        // 각 구현체의 프로퍼티 확장 및 설정
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaProperties(additionalJpaProperties());	
        return em;
    }
    
    /**
     * 각 구현체의 프로퍼티 확장 및 설정
     * @return Properties
     */
    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.generate-ddl", "true");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        return properties;
    }
 
    // H2 DB 설정
//    @Bean(destroyMethod = "shutdown")
//    public EmbeddedDatabase dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .setName("slipp")
//                .build();
//    }
// 
//	@Bean
//	public DataSource dataSource() throws SQLException {
//		   
//	    OracleDataSource dataSource = new OracleDataSource();
//	    dataSource.setUser(username);
//	    dataSource.setPassword(password);
//	    dataSource.setURL(url);
//	    dataSource.setImplicitCachingEnabled(true);
//	    dataSource.setFastConnectionFailoverEnabled(true);
//	    return dataSource;
//	}
	
    
    @Bean
    public DataSource dataSource(){
    	HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://127.0.0.1:3306/push"); 
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("dlfhdia33");
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setPoolName("SpringBootHikariCP");
        hikariConfig.setMinimumIdle(3);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("springHikariCP");
//        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
//        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
//        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
//        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        HikariDataSource hikariDs = new HikariDataSource(hikariConfig);
        return hikariDs;
    }   
    
    
    // Transaction 설정
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    
}