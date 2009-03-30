<#setting number_format = "0">

<#list dataFactory.groups as group>
	<#assign privateLayouts = []>

	<#if group.name == "Guest">
		<#assign publicLayouts = [
			dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,"),
			dataFactory.addLayout(2, "Forums", "/forums", "", "19,")
			dataFactory.addLayout(3, "Blogs", "/blogs", "", "33,")
			dataFactory.addLayout(4, "Wiki", "/wiki", "", "36,")
		]>
	<#else>
		<#assign publicLayouts = []>
	</#if>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>