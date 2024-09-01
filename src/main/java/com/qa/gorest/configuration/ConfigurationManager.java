package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream ip;

	public Properties initProp() {
		prop = new Properties();

		// maven: cmd line argument:
		// mvn clean install -Denv = "qa"

		String envName = System.getProperty("env");
		System.out.println("Running tests on env: " + envName);
		try {
			if (envName == null) {
				System.out.println("no env is given... hence running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.cofiq.properties");
			} else {
				System.out.println("Running tests on env: " + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.cofiq.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.confiq.properties");
					break;
				case "statge":
					ip = new FileInputStream("./src/test/resources/config/stage.confiq.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env name..." + envName);
					throw new APIFrameworkException("NO ENV IS GIVEN");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
// this class will help to read the properties file