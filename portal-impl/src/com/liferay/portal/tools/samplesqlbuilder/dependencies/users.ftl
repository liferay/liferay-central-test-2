<#assign groups = [dataFactory.guestGroup]>
<#assign organizations = []>
<#assign privateLayouts = []>
<#assign roles = [dataFactory.userRole]>

<#assign firstNames = dataFactory.userNames?first>
<#assign lastNames = dataFactory.userNames?last>

<#assign userCount = dataFactory.newInteger()>

<#list lastNames as lastName>
	<#list firstNames as firstName>
		<#assign userCountIncrement = userCount.increment()>

		<#assign contact = dataFactory.addContact(firstName, lastName)>
		<#assign user = dataFactory.addUser(false, contact)>

		<#assign group = dataFactory.addGroup(dataFactory.userClassName.classNameId, user.userId, stringUtil.valueOf(user.userId), "/" + user.screenName)>

		<#assign publicLayouts = [
			dataFactory.addLayout(1, "Home", "/home", "", "33,")
		]>

		${sampleSQLBuilder.insertUser(contact, group, groups, organizations, privateLayouts, publicLayouts, roles, user)}

		${usersCsvWriter.write(stringUtil.lowerCase(firstName + lastName) + "," + group.groupId)}

		<#if (userCount.value < maxUserCount)>
			${usersCsvWriter.write("\n")}
		</#if>

		<#if (lastName_index = 0) && (firstName_index = 0)>
			<#assign firstUserId = user.userId>
		</#if>

		<#if (userCount.value >= maxUserCount)>
			<#break>
		</#if>
	</#list>

	<#if (userCount.value >= maxUserCount)>
		<#break>
	</#if>
</#list>

<#assign mbCategory = dataFactory.addMBCategory(dataFactory.guestGroup.groupId, firstUserId, "Test Category", "This is a test category.")>

${sampleSQLBuilder.insertMBCategory(mbCategory)}

<#assign threadId = counter.get()>

<#assign messageCount = 10>

<#list 1..10 as messageCount>
	<#assign mbMessage = dataFactory.addMBMessage(firstUserId, mbCategory.categoryId, threadId, "Test Message " + messageCount, "This is a test message " + messageCount + ".")>

	${sampleSQLBuilder.insertMBMessage(mbMessage)}

	<#if (messageCount_index = 1)>
		<#assign rootMessageId = mbMessage.messageId>
	</#if>
</#list>

<#assign mbThread = dataFactory.addMBThread(threadId, mbCategory.categoryId, rootMessageId, messageCount, firstUserId)>

${sampleSQLBuilder.insertMBThread(mbThread)}