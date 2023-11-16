package com.camplex.project.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration 
@PropertySource("classpath:/config.properties")
public class DBConfig {
	
	@Autowired
	private ApplicationContext applicationContext; 
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource(HikariConfig config) {

		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
		
	}
	
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);

		sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
		
		//sessionFactoryBean.setTypeAliasesPackage("com.camplex.project.member.model.dto");
		
		sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		
		return sessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
		return new SqlSessionTemplate(sessionFactory);
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
