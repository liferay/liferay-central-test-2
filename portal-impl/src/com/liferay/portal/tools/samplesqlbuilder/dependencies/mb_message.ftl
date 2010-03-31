<#setting number_format = "0">

insert into MBMessage values ('${portalUUIDUtil.generate()}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${companyId}, ${mbMessage.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbMessage.classNameId}, ${mbMessage.classPK}, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', FALSE, FALSE, 0, FALSE, 0, ${mbMessage.userId}, '', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBMessage", mbMessage.messageId)}