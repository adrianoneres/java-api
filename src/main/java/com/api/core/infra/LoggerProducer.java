package com.api.core.infra;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerProducer {
   
	@Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        Logger logger = Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        setLoggerLevel(logger);
        return logger;
    }
	
	private void setLoggerLevel(Logger logger) {
		String environment = System.getProperty("environment");		
		boolean development = environment == null || environment.equalsIgnoreCase("dev");
		boolean test = environment != null && environment.equalsIgnoreCase("test");
		boolean production = environment != null && environment.equalsIgnoreCase("prod");
				
		if (development) {
			logger.setLevel(Level.INFO);
		} else if (test) {
			logger.setLevel(Level.INFO);
		} else if (production) {
			logger.setLevel(Level.ERROR);
		}
	}
}
