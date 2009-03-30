<#setting number_format = "0">

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (${group.groupId}, ${companyId}, ${defaultUserId}, ${group.classNameId}, ${group.classPK}, 0, 0, '${group.name}', '${group.friendlyURL}', TRUE);

insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.get()}, ${companyId}, ${group.groupId}, TRUE, FALSE, 'classic', '01', ${privateLayouts?size});
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.get()}, ${companyId}, ${group.groupId}, FALSE, FALSE, 'classic', '01', ${publicLayouts?size});

<#list privateLayouts as layout>
	insert into Layout (plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values (${layout.getPlid()}, ${group.groupId}, ${companyId}, TRUE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', 0);
</#list>

<#list publicLayouts as layout>
	insert into Layout (plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values (${layout.getPlid()}, ${group.groupId}, ${companyId}, FALSE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', 0);
</#list>