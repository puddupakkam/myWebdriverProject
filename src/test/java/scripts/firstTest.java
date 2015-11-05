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

package scripts;

import library.functions.CommonFunctions;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("unused")
public class firstTest extends CommonFunctions {
    String testCase = "My First Test";

    @Test(description = "My First Test")
    public void myFirstTest() throws Exception {
        pass("Test Report: My First Test on " + getBrowser() + " browser");
        openWebPage("http://www.realestate.com.au/buy");
        waitForPageObject(xpath("//input[@name='where']"));
        type(xpath("//input[@name='where']"), "Thornleigh");
        click(By.cssSelector("button.rui-search-button"));
        waitForPageObject(xpath("//a[@class='suburbLink']"));
        checkCondition(isPageObjectPresent(xpath("//a[@class='suburbLink'][.='Thornleigh, NSW 2120']")), "Thornleigh, NSW 2120 is displayed on the search results page");
        assertTrue(resultcount == 0, testCase + " has failed");
    }


}
