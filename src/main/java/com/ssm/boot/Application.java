package com.ssm.boot;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.Properties;

@SpringBootApplication
public class Application {


	@Bean
	@ConfigurationProperties(
			prefix = "spring.datasource"
	)
	public DataSource dataSource() {
		return new DataSource();
	}



	@Bean
	public SqlSessionTemplate sqlWriteSessionTemplate() throws Exception {
		return new SqlSessionTemplate(this.sqlSessionFactoryBean());
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(this.dataSource());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(this.dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage("com.ssm.boot");
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
		return sqlSessionFactoryBean.getObject();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
