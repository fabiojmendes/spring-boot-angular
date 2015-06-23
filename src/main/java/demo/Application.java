package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		notifyStartup();
	}

	private static void notifyStartup() {
		try {
			Process notify = new ProcessBuilder("systemd-notify", "--ready").start();
			notify.waitFor();
		} catch (Exception e) {
			logger.warn("Error notifying the application Startup: [{}] {}", e.getClass(), e.getMessage());
		}
	}
}
