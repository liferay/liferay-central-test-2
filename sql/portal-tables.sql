create table Account_ (
	accountId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountId LONG,
	name VARCHAR(75) null,
	legalName VARCHAR(75) null,
	legalId VARCHAR(75) null,
	legalType VARCHAR(75) null,
	sicCode VARCHAR(75) null,
	tickerSymbol VARCHAR(75) null,
	industry VARCHAR(75) null,
	type_ VARCHAR(75) null,
	size_ VARCHAR(75) null
);

create table Address (
	addressId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	regionId LONG,
	countryId LONG,
	typeId INTEGER,
	mailing BOOLEAN,
	primary_ BOOLEAN
);

create table AnnouncementsDelivery (
	deliveryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	email BOOLEAN,
	sms BOOLEAN,
	website BOOLEAN
);

create table AnnouncementsEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	title VARCHAR(75) null,
	content STRING null,
	url STRING null,
	type_ VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	priority INTEGER,
	alert BOOLEAN
);

create table AnnouncementsFlag (
	flagId LONG not null primary key,
	userId LONG,
	createDate DATE null,
	entryId LONG,
	value INTEGER
);

create table BlogsCategory (
	categoryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table BlogsEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(150) null,
	urlTitle VARCHAR(150) null,
	content TEXT null,
	displayDate DATE null,
	draft BOOLEAN,
	allowTrackbacks BOOLEAN,
	trackbacks TEXT null
);

create table BlogsStatsUser (
	statsUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	entryCount INTEGER,
	lastPostDate DATE null,
	ratingsTotalEntries INTEGER,
	ratingsTotalScore DOUBLE,
	ratingsAverageScore DOUBLE
);

create table BookmarksEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	name VARCHAR(300) null,
	url STRING null,
	comments STRING null,
	visits INTEGER,
	priority INTEGER
);

create table BookmarksFolder (
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table CalEvent (
	uuid_ VARCHAR(75) null,
	eventId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null,
	durationHour INTEGER,
	durationMinute INTEGER,
	allDay BOOLEAN,
	timeZoneSensitive BOOLEAN,
	type_ VARCHAR(75) null,
	repeating BOOLEAN,
	recurrence TEXT null,
	remindBy VARCHAR(75) null,
	firstReminder INTEGER,
	secondReminder INTEGER
);

create table ClassName_ (
	classNameId LONG not null primary key,
	value VARCHAR(200) null
);

create table Company (
	companyId LONG not null primary key,
	accountId LONG,
	webId VARCHAR(75) null,
	key_ TEXT null,
	virtualHost VARCHAR(75) null,
	mx VARCHAR(75) null,
	logoId LONG
);

create table Contact_ (
	contactId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	accountId LONG,
	parentContactId LONG,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	prefixId INTEGER,
	suffixId INTEGER,
	male BOOLEAN,
	birthday DATE null,
	smsSn VARCHAR(75) null,
	aimSn VARCHAR(75) null,
	facebookSn VARCHAR(75) null,
	icqSn VARCHAR(75) null,
	jabberSn VARCHAR(75) null,
	msnSn VARCHAR(75) null,
	mySpaceSn VARCHAR(75) null,
	skypeSn VARCHAR(75) null,
	twitterSn VARCHAR(75) null,
	ymSn VARCHAR(75) null,
	employeeStatusId VARCHAR(75) null,
	employeeNumber VARCHAR(75) null,
	jobTitle VARCHAR(100) null,
	jobClass VARCHAR(75) null,
	hoursOfOperation VARCHAR(75) null
);

create table Counter (
	name VARCHAR(75) not null primary key,
	currentId LONG
);

create table Country (
	countryId LONG not null primary key,
	name VARCHAR(75) null,
	a2 VARCHAR(75) null,
	a3 VARCHAR(75) null,
	number_ VARCHAR(75) null,
	idd_ VARCHAR(75) null,
	active_ BOOLEAN
);

create table CyrusUser (
	userId VARCHAR(75) not null primary key,
	password_ VARCHAR(75) not null
);

create table CyrusVirtual (
	emailAddress VARCHAR(75) not null primary key,
	userId VARCHAR(75) not null
);

create table DLFileEntry (
	uuid_ VARCHAR(75) null,
	fileEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	name VARCHAR(300) null,
	title VARCHAR(300) null,
	description STRING null,
	version DOUBLE,
	size_ INTEGER,
	readCount INTEGER,
	extraSettings TEXT null
);

create table DLFileRank (
	fileRankId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	folderId LONG,
	name VARCHAR(300) null
);

create table DLFileShortcut (
	uuid_ VARCHAR(75) null,
	fileShortcutId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	toFolderId LONG,
	toName VARCHAR(300) null
);

create table DLFileVersion (
	fileVersionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	folderId LONG,
	name VARCHAR(300) null,
	version DOUBLE,
	size_ INTEGER
);

create table DLFolder (
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	name VARCHAR(100) null,
	description STRING null,
	lastPostDate DATE null
);

create table EmailAddress (
	emailAddressId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	address VARCHAR(75) null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table ExpandoColumn (
	columnId LONG not null primary key,
	tableId LONG,
	name VARCHAR(75) null,
	type_ INTEGER
);

create table ExpandoRow (
	rowId_ LONG not null primary key,
	tableId LONG,
	classPK LONG
);

create table ExpandoTable (
	tableId LONG not null primary key,
	classNameId LONG,
	name VARCHAR(75) null
);

create table ExpandoValue (
	valueId LONG not null primary key,
	tableId LONG,
	columnId LONG,
	rowId_ LONG,
	classNameId LONG,
	classPK LONG,
	data_ STRING null
);

create table Group_ (
	groupId LONG not null primary key,
	companyId LONG,
	creatorUserId LONG,
	classNameId LONG,
	classPK LONG,
	parentGroupId LONG,
	liveGroupId LONG,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER,
	typeSettings STRING null,
	friendlyURL VARCHAR(100) null,
	active_ BOOLEAN
);

create table Groups_Orgs (
	groupId LONG not null,
	organizationId LONG not null,
	primary key (groupId, organizationId)
);

create table Groups_Permissions (
	groupId LONG not null,
	permissionId LONG not null,
	primary key (groupId, permissionId)
);

create table Groups_Roles (
	groupId LONG not null,
	roleId LONG not null,
	primary key (groupId, roleId)
);

create table Groups_UserGroups (
	groupId LONG not null,
	userGroupId LONG not null,
	primary key (groupId, userGroupId)
);

create table IGFolder (
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table IGImage (
	uuid_ VARCHAR(75) null,
	imageId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	name VARCHAR(75) null,
	description STRING null,
	smallImageId LONG,
	largeImageId LONG,
	custom1ImageId LONG,
	custom2ImageId LONG
);

create table Image (
	imageId LONG not null primary key,
	modifiedDate DATE null,
	text_ TEXT null,
	type_ VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	size_ INTEGER
);

create table JBPM_ACTION (ID_ bigint not null auto_increment, class char(1) not null, NAME_ varchar(255), ISPROPAGATIONALLOWED_ bit, ACTIONEXPRESSION_ varchar(255), ISASYNC_ bit, REFERENCEDACTION_ bigint, ACTIONDELEGATION_ bigint, EVENT_ bigint, PROCESSDEFINITION_ bigint, TIMERNAME_ varchar(255), DUEDATE_ varchar(255), REPEAT_ varchar(255), TRANSITIONNAME_ varchar(255), TIMERACTION_ bigint, EXPRESSION_ text, EVENTINDEX_ integer, EXCEPTIONHANDLER_ bigint, EXCEPTIONHANDLERINDEX_ integer, primary key (ID_)) type=InnoDB;

create table JBPM_BYTEARRAY (ID_ bigint not null auto_increment, NAME_ varchar(255), FILEDEFINITION_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_BYTEBLOCK (PROCESSFILE_ bigint not null, BYTES_ blob, INDEX_ integer not null, primary key (PROCESSFILE_, INDEX_)) type=InnoDB;

create table JBPM_COMMENT (ID_ bigint not null auto_increment, VERSION_ integer not null, ACTORID_ varchar(255), TIME_ datetime, MESSAGE_ text, TOKEN_ bigint, TASKINSTANCE_ bigint, TOKENINDEX_ integer, TASKINSTANCEINDEX_ integer, primary key (ID_)) type=InnoDB;
create table JBPM_DECISIONCONDITIONS (DECISION_ bigint not null, TRANSITIONNAME_ varchar(255), EXPRESSION_ varchar(255), INDEX_ integer not null, primary key (DECISION_, INDEX_)) type=InnoDB;

create table JBPM_DELEGATION (ID_ bigint not null auto_increment, CLASSNAME_ text, CONFIGURATION_ text, CONFIGTYPE_ varchar(255), PROCESSDEFINITION_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_EVENT (ID_ bigint not null auto_increment, EVENTTYPE_ varchar(255), TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSDEFINITION_ bigint, NODE_ bigint, TRANSITION_ bigint, TASK_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_EXCEPTIONHANDLER (ID_ bigint not null auto_increment, EXCEPTIONCLASSNAME_ text, TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSDEFINITION_ bigint, GRAPHELEMENTINDEX_ integer, NODE_ bigint, TRANSITION_ bigint, TASK_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_ID_GROUP (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), TYPE_ varchar(255), PARENT_ bigint, primary key (ID_)) type=InnoDB;
create table JBPM_ID_MEMBERSHIP (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), ROLE_ varchar(255), USER_ bigint, GROUP_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_ID_PERMISSIONS (ENTITY_ bigint not null, CLASS_ varchar(255), NAME_ varchar(255), ACTION_ varchar(255)) type=InnoDB;

create table JBPM_ID_USER (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), EMAIL_ varchar(255), PASSWORD_ varchar(255), primary key (ID_)) type=InnoDB;

create table JBPM_LOG (ID_ bigint not null auto_increment, CLASS_ char(1) not null, INDEX_ integer, DATE_ datetime, TOKEN_ bigint, PARENT_ bigint, MESSAGE_ text, EXCEPTION_ text, ACTION_ bigint, NODE_ bigint, ENTER_ datetime, LEAVE_ datetime, DURATION_ bigint, NEWLONGVALUE_ bigint, TRANSITION_ bigint, CHILD_ bigint, SOURCENODE_ bigint, DESTINATIONNODE_ bigint, VARIABLEINSTANCE_ bigint, OLDBYTEARRAY_ bigint, NEWBYTEARRAY_ bigint, OLDDATEVALUE_ datetime, NEWDATEVALUE_ datetime, OLDDOUBLEVALUE_ double precision, NEWDOUBLEVALUE_ double precision, OLDLONGIDCLASS_ varchar(255), OLDLONGIDVALUE_ bigint, NEWLONGIDCLASS_ varchar(255), NEWLONGIDVALUE_ bigint, OLDSTRINGIDCLASS_ varchar(255), OLDSTRINGIDVALUE_ varchar(255), NEWSTRINGIDCLASS_ varchar(255), NEWSTRINGIDVALUE_ varchar(255), OLDLONGVALUE_ bigint, OLDSTRINGVALUE_ text, NEWSTRINGVALUE_ text, TASKINSTANCE_ bigint, TASKACTORID_ varchar(255), TASKOLDACTORID_ varchar(255), SWIMLANEINSTANCE_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_MESSAGE (ID_ bigint not null auto_increment, CLASS_ char(1) not null, DESTINATION_ varchar(255), EXCEPTION_ text, ISSUSPENDED_ bit, TOKEN_ bigint, TEXT_ varchar(255), ACTION_ bigint, NODE_ bigint, TRANSITIONNAME_ varchar(255), TASKINSTANCE_ bigint, primary key (ID_)) type=InnoDB;
create table JBPM_MODULEDEFINITION (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ text, PROCESSDEFINITION_ bigint, STARTTASK_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_MODULEINSTANCE (ID_ bigint not null auto_increment, CLASS_ char(1) not null, PROCESSINSTANCE_ bigint, TASKMGMTDEFINITION_ bigint, NAME_ varchar(255), primary key (ID_)) type=InnoDB;

create table JBPM_NODE (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), PROCESSDEFINITION_ bigint, ISASYNC_ bit, ACTION_ bigint, SUPERSTATE_ bigint, SUBPROCESSDEFINITION_ bigint, DECISIONEXPRESSION_ varchar(255), DECISIONDELEGATION bigint, SIGNAL_ integer, CREATETASKS_ bit, ENDTASKS_ bit, NODECOLLECTIONINDEX_ integer, primary key (ID_)) type=InnoDB;

create table JBPM_POOLEDACTOR (ID_ bigint not null auto_increment, ACTORID_ varchar(255), SWIMLANEINSTANCE_ bigint, primary key (ID_)) type=InnoDB;
create table JBPM_PROCESSDEFINITION (ID_ bigint not null auto_increment, NAME_ varchar(255), VERSION_ integer, ISTERMINATIONIMPLICIT_ bit, STARTSTATE_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_PROCESSINSTANCE (ID_ bigint not null auto_increment, VERSION_ integer not null, START_ datetime, END_ datetime, ISSUSPENDED_ bit, PROCESSDEFINITION_ bigint, ROOTTOKEN_ bigint, SUPERPROCESSTOKEN_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_RUNTIMEACTION (ID_ bigint not null auto_increment, VERSION_ integer not null, EVENTTYPE_ varchar(255), TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSINSTANCE_ bigint, ACTION_ bigint, PROCESSINSTANCEINDEX_ integer, primary key (ID_)) type=InnoDB;

create table JBPM_SWIMLANE (ID_ bigint not null auto_increment, NAME_ varchar(255), ACTORIDEXPRESSION_ varchar(255), POOLEDACTORSEXPRESSION_ varchar(255), ASSIGNMENTDELEGATION_ bigint, TASKMGMTDEFINITION_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_SWIMLANEINSTANCE (ID_ bigint not null auto_increment, NAME_ varchar(255), ACTORID_ varchar(255), SWIMLANE_ bigint, TASKMGMTINSTANCE_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TASK (ID_ bigint not null auto_increment, NAME_ varchar(255), PROCESSDEFINITION_ bigint, DESCRIPTION_ text, ISBLOCKING_ bit, ISSIGNALLING_ bit, DUEDATE_ varchar(255), ACTORIDEXPRESSION_ varchar(255), POOLEDACTORSEXPRESSION_ varchar(255), TASKMGMTDEFINITION_ bigint, TASKNODE_ bigint, STARTSTATE_ bigint, ASSIGNMENTDELEGATION_ bigint, SWIMLANE_ bigint, TASKCONTROLLER_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TASKACTORPOOL (TASKINSTANCE_ bigint not null, POOLEDACTOR_ bigint not null, primary key (TASKINSTANCE_, POOLEDACTOR_)) type=InnoDB;
create table JBPM_TASKCONTROLLER (ID_ bigint not null auto_increment, TASKCONTROLLERDELEGATION_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TASKINSTANCE (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), DESCRIPTION_ text, ACTORID_ varchar(255), CREATE_ datetime, START_ datetime, END_ datetime, DUEDATE_ datetime, PRIORITY_ integer, ISCANCELLED_ bit, ISSUSPENDED_ bit, ISOPEN_ bit, ISSIGNALLING_ bit, ISBLOCKING_ bit, TASK_ bigint, TOKEN_ bigint, SWIMLANINSTANCE_ bigint, TASKMGMTINSTANCE_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TIMER (ID_ bigint not null auto_increment, NAME_ varchar(255), DUEDATE_ datetime, REPEAT_ varchar(255), TRANSITIONNAME_ varchar(255), EXCEPTION_ text, ISSUSPENDED_ bit, ACTION_ bigint, TOKEN_ bigint, PROCESSINSTANCE_ bigint, TASKINSTANCE_ bigint, GRAPHELEMENTTYPE_ varchar(255), GRAPHELEMENT_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TOKEN (ID_ bigint not null auto_increment, VERSION_ integer not null, NAME_ varchar(255), START_ datetime, END_ datetime, NODEENTER_ datetime, NEXTLOGINDEX_ integer, ISABLETOREACTIVATEPARENT_ bit, ISTERMINATIONIMPLICIT_ bit, ISSUSPENDED_ bit, NODE_ bigint, PROCESSINSTANCE_ bigint, PARENT_ bigint, SUBPROCESSINSTANCE_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_TOKENVARIABLEMAP (ID_ bigint not null auto_increment, TOKEN_ bigint, CONTEXTINSTANCE_ bigint, primary key (ID_)) type=InnoDB;
create table JBPM_TRANSITION (ID_ bigint not null auto_increment, NAME_ varchar(255), PROCESSDEFINITION_ bigint, FROM_ bigint, TO_ bigint, FROMINDEX_ integer, primary key (ID_)) type=InnoDB;

create table JBPM_VARIABLEACCESS (ID_ bigint not null auto_increment, VARIABLENAME_ varchar(255), ACCESS_ varchar(255), MAPPEDNAME_ varchar(255), PROCESSSTATE_ bigint, TASKCONTROLLER_ bigint, INDEX_ integer, SCRIPT_ bigint, primary key (ID_)) type=InnoDB;

create table JBPM_VARIABLEINSTANCE (ID_ bigint not null auto_increment, CLASS_ char(1) not null, NAME_ varchar(255), CONVERTER_ char(1), TOKEN_ bigint, TOKENVARIABLEMAP_ bigint, PROCESSINSTANCE_ bigint, BYTEARRAYVALUE_ bigint, DATEVALUE_ datetime, DOUBLEVALUE_ double precision, LONGIDCLASS_ varchar(255), LONGVALUE_ bigint, STRINGIDCLASS_ varchar(255), STRINGVALUE_ varchar(255), TASKINSTANCE_ bigint, primary key (ID_)) type=InnoDB;

alter table JBPM_ACTION add index FK_ACTION_EVENT (EVENT_), add constraint FK_ACTION_EVENT foreign key (EVENT_) references JBPM_EVENT (ID_);
alter table JBPM_ACTION add index FK_ACTION_EXPTHDL (EXCEPTIONHANDLER_), add constraint FK_ACTION_EXPTHDL foreign key (EXCEPTIONHANDLER_) references JBPM_EXCEPTIONHANDLER (ID_);

alter table JBPM_ACTION add index FK_ACTION_PROCDEF (PROCESSDEFINITION_), add constraint FK_ACTION_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_ACTION add index FK_CRTETIMERACT_TA (TIMERACTION_), add constraint FK_CRTETIMERACT_TA foreign key (TIMERACTION_) references JBPM_ACTION (ID_);

alter table JBPM_ACTION add index FK_ACTION_ACTNDEL (ACTIONDELEGATION_), add constraint FK_ACTION_ACTNDEL foreign key (ACTIONDELEGATION_) references JBPM_DELEGATION (ID_);

alter table JBPM_ACTION add index FK_ACTION_REFACT (REFERENCEDACTION_), add constraint FK_ACTION_REFACT foreign key (REFERENCEDACTION_) references JBPM_ACTION (ID_);

alter table JBPM_BYTEARRAY add index FK_BYTEARR_FILDEF (FILEDEFINITION_), add constraint FK_BYTEARR_FILDEF foreign key (FILEDEFINITION_) references JBPM_MODULEDEFINITION (ID_);

alter table JBPM_BYTEBLOCK add index FK_BYTEBLOCK_FILE (PROCESSFILE_), add constraint FK_BYTEBLOCK_FILE foreign key (PROCESSFILE_) references JBPM_BYTEARRAY (ID_);

alter table JBPM_COMMENT add index FK_COMMENT_TOKEN (TOKEN_), add constraint FK_COMMENT_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_COMMENT add index FK_COMMENT_TSK (TASKINSTANCE_), add constraint FK_COMMENT_TSK foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);

alter table JBPM_DECISIONCONDITIONS add index FK_DECCOND_DEC (DECISION_), add constraint FK_DECCOND_DEC foreign key (DECISION_) references JBPM_NODE (ID_);

alter table JBPM_DELEGATION add index FK_DELEGATION_PRCD (PROCESSDEFINITION_), add constraint FK_DELEGATION_PRCD foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_EVENT add index FK_EVENT_PROCDEF (PROCESSDEFINITION_), add constraint FK_EVENT_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_EVENT add index FK_EVENT_NODE (NODE_), add constraint FK_EVENT_NODE foreign key (NODE_) references JBPM_NODE (ID_);

alter table JBPM_EVENT add index FK_EVENT_TRANS (TRANSITION_), add constraint FK_EVENT_TRANS foreign key (TRANSITION_) references JBPM_TRANSITION (ID_);

alter table JBPM_EVENT add index FK_EVENT_TASK (TASK_), add constraint FK_EVENT_TASK foreign key (TASK_) references JBPM_TASK (ID_);

alter table JBPM_ID_GROUP add index FK_ID_GRP_PARENT (PARENT_), add constraint FK_ID_GRP_PARENT foreign key (PARENT_) references JBPM_ID_GROUP (ID_);

alter table JBPM_ID_MEMBERSHIP add index FK_ID_MEMSHIP_GRP (GROUP_), add constraint FK_ID_MEMSHIP_GRP foreign key (GROUP_) references JBPM_ID_GROUP (ID_);

alter table JBPM_ID_MEMBERSHIP add index FK_ID_MEMSHIP_USR (USER_), add constraint FK_ID_MEMSHIP_USR foreign key (USER_) references JBPM_ID_USER (ID_);

alter table JBPM_LOG add index FK_LOG_SOURCENODE (SOURCENODE_), add constraint FK_LOG_SOURCENODE foreign key (SOURCENODE_) references JBPM_NODE (ID_);

alter table JBPM_LOG add index FK_LOG_TOKEN (TOKEN_), add constraint FK_LOG_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_LOG add index FK_LOG_OLDBYTES (OLDBYTEARRAY_), add constraint FK_LOG_OLDBYTES foreign key (OLDBYTEARRAY_) references JBPM_BYTEARRAY (ID_);

alter table JBPM_LOG add index FK_LOG_NEWBYTES (NEWBYTEARRAY_), add constraint FK_LOG_NEWBYTES foreign key (NEWBYTEARRAY_) references JBPM_BYTEARRAY (ID_);

alter table JBPM_LOG add index FK_LOG_CHILDTOKEN (CHILD_), add constraint FK_LOG_CHILDTOKEN foreign key (CHILD_) references JBPM_TOKEN (ID_);

alter table JBPM_LOG add index FK_LOG_DESTNODE (DESTINATIONNODE_), add constraint FK_LOG_DESTNODE foreign key (DESTINATIONNODE_) references JBPM_NODE (ID_);

alter table JBPM_LOG add index FK_LOG_TASKINST (TASKINSTANCE_), add constraint FK_LOG_TASKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);

alter table JBPM_LOG add index FK_LOG_SWIMINST (SWIMLANEINSTANCE_), add constraint FK_LOG_SWIMINST foreign key (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE (ID_);

alter table JBPM_LOG add index FK_LOG_PARENT (PARENT_), add constraint FK_LOG_PARENT foreign key (PARENT_) references JBPM_LOG (ID_);

alter table JBPM_LOG add index FK_LOG_NODE (NODE_), add constraint FK_LOG_NODE foreign key (NODE_) references JBPM_NODE (ID_);

alter table JBPM_LOG add index FK_LOG_ACTION (ACTION_), add constraint FK_LOG_ACTION foreign key (ACTION_) references JBPM_ACTION (ID_);

alter table JBPM_LOG add index FK_LOG_VARINST (VARIABLEINSTANCE_), add constraint FK_LOG_VARINST foreign key (VARIABLEINSTANCE_) references JBPM_VARIABLEINSTANCE (ID_);

alter table JBPM_LOG add index FK_LOG_TRANSITION (TRANSITION_), add constraint FK_LOG_TRANSITION foreign key (TRANSITION_) references JBPM_TRANSITION (ID_);

alter table JBPM_MESSAGE add index FK_MSG_TOKEN (TOKEN_), add constraint FK_MSG_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_MESSAGE add index FK_CMD_NODE (NODE_), add constraint FK_CMD_NODE foreign key (NODE_) references JBPM_NODE (ID_);

alter table JBPM_MESSAGE add index FK_CMD_ACTION (ACTION_), add constraint FK_CMD_ACTION foreign key (ACTION_) references JBPM_ACTION (ID_);

alter table JBPM_MESSAGE add index FK_CMD_TASKINST (TASKINSTANCE_), add constraint FK_CMD_TASKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);

alter table JBPM_MODULEDEFINITION add index FK_TSKDEF_START (STARTTASK_), add constraint FK_TSKDEF_START foreign key (STARTTASK_) references JBPM_TASK (ID_);

alter table JBPM_MODULEDEFINITION add index FK_MODDEF_PROCDEF (PROCESSDEFINITION_), add constraint FK_MODDEF_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_MODULEINSTANCE add index FK_TASKMGTINST_TMD (TASKMGMTDEFINITION_), add constraint FK_TASKMGTINST_TMD foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION (ID_);

alter table JBPM_MODULEINSTANCE add index FK_MODINST_PRCINST (PROCESSINSTANCE_), add constraint FK_MODINST_PRCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_NODE add index FK_PROCST_SBPRCDEF (SUBPROCESSDEFINITION_), add constraint FK_PROCST_SBPRCDEF foreign key (SUBPROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_NODE add index FK_NODE_PROCDEF (PROCESSDEFINITION_), add constraint FK_NODE_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_NODE add index FK_NODE_ACTION (ACTION_), add constraint FK_NODE_ACTION foreign key (ACTION_) references JBPM_ACTION (ID_);

alter table JBPM_NODE add index FK_DECISION_DELEG (DECISIONDELEGATION), add constraint FK_DECISION_DELEG foreign key (DECISIONDELEGATION) references JBPM_DELEGATION (ID_);

alter table JBPM_NODE add index FK_NODE_SUPERSTATE (SUPERSTATE_), add constraint FK_NODE_SUPERSTATE foreign key (SUPERSTATE_) references JBPM_NODE (ID_);
create index IDX_PLDACTR_ACTID on JBPM_POOLEDACTOR (ACTORID_);

alter table JBPM_POOLEDACTOR add index FK_POOLEDACTOR_SLI (SWIMLANEINSTANCE_), add constraint FK_POOLEDACTOR_SLI foreign key (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE (ID_);

alter table JBPM_PROCESSDEFINITION add index FK_PROCDEF_STRTSTA (STARTSTATE_), add constraint FK_PROCDEF_STRTSTA foreign key (STARTSTATE_) references JBPM_NODE (ID_);

alter table JBPM_PROCESSINSTANCE add index FK_PROCIN_PROCDEF (PROCESSDEFINITION_), add constraint FK_PROCIN_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_PROCESSINSTANCE add index FK_PROCIN_ROOTTKN (ROOTTOKEN_), add constraint FK_PROCIN_ROOTTKN foreign key (ROOTTOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_PROCESSINSTANCE add index FK_PROCIN_SPROCTKN (SUPERPROCESSTOKEN_), add constraint FK_PROCIN_SPROCTKN foreign key (SUPERPROCESSTOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_RUNTIMEACTION add index FK_RTACTN_PROCINST (PROCESSINSTANCE_), add constraint FK_RTACTN_PROCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_RUNTIMEACTION add index FK_RTACTN_ACTION (ACTION_), add constraint FK_RTACTN_ACTION foreign key (ACTION_) references JBPM_ACTION (ID_);

alter table JBPM_SWIMLANE add index FK_SWL_ASSDEL (ASSIGNMENTDELEGATION_), add constraint FK_SWL_ASSDEL foreign key (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION (ID_);

alter table JBPM_SWIMLANE add index FK_SWL_TSKMGMTDEF (TASKMGMTDEFINITION_), add constraint FK_SWL_TSKMGMTDEF foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION (ID_);

alter table JBPM_SWIMLANEINSTANCE add index FK_SWIMLANEINST_TM (TASKMGMTINSTANCE_), add constraint FK_SWIMLANEINST_TM foreign key (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE (ID_);

alter table JBPM_SWIMLANEINSTANCE add index FK_SWIMLANEINST_SL (SWIMLANE_), add constraint FK_SWIMLANEINST_SL foreign key (SWIMLANE_) references JBPM_SWIMLANE (ID_);

alter table JBPM_TASK add index FK_TSK_TSKCTRL (TASKCONTROLLER_), add constraint FK_TSK_TSKCTRL foreign key (TASKCONTROLLER_) references JBPM_TASKCONTROLLER (ID_);

alter table JBPM_TASK add index FK_TASK_ASSDEL (ASSIGNMENTDELEGATION_), add constraint FK_TASK_ASSDEL foreign key (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION (ID_);

alter table JBPM_TASK add index FK_TASK_TASKNODE (TASKNODE_), add constraint FK_TASK_TASKNODE foreign key (TASKNODE_) references JBPM_NODE (ID_);

alter table JBPM_TASK add index FK_TASK_PROCDEF (PROCESSDEFINITION_), add constraint FK_TASK_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_TASK add index FK_TASK_STARTST (STARTSTATE_), add constraint FK_TASK_STARTST foreign key (STARTSTATE_) references JBPM_NODE (ID_);

alter table JBPM_TASK add index FK_TASK_TASKMGTDEF (TASKMGMTDEFINITION_), add constraint FK_TASK_TASKMGTDEF foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION (ID_);

alter table JBPM_TASK add index FK_TASK_SWIMLANE (SWIMLANE_), add constraint FK_TASK_SWIMLANE foreign key (SWIMLANE_) references JBPM_SWIMLANE (ID_);

alter table JBPM_TASKACTORPOOL add index FK_TSKACTPOL_PLACT (POOLEDACTOR_), add constraint FK_TSKACTPOL_PLACT foreign key (POOLEDACTOR_) references JBPM_POOLEDACTOR (ID_);

alter table JBPM_TASKACTORPOOL add index FK_TASKACTPL_TSKI (TASKINSTANCE_), add constraint FK_TASKACTPL_TSKI foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);
alter table JBPM_TASKCONTROLLER add index FK_TSKCTRL_DELEG (TASKCONTROLLERDELEGATION_), add constraint FK_TSKCTRL_DELEG foreign key (TASKCONTROLLERDELEGATION_) references JBPM_DELEGATION (ID_);

create index IDX_TASK_ACTORID on JBPM_TASKINSTANCE (ACTORID_);

alter table JBPM_TASKINSTANCE add index FK_TASKINST_TMINST (TASKMGMTINSTANCE_), add constraint FK_TASKINST_TMINST foreign key (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE (ID_);

alter table JBPM_TASKINSTANCE add index FK_TASKINST_TOKEN (TOKEN_), add constraint FK_TASKINST_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_TASKINSTANCE add index FK_TASKINST_SLINST (SWIMLANINSTANCE_), add constraint FK_TASKINST_SLINST foreign key (SWIMLANINSTANCE_) references JBPM_SWIMLANEINSTANCE (ID_);

alter table JBPM_TASKINSTANCE add index FK_TASKINST_TASK (TASK_), add constraint FK_TASKINST_TASK foreign key (TASK_) references JBPM_TASK (ID_);

alter table JBPM_TIMER add index FK_TIMER_TOKEN (TOKEN_), add constraint FK_TIMER_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_TIMER add index FK_TIMER_PRINST (PROCESSINSTANCE_), add constraint FK_TIMER_PRINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_TIMER add index FK_TIMER_ACTION (ACTION_), add constraint FK_TIMER_ACTION foreign key (ACTION_) references JBPM_ACTION (ID_);

alter table JBPM_TIMER add index FK_TIMER_TSKINST (TASKINSTANCE_), add constraint FK_TIMER_TSKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);

alter table JBPM_TOKEN add index FK_TOKEN_PARENT (PARENT_), add constraint FK_TOKEN_PARENT foreign key (PARENT_) references JBPM_TOKEN (ID_);

alter table JBPM_TOKEN add index FK_TOKEN_NODE (NODE_), add constraint FK_TOKEN_NODE foreign key (NODE_) references JBPM_NODE (ID_);

alter table JBPM_TOKEN add index FK_TOKEN_PROCINST (PROCESSINSTANCE_), add constraint FK_TOKEN_PROCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_TOKEN add index FK_TOKEN_SUBPI (SUBPROCESSINSTANCE_), add constraint FK_TOKEN_SUBPI foreign key (SUBPROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_TOKENVARIABLEMAP add index FK_TKVARMAP_CTXT (CONTEXTINSTANCE_), add constraint FK_TKVARMAP_CTXT foreign key (CONTEXTINSTANCE_) references JBPM_MODULEINSTANCE (ID_);

alter table JBPM_TOKENVARIABLEMAP add index FK_TKVARMAP_TOKEN (TOKEN_), add constraint FK_TKVARMAP_TOKEN foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_TRANSITION add index FK_TRANSITION_TO (TO_), add constraint FK_TRANSITION_TO foreign key (TO_) references JBPM_NODE (ID_);

alter table JBPM_TRANSITION add index FK_TRANS_PROCDEF (PROCESSDEFINITION_), add constraint FK_TRANS_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION (ID_);

alter table JBPM_TRANSITION add index FK_TRANSITION_FROM (FROM_), add constraint FK_TRANSITION_FROM foreign key (FROM_) references JBPM_NODE (ID_);

alter table JBPM_VARIABLEACCESS add index FK_VARACC_TSKCTRL (TASKCONTROLLER_), add constraint FK_VARACC_TSKCTRL foreign key (TASKCONTROLLER_) references JBPM_TASKCONTROLLER (ID_);

alter table JBPM_VARIABLEACCESS add index FK_VARACC_SCRIPT (SCRIPT_), add constraint FK_VARACC_SCRIPT foreign key (SCRIPT_) references JBPM_ACTION (ID_);

alter table JBPM_VARIABLEACCESS add index FK_VARACC_PROCST (PROCESSSTATE_), add constraint FK_VARACC_PROCST foreign key (PROCESSSTATE_) references JBPM_NODE (ID_);

alter table JBPM_VARIABLEINSTANCE add index FK_VARINST_TK (TOKEN_), add constraint FK_VARINST_TK foreign key (TOKEN_) references JBPM_TOKEN (ID_);

alter table JBPM_VARIABLEINSTANCE add index FK_VARINST_TKVARMP (TOKENVARIABLEMAP_), add constraint FK_VARINST_TKVARMP foreign key (TOKENVARIABLEMAP_) references JBPM_TOKENVARIABLEMAP (ID_);

alter table JBPM_VARIABLEINSTANCE add index FK_VARINST_PRCINST (PROCESSINSTANCE_), add constraint FK_VARINST_PRCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE (ID_);

alter table JBPM_VARIABLEINSTANCE add index FK_VAR_TSKINST (TASKINSTANCE_), add constraint FK_VAR_TSKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE (ID_);

alter table JBPM_VARIABLEINSTANCE add index FK_BYTEINST_ARRAY (BYTEARRAYVALUE_), add constraint FK_BYTEINST_ARRAY foreign key (BYTEARRAYVALUE_) references JBPM_BYTEARRAY (ID_);


create table JournalArticle (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	resourcePrimKey LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	articleId VARCHAR(75) null,
	version DOUBLE,
	title VARCHAR(100) null,
	description STRING null,
	content TEXT null,
	type_ VARCHAR(75) null,
	structureId VARCHAR(75) null,
	templateId VARCHAR(75) null,
	displayDate DATE null,
	approved BOOLEAN,
	approvedByUserId LONG,
	approvedByUserName VARCHAR(75) null,
	approvedDate DATE null,
	expired BOOLEAN,
	expirationDate DATE null,
	reviewDate DATE null,
	indexable BOOLEAN,
	smallImage BOOLEAN,
	smallImageId LONG,
	smallImageURL VARCHAR(75) null
);

create table JournalArticleImage (
	articleImageId LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null,
	version DOUBLE,
	elName VARCHAR(75) null,
	languageId VARCHAR(75) null,
	tempImage BOOLEAN
);

create table JournalArticleResource (
	resourcePrimKey LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null
);

create table JournalContentSearch (
	contentSearchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	portletId VARCHAR(200) null,
	articleId VARCHAR(75) null
);

create table JournalFeed (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	feedId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	type_ VARCHAR(75) null,
	structureId VARCHAR(75) null,
	templateId VARCHAR(75) null,
	rendererTemplateId VARCHAR(75) null,
	delta INTEGER,
	orderByCol VARCHAR(75) null,
	orderByType VARCHAR(75) null,
	targetLayoutFriendlyUrl VARCHAR(75) null,
	targetPortletId VARCHAR(75) null,
	contentField VARCHAR(75) null,
	feedType VARCHAR(75) null,
	feedVersion DOUBLE
);

create table JournalStructure (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	xsd TEXT null
);

create table JournalTemplate (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	templateId VARCHAR(75) null,
	structureId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	xsl TEXT null,
	langType VARCHAR(75) null,
	cacheable BOOLEAN,
	smallImage BOOLEAN,
	smallImageId LONG,
	smallImageURL VARCHAR(75) null
);

create table Layout (
	plid LONG not null primary key,
	groupId LONG,
	companyId LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	parentLayoutId LONG,
	name STRING null,
	title STRING null,
	description STRING null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	hidden_ BOOLEAN,
	friendlyURL VARCHAR(100) null,
	iconImage BOOLEAN,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css STRING null,
	priority INTEGER,
	dlFolderId LONG
);

create table LayoutSet (
	layoutSetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	privateLayout BOOLEAN,
	logo BOOLEAN,
	logoId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css STRING null,
	pageCount INTEGER,
	virtualHost VARCHAR(75) null
);

create table ListType (
	listTypeId INTEGER not null primary key,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table MBBan (
	banId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	banUserId LONG
);

create table MBCategory (
	uuid_ VARCHAR(75) null,
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

create table MBDiscussion (
	discussionId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	threadId LONG
);

create table MBMessage (
	uuid_ VARCHAR(75) null,
	messageId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	threadId LONG,
	parentMessageId LONG,
	subject VARCHAR(75) null,
	body TEXT null,
	attachments BOOLEAN,
	anonymous BOOLEAN
);

create table MBMessageFlag (
	messageFlagId LONG not null primary key,
	userId LONG,
	messageId LONG,
	flag INTEGER
);

create table MBStatsUser (
	statsUserId LONG not null primary key,
	groupId LONG,
	userId LONG,
	messageCount INTEGER,
	lastPostDate DATE null
);

create table MBThread (
	threadId LONG not null primary key,
	categoryId LONG,
	rootMessageId LONG,
	messageCount INTEGER,
	viewCount INTEGER,
	lastPostByUserId LONG,
	lastPostDate DATE null,
	priority DOUBLE
);

create table MembershipRequest (
	membershipRequestId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	groupId LONG,
	comments STRING null,
	replyComments STRING null,
	replyDate DATE null,
	replierUserId LONG,
	statusId INTEGER
);

create table Organization_ (
	organizationId LONG not null primary key,
	companyId LONG,
	parentOrganizationId LONG,
	name VARCHAR(100) null,
	location BOOLEAN,
	recursable BOOLEAN,
	regionId LONG,
	countryId LONG,
	statusId INTEGER,
	comments STRING null
);

create table OrgGroupPermission (
	organizationId LONG not null,
	groupId LONG not null,
	permissionId LONG not null,
	primary key (organizationId, groupId, permissionId)
);

create table OrgGroupRole (
	organizationId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	orgLaborId LONG not null primary key,
	organizationId LONG,
	typeId INTEGER,
	sunOpen INTEGER,
	sunClose INTEGER,
	monOpen INTEGER,
	monClose INTEGER,
	tueOpen INTEGER,
	tueClose INTEGER,
	wedOpen INTEGER,
	wedClose INTEGER,
	thuOpen INTEGER,
	thuClose INTEGER,
	friOpen INTEGER,
	friClose INTEGER,
	satOpen INTEGER,
	satClose INTEGER
);

create table PasswordPolicy (
	passwordPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultPolicy BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	changeable BOOLEAN,
	changeRequired BOOLEAN,
	minAge LONG,
	checkSyntax BOOLEAN,
	allowDictionaryWords BOOLEAN,
	minLength INTEGER,
	history BOOLEAN,
	historyCount INTEGER,
	expireable BOOLEAN,
	maxAge LONG,
	warningTime LONG,
	graceLimit INTEGER,
	lockout BOOLEAN,
	maxFailure INTEGER,
	lockoutDuration LONG,
	requireUnlock BOOLEAN,
	resetFailureCount LONG
);

create table PasswordPolicyRel (
	passwordPolicyRelId LONG not null primary key,
	passwordPolicyId LONG,
	classNameId LONG,
	classPK LONG
);

create table PasswordTracker (
	passwordTrackerId LONG not null primary key,
	userId LONG,
	createDate DATE null,
	password_ VARCHAR(75) null
);

create table Permission_ (
	permissionId LONG not null primary key,
	companyId LONG,
	actionId VARCHAR(75) null,
	resourceId LONG
);

create table Phone (
	phoneId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	number_ VARCHAR(75) null,
	extension VARCHAR(75) null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table PluginSetting (
	pluginSettingId LONG not null primary key,
	companyId LONG,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles STRING null,
	active_ BOOLEAN
);

create table PollsChoice (
	uuid_ VARCHAR(75) null,
	choiceId LONG not null primary key,
	questionId LONG,
	name VARCHAR(75) null,
	description VARCHAR(1000) null
);

create table PollsQuestion (
	uuid_ VARCHAR(75) null,
	questionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(500) null,
	description STRING null,
	expirationDate DATE null,
	lastVoteDate DATE null
);

create table PollsVote (
	voteId LONG not null primary key,
	userId LONG,
	questionId LONG,
	choiceId LONG,
	voteDate DATE null
);

create table Portlet (
	id_ LONG not null primary key,
	companyId LONG,
	portletId VARCHAR(200) null,
	roles STRING null,
	active_ BOOLEAN
);

create table PortletItem (
	portletItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	portletId VARCHAR(75) null,
	classNameId LONG
);

create table PortletPreferences (
	portletPreferencesId LONG not null primary key,
	ownerId LONG,
	ownerType INTEGER,
	plid LONG,
	portletId VARCHAR(200) null,
	preferences TEXT null
);

create table RatingsEntry (
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	score DOUBLE
);

create table RatingsStats (
	statsId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	totalEntries INTEGER,
	totalScore DOUBLE,
	averageScore DOUBLE
);

create table Region (
	regionId LONG not null primary key,
	countryId LONG,
	regionCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table Release_ (
	releaseId LONG not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	buildNumber INTEGER,
	buildDate DATE null,
	verified BOOLEAN
);

create table Resource_ (
	resourceId LONG not null primary key,
	codeId LONG,
	primKey VARCHAR(300) null
);

create table ResourceCode (
	codeId LONG not null primary key,
	companyId LONG,
	name VARCHAR(300) null,
	scope INTEGER
);

create table Role_ (
	roleId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER
);

create table Roles_Permissions (
	roleId LONG not null,
	permissionId LONG not null,
	primary key (roleId, permissionId)
);

create table SCFrameworkVersi_SCProductVers (
	frameworkVersionId LONG not null,
	productVersionId LONG not null,
	primary key (frameworkVersionId, productVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url STRING null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SCLicense (
	licenseId LONG not null primary key,
	name VARCHAR(75) null,
	url STRING null,
	openSource BOOLEAN,
	active_ BOOLEAN,
	recommended BOOLEAN
);

create table SCLicenses_SCProductEntries (
	licenseId LONG not null,
	productEntryId LONG not null,
	primary key (licenseId, productEntryId)
);

create table SCProductEntry (
	productEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	tags VARCHAR(300) null,
	shortDescription STRING null,
	longDescription STRING null,
	pageURL STRING null,
	author VARCHAR(75) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SCProductScreenshot (
	productScreenshotId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	productEntryId LONG,
	thumbnailId LONG,
	fullImageId LONG,
	priority INTEGER
);

create table SCProductVersion (
	productVersionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId LONG,
	version VARCHAR(75) null,
	changeLog STRING null,
	downloadPageURL STRING null,
	directDownloadURL VARCHAR(2000) null,
	repoStoreArtifact BOOLEAN
);

create table ServiceComponent (
	serviceComponentId LONG not null primary key,
	buildNamespace VARCHAR(75) null,
	buildNumber LONG,
	buildDate LONG,
	data_ TEXT null
);

create table ShoppingCart (
	cartId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	itemIds STRING null,
	couponCodes VARCHAR(75) null,
	altShipping INTEGER,
	insure BOOLEAN
);

create table ShoppingCategory (
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table ShoppingCoupon (
	couponId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	code_ VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null,
	active_ BOOLEAN,
	limitCategories STRING null,
	limitSkus STRING null,
	minOrder DOUBLE,
	discount DOUBLE,
	discountType VARCHAR(75) null
);

create table ShoppingItem (
	itemId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	sku VARCHAR(75) null,
	name VARCHAR(200) null,
	description STRING null,
	properties STRING null,
	fields_ BOOLEAN,
	fieldsQuantities STRING null,
	minQuantity INTEGER,
	maxQuantity INTEGER,
	price DOUBLE,
	discount DOUBLE,
	taxable BOOLEAN,
	shipping DOUBLE,
	useShippingFormula BOOLEAN,
	requiresShipping BOOLEAN,
	stockQuantity INTEGER,
	featured_ BOOLEAN,
	sale_ BOOLEAN,
	smallImage BOOLEAN,
	smallImageId LONG,
	smallImageURL VARCHAR(75) null,
	mediumImage BOOLEAN,
	mediumImageId LONG,
	mediumImageURL VARCHAR(75) null,
	largeImage BOOLEAN,
	largeImageId LONG,
	largeImageURL VARCHAR(75) null
);

create table ShoppingItemField (
	itemFieldId LONG not null primary key,
	itemId LONG,
	name VARCHAR(75) null,
	values_ STRING null,
	description STRING null
);

create table ShoppingItemPrice (
	itemPriceId LONG not null primary key,
	itemId LONG,
	minQuantity INTEGER,
	maxQuantity INTEGER,
	price DOUBLE,
	discount DOUBLE,
	taxable BOOLEAN,
	shipping DOUBLE,
	useShippingFormula BOOLEAN,
	status INTEGER
);

create table ShoppingOrder (
	orderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	number_ VARCHAR(75) null,
	tax DOUBLE,
	shipping DOUBLE,
	altShipping VARCHAR(75) null,
	requiresShipping BOOLEAN,
	insure BOOLEAN,
	insurance DOUBLE,
	couponCodes VARCHAR(75) null,
	couponDiscount DOUBLE,
	billingFirstName VARCHAR(75) null,
	billingLastName VARCHAR(75) null,
	billingEmailAddress VARCHAR(75) null,
	billingCompany VARCHAR(75) null,
	billingStreet VARCHAR(75) null,
	billingCity VARCHAR(75) null,
	billingState VARCHAR(75) null,
	billingZip VARCHAR(75) null,
	billingCountry VARCHAR(75) null,
	billingPhone VARCHAR(75) null,
	shipToBilling BOOLEAN,
	shippingFirstName VARCHAR(75) null,
	shippingLastName VARCHAR(75) null,
	shippingEmailAddress VARCHAR(75) null,
	shippingCompany VARCHAR(75) null,
	shippingStreet VARCHAR(75) null,
	shippingCity VARCHAR(75) null,
	shippingState VARCHAR(75) null,
	shippingZip VARCHAR(75) null,
	shippingCountry VARCHAR(75) null,
	shippingPhone VARCHAR(75) null,
	ccName VARCHAR(75) null,
	ccType VARCHAR(75) null,
	ccNumber VARCHAR(75) null,
	ccExpMonth INTEGER,
	ccExpYear INTEGER,
	ccVerNumber VARCHAR(75) null,
	comments STRING null,
	ppTxnId VARCHAR(75) null,
	ppPaymentStatus VARCHAR(75) null,
	ppPaymentGross DOUBLE,
	ppReceiverEmail VARCHAR(75) null,
	ppPayerEmail VARCHAR(75) null,
	sendOrderEmail BOOLEAN,
	sendShippingEmail BOOLEAN
);

create table ShoppingOrderItem (
	orderItemId LONG not null primary key,
	orderId LONG,
	itemId VARCHAR(75) null,
	sku VARCHAR(75) null,
	name VARCHAR(200) null,
	description STRING null,
	properties STRING null,
	price DOUBLE,
	quantity INTEGER,
	shippedDate DATE null
);

create table SocialActivity (
	activityId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	mirrorActivityId LONG,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG
);

create table SocialRelation (
	uuid_ VARCHAR(75) null,
	relationId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	userId1 LONG,
	userId2 LONG,
	type_ INTEGER
);

create table SocialRequest (
	uuid_ VARCHAR(75) null,
	requestId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG,
	status INTEGER
);

create table Subscription (
	subscriptionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	frequency VARCHAR(75) null
);

create table TagsAsset (
	assetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	startDate DATE null,
	endDate DATE null,
	publishDate DATE null,
	expirationDate DATE null,
	mimeType VARCHAR(75) null,
	title VARCHAR(300) null,
	description STRING null,
	summary STRING null,
	url STRING null,
	height INTEGER,
	width INTEGER,
	priority DOUBLE,
	viewCount INTEGER
);

create table TagsAssets_TagsEntries (
	assetId LONG not null,
	entryId LONG not null,
	primary key (assetId, entryId)
);

create table TagsEntry (
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TagsProperty (
	propertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(300) null
);

create table TagsSource (
	sourceId LONG not null primary key,
	parentSourceId LONG,
	name VARCHAR(75) null,
	acronym VARCHAR(75) null
);

create table TasksProposal (
	proposalId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	publishDate DATE null,
	dueDate DATE null
);

create table TasksReview (
	reviewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	proposalId LONG,
	assignedByUserId LONG,
	assignedByUserName VARCHAR(75) null,
	stage INTEGER,
	completed BOOLEAN,
	rejected BOOLEAN
);

create table User_ (
	uuid_ VARCHAR(75) null,
	userId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	defaultUser BOOLEAN,
	contactId LONG,
	password_ VARCHAR(75) null,
	passwordEncrypted BOOLEAN,
	passwordReset BOOLEAN,
	passwordModifiedDate DATE null,
	graceLoginCount INTEGER,
	screenName VARCHAR(75) null,
	emailAddress VARCHAR(75) null,
	openId VARCHAR(1024) null,
	portraitId LONG,
	languageId VARCHAR(75) null,
	timeZoneId VARCHAR(75) null,
	greeting VARCHAR(75) null,
	comments STRING null,
	loginDate DATE null,
	loginIP VARCHAR(75) null,
	lastLoginDate DATE null,
	lastLoginIP VARCHAR(75) null,
	lastFailedLoginDate DATE null,
	failedLoginAttempts INTEGER,
	lockout BOOLEAN,
	lockoutDate DATE null,
	agreedToTermsOfUse BOOLEAN,
	active_ BOOLEAN
);

create table UserGroup (
	userGroupId LONG not null primary key,
	companyId LONG,
	parentUserGroupId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table UserGroupRole (
	userId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (userId, groupId, roleId)
);

create table UserIdMapper (
	userIdMapperId LONG not null primary key,
	userId LONG,
	type_ VARCHAR(75) null,
	description VARCHAR(75) null,
	externalUserId VARCHAR(75) null
);

create table Users_Groups (
	userId LONG not null,
	groupId LONG not null,
	primary key (userId, groupId)
);

create table Users_Orgs (
	userId LONG not null,
	organizationId LONG not null,
	primary key (userId, organizationId)
);

create table Users_Permissions (
	userId LONG not null,
	permissionId LONG not null,
	primary key (userId, permissionId)
);

create table Users_Roles (
	userId LONG not null,
	roleId LONG not null,
	primary key (userId, roleId)
);

create table Users_UserGroups (
	userId LONG not null,
	userGroupId LONG not null,
	primary key (userId, userGroupId)
);

create table UserTracker (
	userTrackerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	sessionId VARCHAR(200) null,
	remoteAddr VARCHAR(75) null,
	remoteHost VARCHAR(75) null,
	userAgent VARCHAR(200) null
);

create table UserTrackerPath (
	userTrackerPathId LONG not null primary key,
	userTrackerId LONG,
	path_ STRING null,
	pathDate DATE null
);

create table WebDAVProps (
	webDavPropsId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	props TEXT null
);

create table Website (
	websiteId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	url STRING null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table WikiNode (
	uuid_ VARCHAR(75) null,
	nodeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

create table WikiPage (
	uuid_ VARCHAR(75) null,
	pageId LONG not null primary key,
	resourcePrimKey LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	nodeId LONG,
	title VARCHAR(75) null,
	version DOUBLE,
	content TEXT null,
	summary STRING null,
	format VARCHAR(75) null,
	head BOOLEAN,
	parentTitle VARCHAR(75) null,
	redirectTitle VARCHAR(75) null
);

create table WikiPageResource (
	resourcePrimKey LONG not null primary key,
	nodeId LONG,
	title VARCHAR(75) null
);
