<#assign mbMessageCounter = dataFactory.newInteger()>
<#assign totalMBMessageCount = maxMBCategoryCount * maxMBThreadCount * maxMBMessageCount>

<#assign mbSystemCategory = dataFactory.addMBCategory(0, 0, 0, 0, "", "", 0, 0)>

${sampleSQLBuilder.insertMBCategory(mbSystemCategory)}

<#list 1..maxMBCategoryCount as mbCategoryCount>
	<#assign mbCategory = dataFactory.addMBCategory(counter.get(), dataFactory.guestGroup.groupId, companyId, firstUserId, "Test Category " + mbCategoryCount, "This is a test category " + mbCategoryCount + ".", maxMBThreadCount, maxMBThreadCount * maxMBMessageCount)>

	${sampleSQLBuilder.insertMBCategory(mbCategory)}

	<#list 1..maxMBThreadCount as mbThreadCount>
		<#assign threadId = counter.get()>
		<#assign parentMessageId = 0>

		<#list 1..maxMBMessageCount as mbMessageCount>
			<#assign mbMessageCounterIncrement = mbMessageCounter.increment()>

			<#assign mbMessage = dataFactory.addMBMessage(mbCategory.groupId, firstUserId, mbCategory.categoryId, threadId, parentMessageId, "Test Message " + mbMessageCount, "This is a test message " + mbMessageCount + ".")>

			${sampleSQLBuilder.insertMBMessage(mbMessage)}

			<#if (mbMessageCount_index = 0)>
				<#assign parentMessageId = mbMessage.messageId>
				<#assign rootMessageId = mbMessage.messageId>
			</#if>
		</#list>

		<#assign mbThread = dataFactory.addMBThread(threadId, mbCategory.groupId, mbCategory.categoryId, rootMessageId, maxMBCategoryCount, firstUserId)>

		${sampleSQLBuilder.insertMBThread(mbThread)}

		${mbMessagesCsvWriter.write(mbCategory.categoryId + "," + rootMessageId + ",")}

		<#if (mbMessageCounter.value < totalMBMessageCount)>
			${mbMessagesCsvWriter.write("\n")}
		</#if>
	</#list>
</#list>