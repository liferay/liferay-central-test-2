create table QUARTZ_JOB_DETAILS (
	JOB_NAME VARCHAR(80) not null,
	JOB_GROUP VARCHAR(80) not null,
	DESCRIPTION VARCHAR(120) null,
	JOB_CLASS_NAME VARCHAR(128) not null,
	IS_DURABLE VARCHAR(1) not null,
	IS_VOLATILE VARCHAR(1) not null,
	IS_STATEFUL VARCHAR(1) not null,
	REQUESTS_RECOVERY VARCHAR(1) not null,
	JOB_DATA BLOB null,
	primary key (JOB_NAME, JOB_GROUP)
);

create table QUARTZ_JOB_LISTENERS (
	JOB_NAME VARCHAR(80) not null,
	JOB_GROUP VARCHAR(80) not null,
	JOB_LISTENER VARCHAR(80) not null,
	primary key (JOB_NAME, JOB_GROUP, JOB_LISTENER)
);

create table QUARTZ_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	JOB_NAME VARCHAR(80) not null,
	JOB_GROUP VARCHAR(80) not null,
	IS_VOLATILE VARCHAR(1) not null,
	DESCRIPTION VARCHAR(120) null,
	NEXT_FIRE_TIME LONG null,
	PREV_FIRE_TIME LONG null,
	PRIORITY INTEGER null,
	TRIGGER_STATE VARCHAR(16) not null,
	TRIGGER_TYPE VARCHAR(8) not null,
	START_TIME LONG not null,
	END_TIME LONG null,
	CALENDAR_NAME VARCHAR(80) null,
	MISFIRE_INSTR INTEGER null,
	JOB_DATA BLOB null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_SIMPLE_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	REPEAT_COUNT LONG not null,
	REPEAT_INTERVAL LONG not null,
	TIMES_TRIGGERED LONG not null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_CRON_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	CRON_EXPRESSION VARCHAR(80) not null,
	TIME_ZONE_ID VARCHAR(80),
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_BLOB_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	BLOB_DATA BLOB null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_TRIGGER_LISTENERS (
    TRIGGER_NAME VARCHAR(80) not null,
    TRIGGER_GROUP VARCHAR(80) not null,
    TRIGGER_LISTENER VARCHAR(80) not null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_LISTENER)
);

create table QUARTZ_CALENDARS (
	CALENDAR_NAME VARCHAR(80) not null primary key,
	CALENDAR BLOB not null
);

create table QUARTZ_PAUSED_TRIGGER_GRPS (
	TRIGGER_GROUP VARCHAR(80) not null primary key
);

create table QUARTZ_FIRED_TRIGGERS (
	ENTRY_ID VARCHAR(95) not null primary key,
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	IS_VOLATILE VARCHAR(1) not null,
	INSTANCE_NAME VARCHAR(80) not null,
	FIRED_TIME LONG not null,
	PRIORITY INTEGER not null,
	STATE VARCHAR(16) not null,
	JOB_NAME VARCHAR(80) null,
	JOB_GROUP VARCHAR(80) null,
	IS_STATEFUL VARCHAR(1) null,
	REQUESTS_RECOVERY VARCHAR(1) null
);

create table QUARTZ_SCHEDULER_STATE (
	INSTANCE_NAME VARCHAR(80) not null primary key,
	LAST_CHECKIN_TIME LONG not null,
	CHECKIN_INTERVAL LONG not null
);

create table QUARTZ_LOCKS (
	LOCK_NAME VARCHAR(40) not null primary key
);

insert into QUARTZ_LOCKS values('TRIGGER_ACCESS');
insert into QUARTZ_LOCKS values('JOB_ACCESS');
insert into QUARTZ_LOCKS values('CALENDAR_ACCESS');
insert into QUARTZ_LOCKS values('STATE_ACCESS');
insert into QUARTZ_LOCKS values('MISFIRE_ACCESS');

create index IX_quartz_triggers_state on QUARTZ_TRIGGERS(TRIGGER_STATE);
create index IX_quartz_triggers_next_fire_time on QUARTZ_TRIGGERS(NEXT_FIRE_TIME);
create index IX_quartz_triggers_state_next_fire_time on QUARTZ_TRIGGERS(TRIGGER_STATE, NEXT_FIRE_TIME);
create index IX_quartz_fired_triggers_trigger_name on QUARTZ_FIRED_TRIGGERS(TRIGGER_NAME);
create index IX_quartz_fired_triggers_trigger_group on QUARTZ_FIRED_TRIGGERS(TRIGGER_GROUP);
create index IX_quartz_fired_triggers_trigger_name_group on QUARTZ_FIRED_TRIGGERS(TRIGGER_NAME, TRIGGER_GROUP);
create index IX_quartz_fired_triggers_instance_name on QUARTZ_FIRED_TRIGGERS(INSTANCE_NAME);
create index IX_quartz_fired_triggers_job_name on QUARTZ_FIRED_TRIGGERS(JOB_NAME);
create index IX_quartz_fired_triggers_job_group on QUARTZ_FIRED_TRIGGERS(JOB_GROUP);