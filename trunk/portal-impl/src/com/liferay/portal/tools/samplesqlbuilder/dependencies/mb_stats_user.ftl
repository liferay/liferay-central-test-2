<#setting number_format = "0">

insert into MBStatsUser (statsUserId, groupId, userId) values (${counter.get()}, ${mbStatsUser.groupId}, ${mbStatsUser.userId});