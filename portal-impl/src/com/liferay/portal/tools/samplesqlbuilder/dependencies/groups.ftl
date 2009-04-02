<#setting number_format = "0">

<#list dataFactory.groups as group>
	<#assign privateLayouts = []>

	<#assign publicLayouts = [
		dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,")
	]>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>

<#list 1..maxGroupsCount as groupCount>
	<#assign groupId = counter.get()>

	<#assign group = dataFactory.addGroup(groupId, dataFactory.groupClassName.classNameId, groupId, "Community " + groupCount, "/community" + groupCount)>

	<#assign privateLayouts = []>

	<#assign publicLayouts = [
		dataFactory.addLayout(1, "Welcome", "/welcome", "58,", "47,"),
		dataFactory.addLayout(2, "Forums", "/forums", "", "19,")
		dataFactory.addLayout(3, "Blogs", "/blogs", "", "33,")
		dataFactory.addLayout(4, "Wiki", "/wiki", "", "36,")
	]>

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">
</#list>