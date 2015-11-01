package org.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 31, 2015
 *
 */
@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Environment env = event.getApplicationContext().getEnvironment();
		logger.info("Application Version: {} build: {}",
				env.getProperty("info.build.version"),
				env.getProperty("info.build.build"));
	}

}
