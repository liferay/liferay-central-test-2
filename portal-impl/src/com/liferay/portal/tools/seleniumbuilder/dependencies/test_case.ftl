package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.SeleniumException;

<#assign rootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)} extends BaseTestCase {

	<#if rootElement.element("var")??>
		public ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)}() {
			super();

			<#assign varElements = rootElement.elements("var")>

			<#assign context = "definitionScopeVariables">

			<#list varElements as varElement>
				<#include "var_element.ftl">
			</#list>
		}
	</#if>

	@Override
	public void setUp() throws Exception {
		try {
			selenium = SeleniumUtil.getSelenium();

			if (Validator.isNull(selenium.getPrimaryTestSuiteName())) {
				selenium.setPrimaryTestSuiteName("${seleniumBuilderContext.getTestCaseClassName(testCaseName)}");
			}

			selenium.startLogger();
		}
		catch (Exception e) {
			killBrowser();

			throw e;
		}
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public void test${commandName}() throws Exception {
			commandScopeVariables = new HashMap<String, String>();

			commandScopeVariables.putAll(definitionScopeVariables);

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>

			boolean testPassed = false;
			boolean testSkipped = false;

			try {
				definitionScopeVariables.put("testCaseName", "${testCaseName}TestCase${commandName}");

				<#if rootElement.element("set-up")??>
					commandScopeVariables = new HashMap<String, String>();

					commandScopeVariables.putAll(definitionScopeVariables);

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

					<#assign setUpElement = rootElement.element("set-up")>

					<#assign lineNumber = setUpElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

					<#assign blockElement = setUpElement>

					<#include "test_case_block_element.ftl">

					<#assign lineNumber = setUpElement.attributeValue("line-number")>

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
				</#if>

				<#if commandElement.attributeValue("depends")??>
					<#assign depends = commandElement.attributeValue("depends")>

					if (!ArrayUtil.contains(TestPropsValues.FIXED_ISSUES, "${depends}")) {
						throw new SeleniumException();
					}
				</#if>

				commandScopeVariables = new HashMap<String, String>();

				commandScopeVariables.putAll(definitionScopeVariables);

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

				<#assign lineNumber = commandElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

				<#assign blockElement = commandElement>

				<#include "test_case_block_element.ftl">

				<#assign lineNumber = commandElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");

				testPassed = true;
			}
			catch (SeleniumException se) {
				testSkipped = true;
			}
			finally {
				if (!TestPropsValues.TEST_SKIP_TEAR_DOWN) {
					<#if rootElement.element("tear-down")??>
						commandScopeVariables = new HashMap<String, String>();

						commandScopeVariables.putAll(definitionScopeVariables);

						<#assign tearDownElement = rootElement.element("tear-down")>

						selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "start");

						selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pending");

						<#assign lineNumber = tearDownElement.attributeValue("line-number")>

						selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

						<#assign blockElement = tearDownElement>

						<#include "test_case_block_element.ftl">

						<#assign lineNumber = tearDownElement.attributeValue("line-number")>

						selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
					</#if>
				}

				if (testSkipped) {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "skip");
				}
				else if (testPassed) {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "pass");
				}
				else {
					selenium.sendLogger("${testCaseName?uncap_first}TestCase${commandName}", "fail");
				}
			}
		}
	</#list>

	static {
		<#assign commandElements = rootElement.elements("command")>

		testCaseCount = ${commandElements?size};
	}
}