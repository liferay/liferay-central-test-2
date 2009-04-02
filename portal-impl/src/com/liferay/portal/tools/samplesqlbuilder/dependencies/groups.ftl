<#setting number_format = "0">

<#list dataFactory.groups as group>
	<#include "groups_guest_private_layouts.ftl">
	<#include "groups_guest_public_layouts.ftl">

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#list>

<#list 1..maxGroupsCount as groupCount>
	<#assign groupId = counter.get()>

	<#assign group = dataFactory.addGroup(groupId, dataFactory.groupClassName.classNameId, groupId, "Community " + groupCount, "/community" + groupCount)>

	<#include "groups_group_private_layouts.ftl">
	<#include "groups_group_public_layouts.ftl">

	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">
</#list>