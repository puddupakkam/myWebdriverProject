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
package library.functions;

import library.utils.WebDriverUtils;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static library.constants.BrowserConstants.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class BaseFunctions {

    private ReadXmlData rxml;
    private String browser;
    private String remoteUrl;
    private String remoteWebDriverUrl;
    private String urlString;
    private WebDriver webDriver;

    private Boolean record;

    public BaseFunctions() {
        try {
            rxml = new ReadXmlData();
            browser = getData().commonData("browser");
            remoteUrl = getData().commonData("remoteUrl");

            urlString = getData().commonData("url");
            record = Boolean.valueOf(getData().commonData("record"));

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    protected String getBrowser() {
        return browser;
    }

    public boolean getRecord() {
        return record;
    }

    public WebDriver getWebDriver() {
        Validate.notNull(webDriver, "Failed to retrieve valid web driver");
        return webDriver;
    }

    protected ReadXmlData getData() {
        return rxml;
    }

    protected void start(String remoteWebDriverUrl) throws MalformedURLException {
        if (webDriver == null) {
            this.remoteWebDriverUrl = remoteWebDriverUrl;
            webDriver =
                    WebDriverUtils
                            .getInstance()
                            .createWebDriver(
                                    browser,
                                    isEmpty(remoteWebDriverUrl) ? remoteUrl : remoteWebDriverUrl);
            if (webDriver instanceof RemoteWebDriver) {
                String remoteBrowserName = ((RemoteWebDriver) webDriver).getCapabilities().getBrowserName();
                if ("internet explorer".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_IE;
                    browser = remoteBrowserName;
                } else if ("firefox".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_FIREFOX;
                    browser = remoteBrowserName;
                } else if ("chrome".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_CHROME;
                    browser = remoteBrowserName;
                } else if ("safari".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_SAFARI;
                    browser = remoteBrowserName;
                } else {
                    browser = BROWSER_REMOTE + " - " + remoteBrowserName;
                }
            }
        }
    }

    protected void stop() throws Exception {
        getWebDriver().quit();
        WebDriverUtils.getInstance().removeDriver(getBrowser());
    }

    public void pass(String value) {
        System.out.println(value);
    }

    public void fail(String value) {
        System.err.println(value);
    }
}
