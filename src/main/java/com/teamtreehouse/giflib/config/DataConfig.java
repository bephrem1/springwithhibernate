package com.teamtreehouse.giflib.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration //Configurations are picked up at boot time
@PropertySource("app.properties") //Allows spring to know the path to the properties we reference here
public class DataConfig {

    @Autowired
    Environment env;

    //Spring equivalent of the SessionFactory in a normal Hibernate java project, available all throughout the application
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Resource config = new ClassPathResource("hibernate.cfg.xml"); //Load config file into config resource
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean(); //Make new local session factory bean
        sessionFactory.setConfigLocation(config); //Tell spring that our configuration for the session factory is
                                                  // where config was set to with ClassPathResource call
        sessionFactory.setPackagesToScan(env.getProperty("giflib.entity.package"));
                                            //Instead of having statements of every class in the config file of classes
                                            //to scan we can set packages to scan for classes that need to be set up in
                                            //database here

                                            //It's called "Externalization", that's how we will specify the packages to
                                            //scan. It prevents us weaving properties into source code

                                            //This evaluates to what app.properties has for the value, avoids hardcoding

        sessionFactory.setDataSource(dataSource()); //

        return sessionFactory;
    }

    @Bean //@Bean annotate methods MUST be public
    public DataSource dataSource() {
        //We will use DBCP here (Database Connection Pooling) to renew already created connections
        BasicDataSource ds = new BasicDataSource();

        //Driver class name
        ds.setDriverClassName(env.getProperty("giflib.db.driver"));

        //Set URL
        ds.setUrl(env.getProperty("giflib.db.url"));

        //Set username and password
        ds.setUsername(env.getProperty("giflib.db.username"));
        ds.setPassword(env.getProperty("giflib.db.password"));

        return ds;
    }

}
