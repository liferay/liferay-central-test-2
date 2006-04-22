load data
infile 'portal-oracle-journalstructure.csv'
into table JournalStructure
fields terminated by "," optionally enclosed by '\''
(companyId,
 structureId,
 groupId,
 userId,
 userName,
 createDate date 'YYYYMMDD',
 modifiedDate date 'YYYYMMDD',
 name,
 description,
 xsd char(10000000))