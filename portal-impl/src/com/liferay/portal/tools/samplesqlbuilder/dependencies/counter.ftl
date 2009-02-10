delete from Counter where name = '${name}';
insert into Counter (name, currentId) values ('${name}', ${currentId});