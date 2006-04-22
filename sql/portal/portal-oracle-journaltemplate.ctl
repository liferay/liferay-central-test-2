load data
infile 'portal-oracle-journaltemplate.csv'
into table JournalTemplate
fields terminated by "," optionally enclosed by '\''
(companyId,
 templateId,
 groupId,
 userId,
 userName,
 createDate date 'YYYYMMDD',
 modifiedDate date 'YYYYMMDD',
 structureId,
 name,
 description,
 xsl char(10000000),
 langType,
 smallImage,
 smallImageURL)