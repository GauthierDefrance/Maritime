package log;

import config.GameConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Utility class used to generate Log4j logger.
 * @version 1.0
 */
public class LoggerUtility {

	public static Logger getLogger(Class<?> logClass) {
		PropertyConfigurator.configure(GameConfiguration.HTML_LOG_CONFIG);
		String className = logClass.getName();
		return Logger.getLogger(className);
	}
}
