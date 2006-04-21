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
 content, 
 type_, 
 structureId, 
 templateId, 
 displayDate Date 'YYYYMMDD', 
 approved, 
 approvedByUserId, 
 approvedByUserName,
 approvedDate,
 expired,
 expirationDate date 'YYYYMMDD',
 reviewDate date 'YYYYMMDD')