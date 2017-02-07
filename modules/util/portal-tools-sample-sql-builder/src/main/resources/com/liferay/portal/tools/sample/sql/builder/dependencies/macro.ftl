<#setting number_format = "computer">

<#macro insertAssetEntry
	_entry
	_categoryAndTag = false
>
	<#local assetEntryModel = dataFactory.newAssetEntryModel(_entry)>

	${dataFactory.toInsertSQL(assetEntryModel)}

	<#if _categoryAndTag>
		<#local assetCategoryIds = dataFactory.getAssetCategoryIds(assetEntryModel.groupId)>

		<#list assetCategoryIds as assetCategoryId>
			insert into AssetEntries_AssetCategories values (${assetEntryModel.companyId}, ${assetCategoryId}, ${assetEntryModel.entryId});
		</#list>

		<#local assetTagIds = dataFactory.getAssetTagIds(assetEntryModel.groupId)>

		<#list assetTagIds as assetTagId>
			insert into AssetEntries_AssetTags values (${assetEntryModel.companyId}, ${assetEntryModel.entryId}, ${assetTagId});
		</#list>
	</#if>
</#macro>

<#macro insertDDMContent
	_ddmStorageLinkId
	_ddmStructureId
	_entry
	_currentIndex = -1
>
	<#if _currentIndex = -1>
		<#local ddmContentModel = dataFactory.newDDMContentModel(_entry)>
	<#else>
		<#local ddmContentModel = dataFactory.newDDMContentModel(_entry, _currentIndex)>
	</#if>

	${dataFactory.toInsertSQL(ddmContentModel)}

	<@insertDDMStorageLink
		_ddmStorageLinkModel = dataFactory.newDDMStorageLinkModel(_ddmStorageLinkId, ddmContentModel, _ddmStructureId)
	/>
</#macro>

<#macro insertDDMStorageLink
	_ddmStorageLinkModel
>
	${dataFactory.toInsertSQL(_ddmStorageLinkModel)}
</#macro>

<#macro insertDDMStructure
	_ddmStructureModel
	_ddmStructureLayoutModel
	_ddmStructureVersionModel
>
	${dataFactory.toInsertSQL(_ddmStructureModel)}

	<@insertResourcePermissions
		_entry = _ddmStructureModel
	/>

	${dataFactory.toInsertSQL(_ddmStructureLayoutModel)}

	${dataFactory.toInsertSQL(_ddmStructureVersionModel)}
</#macro>

<#macro insertDDMStructureLink
	_entry
>
	${dataFactory.toInsertSQL(dataFactory.newDDMStructureLinkModel(_entry))}
</#macro>

<#macro insertDLFolder
	_ddmStructureId
	_dlFolderDepth
	_groupId
	_parentDLFolderId
>
	<#if _dlFolderDepth <= dataFactory.maxDLFolderDepth>
		<#local dlFolderModels = dataFactory.newDLFolderModels(_groupId, _parentDLFolderId)>

		<#list dlFolderModels as dlFolderModel>
			${dataFactory.toInsertSQL(dlFolderModel)}

			<@insertResourcePermissions
				_entry = dlFolderModel
			/>

			<@insertAssetEntry
				_entry = dlFolderModel
			/>

			<#local dlFileEntryModels = dataFactory.newDlFileEntryModels(dlFolderModel)>

			<#list dlFileEntryModels as dlFileEntryModel>
				${dataFactory.toInsertSQL(dlFileEntryModel)}

				<#local dlFileVersionModel = dataFactory.newDLFileVersionModel(dlFileEntryModel)>

				${dataFactory.toInsertSQL(dlFileVersionModel)}

				<@insertResourcePermissions
					_entry = dlFileEntryModel
				/>

				<@insertAssetEntry
					_entry = dlFileEntryModel
				/>

				<#local ddmStorageLinkId = dataFactory.getCounterNext()>

				<@insertDDMContent
					_ddmStorageLinkId = ddmStorageLinkId
					_ddmStructureId = _ddmStructureId
					_entry = dlFileEntryModel
				/>

				<@insertMBDiscussion
					_classNameId = dataFactory.DLFileEntryClassNameId
					_classPK = dlFileEntryModel.fileEntryId
					_groupId = dlFileEntryModel.groupId
					_maxCommentCount = 0
					_mbRootMessageId = dataFactory.getCounterNext()
					_mbThreadId = dataFactory.getCounterNext()
				/>

				<@insertSocialActivity
					_entry = dlFileEntryModel
				/>

				<#local dlFileEntryMetadataModel = dataFactory.newDLFileEntryMetadataModel(ddmStorageLinkId, _ddmStructureId, dlFileVersionModel)>

				${dataFactory.toInsertSQL(dlFileEntryMetadataModel)}

				<@insertDDMStructureLink
					_entry = dlFileEntryMetadataModel
				/>

				${dataFactory.getCSVWriter("documentLibrary").write(dlFolderModel.folderId + "," + dlFileEntryModel.name + "," + dlFileEntryModel.fileEntryId + "," + dataFactory.getDateLong(dlFileEntryModel.createDate) + "," + dataFactory.getDateLong(dlFolderModel.createDate) + "\n")}
			</#list>

			<@insertDLFolder
				_ddmStructureId = _ddmStructureId
				_dlFolderDepth = _dlFolderDepth + 1
				_groupId = groupId
				_parentDLFolderId = dlFolderModel.folderId
			/>
		</#list>
	</#if>
</#macro>

<#macro insertGroup
	_groupModel
	_publicPageCount
>
	${dataFactory.toInsertSQL(_groupModel)}

	<@insertResourcePermissions
		_entry = _groupModel
	/>

	<#local layoutSetModels = dataFactory.newLayoutSetModels(_groupModel.groupId, _publicPageCount)>

	<#list layoutSetModels as layoutSetModel>
		${dataFactory.toInsertSQL(layoutSetModel)}
	</#list>
</#macro>

<#macro insertLayout
	_layoutModel
>
	${dataFactory.toInsertSQL(_layoutModel)}

	<@insertResourcePermissions
		_entry = _layoutModel
	/>

	<#local layoutFriendlyURLModel = dataFactory.newLayoutFriendlyURLModel(_layoutModel)>

	${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(_layoutModel))}
</#macro>

<#macro insertMBDiscussion
	_classNameId
	_classPK
	_groupId
	_maxCommentCount
	_mbRootMessageId
	_mbThreadId
>
	<#local mbThreadModel = dataFactory.newMBThreadModel(_mbThreadId, _groupId, _mbRootMessageId, _maxCommentCount)>

	${dataFactory.toInsertSQL(mbThreadModel)}

	<#local mbRootMessageModel = dataFactory.newMBMessageModel(mbThreadModel, _classNameId, _classPK, 0)>

	<@insertMBMessage
		_mbMessageModel = mbRootMessageModel
	/>

	<#local mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel, _classNameId, _classPK, _maxCommentCount)>

	<#list mbMessageModels as mbMessageModel>
		<@insertMBMessage
			_mbMessageModel = mbMessageModel
		/>

		<@insertSocialActivity
			_entry = mbMessageModel
		/>
	</#list>

	${dataFactory.toInsertSQL(dataFactory.newMBDiscussionModel(_groupId, _classNameId, _classPK, _mbThreadId))}
</#macro>

<#macro insertMBMessage
	_mbMessageModel
>
	${dataFactory.toInsertSQL(_mbMessageModel)}

	<@insertResourcePermissions
		_entry = _mbMessageModel
	/>

	<@insertAssetEntry
		_entry = _mbMessageModel
	/>
</#macro>

<#macro insertPortletPreferences
	_portletPreferencesModel
>
	${dataFactory.toInsertSQL(_portletPreferencesModel)}

	<@insertResourcePermissions
		_entry = _portletPreferencesModel
	/>
</#macro>

<#macro insertResourcePermission
	_resourcePermissionModel
>
	${dataFactory.toInsertSQL(_resourcePermissionModel)}
</#macro>

<#macro insertResourcePermissions
	_entry
>
	<#local resourcePermissionModels = dataFactory.newResourcePermissionModels(_entry)>

	<#list resourcePermissionModels as resourcePermissionModel>
		<@insertResourcePermission
			_resourcePermissionModel = resourcePermissionModel
		/>
	</#list>
</#macro>

<#macro insertSocialActivity
	_entry
>
	${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(_entry))}
</#macro>

<#macro insertSubscription
	_entry
>
	${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(_entry))}
</#macro>

<#macro insertUser
	_userModel
	_groupIds = []
	_roleIds = []
>
	${dataFactory.toInsertSQL(_userModel)}

	${dataFactory.toInsertSQL(dataFactory.newContactModel(_userModel))}

	<#list _roleIds as roleId>
		insert into Users_Roles values (0, ${roleId}, ${_userModel.userId});
	</#list>

	<#list _groupIds as groupId>
		insert into Users_Groups values (0, ${groupId}, ${_userModel.userId});
	</#list>
</#macro>