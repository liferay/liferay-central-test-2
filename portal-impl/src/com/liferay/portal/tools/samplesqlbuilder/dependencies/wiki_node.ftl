<#setting number_format = "0">

insert into WikiNode values ('${portalUUIDUtil.generate()}', ${wikiNode.nodeId}, ${wikiNode.groupId}, ${companyId}, ${wikiNode.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${wikiNode.name}', '${wikiNode.description}', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.wiki.model.WikiNode", wikiNode.nodeId)}