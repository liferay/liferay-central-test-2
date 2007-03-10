Liferay.Service = {
	url: themeDisplay.getPathMain() + "/portal/json_service",

	classNameSuffix: "ServiceJSON",

	getParameters: function(params) {
		var serviceParameters = "";

		for (var key in params) {
			serviceParameters += key + ",";
		}

		if (endsWith(serviceParameters, ",")) {
			serviceParameters = serviceParameters.substring(0, serviceParameters.length - 1);
		}

		return serviceParameters;
	}
};

Liferay.Service.Portal = {
	servicePackage: "com.liferay.portal.service.http."
};

Liferay.Service.Portal.Address = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Address" + Liferay.Service.classNameSuffix,

	addAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getAddresses: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getAddresses";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Company = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Company" + Liferay.Service.classNameSuffix,

	updateCompany: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateCompany";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateDisplay: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateDisplay";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateSecurity: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateSecurity";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Country = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Country" + Liferay.Service.classNameSuffix,

	getCountries: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCountries";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCountry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCountry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.EmailAddress = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "EmailAddress" + Liferay.Service.classNameSuffix,

	addEmailAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEmailAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEmailAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEmailAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEmailAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEmailAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEmailAddresses: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEmailAddresses";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEmailAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEmailAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Group = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Group" + Liferay.Service.classNameSuffix,

	addGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addRoleGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addRoleGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrganizationsGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrganizationsGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserGroupsGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserGroupsGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	hasUserGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "hasUserGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	search: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "search";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	searchCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "searchCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setRoleGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setRoleGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetRoleGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetRoleGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Layout = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Layout" + Liferay.Service.classNameSuffix,

	addLayout: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addLayout";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteLayout: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteLayout";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getLayoutName: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getLayoutName";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getLayoutReferences: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getLayoutReferences";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	exportLayouts: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "exportLayouts";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setLayouts: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setLayouts";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateLayout: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateLayout";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateLookAndFeel: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateLookAndFeel";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateName: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateName";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.LayoutSet = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "LayoutSet" + Liferay.Service.classNameSuffix,

	updateLookAndFeel: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateLookAndFeel";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateVirtualHost: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateVirtualHost";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.ListType = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "ListType" + Liferay.Service.classNameSuffix,

	getListType: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getListType";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getListTypes: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getListTypes";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	validate: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "validate";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Organization = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Organization" + Liferay.Service.classNameSuffix,

	addGroupOrganizations: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addGroupOrganizations";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addOrganization: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addOrganization";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteOrganization: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteOrganization";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrganization: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrganization";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrganizationId: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrganizationId";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserOrganizations: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserOrganizations";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setGroupOrganizations: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setGroupOrganizations";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetGroupOrganizations: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetGroupOrganizations";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateOrganization: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateOrganization";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.OrgLabor = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "OrgLabor" + Liferay.Service.classNameSuffix,

	addOrgLabor: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addOrgLabor";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteOrgLabor: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteOrgLabor";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrgLabor: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrgLabor";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrgLabors: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrgLabors";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateOrgLabor: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateOrgLabor";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Permission = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Permission" + Liferay.Service.classNameSuffix,

	checkPermission: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "checkPermission";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	hasGroupPermission: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "hasGroupPermission";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	hasUserPermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "hasUserPermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setGroupPermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setGroupPermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setOrgGroupPermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setOrgGroupPermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setRolePermission: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setRolePermission";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setUserPermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setUserPermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetRolePermission: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetRolePermission";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetRolePermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetRolePermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetUserPermissions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetUserPermissions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Phone = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Phone" + Liferay.Service.classNameSuffix,

	addPhone: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addPhone";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deletePhone: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deletePhone";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getPhone: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getPhone";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getPhones: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getPhones";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updatePhone: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePhone";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Portal = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Portal" + Liferay.Service.classNameSuffix,

	test: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "test";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.PluginSetting = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "PluginSetting" + Liferay.Service.classNameSuffix,

	updatePluginSetting: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePluginSetting";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Portlet = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Portlet" + Liferay.Service.classNameSuffix,

	updatePortlet: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePortlet";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Region = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Region" + Liferay.Service.classNameSuffix,

	getRegions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getRegions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getRegion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getRegion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Resource = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Resource" + Liferay.Service.classNameSuffix,

	getResource: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getResource";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Role = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Role" + Liferay.Service.classNameSuffix,

	addRole: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addRole";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteRole: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteRole";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getGroupRole: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getGroupRole";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getRole: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getRole";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserGroupRoles: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserGroupRoles";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserRelatedRoles: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserRelatedRoles";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserRoles: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserRoles";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateRole: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateRole";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.User = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "User" + Liferay.Service.classNameSuffix,

	addGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addRoleUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addRoleUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addUserGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addUserGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteRoleUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteRoleUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getRoleUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getRoleUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserByEmailAddress: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserByEmailAddress";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserById: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserById";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserByScreenName: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserByScreenName";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	hasGroupUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "hasGroupUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	hasRoleUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "hasRoleUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setRoleUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setRoleUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	setUserGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "setUserGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetRoleUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetRoleUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetUserGroupUsers: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetUserGroupUsers";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateActive: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateActive";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateAgreedToTermsOfUse: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateAgreedToTermsOfUse";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updatePassword: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePassword";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updatePortrait: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePortrait";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateUser: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateUser";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.UserGroup = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "UserGroup" + Liferay.Service.classNameSuffix,

	addGroupUserGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addGroupUserGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addUserGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addUserGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteUserGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteUserGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserUserGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserUserGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsetGroupUserGroups: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsetGroupUserGroups";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateUserGroup: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateUserGroup";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.UserGroupRole = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "UserGroupRole" + Liferay.Service.classNameSuffix,

	addUserGroupRoles: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addUserGroupRoles";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteUserGroupRoles: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteUserGroupRoles";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Portal.Website = {
	serviceClassName: Liferay.Service.Portal.servicePackage + "Website" + Liferay.Service.classNameSuffix,

	addWebsite: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addWebsite";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteWebsite: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteWebsite";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getWebsite: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getWebsite";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getWebsites: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getWebsites";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateWebsite: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateWebsite";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Blogs = {
	servicePackage: "com.liferay.portlet.blogs.service.http."
};

Liferay.Service.Blogs.BlogsCategory = {
	serviceClassName: Liferay.Service.Blogs.servicePackage + "BlogsCategory" + Liferay.Service.classNameSuffix,

	addCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Blogs.BlogsEntry = {
	serviceClassName: Liferay.Service.Blogs.servicePackage + "BlogsEntry" + Liferay.Service.classNameSuffix,

	addEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Bookmarks = {
	servicePackage: "com.liferay.portlet.bookmarks.service.http."
};

Liferay.Service.Bookmarks.BookmarksEntry = {
	serviceClassName: Liferay.Service.Bookmarks.servicePackage + "BookmarksEntry" + Liferay.Service.classNameSuffix,

	addEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	openEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "openEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Bookmarks.BookmarksFolder = {
	serviceClassName: Liferay.Service.Bookmarks.servicePackage + "BookmarksFolder" + Liferay.Service.classNameSuffix,

	addFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Cal = {
	servicePackage: "com.liferay.portlet.calendar.service.http."
};

Liferay.Service.Cal.CalEvent = {
	serviceClassName: Liferay.Service.Cal.servicePackage + "CalEvent" + Liferay.Service.classNameSuffix,

	addEvent: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEvent";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEvent: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEvent";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEvent: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEvent";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEvent: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEvent";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.DL = {
	servicePackage: "com.liferay.portlet.documentlibrary.service.http."
};

Liferay.Service.DL.DLFileEntry = {
	serviceClassName: Liferay.Service.DL.servicePackage + "DLFileEntry" + Liferay.Service.classNameSuffix,

	addFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	lockFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "lockFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unlockFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unlockFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFileEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFileEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.DL.DLFileShortcut = {
	serviceClassName: Liferay.Service.DL.servicePackage + "DLFileShortcut" + Liferay.Service.classNameSuffix,

	addFileShortcut: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFileShortcut";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFileShortcut: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFileShortcut";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFileShortcut: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFileShortcut";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFileShortcut: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFileShortcut";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.DL.DLFolder = {
	serviceClassName: Liferay.Service.DL.servicePackage + "DLFolder" + Liferay.Service.classNameSuffix,

	addFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.IG = {
	servicePackage: "com.liferay.portlet.imagegallery.service.http."
};

Liferay.Service.IG.IGFolder = {
	serviceClassName: Liferay.Service.IG.servicePackage + "IGFolder" + Liferay.Service.classNameSuffix,

	addFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFolder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFolder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.IG.IGImage = {
	serviceClassName: Liferay.Service.IG.servicePackage + "IGImage" + Liferay.Service.classNameSuffix,

	deleteImage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteImage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getImage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getImage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Journal = {
	servicePackage: "com.liferay.portlet.journal.service.http."
};

Liferay.Service.Journal.JournalArticle = {
	serviceClassName: Liferay.Service.Journal.servicePackage + "JournalArticle" + Liferay.Service.classNameSuffix,

	getArticle: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getArticle";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	removeArticleLocale: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "removeArticleLocale";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Journal.JournalStructure = {
	serviceClassName: Liferay.Service.Journal.servicePackage + "JournalStructure" + Liferay.Service.classNameSuffix,

	addStructure: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addStructure";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteStructure: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteStructure";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getStructure: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getStructure";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateStructure: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateStructure";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Journal.JournalTemplate = {
	serviceClassName: Liferay.Service.Journal.servicePackage + "JournalTemplate" + Liferay.Service.classNameSuffix,

	deleteTemplate: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteTemplate";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getTemplate: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getTemplate";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.MB = {
	servicePackage: "com.liferay.portlet.messageboards.service.http."
};

Liferay.Service.MB.MBCategory = {
	serviceClassName: Liferay.Service.MB.servicePackage + "MBCategory" + Liferay.Service.classNameSuffix,

	addCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	subscribeCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "subscribeCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsubscribeCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsubscribeCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.MB.MBMessage = {
	serviceClassName: Liferay.Service.MB.servicePackage + "MBMessage" + Liferay.Service.classNameSuffix,

	addDiscussionMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addDiscussionMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	addMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteDiscussionMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteDiscussionMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getMessageDisplay: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getMessageDisplay";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	subscribeMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "subscribeMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	unsubscribeMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "unsubscribeMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateDiscussionMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateDiscussionMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateMessage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateMessage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Polls = {
	servicePackage: "com.liferay.portlet.polls.service.http."
};

Liferay.Service.Polls.PollsQuestion = {
	serviceClassName: Liferay.Service.Polls.servicePackage + "PollsQuestion" + Liferay.Service.classNameSuffix,

	addQuestion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addQuestion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteQuestion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteQuestion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getQuestion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getQuestion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateQuestion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateQuestion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Polls.PollsVote = {
	serviceClassName: Liferay.Service.Polls.servicePackage + "PollsVote" + Liferay.Service.classNameSuffix,

	addVote: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addVote";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Ratings = {
	servicePackage: "com.liferay.portlet.ratings.service.http."
};

Liferay.Service.Ratings.RatingsEntry = {
	serviceClassName: Liferay.Service.Ratings.servicePackage + "RatingsEntry" + Liferay.Service.classNameSuffix,

	updateEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Shopping = {
	servicePackage: "com.liferay.portlet.shopping.service.http."
};

Liferay.Service.Shopping.ShoppingCategory = {
	serviceClassName: Liferay.Service.Shopping.servicePackage + "ShoppingCategory" + Liferay.Service.classNameSuffix,

	addCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateCategory: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateCategory";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Shopping.ShoppingCoupon = {
	serviceClassName: Liferay.Service.Shopping.servicePackage + "ShoppingCoupon" + Liferay.Service.classNameSuffix,

	addCoupon: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addCoupon";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteCoupon: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteCoupon";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCoupon: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCoupon";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	search: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "search";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateCoupon: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateCoupon";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Shopping.ShoppingItem = {
	serviceClassName: Liferay.Service.Shopping.servicePackage + "ShoppingItem" + Liferay.Service.classNameSuffix,

	addBookItems: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addBookItems";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteItem: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteItem";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getItem: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getItem";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Shopping.ShoppingOrder = {
	serviceClassName: Liferay.Service.Shopping.servicePackage + "ShoppingOrder" + Liferay.Service.classNameSuffix,

	completeOrder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "completeOrder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteOrder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteOrder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getOrder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getOrder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	sendEmail: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "sendEmail";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateOrder: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateOrder";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.SC = {
	servicePackage: "com.liferay.portlet.softwarecatalog.service.http."
};

Liferay.Service.SC.SCLicense = {
	serviceClassName: Liferay.Service.SC.servicePackage + "SCLicense" + Liferay.Service.classNameSuffix,

	addLicense: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addLicense";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteLicense: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteLicense";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getLicense: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getLicense";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateLicense: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateLicense";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.SC.SCFrameworkVersion = {
	serviceClassName: Liferay.Service.SC.servicePackage + "SCFrameworkVersion" + Liferay.Service.classNameSuffix,

	addFrameworkVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addFrameworkVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteFrameworkVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteFrameworkVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFrameworkVersions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFrameworkVersions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getFrameworkVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getFrameworkVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateFrameworkVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateFrameworkVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.SC.SCProductEntry = {
	serviceClassName: Liferay.Service.SC.servicePackage + "SCProductEntry" + Liferay.Service.classNameSuffix,

	deleteProductEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteProductEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProductEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProductEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.SC.SCProductVersion = {
	serviceClassName: Liferay.Service.SC.servicePackage + "SCProductVersion" + Liferay.Service.classNameSuffix,

	addProductVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addProductVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteProductVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteProductVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProductVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProductVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProductVersions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProductVersions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProductVersionsCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProductVersionsCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateProductVersion: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateProductVersion";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Tags = {
	servicePackage: "com.liferay.portlet.tags.service.http."
};

Liferay.Service.Tags.TagsAsset = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsAsset" + Liferay.Service.classNameSuffix,

	deleteAsset: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteAsset";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getAsset: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getAsset";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Tags.TagsEntry = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsEntry" + Liferay.Service.classNameSuffix,

	addEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getEntries: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getEntries";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	search: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "search";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	searchAutocomplete: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "searchAutocomplete";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	searchCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "searchCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Tags.TagsProperty = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsProperty" + Liferay.Service.classNameSuffix,

	addProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProperties: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProperties";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getPropertyValues: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getPropertyValues";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Wiki = {
	servicePackage: "com.liferay.portlet.wiki.service.http."
};

Liferay.Service.Wiki.WikiNode = {
	serviceClassName: Liferay.Service.Wiki.servicePackage + "WikiNode" + Liferay.Service.classNameSuffix,

	addNode: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addNode";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteNode: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteNode";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getNode: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getNode";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateNode: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateNode";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Wiki.WikiPage = {
	serviceClassName: Liferay.Service.Wiki.servicePackage + "WikiPage" + Liferay.Service.classNameSuffix,

	addPage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addPage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deletePage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deletePage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getPage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getPage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	revertPage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "revertPage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updatePage: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updatePage";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Workflow = {
	servicePackage: "com.liferay.portlet.workflow.service.http."
};

Liferay.Service.Workflow.WorkflowComponent = {
	serviceClassName: Liferay.Service.Workflow.servicePackage + "WorkflowComponent" + Liferay.Service.classNameSuffix,

	getCurrentTasks: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCurrentTasks";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getCurrentTasksXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getCurrentTasksXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deploy: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deploy";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinition: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinition";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinitions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinitions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinitionsXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinitionsXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinitionsCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinitionsCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinitionsCountXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinitionsCountXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinitionXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinitionXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getInstances: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getInstances";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getInstancesCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getInstancesCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getInstancesCountXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getInstancesCountXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getInstancesXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getInstancesXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getTaskFormElements: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getTaskFormElements";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getTaskFormElementsXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getTaskFormElementsXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getTaskTransitions: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getTaskTransitions";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getTaskTransitionsXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getTaskTransitionsXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserTasks: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserTasks";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserTasksCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserTasksCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserTasksCountXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserTasksCountXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getUserTasksXml: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getUserTasksXml";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	signalInstance: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "signalInstance";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	signalToken: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "signalToken";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	startWorkflow: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "startWorkflow";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Workflow.WorkflowDefinition = {
	serviceClassName: Liferay.Service.Workflow.servicePackage + "WorkflowDefinition" + Liferay.Service.classNameSuffix,

	addDefinition: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addDefinition";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getDefinition: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getDefinition";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Workflow.WorkflowInstance = {
	serviceClassName: Liferay.Service.Workflow.servicePackage + "WorkflowInstance" + Liferay.Service.classNameSuffix,

	addInstance: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addInstance";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	signalInstance: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "signalInstance";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	signalToken: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "signalToken";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};