/*
 * Copyright (c) [2014] - [2015], [Pavandeep Puddupakkam] and the [SeleniumWiki] contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

/*
 * This source file is proprietary property of Pavandeep Puddupakkam.
 */
package library.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import static library.constants.BrowserConstants.*;
import static org.openqa.selenium.remote.DesiredCapabilities.*;

public class WebDriverUtils {

    private static final Logger LOGGER = Logger.getLogger(WebDriverUtils.class);

    private static WebDriverUtils webDriverUtils;

    public static WebDriverUtils getInstance() {
        if (webDriverUtils == null) {
            webDriverUtils = new WebDriverUtils();
        }
        return webDriverUtils;
    }

    private ConcurrentHashMap<String, WebDriver> webDriverCache;

    private WebDriverUtils() {
        webDriverCache = new ConcurrentHashMap<String, WebDriver>();
    }

    public void removeDriver(String type) {
        LOGGER.debug("Removing cached WebDriver of type [" + type + "]");
        webDriverCache.remove(type);
    }

    public WebDriver createWebDriver(String browserName, String remoteWebDriverUrl) throws MalformedURLException {
        LOGGER.debug("Creating [" + browserName + "] WebDriver");
        WebDriver webDriver;
        if (webDriverCache.containsKey(browserName)) {
            webDriver = webDriverCache.get(browserName);
        } else {
            if (BROWSER_FIREFOX.contains(browserName) || BROWSER_HTML_UNIT.contains(browserName) || BROWSER_IE.contains
                    (browserName) || BROWSER_CHROME.contains(browserName)) {
                webDriver = getWebDriver(remoteWebDriverUrl, browserName);
                browserName = BROWSER_REMOTE.contains(browserName) ? BROWSER_REMOTE + " - " + remoteBrowserName(
                        (RemoteWebDriver) webDriver) : remoteBrowserName((RemoteWebDriver) webDriver);
            } else if (BROWSER_SAUCELABS.contains(browserName)) {
                DesiredCapabilities caps = internetExplorer();
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "11");
                webDriver = new RemoteWebDriver(new URL(remoteWebDriverUrl), caps);
                browserName = BROWSER_REMOTE + " - " + remoteBrowserName((RemoteWebDriver) webDriver);
            } else {
                throw new IllegalArgumentException("Unable to determine the driver for [" + browserName + "]");
            }
            LOGGER.debug("Caching [" + browserName + "] WebDriver");
            webDriverCache.put(browserName, webDriver);
        }
        return webDriver;
    }

    protected WebDriver getWebDriver(String remoteWebDriverUrl, String browserName) throws MalformedURLException {
        return BROWSER_FIREFOX.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), firefox())
                : BROWSER_IE.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), internetExplorer())
                : BROWSER_CHROME.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), chrome())
                : BROWSER_REMOTE.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), chrome())
                : new RemoteWebDriver(new URL(remoteWebDriverUrl), htmlUnit());
    }

    protected String remoteBrowserName(RemoteWebDriver webDriver) {
        return webDriver.getCapabilities().getBrowserName();
    }
}
