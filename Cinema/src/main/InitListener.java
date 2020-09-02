package main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.ConnectionManager;

public class InitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	// TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent event)  {

    	ConnectionManager.open();
    	System.out.println("done");

    }

}
