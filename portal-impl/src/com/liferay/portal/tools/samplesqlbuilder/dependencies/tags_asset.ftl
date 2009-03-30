<#setting number_format = "0">

insert into TagsAsset (assetId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, mimeType, title) values (${counter.get()}, ${tagsAsset.groupId}, ${companyId}, ${tagsAsset.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${tagsAsset.classNameId}, ${tagsAsset.classPK}, '${tagsAsset.mimeType}', '${tagsAsset.title}');