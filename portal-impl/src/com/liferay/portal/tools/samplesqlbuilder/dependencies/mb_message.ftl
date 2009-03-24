<#setting number_format = "0">

insert into MBMessage (uuid_, messageId, groupId, companyId, userId, createDate, modifiedDate, categoryId, threadId, parentMessageId, subject, body, attachments, anonymous) values ('${portalUUIDUtil.generate()}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${companyId}, ${mbMessage.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', FALSE, FALSE);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBMessage", mbMessage.messageId)}