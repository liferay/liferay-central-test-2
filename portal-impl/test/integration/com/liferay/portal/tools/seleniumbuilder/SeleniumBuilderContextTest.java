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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderContextTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		try {
			_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(
				_BASE_DIR, ".");

			_seleniumBuilderContext = new SeleniumBuilderContext(
				_seleniumBuilderFileUtil, _LIFERAY_SELENIUM_DIR);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Test
	public void testAction() throws Exception {
		test("Action.action");
	}

	@Test
	public void testActionCaseElementInvalidAttributeValue() throws Exception {
		test(
			"ActionCaseElementInvalidAttributeValue_5.action",
			"Error 1010: Invalid locator-key LOCATOR_KEY_1 at " + _DIR_NAME +
				"/ActionCaseElementInvalidAttributeValue_5.action:3");

		test(
			"ActionCaseElementInvalidAttributeValue_6.action",
			"Error 1010: Invalid locator-key FAIL at " + _DIR_NAME +
				"/ActionCaseElementInvalidAttributeValue_6.action:3");

		test(
			"ActionCaseElementInvalidAttributeValue_7.action",
			"Error 1010: Invalid locator-key LOCATOR_ at " + _DIR_NAME +
				"/ActionCaseElementInvalidAttributeValue_7.action:3");

		test(
			"ActionCaseElementInvalidAttributeValue_8.action",
			"Error 1010: Invalid locator-key _KEY at " + _DIR_NAME +
				"/ActionCaseElementInvalidAttributeValue_8.action:3");
	}

	@Test
	public void testActionCommandElementDuplicateCommandName()
		throws Exception {

		test(
			"ActionCommandElementDuplicateCommandName.action",
			"Error 1009: Duplicate command name click at " + _DIR_NAME +
				"/ActionCommandElementDuplicateCommandName.action:8");
	}

	@Test
	public void testActionCommandElementInvalidCommandName() throws Exception {
		test(
			"ActionCommandElementInvalidCommandName.action",
			"Error 2001: Action command nameFail does not match a function " +
				"name at " + _DIR_NAME +
				"/ActionCommandElementInvalidCommandName.action:2");
	}

	@Test
	public void testActionCommandElementMissingPath() throws Exception {
		test(
			"ActionCommandElementMissingPath.action",
			"Error 2002: Missing matching " +
				"ActionCommandElementMissingPath.path for " + _DIR_NAME +
				"/ActionCommandElementMissingPath.action");
	}

	@Test
	public void testActionExecuteElementInvalidAttributeValue()
		throws Exception {

		test(
			"ActionExecuteElementInvalidAttributeValue_2.action",
			"Error 1011: Invalid function name Clicks at " + _DIR_NAME +
				"/ActionExecuteElementInvalidAttributeValue_2.action:4");

		test(
			"ActionExecuteElementInvalidAttributeValue_3.action",
			"Error 1011: Invalid function name clickAt at " + _DIR_NAME +
				"/ActionExecuteElementInvalidAttributeValue_3.action:4");
	}

	@Test
	public void testActionExecuteElementInvalidFunctionCommand()
		throws Exception {

		test(
			"ActionExecuteElementInvalidFunctionCommand.action",
			"Error 1012: Invalid function command clickAtX at " + _DIR_NAME +
				"/ActionExecuteElementInvalidFunctionCommand.action:4");
	}

	@Test
	public void testActionExecuteElementInvalidFunctionName() throws Exception {
		test(
			"ActionExecuteElementInvalidFunctionName.action",
			"Error 1011: Invalid function name ClickX at " + _DIR_NAME +
				"/ActionExecuteElementInvalidFunctionName.action:4");
	}

	@Test
	public void testActionFileName1008() throws Exception {
		test(
			"BaseLiferay.action",
			"Error 1008: Duplicate file name BaseLiferay at " + _DIR_NAME +
				"/BaseLiferay.action");
	}

	@Test
	public void testFunction() throws Exception {
		test("Function.function");
	}

	@Test
	public void testFunctionCommandElement1009() throws Exception {
		test(
			"FunctionCommandElement1009.function",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/FunctionCommandElement1009.function:6");
	}

	@Test
	public void testFunctionConditionElement1011() throws Exception {
		test(
			"FunctionConditionElement1011_1.function",
			"Error 1011: Invalid function name IsElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1011_1.function:4");

		test(
			"FunctionConditionElement1011_2.function",
			"Error 1011: Invalid function name IsElementPresents at " +
				_DIR_NAME + "/FunctionConditionElement1011_2.function:4");

		test(
			"FunctionConditionElement1011_3.function",
			"Error 1011: Invalid function name isElementPresents at " +
				_DIR_NAME + "/FunctionConditionElement1011_3.function:4");
	}

	@Test
	public void testFunctionConditionElement1012_1() throws Exception {
		test(
			"FunctionConditionElement1012_1.function",
			"Error 1012: Invalid function command isElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1012_1.function:4");
	}

	@Test
	public void testFunctionConditionElement1012_2() throws Exception {
		test(
			"FunctionConditionElement1012_2.function",
			"Error 1012: Invalid selenium command isElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1012_2.function:4");
	}

	@Test
	public void testFunctionExecuteElement1011() throws Exception {
		test(
			"FunctionExecuteElement1011_1.function",
			"Error 1011: Invalid function name ClickX at " + _DIR_NAME +
				"/FunctionExecuteElement1011_1.function:3");

		test(
			"FunctionExecuteElement1011_2.function",
			"Error 1011: Invalid function name Clicks at " + _DIR_NAME +
				"/FunctionExecuteElement1011_2.function:3");

		test(
			"FunctionExecuteElement1011_3.function",
			"Error 1011: Invalid function name clickAt at " + _DIR_NAME +
				"/FunctionExecuteElement1011_3.function:3");
	}

	@Test
	public void testFunctionExecuteElement1012_1() throws Exception {
		test(
			"FunctionExecuteElement1012_1.function",
			"Error 1012: Invalid function command clickAtX at " + _DIR_NAME +
				"/FunctionExecuteElement1012_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1012_2() throws Exception {
		test(
			"FunctionExecuteElement1012_2.function",
			"Error 1012: Invalid selenium command clickX at " + _DIR_NAME +
				"/FunctionExecuteElement1012_2.function:3");
	}

	@Test
	public void testFunctionFileName1008() throws Exception {
		test(
			"Click.function",
			"Error 1008: Duplicate file name Click at " + _DIR_NAME +
				"/Click.function");
	}

	@Test
	public void testMacro() throws Exception {
		test("Macro.macro");
	}

	@Test
	public void testMacroCommandElement1009() throws Exception {
		test(
			"MacroCommandElement1009.macro",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/MacroCommandElement1009.macro:6");
	}

	@Test
	public void testMacroConditionElement1006_5() throws Exception {
		test(
			"MacroConditionElement1006_5.macro",
			"Error 1006: Invalid action attribute value in " +
				_DIR_NAME + "/MacroConditionElement1006_5.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_6() throws Exception {
		test(
			"MacroConditionElement1006_6.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_6.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_7() throws Exception {
		test(
			"MacroConditionElement1006_7.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_7.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_8() throws Exception {
		test(
			"MacroConditionElement1006_8.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_8.macro:4");
	}

	@Test
	public void testMacroConditionElement1010_1() throws Exception {
		test(
			"MacroConditionElement1010_1.macro",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/MacroConditionElement1010_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1010_2() throws Exception {
		test(
			"MacroConditionElement1010_2.macro",
			"Error 1010: Invalid locator-key ${page}_NAME_X at " + _DIR_NAME +
				"/MacroConditionElement1010_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1011_1() throws Exception {
		test(
			"MacroConditionElement1011_1.macro",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/MacroConditionElement1011_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1011_2() throws Exception {
		test(
			"MacroConditionElement1011_2.macro",
			"Error 1011: Invalid macro name Pages at " + _DIR_NAME +
				"/MacroConditionElement1011_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1012_1() throws Exception {
		test(
			"MacroConditionElement1012_1.macro",
			"Error 1012: Invalid action command isElementPresents at " +
				_DIR_NAME + "/MacroConditionElement1012_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1012_2() throws Exception {
		test(
			"MacroConditionElement1012_2.macro",
			"Error 1012: Invalid macro command isPresents at " + _DIR_NAME +
				"/MacroConditionElement1012_2.macro:4");
	}

	@Test
	public void testMacroDefinitionElement() throws Exception {
		test(
			"MacroDefinitionElement1006_2.macro",
			"Error 1006: Invalid extends attribute value in " + _DIR_NAME +
				"/MacroDefinitionElement1006_2.macro:1");

		test(
			"MacroDefinitionElement1006_3.macro",
			"Error 1006: Invalid extends attribute value in " + _DIR_NAME +
				"/MacroDefinitionElement1006_3.macro:1");

		test(
			"MacroDefinitionElement1006_4.macro",
			"Error 1006: Invalid extends attribute value in " + _DIR_NAME +
				"/MacroDefinitionElement1006_4.macro:1");
	}

	@Test
	public void testMacroExecuteElement1006_3() throws Exception {
		test(
			"MacroExecuteElement1006_3.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_3.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_4() throws Exception {
		test(
			"MacroExecuteElement1006_4.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_4.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_5() throws Exception {
		test(
			"MacroExecuteElement1006_5.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_5.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_6() throws Exception {
		test(
			"MacroExecuteElement1006_6.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_6.macro:3");
	}

	@Test
	public void testMacroExecuteElement1010_1() throws Exception {
		test(
			"MacroExecuteElement1010_1.macro",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/MacroExecuteElement1010_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1010_2() throws Exception {
		test(
			"MacroExecuteElement1010_2.macro",
			"Error 1010: Invalid locator-key ${page}_NAME_X at " + _DIR_NAME +
				"/MacroExecuteElement1010_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1011_1() throws Exception {
		test(
			"MacroExecuteElement1011_1.macro",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/MacroExecuteElement1011_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1011_2() throws Exception {
		test(
			"MacroExecuteElement1011_2.macro",
			"Error 1011: Invalid macro name BlogsEntrys at " + _DIR_NAME +
				"/MacroExecuteElement1011_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1012_1() throws Exception {
		test(
			"MacroExecuteElement1012_1.macro",
			"Error 1012: Invalid action command clicks at " + _DIR_NAME +
				"/MacroExecuteElement1012_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1012_2() throws Exception {
		test(
			"MacroExecuteElement1012_2.macro",
			"Error 1012: Invalid macro command pgAdder at " + _DIR_NAME +
				"/MacroExecuteElement1012_2.macro:3");
	}

	@Test
	public void testMacroFileName1008() throws Exception {
		test(
			"BlogsEntry.macro",
			"Error 1008: Duplicate file name BlogsEntry at " + _DIR_NAME +
				"/BlogsEntry.macro");
	}

	@Test
	public void testMacroNotElement1006() throws Exception {
		test(
			"MacroNotElement1006.macro",
			"Error 1006: Invalid action attribute value in " +
				_DIR_NAME + "/MacroNotElement1006.macro:5");
	}

	@Test
	public void testMacroNotElement1010() throws Exception {
		test(
			"MacroNotElement1010.macro",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/MacroNotElement1010.macro:5");
	}

	@Test
	public void testMacroVarElement1010() throws Exception {
		test(
			"MacroVarElement1010.macro",
			"Error 1010: Invalid locator-key FAIL at " + _DIR_NAME +
				"/MacroVarElement1010.macro:2");
	}

	@Test
	public void testMacroVarElement1014() throws Exception {
		test(
			"MacroVarElement1014.macro",
			"Error 1014: Invalid path Fail at " + _DIR_NAME +
				"/MacroVarElement1014.macro:2");
	}

	@Test
	public void testPathExtendPath() throws Exception {
		test("PathExtend.action");

		test(
			"PathExtendPathInvalidAttributeValue_1.action",
			"Error 1010: Invalid locator-key LOCATOR_KEY at " + _DIR_NAME +
				"/PathExtendPathInvalidAttributeValue_1.action:3");

		test(
			"PathExtendPathInvalidAttributeValue_2.action",
			"Error 1010: Invalid locator-key LOCATOR_ at " + _DIR_NAME +
				"/PathExtendPathInvalidAttributeValue_2.action:3");

		test(
			"PathExtendPathInvalidFunctionCommand.action",
			"Error 1012: Invalid function command Function at " + _DIR_NAME +
				"/PathExtendPathInvalidFunctionCommand.action:4");
	}

	@Test
	public void testPathVariable1010() throws Exception {
		test(
			"PathVariable1010.action",
			"Error 1010: Invalid locator-key LOCATO at " + _DIR_NAME +
				"/PathVariable1010.action:3");
	}

	@Test
	public void testPathVariable1012() throws Exception {
		test(
			"PathVariable1012.action",
			"Error 1012: Invalid function command HELLOWORLD at " + _DIR_NAME +
				"/PathVariable1012.action:4");
	}

	@Test
	public void testTestCaseCommandElement1009() throws Exception {
		test(
			"TestCaseCommandElement1009.testcase",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/TestCaseCommandElement1009.testcase:8");
	}

	@Test
	public void testTestCaseDefinitionElement1006_2() throws Exception {
		test(
			"TestCaseDefinitionElement1006_2.testcase",
			"Error 1006: Invalid extends attribute value in " + _DIR_NAME +
				"/TestCaseDefinitionElement1006_2.testcase:1");
	}

	@Test
	public void testTestCaseDefinitionElement1006_3() throws Exception {
		test(
			"TestCaseDefinitionElement1006_3.testcase",
			"Error 1006: Invalid extends attribute value in " + _DIR_NAME +
				"/TestCaseDefinitionElement1006_3.testcase:1");
	}

	@Test
	public void testTestCaseExecuteElement1006_3() throws Exception {
		test(
			"TestCaseExecuteElement1006_3.testcase",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_3.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1006_4() throws Exception {
		test(
			"TestCaseExecuteElement1006_4.testcase",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_4.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1006_5() throws Exception {
		test(
			"TestCaseExecuteElement1006_5.testcase",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_5.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1006_6() throws Exception {
		test(
			"TestCaseExecuteElement1006_6.testcase",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_6.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1010() throws Exception {
		test(
			"TestCaseExecuteElement1010.testcase",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/TestCaseExecuteElement1010.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1011_1() throws Exception {
		test(
			"TestCaseExecuteElement1011_1.testcase",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/TestCaseExecuteElement1011_1.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1011_2() throws Exception {
		test(
			"TestCaseExecuteElement1011_2.testcase",
			"Error 1011: Invalid macro name BlogsEntrys at " + _DIR_NAME +
				"/TestCaseExecuteElement1011_2.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1012_1() throws Exception {
		test(
			"TestCaseExecuteElement1012_1.testcase",
			"Error 1012: Invalid action command clicks at " + _DIR_NAME +
				"/TestCaseExecuteElement1012_1.testcase:5");
	}

	@Test
	public void testTestCaseExecuteElement1012_2() throws Exception {
		test(
			"TestCaseExecuteElement1012_2.testcase",
			"Error 1012: Invalid macro command pgAdder at " + _DIR_NAME +
				"/TestCaseExecuteElement1012_2.testcase:5");
	}

	@Test
	public void testTestCaseFileName1008() throws Exception {
		test(
			"CPBlogsAcceptance.testcase",
			"Error 1008: Duplicate file name CPBlogsAcceptance at " +
				_DIR_NAME + "/CPBlogsAcceptance.testcase");
	}

	@Test
	public void testTestCaseSetUpElement1006_2() throws Exception {
		test("TestCase.testcase");

		test(
			"TestCaseSetUpElement1006_2.testcase",
			"Error 1006: Invalid test-case attribute value in " + _DIR_NAME +
				"/TestCaseSetUpElement1006_2.testcase:5");
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null, false);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, expectedErrorMessage, true);
	}

	protected void test(
			String fileName, String expectedErrorMessage,
			boolean expectException)
		throws Exception {

		String actualErrorMessage = null;

		try {
			if (fileName.endsWith(".action")) {
				String pathFileName = StringUtil.replace(
					fileName, ".action", ".path");

				if (FileUtil.exists(
						_BASE_DIR + _DIR_NAME + "/" + pathFileName)) {

					_seleniumBuilderContext.addFile(
						_DIR_NAME + "/" + pathFileName);
				}
			}

			_seleniumBuilderContext.addFile(_DIR_NAME + "/" + fileName);

			_seleniumBuilderContext.validateElements(
				_DIR_NAME + "/" + fileName);
		}
		catch (IllegalArgumentException iae) {
			actualErrorMessage = iae.getMessage();
		}
		finally {
			if (expectException) {
				Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
			}
			else {
				Assert.assertEquals(null, actualErrorMessage);
			}
		}
	}

	private static final String _BASE_DIR =
		"./portal-impl/test/integration/com/liferay/portal/tools" +
			"/seleniumbuilder/dependencies/context";

	private static final String _DIR_NAME = "/..";

	private static final String _LIFERAY_SELENIUM_DIR =
		"../../../../../../../../../../portal-web/test/functional/com/liferay" +
			"/portalweb/portal/util/liferayselenium/";

	private static final Log _log = LogFactoryUtil.getLog(
		SeleniumBuilderContextTest.class);

	private static SeleniumBuilderContext _seleniumBuilderContext;
	private static SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}