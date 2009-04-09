<#if (maxWikiNodeCount > 0)>
	<#list 1..maxWikiNodeCount as wikiNodeCount>
		<#assign wikiNode = dataFactory.addWikiNode(groupId, firstUserId, "Test Node " + wikiNodeCount, "This is a test node " + wikiNodeCount + ".")>

		${sampleSQLBuilder.insertWikiNode(wikiNode)}

		<#if (maxWikiPageCount > 0)>
			<#list 1..maxWikiPageCount as wikiPageCount>
				<#assign wikiPage = dataFactory.addWikiPage(firstUserId, wikiNode.nodeId, "Test Page " + wikiPageCount, 1.0, "This is a test page " + wikiPageCount + ".", true)>

				${sampleSQLBuilder.insertWikiPage(wikiNode, wikiPage)}

				<#assign mbCompanyId = 0>
				<#assign mbGroupId = 0>
				<#assign mbUserId = wikiPage.userId>
				<#assign mbCategoryId = 0>
				<#assign mbThreadId = counter.get()>

				<#assign mbRootMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, mbCategoryId, mbThreadId, 0, stringUtil.valueOf(wikiPage.resourcePrimKey), stringUtil.valueOf(wikiPage.resourcePrimKey))>

				${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

				<#assign mbThread = dataFactory.addMBThread(mbThreadId, mbGroupId, mbCategoryId, mbRootMessage.messageId, maxWikiPageCommentCount, mbUserId)>

				${sampleSQLBuilder.insertMBThread(mbThread)}

				<#if (maxWikiPageCommentCount > 0)>
					<#list 1..maxWikiPageCommentCount as wikiPageComment>
						<#assign mbMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, mbCategoryId, mbThreadId, mbRootMessage.messageId, "N/A", "This is a test comment " + wikiPageComment + ".")>

						${sampleSQLBuilder.insertMBMessage(mbMessage)}
					</#list>
				</#if>

				<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.wikiPageClassName.classNameId, wikiPage.resourcePrimKey, mbThreadId)>

				${sampleSQLBuilder.insertMBDiscussion(mbDiscussion)}
			</#list>
		</#if>
	</#list>
</#if>