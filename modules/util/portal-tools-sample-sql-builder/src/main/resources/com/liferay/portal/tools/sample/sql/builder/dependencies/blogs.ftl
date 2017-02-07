<#assign blogsEntryModels = dataFactory.newBlogsEntryModels(groupId) />

<#list blogsEntryModels as blogsEntryModel>
	${dataFactory.toInsertSQL(blogsEntryModel)}

	<@insertAssetEntry
		_entry = blogsEntryModel
		_categoryAndTag = true
	/>

	<#assign
		mbThreadId = dataFactory.getCounterNext()
		mbRootMessageId = dataFactory.getCounterNext()
	/>

	<@insertMBDiscussion
		_classNameId = dataFactory.blogsEntryClassNameId
		_classPK = blogsEntryModel.entryId
		_groupId = groupId
		_maxCommentCount = dataFactory.maxBlogsEntryCommentCount
		_mbRootMessageId = mbRootMessageId
		_mbThreadId = mbThreadId
	/>

	${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(blogsEntryModel))}

	${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(blogsEntryModel))}

	${dataFactory.getCSVWriter("blog").write(blogsEntryModel.entryId + "," + blogsEntryModel.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
</#list>