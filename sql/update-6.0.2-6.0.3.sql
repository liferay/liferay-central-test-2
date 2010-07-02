alter table AssetEntry add classUuid VARCHAR(75) null;

alter table DLFileEntry add extension VARCHAR(75) null;

alter table DLFileVersion add extension VARCHAR(75) null;
alter table DLFileVersion add title VARCHAR(255) null;
alter table DLFileVersion add changeLog STRING null;
alter table DLFileVersion add extraSettings TEXT null;

COMMIT_TRANSACTION;

update DLFileVersion set changeLog = description;

alter table Layout add uuid_ VARCHAR(75) null;

alter table MBMessage add rootMessageId LONG;

update MBMessage set rootMessageId = (select rootMessageId from MBThread where MBThread.threadId = MBMessage.threadId);

alter table PasswordPolicy add minSymbols INTEGER;

alter table SocialEquityUser add contributionB DOUBLE;
alter table SocialEquityUser add contributionK DOUBLE;
alter table SocialEquityUser add rank INTEGER;

alter table User_ add facebookId LONG;

alter table WikiPageResource add uuid_ VARCHAR(75) null;