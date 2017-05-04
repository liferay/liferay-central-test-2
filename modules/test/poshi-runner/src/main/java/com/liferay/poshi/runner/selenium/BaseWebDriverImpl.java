/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.selenium;

import com.deque.axe.AXE;

import com.liferay.poshi.runner.PoshiRunnerContext;
import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.util.AntCommands;
import com.liferay.poshi.runner.util.CharPool;
import com.liferay.poshi.runner.util.EmailCommands;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.HtmlUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.RuntimeVariables;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Location;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.CanvasBuilder.ElementAdder;
import org.sikuli.api.visual.CanvasBuilder.ElementAreaSetter;
import org.sikuli.api.visual.DesktopCanvas;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl implements LiferaySelenium, WebDriver {

	public BaseWebDriverImpl(String browserURL, WebDriver webDriver) {
		_webDriver = webDriver;

		initKeysSpecialChars();

		setDefaultWindowHandle(webDriver.getWindowHandle());
		setNavigationBarHeight(120);

		System.setProperty("java.awt.headless", "false");

		String outputDirName = _OUTPUT_DIR_NAME;
		String sikuliImagesDirName =
			_TEST_DEPENDENCIES_DIR_NAME + "//sikuli//linux//";
		String testDependenciesDirName = _TEST_DEPENDENCIES_DIR_NAME;

		if (OSDetector.isApple()) {
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "osx");
		}
		else if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(outputDirName, "//", "\\");

			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "//", "\\");
			sikuliImagesDirName = StringUtil.replace(
				sikuliImagesDirName, "linux", "windows");

			testDependenciesDirName = StringUtil.replace(
				testDependenciesDirName, "//", "\\");
		}

		_outputDirName = outputDirName;
		_sikuliImagesDirName = sikuliImagesDirName;
		_testDependenciesDirName = testDependenciesDirName;

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		window.setSize(new Dimension(1280, 1040));

		webDriver.get(browserURL);
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		Select select = new Select(getWebElement(locator));

		if (optionLocator.startsWith("index=")) {
			select.selectByIndex(
				GetterUtil.getInteger(optionLocator.substring(6)));
		}
		else if (optionLocator.startsWith("label=")) {
			select.selectByVisibleText(optionLocator.substring(6));
		}
		else if (optionLocator.startsWith("value=")) {
			select.selectByValue(optionLocator.substring(6));
		}
		else {
			select.selectByVisibleText(optionLocator);
		}
	}

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		AntCommands antCommands = new AntCommands(fileName, target);

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<Void> future = executorService.submit(antCommands);

		try {
			future.get(150, TimeUnit.SECONDS);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (TimeoutException te) {
		}
	}

	@Override
	public void assertAccessible() throws Exception {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		String sourceDirFilePath = LiferaySeleniumHelper.getSourceDirFilePath(
			getTestDependenciesDirName());

		File file = new File(sourceDirFilePath + "/axe.min.js");

		URI uri = file.toURI();

		URL url = uri.toURL();

		AXE.Builder axeBuilder = new AXE.Builder(webDriver, url);

		axeBuilder = axeBuilder.options(
			PropsValues.ACCESSIBILITY_STANDARDS_JSON);

		JSONObject jsonObject = axeBuilder.analyze();

		JSONArray jsonArray = jsonObject.getJSONArray("violations");

		if (jsonArray.length() != 0) {
			throw new Exception(AXE.report(jsonArray));
		}
	}

	@Override
	public void assertAlert(String pattern) throws Exception {
		TestCase.assertEquals(pattern, getAlert());
	}

	@Override
	public void assertAlertNotPresent() throws Exception {
		if (isAlertPresent()) {
			throw new Exception("Alert is present");
		}
	}

	@Override
	public void assertChecked(String locator) throws Exception {
		assertElementPresent(locator);

		if (isNotChecked(locator)) {
			throw new Exception(
				"Element is not checked at \"" + locator + "\"");
		}
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
		String confirmation = getConfirmation();

		if (!pattern.equals(confirmation)) {
			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + confirmation + "\"");
		}
	}

	@Override
	public void assertConsoleErrors() throws Exception {
		LiferaySeleniumHelper.assertConsoleErrors();
	}

	@Override
	public void assertConsoleTextNotPresent(String text) throws Exception {
		if (isConsoleTextPresent(text)) {
			throw new Exception("\"" + text + "\" is present in console");
		}
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		if (!isConsoleTextPresent(text)) {
			throw new Exception("\"" + text + "\" is not present in console");
		}
	}

	@Override
	public void assertCssValue(
			String locator, String cssAttribute, String cssValue)
		throws Exception {

		WebElement webElement = getWebElement(locator);

		String actualCssValue = webElement.getCssValue(cssAttribute);

		if (!actualCssValue.equals(cssValue)) {
			throw new Exception(
				"CSS Value " + actualCssValue + " does not match " + cssValue);
		}
	}

	@Override
	public void assertEditable(String locator) throws Exception {
		if (isNotEditable(locator)) {
			throw new Exception(
				"Element is not editable at \"" + locator + "\"");
		}
	}

	@Override
	public void assertElementNotPresent(String locator) throws Exception {
		if (isElementPresent(locator)) {
			throw new Exception("Element is present at \"" + locator + "\"");
		}
	}

	@Override
	public void assertElementPresent(String locator) throws Exception {
		if (isElementNotPresent(locator)) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}
	}

	@Override
	public void assertEmailBody(String index, String body) throws Exception {
		TestCase.assertEquals(body, getEmailBody(index));
	}

	@Override
	public void assertEmailSubject(String index, String subject)
		throws Exception {

		TestCase.assertEquals(subject, getEmailSubject(index));
	}

	@Override
	public void assertHTMLSourceTextNotPresent(String value) throws Exception {
		if (isHTMLSourceTextPresent(value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does exists in the HTML source");
		}
	}

	@Override
	public void assertHTMLSourceTextPresent(String value) throws Exception {
		if (!isHTMLSourceTextPresent(value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does not exists in the HTML source");
		}
	}

	@Override
	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception {

		if (!PropsValues.TEST_ASSERT_JAVASCRIPT_ERRORS) {
			return;
		}

		String location = getLocation();

		if (!location.contains("localhost")) {
			return;
		}

		String pageSource = null;

		try {
			pageSource = getPageSource();
		}
		catch (Exception e) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.window(_defaultWindowHandle);

			pageSource = getPageSource();
		}

		if (pageSource.contains(
				"html id=\"feedHandler\" xmlns=" +
					"\"http://www.w3.org/1999/xhtml\"")) {

			return;
		}

		List<JavaScriptError> javaScriptErrors = new ArrayList<>();

		try {
			WebElement webElement = getWebElement("//body");

			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			javaScriptErrors.addAll(
				JavaScriptError.readErrors(wrappedWebDriver));
		}
		catch (Exception e) {
		}

		List<Exception> exceptions = new ArrayList<>();

		if (!javaScriptErrors.isEmpty()) {
			for (JavaScriptError javaScriptError : javaScriptErrors) {
				String javaScriptErrorValue = javaScriptError.toString();

				if (Validator.isNotNull(ignoreJavaScriptError) &&
					javaScriptErrorValue.contains(ignoreJavaScriptError)) {

					continue;
				}

				if (LiferaySeleniumHelper.isInIgnoreErrorsFile(
						javaScriptErrorValue, "javascript")) {

					continue;
				}

				String message = "JAVA_SCRIPT_ERROR: " + javaScriptErrorValue;

				System.out.println(message);

				exceptions.add(new PoshiRunnerWarningException(message));
			}
		}

		if (!exceptions.isEmpty()) {
			LiferaySeleniumHelper.addToJavaScriptExceptions(exceptions);

			throw exceptions.get(0);
		}
	}

	@Override
	public void assertLiferayErrors() throws Exception {
		LiferaySeleniumHelper.assertConsoleErrors();
	}

	@Override
	public void assertLocation(String pattern) throws Exception {
		TestCase.assertEquals(pattern, getLocation());
	}

	@Override
	public void assertNoJavaScriptExceptions() throws Exception {
		LiferaySeleniumHelper.assertNoJavaScriptExceptions();
	}

	@Override
	public void assertNoLiferayExceptions() throws Exception {
		LiferaySeleniumHelper.assertNoLiferayExceptions();
	}

	@Override
	public void assertNotAlert(String pattern) {
		TestCase.assertTrue(Objects.equals(pattern, getAlert()));
	}

	@Override
	public void assertNotChecked(String locator) throws Exception {
		assertElementPresent(locator);

		if (isChecked(locator)) {
			throw new Exception("Element is checked at \"" + locator + "\"");
		}
	}

	@Override
	public void assertNotEditable(String locator) throws Exception {
		if (isEditable(locator)) {
			throw new Exception("Element is editable at \"" + locator + "\"");
		}
	}

	@Override
	public void assertNotLocation(String pattern) throws Exception {
		TestCase.assertTrue(Objects.equals(pattern, getLocation()));
	}

	@Override
	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		if (isPartialText(locator, pattern)) {
			String text = getText(locator);

			throw new Exception(
				"\"" + text + "\" contains \"" + pattern + "\" at \"" +
					locator + "\"");
		}
	}

	@Override
	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		assertElementPresent(selectLocator);

		if (isSelectedLabel(selectLocator, pattern)) {
			String text = getSelectedLabel(selectLocator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + text + "\" at \"" +
					selectLocator + "\"");
		}
	}

	@Override
	public void assertNotText(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		if (isText(locator, pattern)) {
			String text = getText(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + text + "\" at \"" +
					locator + "\"");
		}
	}

	@Override
	public void assertNotValue(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		if (isValue(locator, pattern)) {
			String value = getElementValue(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + value + "\" at \"" +
					locator + "\"");
		}
	}

	@Override
	public void assertNotVisible(String locator) throws Exception {
		assertElementPresent(locator);

		if (isVisible(locator)) {
			throw new Exception("Element is visible at \"" + locator + "\"");
		}
	}

	@Override
	public void assertPartialConfirmation(String pattern) throws Exception {
		String confirmation = getConfirmation();

		if (!confirmation.contains(pattern)) {
			throw new Exception(
				"\"" + confirmation + "\" does not contain \"" + pattern +
					"\"");
		}
	}

	@Override
	public void assertPartialLocation(String pattern) throws Exception {
		String location = getLocation();

		if (!location.contains(pattern)) {
			throw new Exception(
				"\"" + location + "\" does not contain \"" + pattern + "\"");
		}
	}

	@Override
	public void assertPartialText(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		if (isNotPartialText(locator, pattern)) {
			String text = getText(locator);

			throw new Exception(
				"\"" + text + "\" does not contain \"" + pattern + "\" at \"" +
					locator + "\"");
		}
	}

	@Override
	public void assertPartialTextAceEditor(String locator, String pattern)
		throws Exception {

		assertElementPresent(locator);

		if (isNotPartialTextAceEditor(locator, pattern)) {
			String text = getTextAceEditor(locator);

			throw new Exception(
				"\"" + text + "\" does not contain \"" + pattern + "\" at \"" +
					locator + "\"");
		}
	}

	@Override
	public void assertPrompt(String pattern, String value) throws Exception {
		String confirmation = getConfirmation(value);

		if (!pattern.equals(confirmation)) {
			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + confirmation + "\"");
		}
	}

	@Override
	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		assertElementPresent(selectLocator);

		if (isNotSelectedLabel(selectLocator, pattern)) {
			String text = getSelectedLabel(selectLocator);

			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + text + "\" at \"" +
						selectLocator + "\"");
		}
	}

	@Override
	public void assertText(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		if (isNotText(locator, pattern)) {
			String text = getText(locator);

			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + text + "\" at \"" +
						locator + "\"");
		}
	}

	@Override
	public void assertTextNotPresent(String pattern) throws Exception {
		if (isTextPresent(pattern)) {
			throw new Exception("\"" + pattern + "\" is present");
		}
	}

	@Override
	public void assertTextPresent(String pattern) throws Exception {
		if (isTextNotPresent(pattern)) {
			throw new Exception("\"" + pattern + "\" is not present");
		}
	}

	@Override
	public void assertValue(String locator, String pattern) throws Exception {
		assertElementPresent(locator);

		if (isNotValue(locator, pattern)) {
			String value = getElementValue(locator);

			throw new Exception(
				"Expected text \"" + pattern +
					"\" does not match actual text \"" + value + "\" at \"" +
						locator + "\"");
		}
	}

	@Override
	public void assertVisible(String locator) throws Exception {
		assertElementPresent(locator);

		if (isNotVisible(locator)) {
			throw new Exception(
				"Element is not visible at \"" + locator + "\"");
		}
	}

	@Override
	public void check(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isSelected()) {
			webElement.click();
		}
	}

	@Override
	public void click(String locator) {
		if (locator.contains("x:")) {
			String url = getHtmlNodeHref(locator);

			open(url);
		}
		else {
			WebElement webElement = getWebElement(locator);

			try {
				webElement.click();
			}
			catch (Exception e) {
				scrollWebElementIntoView(webElement);

				webElement.click();
			}
		}
	}

	@Override
	public void clickAt(String locator, String coordString) {
		clickAt(locator, coordString, true);
	}

	public void clickAt(
		String locator, String coordString, boolean scrollIntoView) {

		int offsetX = 0;
		int offsetY = 0;

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			offsetX = GetterUtil.getInteger(coords[0]);
			offsetY = GetterUtil.getInteger(coords[1]);
		}

		if ((offsetX == 0) && (offsetY == 0)) {
			click(locator);
		}
		else {
			WebElement bodyWebElement = getWebElement("//body");

			WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

			WebDriver webDriver = wrapsDriver.getWrappedDriver();

			WebDriver.Options options = webDriver.manage();

			WebDriver.Window window = options.window();

			Point windowPoint = window.getPosition();

			WebElement webElement = getWebElement(locator);

			Point webElementPoint = webElement.getLocation();

			int clickDestinationX = 0;
			int clickDestinationY = 0;

			if (scrollIntoView) {
				scrollWebElementIntoView(webElement);

				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY = windowPoint.getY() + offsetY;
			}
			else {
				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY =
					windowPoint.getY() + webElementPoint.getY() + offsetY;
			}

			try {
				Robot robot = new Robot();

				robot.mouseMove(clickDestinationX, clickDestinationY);

				robot.mousePress(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);

				robot.mouseRelease(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void close() {
		_webDriver.close();
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumHelper.connectToEmailAccount(
			emailAddress, emailPassword);
	}

	@Override
	public void copyText(String locator) throws Exception {
		_clipBoard = getText(locator);
	}

	@Override
	public void copyValue(String locator) throws Exception {
		_clipBoard = getElementValue(locator);
	}

	@Override
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumHelper.deleteAllEmails();
	}

	@Override
	public void doubleClick(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.doubleClick(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void doubleClickAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.doubleClick();
		}
		else {
			actions.doubleClick(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void dragAndDrop(String locator, String coordString) {
		try {
			int x = getElementPositionCenterX(locator);

			x += getFramePositionLeft();
			x += getWindowPositionLeft();
			x -= getScrollOffsetX();

			int y = getElementPositionCenterY(locator);

			y += getFramePositionTop();
			y += getNavigationBarHeight();
			y += getWindowPositionTop();
			y -= getScrollOffsetY();

			Robot robot = new Robot();

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mousePress(InputEvent.BUTTON1_MASK);

			robot.delay(1500);

			String[] coords = coordString.split(",");

			x += GetterUtil.getInteger(coords[0]);
			y += GetterUtil.getInteger(coords[1]);

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		catch (Exception e) {
		}
	}

	@Override
	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		WebElement objectToBeDraggedWebElement = getWebElement(
			locatorOfObjectToBeDragged);

		WrapsDriver wrapsDriver = (WrapsDriver)objectToBeDraggedWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		WebElement dragDestinationObjectWebElement = getWebElement(
			locatorOfDragDestinationObject);

		actions.dragAndDrop(
			objectToBeDraggedWebElement, dragDestinationObjectWebElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	@Override
	public void fail(String message) {
		LiferaySeleniumHelper.fail(message);
	}

	@Override
	public WebElement findElement(By by) {
		return _webDriver.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return _webDriver.findElements(by);
	}

	@Override
	public void get(String url) {
		_webDriver.get(url);
	}

	@Override
	public String getAlert() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		Alert alert = webDriverWait.until(ExpectedConditions.alertIsPresent());

		return alert.getText();
	}

	@Override
	public String getAttribute(String attributeLocator) {
		int pos = attributeLocator.lastIndexOf(CharPool.AT);

		String locator = attributeLocator.substring(0, pos);

		WebElement webElement = getWebElement(locator);

		String attribute = attributeLocator.substring(pos + 1);

		return webElement.getAttribute(attribute);
	}

	@Override
	public String getBodyText() {
		WebElement webElement = findElement(By.tagName("body"));

		return webElement.getText();
	}

	@Override
	public String getConfirmation() {
		return getConfirmation(null);
	}

	@Override
	public String getConfirmation(String value) {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		try {
			Alert alert = webDriverWait.until(
				ExpectedConditions.alertIsPresent());

			String confirmation = alert.getText();

			if (Validator.isNotNull(value)) {
				alert.sendKeys(value);
			}

			alert.accept();

			return confirmation;
		}
		catch (Exception e) {
			throw new WebDriverException();
		}
	}

	@Override
	public String getCurrentUrl() {
		return _webDriver.getCurrentUrl();
	}

	@Override
	public int getElementHeight(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		Dimension dimension = webElement.getSize();

		return dimension.getHeight();
	}

	@Override
	public String getElementValue(String locator) throws Exception {
		return getElementValue(locator, null);
	}

	public String getElementValue(String locator, String timeout)
		throws Exception {

		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		scrollWebElementIntoView(webElement);

		return webElement.getAttribute("value");
	}

	@Override
	public int getElementWidth(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		Dimension dimension = webElement.getSize();

		return dimension.getWidth();
	}

	@Override
	public String getEmailBody(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailBody(index);
	}

	@Override
	public String getEmailSubject(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailSubject(index);
	}

	@Override
	public String getEval(String script) {
		WebElement webElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return (String)javascriptExecutor.executeScript(script);
	}

	@Override
	public String getFirstNumber(String locator) {
		WebElement webElement = getWebElement(locator);

		String text = webElement.getText();

		if (text == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean digit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);

				digit = true;
			}

			String s = sb.toString();

			if (Validator.isNotNull(s) && !digit) {
				return s;
			}
		}

		return sb.toString();
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		String firstNumber = getFirstNumber(locator);

		return StringUtil.valueOf(GetterUtil.getInteger(firstNumber) + 1);
	}

	public Node getHtmlNode(String locator) {
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();

			XPath xPath = xPathFactory.newXPath();

			locator = StringUtil.replace(locator, "x:", "");

			XPathExpression xPathExpression = xPath.compile(locator);

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			String htmlSource = getHtmlSource();

			htmlSource = htmlSource.substring(htmlSource.indexOf("<html"));

			StringReader stringReader = new StringReader(htmlSource);

			InputSource inputSource = new InputSource(stringReader);

			Document document = documentBuilder.parse(inputSource);

			NodeList nodeList = (NodeList)xPathExpression.evaluate(
				document, XPathConstants.NODESET);

			if (nodeList.getLength() < 1) {
				throw new Exception(locator + " is not present");
			}

			return nodeList.item(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getHtmlNodeHref(String locator) {
		Node elementNode = getHtmlNode(locator);

		NamedNodeMap namedNodeMap = elementNode.getAttributes();

		Node attributeNode = namedNodeMap.getNamedItem("href");

		return attributeNode.getTextContent();
	}

	public String getHtmlNodeText(String locator) throws Exception {
		Node node = getHtmlNode(locator);

		if (node == null) {
			throw new Exception(locator + " is not present");
		}

		return node.getTextContent();
	}

	@Override
	public String getHtmlSource() {
		return getPageSource();
	}

	@Override
	public String getLocation() throws Exception {
		List<Exception> exceptions = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			FutureTask<String> futureTask = new FutureTask<>(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return _webDriver.getCurrentUrl();
					}

					private Callable<String> _init(WebDriver webDriver)
						throws Exception {

						_webDriver = webDriver;

						return this;
					}

					private WebDriver _webDriver;

				}._init(this));

			Thread thread = new Thread(futureTask);

			thread.start();

			try {
				String location = futureTask.get(
					PropsValues.TIMEOUT_EXPLICIT_WAIT, TimeUnit.SECONDS);

				return location;
			}
			catch (CancellationException ce) {
				exceptions.add(ce);
			}
			catch (ExecutionException ee) {
				exceptions.add(ee);
			}
			catch (InterruptedException ie) {
				exceptions.add(ie);
			}
			catch (TimeoutException te) {
				exceptions.add(te);
			}
			finally {
				thread.interrupt();
			}

			System.out.println("getLocation(WebDriver):");
			System.out.println(toString());

			Set<String> windowHandles = getWindowHandles();

			for (String windowHandle : windowHandles) {
				System.out.println(windowHandle);
			}
		}

		if (!exceptions.isEmpty()) {
			throw new Exception(exceptions.get(0));
		}
		else {
			throw new TimeoutException();
		}
	}

	@Override
	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	@Override
	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	@Override
	public String getOutputDirName() {
		return _outputDirName;
	}

	@Override
	public String getPageSource() {
		return _webDriver.getPageSource();
	}

	@Override
	public String getPrimaryTestSuiteName() {
		return _primaryTestSuiteName;
	}

	@Override
	public String getSelectedLabel(String selectLocator) {
		return getSelectedLabel(selectLocator, null);
	}

	public String getSelectedLabel(String selectLocator, String timeout) {
		try {
			WebElement selectLocatorWebElement = getWebElement(
				selectLocator, timeout);

			Select select = new Select(selectLocatorWebElement);

			WebElement firstSelectedOptionWebElement =
				select.getFirstSelectedOption();

			return firstSelectedOptionWebElement.getText();
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		WebElement selectLocatorWebElement = getWebElement(selectLocator);

		Select select = new Select(selectLocatorWebElement);

		List<WebElement> allSelectedOptionsWebElements =
			select.getAllSelectedOptions();

		String[] selectedOptionsWebElements =
			new String[allSelectedOptionsWebElements.size()];

		for (int i = 0; i < allSelectedOptionsWebElements.size(); i++) {
			WebElement webElement = allSelectedOptionsWebElements.get(i);

			if (webElement != null) {
				selectedOptionsWebElements[i] = webElement.getText();
			}
		}

		return selectedOptionsWebElements;
	}

	@Override
	public String getSikuliImagesDirName() {
		return _sikuliImagesDirName;
	}

	@Override
	public String getTestDependenciesDirName() {
		return _testDependenciesDirName;
	}

	@Override
	public String getText(String locator) throws Exception {
		return getText(locator, null);
	}

	public String getText(String locator, String timeout) throws Exception {
		if (locator.contains("x:")) {
			return getHtmlNodeText(locator);
		}

		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		scrollWebElementIntoView(webElement);

		String text = webElement.getText();

		text = text.trim();

		return text.replace("\n", " ");
	}

	public String getTextAceEditor(String locator) throws Exception {
		return getTextAceEditor(locator, null);
	}

	public String getTextAceEditor(String locator, String timeout)
		throws Exception {

		WebElement webElement = getWebElement(locator, timeout);

		if (webElement == null) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}

		scrollWebElementIntoView(webElement);

		String text = webElement.getText();

		text = text.trim();

		return text.replace("\n", "");
	}

	@Override
	public String getTitle() {
		return _webDriver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return _webDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return _webDriver.getWindowHandles();
	}

	public WebDriver getWrappedWebDriver() {
		return _webDriver;
	}

	@Override
	public void goBack() {
		WebDriver.Navigation navigation = navigate();

		navigation.back();
	}

	@Override
	public boolean isAlertPresent() {
		boolean alertPresent = false;

		switchTo();

		try {
			WebDriverWait webDriverWait = new WebDriverWait(this, 1);

			webDriverWait.until(ExpectedConditions.alertIsPresent());

			alertPresent = true;
		}
		catch (Exception e) {
			alertPresent = false;
		}

		return alertPresent;
	}

	@Override
	public boolean isChecked(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		scrollWebElementIntoView(webElement);

		return webElement.isSelected();
	}

	@Override
	public boolean isConfirmation(String pattern) {
		String confirmation = getConfirmation();

		return pattern.equals(confirmation);
	}

	@Override
	public boolean isConsoleTextNotPresent(String text) throws Exception {
		return !LiferaySeleniumHelper.isConsoleTextPresent(text);
	}

	@Override
	public boolean isConsoleTextPresent(String text) throws Exception {
		return LiferaySeleniumHelper.isConsoleTextPresent(text);
	}

	@Override
	public boolean isEditable(String locator) {
		WebElement webElement = getWebElement(locator);

		return webElement.isEnabled();
	}

	@Override
	public boolean isElementNotPresent(String locator) {
		return !isElementPresent(locator);
	}

	@Override
	public boolean isElementPresent(String locator) {
		List<WebElement> webElements = getWebElements(locator, "1");

		return !webElements.isEmpty();
	}

	@Override
	public boolean isElementPresentAfterWait(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				return isElementPresent(locator);
			}

			if (isElementPresent(locator)) {
				break;
			}

			Thread.sleep(1000);
		}

		return isElementPresent(locator);
	}

	@Override
	public boolean isHTMLSourceTextPresent(String value) throws Exception {
		URL url = new URL(getLocation());

		InputStream inputStream = url.openStream();

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			Pattern pattern = Pattern.compile(value);

			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				return true;
			}
		}

		inputStream.close();

		bufferedReader.close();

		return false;
	}

	@Override
	public boolean isNotChecked(String locator) {
		return !isChecked(locator);
	}

	@Override
	public boolean isNotEditable(String locator) {
		return !isEditable(locator);
	}

	@Override
	public boolean isNotPartialText(String locator, String value) {
		return !isPartialText(locator, value);
	}

	@Override
	public boolean isNotPartialTextAceEditor(String locator, String value) {
		return !isPartialTextAceEditor(locator, value);
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		if (isElementNotPresent(selectLocator)) {
			return false;
		}

		String[] selectedLabels = getSelectedLabels(selectLocator);

		List<String> selectedLabelsList = Arrays.asList(selectedLabels);

		return !selectedLabelsList.contains(pattern);
	}

	@Override
	public boolean isNotText(String locator, String value) throws Exception {
		return !isText(locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) throws Exception {
		return !isValue(locator, value);
	}

	@Override
	public boolean isNotVisible(String locator) {
		return !isVisible(locator);
	}

	@Override
	public boolean isPartialText(String locator, String value) {
		WebElement webElement = getWebElement(locator, "1");

		String text = webElement.getText();

		return text.contains(value);
	}

	@Override
	public boolean isPartialTextAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator, "1");

		String text = webElement.getText();

		text = text.replace("\n", "");

		return text.contains(value);
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern) {
		if (isElementNotPresent(selectLocator)) {
			return false;
		}

		return pattern.equals(getSelectedLabel(selectLocator, "1"));
	}

	@Override
	public boolean isSikuliImagePresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		if (screenRegion.find(imageTarget) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTCatEnabled() {
		return PropsValues.TCAT_ENABLED;
	}

	@Override
	public boolean isTestName(String testName) {
		if (testName.equals(PoshiRunnerContext.getTestCaseCommandName())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isText(String locator, String value) throws Exception {
		return value.equals(getText(locator, "1"));
	}

	@Override
	public boolean isTextNotPresent(String pattern) {
		return !isTextPresent(pattern);
	}

	@Override
	public boolean isTextPresent(String pattern) {
		WebElement webElement = findElement(By.tagName("body"));

		String text = webElement.getText();

		return text.contains(pattern);
	}

	@Override
	public boolean isValue(String locator, String value) throws Exception {
		return value.equals(getElementValue(locator, "1"));
	}

	@Override
	public boolean isVisible(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		scrollWebElementIntoView(webElement);

		return webElement.isDisplayed();
	}

	@Override
	public void javaScriptClick(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "click");
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mousedown");
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		executeJavaScriptEvent(locator, "MouseEvent", "mouseup");
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		String keycode = keySequence.substring(1);

		Keys keys = Keys.valueOf(keycode);

		actions.keyDown(webElement, keys);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			String keycode = keySequence.substring(1);

			if (isValidKeycode(keycode)) {
				Keys keys = Keys.valueOf(keycode);

				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				WebDriver webDriver = wrapsDriver.getWrappedDriver();

				Actions actions = new Actions(webDriver);

				if (keycode.equals("ALT") || keycode.equals("COMMAND") ||
					keycode.equals("CONTROL") || keycode.equals("SHIFT")) {

					actions.keyDown(webElement, keys);
					actions.keyUp(webElement, keys);

					Action action = actions.build();

					action.perform();
				}
				else {
					webElement.sendKeys(keys);
				}
			}
		}
		else {
			webElement.sendKeys(keySequence);
		}
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		String keycode = keySequence.substring(1);

		Keys keys = Keys.valueOf(keycode);

		actions.keyUp(webElement, keys);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void makeVisible(String locator) {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("element.style.cssText = 'display:inline !important';");
		sb.append("element.style.overflow = 'visible';");
		sb.append("element.style.minHeight = '1px';");
		sb.append("element.style.minWidth = '1px';");
		sb.append("element.style.opacity = '1';");
		sb.append("element.style.visibility = 'visible';");

		WebElement locatorWebElement = getWebElement(locator);

		javascriptExecutor.executeScript(sb.toString(), locatorWebElement);
	}

	@Override
	public Options manage() {
		return _webDriver.manage();
	}

	@Override
	public void mouseDown(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		actions.clickAndHold(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.clickAndHold();
		}
		else {
			actions.moveToElement(webElement);

			actions.clickAndHold(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseMove(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);
		}
		else {
			actions.moveToElement(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOut(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);
		actions.moveByOffset(10, 10);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOver(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseRelease() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.release();

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUp(String locator) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.release(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (Validator.isNotNull(coordString) && coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.release();
		}
		else {
			actions.moveToElement(webElement);
			actions.release(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public Navigation navigate() {
		return _webDriver.navigate();
	}

	@Override
	public void open(String url) {
		String targetURL = url.trim();

		if (targetURL.startsWith("/")) {
			targetURL = PropsValues.PORTAL_URL + targetURL;
		}

		get(targetURL);

		if (PropsValues.BROWSER_TYPE.equals("internetexplorer")) {
			refresh();
		}
	}

	@Override
	public void openWindow(String url, String windowID) {
		open(url);
	}

	@Override
	public void paste(String location) {
		type(location, _clipBoard);
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	@Override
	public void quit() {
		_webDriver.quit();
	}

	@Override
	public void refresh() {
		WebDriver.Navigation navigation = navigate();

		navigation.refresh();

		if (isAlertPresent()) {
			getConfirmation();
		}
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		EmailCommands.replyToEmail(to, body);

		pause("3000");
	}

	@Override
	public void runScript(String script) {
		getEval(script);
	}

	@Override
	public void saveScreenshot() throws Exception {
		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		_screenshotCount++;

		LiferaySeleniumHelper.captureScreen(
			_CURRENT_DIR_NAME + "test-results/functional/screenshots/" +
				_screenshotCount + ".jpg");
	}

	@Override
	public void saveScreenshotAndSource() throws Exception {
	}

	@Override
	public void saveScreenshotBeforeAction(boolean actionFailed)
		throws Exception {

		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		if (actionFailed) {
			_screenshotErrorCount++;
		}

		LiferaySeleniumHelper.captureScreen(
			_CURRENT_DIR_NAME + "test-results/functional/screenshots" +
				"/ScreenshotBeforeAction" + _screenshotErrorCount + ".jpg");
	}

	@Override
	public void scrollBy(String coordString) {
		WebElement webElement = getWebElement("//html");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		javascriptExecutor.executeScript(
			"window.scrollBy(" + coordString + ");");
	}

	@Override
	public void scrollWebElementIntoView(String locator) throws Exception {
		WebElement webElement = getWebElement(locator);

		scrollWebElementIntoView(webElement);
	}

	@Override
	public void select(String selectLocator, String optionLocator) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		String label = optionLocator;

		if (optionLocator.startsWith("index=")) {
			String indexString = optionLocator.substring(6);

			int index = GetterUtil.getInteger(indexString);

			select.selectByIndex(index - 1);
		}
		else if (optionLocator.startsWith("value=")) {
			String value = optionLocator.substring(6);

			if (value.startsWith("regexp:")) {
				String regexp = value.substring(7);

				selectByRegexpValue(selectLocator, regexp);
			}
			else {
				List<WebElement> optionWebElements = select.getOptions();

				for (WebElement optionWebElement : optionWebElements) {
					String optionWebElementValue =
						optionWebElement.getAttribute("value");

					if (optionWebElementValue.equals(value)) {
						label = optionWebElementValue;

						break;
					}
				}

				select.selectByValue(label);
			}
		}
		else {
			if (optionLocator.startsWith("label=")) {
				label = optionLocator.substring(6);
			}

			if (label.startsWith("regexp:")) {
				String regexp = label.substring(7);

				selectByRegexpText(selectLocator, regexp);
			}
			else {
				select.selectByVisibleText(label);
			}
		}
	}

	@Override
	public void selectFieldText() {
		LiferaySeleniumHelper.selectFieldText();
	}

	@Override
	public void selectFrame(String locator) {
		WebDriver.TargetLocator targetLocator = switchTo();

		if (locator.equals("relative=parent")) {
			targetLocator.window(_defaultWindowHandle);

			if (!_frameWebElements.isEmpty()) {
				_frameWebElements.pop();

				if (!_frameWebElements.isEmpty()) {
					targetLocator.frame(_frameWebElements.peek());
				}
			}
		}
		else if (locator.equals("relative=top")) {
			_frameWebElements = new Stack<>();

			targetLocator.window(_defaultWindowHandle);
		}
		else {
			_frameWebElements.push(getWebElement(locator));

			targetLocator.frame(_frameWebElements.peek());
		}
	}

	@Override
	public void selectPopUp(String windowID) {
		Set<String> windowHandles = getWindowHandles();

		if (windowID.equals("") || windowID.equals("null")) {
			String title = getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(getTitle())) {
					return;
				}
			}
		}
		else {
			selectWindow(windowID);
		}
	}

	@Override
	public void selectWindow(String windowID) {
		Set<String> windowHandles = getWindowHandles();

		if (windowID.equals("name=undefined")) {
			String title = getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
		else if (windowID.equals("null")) {
			WebDriver.TargetLocator targetLocator = switchTo();

			targetLocator.window(_defaultWindowHandle);
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (targetWindowTitle.equals(getTitle())) {
					return;
				}
			}

			TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
		}
	}

	@Override
	public void sendActionDescriptionLogger(String description) {
	}

	@Override
	public boolean sendActionLogger(String command, String[] params) {
		return true;
	}

	@Override
	public void sendEmail(String to, String subject, String body)
		throws Exception {

		EmailCommands.sendEmail(to, subject, body);

		pause("3000");
	}

	@Override
	public void sendKeys(String locator, String value) {
		typeKeys(locator, value);
	}

	@Override
	public void sendKeysAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.END));

		typeKeys(locator, "");

		Keyboard keyboard = new DesktopKeyboard();

		Matcher matcher = _aceEditorPattern.matcher(value);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start();

			String line = value.substring(x, y);

			keyboard.type(line.trim());

			String specialCharacter = matcher.group();

			if (specialCharacter.equals("(")) {
				keyboard.type("(");
			}
			else if (specialCharacter.equals("${line.separator}")) {
				keyPress(locator, "\\SPACE");
				keyPress(locator, "\\RETURN");
			}

			x = y + specialCharacter.length();
		}

		String line = value.substring(x);

		keyboard.type(line.trim());
	}

	@Override
	public void sendLogger(String id, String status) {
	}

	@Override
	public void sendMacroDescriptionLogger(String description) {
	}

	@Override
	public void sendTestCaseCommandLogger(String command) {
	}

	@Override
	public void sendTestCaseHeaderLogger(String command) {
	}

	@Override
	public void setDefaultTimeout() {
	}

	@Override
	public void setDefaultTimeoutImplicit() {
		int timeout = PropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		setTimeoutImplicit(String.valueOf(timeout));
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeout(String timeout) {
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
		WebDriver.Options options = manage();

		WebDriver.Timeouts timeouts = options.timeouts();

		if (!PropsValues.BROWSER_TYPE.equals("safari")) {
			timeouts.implicitlyWait(
				GetterUtil.getInteger(timeout), TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public void setWindowSize(String coordString) {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		WebDriver.Options options = webDriver.manage();

		WebDriver.Window window = options.window();

		String[] screenResolution = StringUtil.split(coordString, ",");

		int x = GetterUtil.getInteger(screenResolution[0]);
		int y = GetterUtil.getInteger(screenResolution[1]);

		window.setSize(new Dimension(x, y));
	}

	@Override
	public void sikuliAssertElementNotPresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		if (screenRegion.wait(imageTarget, 5000) != null) {
			throw new Exception("Element is present");
		}
	}

	@Override
	public void sikuliAssertElementPresent(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		screenRegion = screenRegion.wait(imageTarget, 5000);

		if (screenRegion == null) {
			throw new Exception("Element is not present");
		}

		Canvas canvas = new DesktopCanvas();

		ElementAdder elementAdder = canvas.add();

		ElementAreaSetter elementAreaSetter = elementAdder.box();

		elementAreaSetter.around(screenRegion);

		canvas.display(2);
	}

	@Override
	public void sikuliClick(String image) throws Exception {
		Mouse mouse = new DesktopMouse();

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		ScreenRegion imageTargetScreenRegion = screenRegion.find(imageTarget);

		if (imageTargetScreenRegion != null) {
			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	@Override
	public void sikuliClickByIndex(String image, String index)
		throws Exception {

		Mouse mouse = new DesktopMouse();

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		List<ScreenRegion> imageTargetScreenRegions = screenRegion.findAll(
			imageTarget);

		ScreenRegion imageTargetScreenRegion = imageTargetScreenRegions.get(
			Integer.parseInt(index));

		if (imageTargetScreenRegion != null) {
			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	@Override
	public void sikuliDragAndDrop(String image, String coordString)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		screenRegion = screenRegion.find(imageTarget);

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());

		Robot robot = new Robot();

		robot.delay(1000);

		mouse.press();

		robot.delay(2000);

		String[] coords = coordString.split(",");

		Location location = screenRegion.getCenter();

		int x = location.getX() + GetterUtil.getInteger(coords[0]);
		int y = location.getY() + GetterUtil.getInteger(coords[1]);

		robot.mouseMove(x, y);

		robot.delay(1000);

		mouse.release();
	}

	@Override
	public void sikuliLeftMouseDown() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.press();
	}

	@Override
	public void sikuliLeftMouseUp() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.release();
	}

	@Override
	public void sikuliMouseMove(String image) throws Exception {
		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(image);

		screenRegion = screenRegion.find(imageTarget);

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());
	}

	@Override
	public void sikuliRightMouseDown() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightPress();
	}

	@Override
	public void sikuliRightMouseUp() throws Exception {
		pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightRelease();
	}

	@Override
	public void sikuliType(String image, String value) throws Exception {
		sikuliClick(image);

		pause("1000");

		Keyboard keyboard = new DesktopKeyboard();

		if (value.contains("${line.separator}")) {
			String[] tokens = StringUtil.split(value, "${line.separator}");

			for (int i = 0; i < tokens.length; i++) {
				keyboard.type(tokens[i]);

				if ((i + 1) < tokens.length) {
					keyboard.type(Key.ENTER);
				}
			}

			if (value.endsWith("${line.separator}")) {
				keyboard.type(Key.ENTER);
			}
		}
		else {
			keyboard.type(value);
		}
	}

	@Override
	public void sikuliUploadCommonFile(String image, String value)
		throws Exception {

		sikuliClick(image);

		Keyboard keyboard = new DesktopKeyboard();

		String filePath =
			FileUtil.getSeparator() + _TEST_DEPENDENCIES_DIR_NAME +
				FileUtil.getSeparator() + value;

		filePath = LiferaySeleniumHelper.getSourceDirFilePath(filePath);

		filePath = StringUtil.replace(filePath, "/", FileUtil.getSeparator());

		if (OSDetector.isApple()) {
			keyboard.keyDown(Key.CMD);
			keyboard.keyDown(Key.SHIFT);

			keyboard.type("g");

			keyboard.keyUp(Key.CMD);
			keyboard.keyUp(Key.SHIFT);

			sikuliType(image, filePath);

			keyboard.type(Key.ENTER);
		}
		else {
			keyboard.keyDown(Key.CTRL);

			keyboard.type("a");

			keyboard.keyUp(Key.CTRL);

			sikuliType(image, filePath);
		}

		keyboard.type(Key.ENTER);
	}

	@Override
	public void sikuliUploadTCatFile(String image, String value)
		throws Exception {

		String fileName = PropsValues.TCAT_ADMIN_REPOSITORY + "/" + value;

		if (OSDetector.isWindows()) {
			fileName = StringUtil.replace(fileName, "/", "\\");
		}

		sikuliType(image, fileName);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.type(Key.ENTER);
	}

	@Override
	public void sikuliUploadTempFile(String image, String value)
		throws Exception {

		sikuliClick(image);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.keyDown(Key.CTRL);

		keyboard.type("a");

		keyboard.keyUp(Key.CTRL);

		String fileName = getOutputDirName() + "/" + value;

		if (OSDetector.isWindows()) {
			fileName = StringUtil.replace(fileName, "/", "\\");
		}

		sikuliType(image, fileName);

		keyboard.type(Key.ENTER);
	}

	@Override
	public void startLogger() {
	}

	@Override
	public void stop() {
		quit();
	}

	@Override
	public void stopLogger() {
	}

	@Override
	public TargetLocator switchTo() {
		return _webDriver.switchTo();
	}

	@Override
	public void type(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		webElement.clear();

		typeKeys(locator, value);
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		typeKeys(locator, "");

		Keyboard keyboard = new DesktopKeyboard();

		Matcher matcher = _aceEditorPattern.matcher(value);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start();

			String line = value.substring(x, y);

			keyboard.type(line.trim());

			String specialCharacter = matcher.group();

			if (specialCharacter.equals("(")) {
				keyboard.type("(");
			}
			else if (specialCharacter.equals("${line.separator}")) {
				keyPress(locator, "\\SPACE");
				keyPress(locator, "\\RETURN");
			}

			x = y + specialCharacter.length();
		}

		String line = value.substring(x);

		keyboard.type(line.trim());

		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END));

		webElement.sendKeys(Keys.DELETE);
	}

	@Override
	public void typeAlloyEditor(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBuilder sb = new StringBuilder();

		sb.append("CKEDITOR.instances[\"");

		String titleAttribute = getAttribute(locator + "@title");

		int x = titleAttribute.indexOf(",");

		int y = titleAttribute.indexOf(",", x + 1);

		if (y == -1) {
			y = titleAttribute.length();
		}

		sb.append(titleAttribute.substring(x + 2, y));

		sb.append("\"].setData(\"");
		sb.append(HtmlUtil.escapeJS(value.replace("\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
	}

	@Override
	public void typeCKEditor(String locator, String value) {
		StringBuilder sb = new StringBuilder();

		String idAttribute = getAttribute(locator + "@id");

		int x = idAttribute.indexOf("cke__");

		int y = idAttribute.indexOf("cke__", x + 1);

		if (y == -1) {
			y = idAttribute.length();
		}

		sb.append(idAttribute.substring(x + 4, y));

		sb.append(".setHTML(\"");
		sb.append(HtmlUtil.escapeJS(value.replace("\\", "\\\\")));
		sb.append("\")");

		runScript(sb.toString());
	}

	@Override
	public void typeEditor(String locator, String value) {
		WrapsDriver wrapsDriver = (WrapsDriver)getWebElement(locator);

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrapsDriver.getWrappedDriver();

		StringBuilder sb = new StringBuilder();

		sb.append("CKEDITOR.instances[\"");
		sb.append(getEditorName(locator));
		sb.append("\"].setData(\"");
		sb.append(HtmlUtil.escapeJS(value.replace("\\", "\\\\")));
		sb.append("\");");

		javascriptExecutor.executeScript(sb.toString());
	}

	@Override
	public void typeKeys(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		if (value.contains("line-number=")) {
			value = value.replaceAll("line-number=\"\\d+\"", "");
		}

		int i = 0;

		Set<Integer> specialCharIndexes = getSpecialCharIndexes(value);

		for (int specialCharIndex : specialCharIndexes) {
			webElement.sendKeys(value.substring(i, specialCharIndex));

			String specialChar = String.valueOf(value.charAt(specialCharIndex));

			if (specialChar.equals("-")) {
				webElement.sendKeys(Keys.SUBTRACT);
			}
			else if (specialChar.equals("\t")) {
				webElement.sendKeys(Keys.TAB);
			}
			else {
				webElement.sendKeys(
					Keys.SHIFT, _keysSpecialChars.get(specialChar));
			}

			i = specialCharIndex + 1;
		}

		webElement.sendKeys(value.substring(i, value.length()));
	}

	@Override
	public void typeScreen(String value) {
		LiferaySeleniumHelper.typeScreen(value);
	}

	@Override
	public void uncheck(String locator) {
		WebElement webElement = getWebElement(locator);

		if (webElement.isSelected()) {
			webElement.click();
		}
	}

	@Override
	public void uploadCommonFile(String location, String value)
		throws Exception {

		String filePath =
			FileUtil.getSeparator() + _testDependenciesDirName +
				FileUtil.getSeparator() + value;

		filePath = LiferaySeleniumHelper.getSourceDirFilePath(filePath);

		if (OSDetector.isWindows()) {
			filePath = StringUtil.replace(filePath, "/", "\\");
		}

		uploadFile(location, filePath);
	}

	@Override
	public void uploadFile(String location, String value) {
		makeVisible(location);

		WebElement webElement = getWebElement(location);

		webElement.sendKeys(value);
	}

	@Override
	public void uploadTempFile(String location, String value) {
		String filePath = _outputDirName + FileUtil.getSeparator() + value;

		if (OSDetector.isWindows()) {
			filePath = StringUtil.replace(filePath, "/", "\\");
		}

		uploadFile(location, filePath);
	}

	@Override
	public void waitForConfirmation(String pattern) throws Exception {
		int timeout =
			PropsValues.TIMEOUT_EXPLICIT_WAIT /
				PropsValues.TIMEOUT_IMPLICIT_WAIT;

		for (int second = 0;; second++) {
			if (second >= timeout) {
				assertConfirmation(pattern);
			}

			try {
				if (isConfirmation(pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void waitForConsoleTextNotPresent(String text) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertConsoleTextNotPresent(text);
			}

			try {
				if (isConsoleTextNotPresent(text)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForConsoleTextPresent(String text) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertConsoleTextPresent(text);
			}

			try {
				if (isConsoleTextPresent(text)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForElementNotPresent(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertElementNotPresent(locator);
			}

			try {
				if (isElementNotPresent(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForElementPresent(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertElementPresent(locator);
			}

			try {
				if (isElementPresent(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertNotPartialText(locator, value);
			}

			try {
				if (isNotPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertNotSelectedLabel(selectLocator, pattern);
			}

			try {
				if (isNotSelectedLabel(selectLocator, pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForNotText(String locator, String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertNotText(locator, value);
			}

			try {
				if (isNotText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForNotValue(String locator, String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertNotValue(locator, value);
			}

			try {
				if (isNotValue(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForNotVisible(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertNotVisible(locator);
			}

			try {
				if (isNotVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertPartialText(locator, value);
			}

			try {
				if (isPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForPartialTextAceEditor(String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertPartialTextAceEditor(locator, value);
			}

			try {
				if (isPartialTextAceEditor(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		int wait = 0;

		if (timeout.equals("")) {
			wait = 30;
		}
		else {
			wait = GetterUtil.getInteger(timeout) / 1000;
		}

		if (windowID.equals("") || windowID.equals("null")) {
			for (int i = 0; i <= wait; i++) {
				Set<String> windowHandles = getWindowHandles();

				if (windowHandles.size() > 1) {
					return;
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (int i = 0; i <= wait; i++) {
				for (String windowHandle : getWindowHandles()) {
					WebDriver.TargetLocator targetLocator = switchTo();

					targetLocator.window(windowHandle);

					if (targetWindowTitle.equals(getTitle())) {
						targetLocator.window(getDefaultWindowHandle());

						return;
					}
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}

		TestCase.fail("Unable to find the window ID \"" + windowID + "\"");
	}

	@Override
	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertSelectedLabel(selectLocator, pattern);
			}

			try {
				if (isSelectedLabel(selectLocator, pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForText(String locator, String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertText(locator, value);
			}

			try {
				if (isText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForTextNotPresent(String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertTextNotPresent(value);
			}

			try {
				if (isTextNotPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForTextPresent(String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertTextPresent(value);
			}

			try {
				if (isTextPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForValue(String locator, String value) throws Exception {
		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertValue(locator, value);
			}

			try {
				if (isValue(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	@Override
	public void waitForVisible(String locator) throws Exception {
		for (int second = 0;; second++) {
			if (second >= PropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertVisible(locator);
			}

			try {
				if (isVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	protected void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		alert.accept();
	}

	protected void executeJavaScriptEvent(
		String locator, String eventType, String event) {

		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder(6);

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('");
		sb.append(eventType);
		sb.append("');event.initEvent('");
		sb.append(event);
		sb.append("', true, false);element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	protected By getBy(String locator) {
		if (locator.startsWith("//")) {
			return By.xpath(locator);
		}
		else if (locator.startsWith("class=")) {
			locator = locator.substring(6);

			return By.className(locator);
		}
		else if (locator.startsWith("css=")) {
			locator = locator.substring(4);

			return By.cssSelector(locator);
		}
		else if (locator.startsWith("link=")) {
			locator = locator.substring(5);

			return By.linkText(locator);
		}
		else if (locator.startsWith("name=")) {
			locator = locator.substring(5);

			return By.name(locator);
		}
		else if (locator.startsWith("tag=")) {
			locator = locator.substring(4);

			return By.tagName(locator);
		}
		else if (locator.startsWith("xpath=") || locator.startsWith("xPath=")) {
			locator = locator.substring(6);

			return By.xpath(locator);
		}
		else {
			return By.id(locator);
		}
	}

	protected String getCSSSource(String htmlSource) throws Exception {
		org.jsoup.nodes.Document htmlDocument = Jsoup.parse(htmlSource);

		Elements elements = htmlDocument.select("link[type=text/css]");

		StringBuilder sb = new StringBuilder();

		for (Element element : elements) {
			String href = element.attr("href");

			if (!href.contains(PropsValues.PORTAL_URL)) {
				href = PropsValues.PORTAL_URL + href;
			}

			Connection connection = Jsoup.connect(href);

			org.jsoup.nodes.Document document = connection.get();

			sb.append(document.text());

			sb.append("\n");
		}

		return sb.toString();
	}

	protected String getDefaultWindowHandle() {
		return _defaultWindowHandle;
	}

	protected String getEditorName(String locator) {
		String titleAttribute = getAttribute(locator + "@title");

		if (titleAttribute.contains("Rich Text Editor,")) {
			int x = titleAttribute.indexOf(",");

			int y = titleAttribute.indexOf(",", x + 1);

			if (y == -1) {
				y = titleAttribute.length();
			}

			return titleAttribute.substring(x + 2, y);
		}

		String idAttribute = getAttribute(locator + "@id");

		if (idAttribute.contains("cke__")) {
			int x = idAttribute.indexOf("cke__");

			int y = idAttribute.indexOf("cke__", x + 1);

			if (y == -1) {
				y = idAttribute.length();
			}

			return idAttribute.substring(x + 4, y);
		}

		return idAttribute;
	}

	protected int getElementPositionBottom(String locator) {
		return getElementPositionTop(locator) + getElementHeight(locator);
	}

	protected int getElementPositionCenterX(String locator) {
		return getElementPositionLeft(locator) + (getElementWidth(locator) / 2);
	}

	protected int getElementPositionCenterY(String locator) {
		return getElementPositionTop(locator) + (getElementHeight(locator) / 2);
	}

	protected int getElementPositionLeft(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		Point point = webElement.getLocation();

		return point.getX();
	}

	protected int getElementPositionRight(String locator) {
		return getElementPositionLeft(locator) + getElementWidth(locator);
	}

	protected int getElementPositionTop(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		Point point = webElement.getLocation();

		return point.getY();
	}

	protected Point getFramePoint() {
		int x = 0;
		int y = 0;

		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		WebDriver.TargetLocator targetLocator = wrappedWebDriver.switchTo();

		targetLocator.window(_defaultWindowHandle);

		for (WebElement webElement : _frameWebElements) {
			Point point = webElement.getLocation();

			x += point.getX();
			y += point.getY();

			targetLocator.frame(webElement);
		}

		return new Point(x, y);
	}

	protected int getFramePositionLeft() {
		Point point = getFramePoint();

		return point.getX();
	}

	protected int getFramePositionTop() {
		Point point = getFramePoint();

		return point.getY();
	}

	protected ImageTarget getImageTarget(String image) throws Exception {
		String filePath =
			FileUtil.getSeparator() + getSikuliImagesDirName() + image;

		File file = new File(
			LiferaySeleniumHelper.getSourceDirFilePath(filePath));

		return new ImageTarget(file);
	}

	protected int getNavigationBarHeight() {
		return _navigationBarHeight;
	}

	protected int getScrollOffsetX() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageXOffset = javascriptExecutor.executeScript(
			"return window.pageXOffset;");

		return GetterUtil.getInteger(pageXOffset);
	}

	protected int getScrollOffsetY() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		Object pageYOffset = javascriptExecutor.executeScript(
			"return window.pageYOffset;");

		return GetterUtil.getInteger(pageYOffset);
	}

	protected Set<Integer> getSpecialCharIndexes(String value) {
		Set<Integer> specialCharIndexes = new TreeSet<>();

		Set<String> specialChars = new TreeSet<>();

		specialChars.addAll(_keysSpecialChars.keySet());

		specialChars.add("-");
		specialChars.add("\t");

		for (String specialChar : specialChars) {
			while (value.contains(specialChar)) {
				specialCharIndexes.add(value.indexOf(specialChar));

				value = StringUtil.replaceFirst(value, specialChar, " ");
			}
		}

		return specialCharIndexes;
	}

	protected int getViewportHeight() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		return GetterUtil.getInteger(
			javascriptExecutor.executeScript("return window.innerHeight;"));
	}

	protected int getViewportPositionBottom() {
		return getScrollOffsetY() + getViewportHeight();
	}

	protected WebElement getWebElement(String locator) {
		return getWebElement(locator, null);
	}

	protected WebElement getWebElement(String locator, String timeout) {
		List<WebElement> webElements = getWebElements(locator, timeout);

		if (!webElements.isEmpty()) {
			return webElements.get(0);
		}

		return null;
	}

	protected List<WebElement> getWebElements(String locator) {
		return getWebElements(locator, null);
	}

	protected List<WebElement> getWebElements(String locator, String timeout) {
		if (timeout != null) {
			setTimeoutImplicit(timeout);
		}

		try {
			List<WebElement> webElements = new ArrayList<>();

			for (WebElement webElement : findElements(getBy(locator))) {
				webElements.add(new RetryWebElementImpl(locator, webElement));
			}

			return webElements;
		}
		finally {
			if (timeout != null) {
				setDefaultTimeoutImplicit();
			}
		}
	}

	protected Point getWindowPoint() {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		WebDriver.Options options = wrappedWebDriver.manage();

		WebDriver.Window window = options.window();

		return window.getPosition();
	}

	protected int getWindowPositionLeft() {
		Point point = getWindowPoint();

		return point.getX();
	}

	protected int getWindowPositionTop() {
		Point point = getWindowPoint();

		return point.getY();
	}

	protected void initKeysSpecialChars() {
		_keysSpecialChars.put("!", "1");
		_keysSpecialChars.put("#", "3");
		_keysSpecialChars.put("$", "4");
		_keysSpecialChars.put("%", "5");
		_keysSpecialChars.put("&", "7");
		_keysSpecialChars.put("(", "9");
		_keysSpecialChars.put(")", "0");
		_keysSpecialChars.put("<", ",");
		_keysSpecialChars.put(">", ".");
	}

	protected boolean isObscured(WebElement webElement) {
		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrapsDriver.getWrappedDriver();

		StringBuilder sb = new StringBuilder();

		sb.append("var element = arguments[0];");
		sb.append("console.log(element);");
		sb.append("var rect = element.getBoundingClientRect();");
		sb.append("elementX = (rect.right + rect.left) / 2;");
		sb.append("elementY = (rect.top + rect.bottom) / 2;");
		sb.append("var newElement = ");
		sb.append("document.elementFromPoint(elementX, elementY);");
		sb.append("if (element == newElement) {");
		sb.append("return false;}");
		sb.append("return true;");

		Boolean obscured = (Boolean)javascriptExecutor.executeScript(
			sb.toString(), webElement);

		return obscured.booleanValue();
	}

	protected boolean isValidKeycode(String keycode) {
		for (Keys keys : Keys.values()) {
			String keysName = keys.name();

			if (keysName.equals(keycode)) {
				return true;
			}
		}

		return false;
	}

	protected void saveWebPage(String fileName, String htmlSource)
		throws Exception {

		if (!PropsValues.SAVE_WEB_PAGE) {
			return;
		}

		StringBuilder sb = new StringBuilder(3);

		sb.append("<style>");
		sb.append(getCSSSource(htmlSource));
		sb.append("</style></html>");

		FileUtil.write(fileName, htmlSource.replace("<\\html>", sb.toString()));
	}

	protected void scrollWebElementIntoView(WebElement webElement) {
		if (!webElement.isDisplayed() || isObscured(webElement)) {
			WrapsDriver wrapsDriver = (WrapsDriver)webElement;

			WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

			JavascriptExecutor javascriptExecutor =
				(JavascriptExecutor)wrappedWebDriver;

			javascriptExecutor.executeScript(
				"arguments[0].scrollIntoView(false);", webElement);
		}
	}

	protected void selectByRegexpText(String selectLocator, String regexp) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementText = optionWebElement.getText();

			Matcher matcher = pattern.matcher(optionWebElementText);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	protected void selectByRegexpValue(String selectLocator, String regexp) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementValue = optionWebElement.getAttribute(
				"value");

			Matcher matcher = pattern.matcher(optionWebElementValue);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	protected void setDefaultWindowHandle(String defaultWindowHandle) {
		_defaultWindowHandle = defaultWindowHandle;
	}

	protected void setNavigationBarHeight(int navigationBarHeight) {
		_navigationBarHeight = navigationBarHeight;
	}

	private static final String _CURRENT_DIR_NAME =
		PoshiRunnerGetterUtil.getCanonicalPath(".");

	private static final String _OUTPUT_DIR_NAME = PropsValues.OUTPUT_DIR_NAME;

	private static final String _TEST_DEPENDENCIES_DIR_NAME =
		PropsValues.TEST_DEPENDENCIES_DIR_NAME;

	private final Pattern _aceEditorPattern = Pattern.compile(
		"\\(|\\$\\{line\\.separator\\}");
	private String _clipBoard = "";
	private String _defaultWindowHandle;
	private Stack<WebElement> _frameWebElements = new Stack<>();
	private final Map<String, String> _keysSpecialChars = new HashMap<>();
	private int _navigationBarHeight;
	private final String _outputDirName;
	private String _primaryTestSuiteName;
	private int _screenshotCount;
	private int _screenshotErrorCount;
	private final String _sikuliImagesDirName;
	private final String _testDependenciesDirName;
	private final WebDriver _webDriver;

}