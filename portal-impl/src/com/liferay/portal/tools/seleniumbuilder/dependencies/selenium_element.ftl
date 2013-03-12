<#assign selenium = seleniumElement.attributeValue("selenium")>

liferaySelenium.${selenium}(

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) == 1>
	target1
<#elseif seleniumBuilderContext.getSeleniumParameterCount(selenium) == 2>
	target1, value1
</#if>

)