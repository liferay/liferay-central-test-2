<#setting number_format = "0">

insert into MBThread (threadId, groupId, categoryId, rootMessageId, messageCount, viewCount, lastPostByUserId, lastPostDate) values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP);