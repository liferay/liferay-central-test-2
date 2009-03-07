<#setting number_format = "0">

insert into MBCategory (uuid_, categoryId, groupId, companyId, userId, createDate, modifiedDate, parentCategoryId, name, description) values ('${portalUUIDUtil.generate()}', ${mbCategory.categoryId}, ${mbCategory.groupId}, ${companyId}, ${mbCategory.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '${mbCategory.name}', '${mbCategory.description}');