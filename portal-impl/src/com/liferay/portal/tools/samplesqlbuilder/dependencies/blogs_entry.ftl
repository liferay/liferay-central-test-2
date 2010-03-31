<#setting number_format = "0">

insert into BlogsEntry values ('${portalUUIDUtil.generate()}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${companyId}, ${blogsEntry.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.content}', CURRENT_TIMESTAMP, FALSE, FALSE, '', ${blogsEntry.status}, ${blogsEntry.statusByUserId}, '${blogsEntry.statusByUserName}', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.blogs.model.BlogsEntry", blogsEntry.entryId)}

<#assign assetEntry = dataFactory.addAssetEntry(blogsEntry.groupId, blogsEntry.userId, dataFactory.blogsEntryClassName.classNameId, blogsEntry.entryId, "text/html", blogsEntry.title)>

${sampleSQLBuilder.insertAssetEntry(assetEntry)}