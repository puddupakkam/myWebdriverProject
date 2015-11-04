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

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("unused")
public class CommonFunctions extends WebFunctions {

    private boolean hub;
    private boolean isStartSearchOrisIE;
    private boolean previousSearch;
    private String client_user_id;
    public int resultcount;
    public String ie_version = getData().commonData("ie_version");
    private String testresults;
    private String retrieve_selected_certificates;
    private Boolean share_workspace;
    private String end_point;

    public CommonFunctions() {
    }

    @Parameters(value = "IPAddress")
    @BeforeClass()
    protected void setup(@Optional String remoteWebDriverUrl) throws MalformedURLException {
        super.start(remoteWebDriverUrl);
    }

    @AfterClass
    public void teardown() throws Exception {
        if (getRecord()) {
            stopRecording();
        }
        super.stop();
    }

    public void open(String value) {
        getWebDriver().get(value);
    }

    public int openAndVerifyPDFDoc(By by, String... checkValue) throws InterruptedException, IOException {
        for (String childWindow : getWebDriver().getWindowHandles()) {
            getWebDriver().switchTo().window(childWindow);
        }
        String httphead_url = getWebDriver().getCurrentUrl();
        if (httphead_url.contains("/resource/document/open")) {
            pass("The document is opened in a new window");
            resultcount = resultcount + checkPDFContent(checkValue);
        }
        return resultcount;
    }


    public int checkPDFContent(String... checkValue) throws IOException {
        URL url = new URL(getWebDriver().getCurrentUrl());
        BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
        PDFParser parser = new PDFParser(fileToParse);
        parser.parse();
        String output = new PDFTextStripper().getText(parser.getPDDocument());
        output = sanitise(output);
        int len = checkValue.length;
        for (int i = 0; i <= (len - 1); i++) {
            if (output.contains(checkValue[i])) {
                pass(checkValue[i] + " is present in the PDF file");
            } else {
                fail(checkValue[i] + " is not present in the PDF file");
                resultcount++;
                takeScreenshot();
            }
        }
        pass(output);
        parser.getPDDocument().close();
        getWebDriver().close();
        return resultcount;
    }

    private String sanitise(String output) {
        return output.replaceAll("\r", "");
    }

    public int checkElementUsingXPath(String element, String message) throws IOException {
        try {
            assertTrue(isElementPresent(xpath(element)));
            pass(message);
        } catch (AssertionError e) {
            message = message.contains("is displayed") ? message.replace("is displayed",
                    "is not displayed") : message.contains("is disabled") ? message.replace("is disabled",
                    "is not disabled") : message.contains("is enabled") ? message.replace("is enabled",
                    "is not enabled") : message.contains("is not enabled") ? message.replace("is not enabled",
                    "is enabled") : message.contains("is generated") ? message.replace("is generated",
                    "is not generated") : message.contains("is selected") ? message.replace("is selected",
                    "is not selected") : message.contains("is identified") ? message.replace("is identified",
                    "is not identified") : message.contains("is added") ? message.replace("is added",
                    "is not added") : message.contains("is successfully") ? message.replace("is successfully",
                    "is not successfully") : message.contains("is highlighted") ? message.replace("is highlighted",
                    "is not highlighted") : message.contains("is being Processed") ? message.replace("is being Processed",
                    "has Failed") : message;
            fail(message);
            takeScreenshot();
            resultcount++;
        }
        return resultcount;
    }

    public int checkElementUsing(By element, String message) throws IOException {
        try {
            assertTrue(isElementPresent(element));
            pass(message);
        } catch (AssertionError e) {
            message = message.contains("is displayed") ? message.replace("is displayed",
                    "is not displayed") : message.contains("is disabled") ? message.replace("is disabled",
                    "is not disabled") : message.contains("is enabled") ? message.replace("is enabled",
                    "is not enabled") : message.contains("is not enabled") ? message.replace("is not enabled",
                    "is enabled") : message.contains("is generated") ? message.replace("is generated",
                    "is not generated") : message.contains("is selected") ? message.replace("is selected",
                    "is not selected") : message.contains("is identified") ? message.replace("is identified",
                    "is not identified") : message.contains("is added") ? message.replace("is added",
                    "is not added") : message.contains("is successfully") ? message.replace("is successfully",
                    "is not successfully") : message.contains("is highlighted") ? message.replace("is highlighted",
                    "is not highlighted") : message.contains("is being Processed") ? message.replace("is being Processed",
                    "has Failed") : message;
            fail(message);
            takeScreenshot();
            resultcount++;
        }
        return resultcount;
    }

    public int checkCondition(boolean condition, String message) throws IOException {
        try {
            assertTrue(condition);
            pass(message);
        } catch (AssertionError e) {
            message = message.contains("is displayed") ? message.replace("is displayed",
                    "is not displayed") : message.contains("is disabled") ? message.replace("is disabled",
                    "is not disabled") : message.contains("is enabled") ? message.replace("is enabled",
                    "is not enabled") : message.contains("is not enabled") ? message.replace("is not enabled",
                    "is enabled") : message.contains("is generated") ? message.replace("is generated",
                    "is not generated") : message.contains("is selected") ? message.replace("is selected",
                    "is not selected") : message.contains("is identified") ? message.replace("is identified",
                    "is not identified") : message.contains("is added") ? message.replace("is added",
                    "is not added") : message.contains("is successfully") ? message.replace("is successfully",
                    "is not successfully") : message.contains("is highlighted") ? message.replace("is highlighted",
                    "is not highlighted") : message.contains("is being Processed") ? message.replace("is being Processed",
                    "has Failed") : message.contains("has warning symbol") ? message.replace("has warning symbol",
                    "has no warning symbol") : message;
            fail(message);
            takeScreenshot();
            resultcount++;
        }
        return resultcount;
    }

    public int checkElement(By by, String message) throws IOException {
        try {
            assertTrue(isElementPresent(by));
            pass(message);
        } catch (AssertionError e) {
            message = message.contains("is displayed") ? message.replace("is displayed",
                    "is not displayed") : message.contains("is disabled") ? message.replace("is disabled",
                    "is not disabled") : message.contains("is enabled") ? message.replace("is enabled",
                    "is not enabled") : message.contains("is not enabled") ? message.replace("is not enabled",
                    "is enabled") : message.contains("is generated") ? message.replace("is generated",
                    "is not generated") : message.contains("is selected") ? message.replace("is selected",
                    "is not selected") : message.contains("is identified") ? message.replace("is identified",
                    "is not identified") : message.contains("is added") ? message.replace("is added",
                    "is not added") : message;
            fail(message);
            takeScreenshot();
            resultcount++;
        }
        return resultcount;
    }

    public void clickAt(By by) throws InterruptedException, IOException {
        Thread.sleep(1000);
        if (getBrowser().contains("Firefox")) {
            click(by);
        } else {
            Actions builder = new Actions(getWebDriver());
            try {
                WebElement tagElement = getWebDriver().findElement(by);
                builder.moveToElement(tagElement).click().perform();
                Thread.sleep(1000);
            } catch (Exception e) {
                takeScreenshot();
                fail("Can not find " + by);
            }
        }
    }

    public void doubleClick(By by) throws InterruptedException, IOException {
        Thread.sleep(1000);
        Actions builder = new Actions(getWebDriver());
        try {
            WebElement tagElement = getWebDriver().findElement(by);
            builder.doubleClick(tagElement).perform();
            Thread.sleep(1000);
        } catch (Exception e) {
            takeScreenshot();
        }
    }

    public void type(By by, String value) throws InterruptedException {
        try {
            getWebDriver().findElement(by).clear();
            getWebDriver().findElement(by).sendKeys(value);
        } catch (Exception e) {
            getWebDriver().findElement(by).clear();
            getWebDriver().findElement(by).sendKeys(value);
        }
    }

    public void typeAll(String[] path, String[] value) throws InterruptedException {
        int len = path.length;
        for (int i = 0; i <= (len - 1); i++) {
            if (isElementPresent(xpath(path[i]))) {
                type(xpath(path[i]), value[i]);
            }
        }
    }

    public void actionType(By by, String value) throws InterruptedException {
        waitForElementPresent((by));
        new Actions(getWebDriver()).moveToElement(getWebDriver().findElement(by)).click()
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), value).perform();
    }

}
