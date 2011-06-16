<#setting number_format = "0">

insert into BlogsStatsUser (statsUserId, groupId, companyId, userId) values (${counter.get()}, ${blogsStatsUser.groupId}, ${companyId}, ${blogsStatsUser.userId});