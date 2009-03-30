<#setting number_format = "0">

insert into BlogsStatsUser (statsUserId, companyId, groupId, userId) values (${counter.get()}, ${companyId}, ${blogsStatsUser.groupId}, ${blogsStatsUser.userId});