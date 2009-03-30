<#list 1..maxBlogsEntryCount as blogsEntryCount>
	<#assign blogsEntry = dataFactory.addBlogsEntry(dataFactory.guestGroup.groupId, firstUserId, "Test Blog " + blogsEntryCount, "testblog" + blogsEntryCount, "This is a test blog " + blogsEntryCount + ".")>

	${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

	${blogsEntriesCsvWriter.write(blogsEntry.entryId + ",")}

	<#if (blogsEntryCount < maxBlogsEntryCount)>
		${blogsEntriesCsvWriter.write("\n")}
	</#if>
</#list>