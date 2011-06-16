<#setting number_format = "0">

${dataFactory.initCounters()}

<#list dataFactory.counters as counter>
	update Counter set currentId = ${counter.currentId} where name = '${counter.name}';
</#list>