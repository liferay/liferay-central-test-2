<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("function")??>
			<#assign function = element.attributeValue("function")>

			<#assign x = function?last_index_of("#")>

			${seleniumBuilderFileUtil.getVariableName(function?substring(0, x))}Function.${function?substring(x + 1)}(

			<#list 1..seleniumBuilderContext.getFunctionTargetCount(functionName) as i>
				target${i}, value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			);
		<#elseif element.attributeValue("selenium")??>
			<#assign seleniumElement = element>

			<#assign selenium = seleniumElement.attributeValue("selenium")>

			<#if selenium?starts_with("is")>
				return
			</#if>

			<#include "selenium_element.ftl">

			;
		</#if>
	<#elseif name == "if">
		<#assign conditionElement = element.element("condition")>

		if (
			<#assign seleniumElement = conditionElement>

			<#include "selenium_element.ftl">
		) {
			<#assign thenElement = element.element("then")>

			<#assign blockElement = thenElement>

			<#include "block_element.ftl">
		}

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign blockElement = elseElement>

				<#include "block_element.ftl">
			}
		</#if>
	</#if>
</#list>