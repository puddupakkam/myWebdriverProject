/*
 * This source file is proprietary property of Pavandeep Puddupakkam.
 */
package library.functions;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static library.constants.BrowserConstants.BROWSER_REMOTE;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;

public class WebFunctions extends BaseFunctions {
    private static final Logger LOGGER = Logger.getLogger(BaseFunctions.class);
    private int resultcount;
    private boolean record;
    private ScreenRecorder screenRecorder;

    public WebFunctions() {
        record = Boolean.parseBoolean(getData().commonData("record"));
        resultcount = 0;
    }

    public WebElement findElement(By by) {
        WebElement elem = getWebDriver().findElement(by);
        if (getWebDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].style.border='3px solid red'", elem);
        }
        return elem;
    }


    public void rightClick(By by) {
        new Actions(getWebDriver()).moveToElement(getWebDriver().findElement(by)).contextClick(getWebDriver().findElement(by)).perform();
    }

    public void click(By by) throws InterruptedException, IOException {
        waitForElementPresent(by, 5);
        try {
            getWebDriver().findElement(by).click();
        } catch (Exception e) {
            try {
                getWebDriver().findElement(by).click();
            } catch (Exception f) {
                takeScreenshot();
            }
        }
    }

    public void takeScreenshot() throws IOException {
        Random rand = new Random();
        int num = rand.nextInt(150);
        File scrFile = null;
        Integer numNoRange = rand.nextInt();
        if (getBrowser().contains(BROWSER_REMOTE)) {
            try {
                WebDriver augmentedDriver = new Augmenter().augment(getWebDriver());
                scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
                String filename = numNoRange.toString();
                fail("Screenshot file name is : " + filename);
                copyFile(scrFile, new File(getData().commonData("screen_shot_location") + filename + ".png"));
            } catch (Exception n) {
                LOGGER.error("Failed to take screen shot of remote browser", n);
            }
        }
        if (scrFile == null) {
            try {
                scrFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
                String filename = numNoRange.toString();
                fail("Screenshot file name is : " + filename);
                copyFile(scrFile, new File(getData().commonData("screen_shot_location") + filename + ".png"));
            } catch (NullPointerException n) {
                fail(n.getMessage());
            }
        }
    }

    public void takeScreenshot(String testcase) throws IOException {
        Random rand = new Random();
        File scrFile = null;
        Integer numNoRange = rand.nextInt();
        if (getBrowser().contains("Remote")) {
            try {
                WebDriver augmentedDriver = new Augmenter().augment(getWebDriver());
                scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
                String filename = numNoRange.toString();
                fail("Screenshot file name is : " + testcase + filename);
                copyFile(scrFile, new File(getData().commonData("screen_shot_location") + testcase + filename + ".png"));
            } catch (Exception n) {
                LOGGER.error("Failed to take screen shot of remote browser", n);
            }
        }
        if (scrFile == null) {
            try {
                scrFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
                String filename = numNoRange.toString();
                fail("Screenshot file name is : " + testcase + filename);
                copyFile(scrFile, new File(getData().commonData("screen_shot_location") + testcase + filename + ".png"));
            } catch (NullPointerException n) {
                fail(n.getMessage());
            }
        }
    }

    public void startRecording() throws Exception {
        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        this.screenRecorder = new ScreenRecorder(gc,
                new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null);
        this.screenRecorder.start();
    }

    public void stopRecording() throws Exception {
        this.screenRecorder.stop();
    }

    public void waitForElementPresent(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 15)
                break;
            if (isElementPresent(by))
                break;
            Thread.sleep(1000);
        }
    }

    public void waitForElementPresent(By by, int i) throws InterruptedException {
        for (int second = 0; second <= i; second++) {
            if (second >= i) {
                break;
            }
            if (isElementPresent(by)) {
                break;
            }
            Thread.sleep(1000);
        }
    }

    public void waitForElementNotPresent(By by) throws IOException {
        if (!getBrowser().equalsIgnoreCase("InternetExplorer")) {
            try {
                if (isElementPresent(by)) {
                    WebDriverWait wait = new WebDriverWait(getWebDriver(), 120);
                    Boolean res = wait.until(ExpectedConditions.stalenessOf(getWebDriver().findElement(by)));
                }
            } catch (Exception e) {
                takeScreenshot();
            }
        }
    }

    public void waitForElementNotPresent(By by, int t) throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(getWebDriver(), t);
            Boolean res = wait.until(ExpectedConditions.stalenessOf(getWebDriver().findElement(by)));
        } catch (Exception e) {
            takeScreenshot();
        }
    }

    public void waitForOneElementPresent(int i, By... by) throws InterruptedException, IOException {
        boolean found = false;
        for (int second = 0; second <= i; second++) {
            if (second >= i) {
                takeScreenshot();
                break;
            }
            for (By singleBy : by) {
                if (isElementPresent(singleBy)) {
                    try {
                        waitForElementVisible(singleBy);
                    } catch (Exception e) {
                        takeScreenshot();
                    }
                    found = true;
                }
            }
            if (found) {
                break;
            }
            Thread.sleep(1000);
        }
    }

    public void waitForMultipleElements(int i, By... by) throws InterruptedException, IOException {
        for (int second = 0; second <= i; second++) {
            if (second >= i) {
                takeScreenshot();
                break;
            }
            if (areAllElementsPresent(by)) {
                for (By singleBy : by) {
                }
                break;
            }
            Thread.sleep(1000);
        }
    }

    public boolean areAllElementsPresent(By... by) {
        for (By singleBy : by) {
            if (!isElementPresent(singleBy)) {
                return false;
            }
        }
        return true;
    }

    public void waitLongerForElementPresent(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 360)
                break;
            if (isElementPresent(by)) {
                break;
            }
            Thread.sleep(1000);
        }
    }

    public boolean isElementVisible(final By by) throws InterruptedException {
        boolean value = false;
        waitForElementPresent(by);
        if (getWebDriver().findElement(by).isDisplayed()) {
            value = true;
        }
        return value;
    }


    public void waitForElementVisible(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 15)
                break;
            if (isElementVisible(by))
                break;
            Thread.sleep(1000);
        }
    }

    public void waitForElementVisible(By by, int time) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= time)
                break;
            if (isElementVisible(by))
                break;
            Thread.sleep(1000);
        }
    }

    public boolean isElementPresent(By by) {
        try {
            getWebDriver().findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getText(By by) {
        String value = getWebDriver().findElement(by).getText();
        return value;
    }

    public String getValue(By by) {
        String value = "";
        if (isElementPresent(by)) {
            value = getWebDriver().findElement(by).getAttribute("value");
        }
        return value;
    }

}
