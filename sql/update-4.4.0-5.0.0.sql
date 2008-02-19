alter table IGImage add name VARCHAR(75) null;
alter table IGImage add custom1ImageId LONG null;
alter table IGImage add custom2ImageId LONG null;

update Group_ set type_ = 3 where type_ = 0;

update Image set type_ = 'jpg' where type_ = 'jpeg';

alter table WikiPage add parentTitle VARCHAR(75) null;
alter table WikiPage add redirectTitle VARCHAR(75) null;