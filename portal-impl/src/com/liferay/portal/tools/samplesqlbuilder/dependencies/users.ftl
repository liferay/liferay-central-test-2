<#setting number_format = "0">

<#assign groups = [dataFactory.guestGroup]>
<#assign organizations = []>
<#assign privateLayouts = []>
<#assign roles = [dataFactory.administratorRole]>

<#assign firstNames = dataFactory.userNames?first>
<#assign lastNames = dataFactory.userNames?last>

<#assign userCounter = dataFactory.newInteger()>

<#list lastNames as lastName>
	<#list firstNames as firstName>
		<#assign userCounterIncrement = userCounter.increment()>

		<#assign contact = dataFactory.addContact(firstName, lastName)>
		<#assign user = dataFactory.addUser(false, contact)>

		<#assign group = dataFactory.addGroup(dataFactory.userClassName.classNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName)>

		<#assign publicLayouts = [
			dataFactory.addLayout(1, "Home", "/home", "", "33,")
		]>

		${sampleSQLBuilder.insertUser(contact, group, groups, organizations, privateLayouts, publicLayouts, roles, user)}

		<#assign blogsStatsUser = dataFactory.addBlogsStatsUser(group.groupId, user.userId)>

		${sampleSQLBuilder.insertBlogsStatsUser(blogsStatsUser)}

		<#assign mbStatsUser = dataFactory.addMBStatsUser(group.groupId, user.userId)>

		${sampleSQLBuilder.insertMBStatsUser(mbStatsUser)}

		${usersCsvWriter.write(stringUtil.lowerCase(firstName + lastName) + "," + group.groupId + ",")}

		<#if (userCounter.value < maxUserCount)>
			${usersCsvWriter.write("\n")}
		</#if>

		<#if (lastName_index = 0) && (firstName_index = 0)>
			<#assign firstUserId = user.userId>
		</#if>

		<#if (userCounter.value >= maxUserCount)>
			<#break>
		</#if>
	</#list>

	<#if (userCounter.value >= maxUserCount)>
		<#break>
	</#if>
</#list>

<#include "blogs.ftl">

<#include "mb.ftl">