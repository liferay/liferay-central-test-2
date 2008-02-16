alter table IGImage add custom1ImageId LONG null;
alter table IGImage add custom2ImageId LONG null;
alter table IGImage add name VARCHAR(75) null;

update Group_ set type_ = 3 where type_ = 0;

alter table WikiPage add redirectTo VARCHAR(75) null;
alter table WikiPage add parent VARCHAR(75) null;