Liferay.Service.register("Liferay.Service.Portal", "com.liferay.portal.service");

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Address",
	{
		addAddress: true,
		deleteAddress: true,
		getAddress: true,
		getAddresses: true,
		updateAddress: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ClassName",
	{
		getClassName: true,
		getClassNameId: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Company",
	{
		addCompany: true,
		getCompanyById: true,
		getCompanyByLogoId: true,
		getCompanyByMx: true,
		getCompanyByVirtualHost: true,
		getCompanyByWebId: true,
		updateCompany: true,
		updateDisplay: true,
		updatePreferences: true,
		updateSecurity: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Country",
	{
		addCountry: true,
		getCountries: true,
		getCountry: true,
		getCountryByA2: true,
		getCountryByA3: true,
		getCountryByName: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "EmailAddress",
	{
		addEmailAddress: true,
		deleteEmailAddress: true,
		getEmailAddress: true,
		getEmailAddresses: true,
		updateEmailAddress: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Group",
	{
		addGroup: true,
		addRoleGroups: true,
		deleteGroup: true,
		getGroup: true,
		getManageableGroups: true,
		getOrganizationsGroups: true,
		getUserGroup: true,
		getUserGroupsGroups: true,
		getUserOrganizationsGroups: true,
		hasUserGroup: true,
		search: true,
		searchCount: true,
		setRoleGroups: true,
		unsetRoleGroups: true,
		updateFriendlyURL: true,
		updateGroup: true,
		updateWorkflow: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Layout",
	{
		addLayout: true,
		deleteLayout: true,
		getLayoutName: true,
		getLayoutReferences: true,
		setLayouts: true,
		unschedulePublishToLive: true,
		unschedulePublishToRemote: true,
		updateLayout: true,
		updateLookAndFeel: true,
		updateName: true,
		updateParentLayoutId: true,
		updatePriority: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSet",
	{
		updateLookAndFeel: true,
		updateVirtualHost: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ListType",
	{
		getListType: true,
		getListTypes: true,
		validate: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "MembershipRequest",
	{
		addMembershipRequest: true,
		deleteMembershipRequests: true,
		getMembershipRequest: true,
		updateStatus: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Organization",
	{
		addGroupOrganizations: true,
		addPasswordPolicyOrganizations: true,
		addOrganization: true,
		deleteLogo: true,
		deleteOrganization: true,
		getManageableOrganizations: true,
		getOrganization: true,
		getOrganizationId: true,
		getUserOrganizations: true,
		setGroupOrganizations: true,
		unsetGroupOrganizations: true,
		unsetPasswordPolicyOrganizations: true,
		updateOrganization: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "OrgLabor",
	{
		addOrgLabor: true,
		deleteOrgLabor: true,
		getOrgLabor: true,
		getOrgLabors: true,
		updateOrgLabor: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PasswordPolicy",
	{
		addPasswordPolicy: true,
		deletePasswordPolicy: true,
		updatePasswordPolicy: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Permission",
	{
		checkPermission: true,
		hasGroupPermission: true,
		hasUserPermission: true,
		hasUserPermissions: true,
		setGroupPermissions: true,
		setOrgGroupPermissions: true,
		setRolePermission: true,
		setRolePermissions: true,
		setUserPermissions: true,
		unsetRolePermission: true,
		unsetRolePermissions: true,
		unsetUserPermissions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Phone",
	{
		addPhone: true,
		deletePhone: true,
		getPhone: true,
		getPhones: true,
		updatePhone: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PluginSetting",
	{
		updatePluginSetting: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portal",
	{
		getAutoDeployDirectory: true,
		getBuildNumber: true,
		test: true,
		testCounterRollback: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portlet",
	{
		getWARPortlets: true,
		updatePortlet: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PortletPreferences",
	{
		deleteArchivedPreferences: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Region",
	{
		addRegion: true,
		getRegions: true,
		getRegion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Resource",
	{
		getResource: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Role",
	{
		addRole: true,
		addUserRoles: true,
		deleteRole: true,
		getGroupRole: true,
		getGroupRoles: true,
		getRole: true,
		getUserGroupRoles: true,
		getUserRelatedRoles: true,
		getUserRoles: true,
		hasUserRole: true,
		hasUserRoles: true,
		unsetUserRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Theme",
	{
		getThemes: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "User",
	{
		addGroupUsers: true,
		addOrganizationUsers: true,
		addPasswordPolicyUsers: true,
		addRoleUsers: true,
		addUserGroupUsers: true,
		addUser: true,
		deletePortrait: true,
		deleteRoleUser: true,
		deleteUser: true,
		getDefaultUserId: true,
		getGroupUserIds: true,
		getOrganizationUserIds: true,
		getRoleUserIds: true,
		getUserByEmailAddress: true,
		getUserById: true,
		getUserByScreenName: true,
		getUserIdByEmailAddress: true,
		getUserIdByScreenName: true,
		hasGroupUser: true,
		hasRoleUser: true,
		setRoleUsers: true,
		setUserGroupUsers: true,
		unsetGroupUsers: true,
		unsetOrganizationUsers: true,
		unsetPasswordPolicyUsers: true,
		unsetRoleUsers: true,
		unsetUserGroupUsers: true,
		updateActive: true,
		updateAgreedToTermsOfUse: true,
		updateEmailAddress: true,
		updateLockout: true,
		updateOpenId: true,
		updateOrganizations: true,
		updatePassword: true,
		updateReminderQuery: true,
		updateScreenName: true,
		updateUser: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroup",
	{
		addGroupUserGroups: true,
		addUserGroup: true,
		deleteUserGroup: true,
		getUserGroup: true,
		getUserUserGroups: true,
		unsetGroupUserGroups: true,
		updateUserGroup: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroupRole",
	{
		addUserGroupRoles: true,
		deleteUserGroupRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Website",
	{
		addWebsite: true,
		deleteWebsite: true,
		getWebsite: true,
		getWebsites: true,
		updateWebsite: true
	}
);

Liferay.Service.register("Liferay.Service.Announcements", "com.liferay.portlet.announcements.service");

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsDelivery",
	{
		updateDelivery: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsEntry",
	{
		addEntry: true,
		deleteEntry: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsFlag",
	{
		addFlag: true,
		getFlag: true,
		deleteFlag: true
	}
);

Liferay.Service.register("Liferay.Service.Blogs", "com.liferay.portlet.blogs.service");

Liferay.Service.registerClass(
	Liferay.Service.Blogs, "BlogsEntry",
	{
		addEntry: true,
		deleteEntry: true,
		getCompanyEntries: true,
		getEntry: true,
		getGroupEntries: true,
		getOrganizationEntries: true,
		updateEntry: true
	}
);

Liferay.Service.register("Liferay.Service.Bookmarks", "com.liferay.portlet.bookmarks.service");

Liferay.Service.registerClass(
	Liferay.Service.Bookmarks, "BookmarksEntry",
	{
		addEntry: true,
		deleteEntry: true,
		getEntry: true,
		openEntry: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Bookmarks, "BookmarksFolder",
	{
		addFolder: true,
		deleteFolder: true,
		getFolder: true,
		updateFolder: true
	}
);

Liferay.Service.register("Liferay.Service.Cal", "com.liferay.portlet.calendar.service");

Liferay.Service.registerClass(
	Liferay.Service.Cal, "CalEvent",
	{
		addEvent: true,
		deleteEvent: true,
		getEvent: true,
		updateEvent: true
	}
);

Liferay.Service.register("Liferay.Service.DL", "com.liferay.portlet.documentlibrary.service");

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileEntry",
	{
		deleteFileEntry: true,
		deleteFileEntryByTitle: true,
		getFileEntries: true,
		getFileEntry: true,
		getFileEntryByTitle: true,
		hasFileEntryLock: true,
		lockFileEntry: true,
		refreshFileEntryLock: true,
		unlockFileEntry: true,
		verifyFileEntryLock: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileShortcut",
	{
		addFileShortcut: true,
		deleteFileShortcut: true,
		getFileShortcut: true,
		updateFileShortcut: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFolder",
	{
		addFolder: true,
		copyFolder: true,
		deleteFolder: true,
		getFolder: true,
		getFolderId: true,
		getFolders: true,
		hasInheritableLock: true,
		lockFolder: true,
		refreshFolderLock: true,
		reIndexSearch: true,
		unlockFolder: true,
		updateFolder: true,
		verifyInheritableLock: true
	}
);

Liferay.Service.register("Liferay.Service.Expando", "com.liferay.portlet.expando.service");

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoColumn",
	{
		addColumn: true,
		deleteColumn: true,
		updateColumn: true,
		updateTypeSettings: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoValue",
	{
		addValue: true,
		getData: true
	}
);

Liferay.Service.register("Liferay.Service.Flags", "com.liferay.portlet.flags.service");

Liferay.Service.registerClass(
	Liferay.Service.Flags, "FlagsEntry",
	{
		addFlagEntry: true
	}
);