package sheng.gcp.web.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "goldbookEntityManagerFactory",
        transactionManagerRef = "goldbookTransactionManager",
        basePackages = { "sheng.gcp.web.server.model.goldbook"},
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class
)
@EnableTransactionManagement
public class GoldBookDBConfig {

    final private static String username = "goldbook";
    final private static String password = "goldbook520";
    final private static String url = "jdbc:mysql://localhost:3306/goldbook";
    final private static String driverClassName = "com.mysql.jdbc.Driver";

    @Primary
    @Bean(name = "goldbookDataSource")
    public DataSource goldbookDataSource() {
        return DataSourceBuilder
                .create()
                .username(this.username)
                .password(this.password)
                .url(this.url)
                .driverClassName(this.driverClassName)
                .build();
    }

    @Primary
    @Bean(name = "goldbookEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean goldbookEntityManagerFactory () {
        LocalContainerEntityManagerFactoryBean entityManagerFactory
                = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(goldbookDataSource());
        entityManagerFactory.setPackagesToScan(new String[] { "sheng.gcp.web.server.model.goldbook" });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;

    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.ejb.entitymanager_factory_name", "goldbookEntityManager");
        properties.setProperty("hibernate.autoReconnect", "true");
        properties.setProperty("hibernate.autoReconnectForPools", "true");
        properties.setProperty("hibernate.is-connection-validation-required", "true");
        return properties;
    }

    @Primary
    @Bean(name = "goldbookTransactionManager")
    public PlatformTransactionManager goldbookTransactionManager (
            @Qualifier("goldbookEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "goldbookJdbcTemplate")
    public JdbcTemplate goldbookTemplate(@Qualifier("goldbookDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
