<#list dataFactory.groups as group>
	<#assign privateLayouts = []>

	<#if group.name == "Guest">
		<#assign publicLayouts = [
			dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,"),
			dataFactory.addLayout(2, "Forums", "/forums", "", "19,")
		]>
	<#else>
		<#assign publicLayouts = []>
	</#if>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>