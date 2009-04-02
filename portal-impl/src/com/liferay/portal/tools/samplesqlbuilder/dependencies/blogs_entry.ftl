<#setting number_format = "0">

insert into BlogsEntry values ('${portalUUIDUtil.generate()}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${companyId}, ${blogsEntry.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.content}', CURRENT_TIMESTAMP, FALSE, FALSE, '');

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.blogs.model.BlogsEntry", blogsEntry.entryId)}

<#assign tagsAsset = dataFactory.addTagsAsset(blogsEntry.groupId, blogsEntry.userId, dataFactory.blogsEntryClassName.classNameId, blogsEntry.entryId, "text/html", blogsEntry.title)>

${sampleSQLBuilder.insertTagsAsset(tagsAsset)}