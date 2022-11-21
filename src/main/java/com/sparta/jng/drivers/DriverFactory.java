package com.sparta.jng.drivers;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final DriverOptions driverOptions;
    private final Logger log;
    private static ChromeDriver chromeDriver;
    private static SafariDriver safariDriver;
    private static FirefoxDriver firefoxDriver;

    public DriverFactory(DriverOptions driverOptions, Logger log) {
        this.driverOptions = driverOptions;
        this.log = log;
    }

    public static WebDriver getDriver(DriverOptions driverOptions) {
        if (driverOptions.equals(DriverOptions.CHROME)) {
            chromeDriver = new ChromeDriver();
            return chromeDriver;

        } else if (driverOptions.equals(DriverOptions.SAFARI)) {
            safariDriver = new SafariDriver();
            return safariDriver;

        } else if(driverOptions.equals(DriverOptions.FIREFOX)) {
            firefoxDriver = new FirefoxDriver();
            return firefoxDriver;

        } else if (driverOptions.equals(DriverOptions.CHROME_IPHONE13)) {
            chromeDriver.manage().window().setSize(new Dimension(390, 844));
            return chromeDriver;
        }

        return null;
    }

    public WebDriver createDriver() {
        // Create driver
        log.info("Create driver: " + driverOptions);

        switch (driverOptions) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver());
                break;

            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver());
                break;

            case CHROME_HEADLESS:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case FIREFOX_HEADLESS:
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

//            case "PHATOMJS":
//                System.setProperty("phantomjs.binary.path", "src/main/resources/phantomjs.exe");
//                driver.set(new PhantomJSDriver());
//                break;
//
//            case "HTMLUNIT":
//                driver.set(new HtmlUnitDriver());
//                break;

            default:
                System.out.println("Do not know how to start: " + driverOptions + ", starting chrome.");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver());
                break;
        }

        return driver.get();
    }

    public WebDriver createChromeWithProfile(String profile) {
        log.info("Starting chrome driver with profile: " + profile);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-dir=C:/Users/kwokl/Sparta Global/advanced-selenium-webdriver/src/main/" +
                "resources/Profiles/" + profile);
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver.set(new ChromeDriver(chromeOptions));
        return driver.get();
    }

    public WebDriver createChromeWithMobileEmulation(String deviceName) {
        log.info("Starting driver with " + deviceName + " emulation]");
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", deviceName);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver.set(new ChromeDriver(chromeOptions));
        return driver.get();
    }
}
