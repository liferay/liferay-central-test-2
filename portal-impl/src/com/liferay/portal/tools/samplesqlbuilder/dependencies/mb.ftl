<#assign mbMessageCounter = dataFactory.newInteger()>
<#assign totalMBMessageCount = maxMBCategoryCount * maxMBThreadCount * maxMBMessageCount>

<#list 1..maxMBCategoryCount as mbCategoryCount>
	<#assign mbCategory = dataFactory.addMBCategory(dataFactory.guestGroup.groupId, firstUserId, "Test Category " + mbCategoryCount, "This is a test category " + mbCategoryCount + ".")>

	${sampleSQLBuilder.insertMBCategory(mbCategory)}

	<#list 1..maxMBThreadCount as mbThreadCount>
		<#assign threadId = counter.get()>

		<#list 1..maxMBMessageCount as mbMessageCount>
			<#assign mbMessageCounterIncrement = mbMessageCounter.increment()>

			<#assign mbMessage = dataFactory.addMBMessage(firstUserId, mbCategory.categoryId, threadId, "Test Message " + mbMessageCount, "This is a test message " + mbMessageCount + ".")>

			${sampleSQLBuilder.insertMBMessage(mbMessage)}

			<#if (mbMessageCount_index = 0)>
				<#assign rootMessageId = mbMessage.messageId>
			</#if>
		</#list>

		<#assign mbThread = dataFactory.addMBThread(threadId, mbCategory.categoryId, rootMessageId, maxMBCategoryCount, firstUserId)>

		${sampleSQLBuilder.insertMBThread(mbThread)}

		${mbMessagesCsvWriter.write(mbCategory.categoryId + "," + rootMessageId + ",")}

		<#if (mbMessageCounter.value < totalMBMessageCount)>
			${mbMessagesCsvWriter.write("\n")}
		</#if>
	</#list>
</#list>