<#setting number_format = "0">

${dataFactory.initCounters()}

<#list dataFactory.counters as counter>
	delete from Counter where name = '${counter.name}';
	insert into Counter (name, currentId) values ('${counter.name}', ${counter.currentId});
</#list>