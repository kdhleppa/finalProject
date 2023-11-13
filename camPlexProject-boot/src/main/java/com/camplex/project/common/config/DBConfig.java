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

@Configuration // @Configuration : 구성
// 스프링 어플리케이션을 구성하기 위한 설정용 Bean 생성 클래스
@PropertySource("classpath:/config.properties")
public class DBConfig {
	
	//import org.springframework.context.ApplicationContext;
	@Autowired
	private ApplicationContext applicationContext; // application scope 객체
	
	@Bean
	// - 개발자가 수동으로 bean을 등록하는 어노테이션
	// - @Bean 어노테이션이 작성된 메서드에서 반환된 객체는
	// Spring Container가 관리함
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	// properties 파일의 내용을 이용해서 생성되는 bean을 설정하는 어노테이션
	// prefix를 지정하여 spring.datasource.hikari으로 시작하는 설정을 모두 적용
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource(HikariConfig config) {
							// 매개변수에 bean이 자동으로 주입된다(DI)
		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	
	////////////////////////////Mybatis 설정 추가 ////////////////////////////
	//SqlSessionFactory : SqlSession을 만드는 객체
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);

		//매퍼 파일이 모여있는 경로 지정
		sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
		
		//별칭을 지정해야하는 DTO가 모여있는 패키지 지정
		//-> 해당 패키지에 있는 모든 클래스가 클래스명으로 별칭이 지정됨
		//sessionFactoryBean.setTypeAliasesPackage("edu.kh.project.member.model.dto, edu.kh.project.board.model.dto");
		sessionFactoryBean.setTypeAliasesPackage("edu.kh.project.member.model.dto");
		
		//마이바티스 설정 파일 경로 지정
		sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		
		//SqlSession 객체 반환
		return sessionFactoryBean.getObject();
	}
	
	//SqlSessionTemplate : 기본 SQL 실행 + 트랜잭션 처리
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
		return new SqlSessionTemplate(sessionFactory);
	}

	//DataSourceTransactionManager : 트랜잭션 매니저
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
