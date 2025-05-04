package org.event.manage.eventmanage.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.event.manage.eventmanage.util.HibernateUtilService;

@WebListener
public class HibernateStartupConfig implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Hibernate...");
        HibernateUtilService.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("Shutting down Hibernate...");
//        HibernateUtilService.shutdown();
    }
}
