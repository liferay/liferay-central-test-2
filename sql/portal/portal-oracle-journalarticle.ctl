load data
infile 'portal-oracle-journalarticle.csv'
into table JournalArticle
fields terminated by "," optionally enclosed by '\''
(companyId,
 articleId,
 version,
 groupId,
 userId,
 userName,
 createDate date 'YYYYMMDD',
 modifiedDate date 'YYYYMMDD',
 title,
 description,
 content char(10000000),
 type_,
 structureId,
 templateId,
 displayDate Date 'YYYYMMDD',
 approved,
 approvedByUserId,
 approvedByUserName,
 expired)