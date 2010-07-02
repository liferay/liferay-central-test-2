<#setting number_format = "0">

insert into MBMessage values ('${portalUUIDUtil.generate()}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${companyId}, ${mbMessage.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbMessage.classNameId}, ${mbMessage.classPK}, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.rootMessageId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', FALSE, FALSE, 0, FALSE, ${mbMessage.status}, ${mbMessage.statusByUserId}, '${mbMessage.statusByUserName}', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBMessage", mbMessage.messageId)}