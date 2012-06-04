/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.security.pacl;

/**
 * @author Raymond Augé
 */
public interface PACLConstants {

	public static final String FILE_PERMISSION_ACTION_DELETE = "delete";

	public static final String FILE_PERMISSION_ACTION_EXECUTE = "execute";

	public static final String FILE_PERMISSION_ACTION_READ = "read";

	public static final String FILE_PERMISSION_ACTION_WRITE = "write";

	public static final String PORTAL_HOOK_PERMISSION_CUSTOM_JSP_DIR =
		"customJspDir";

	public static final String PORTAL_HOOK_PERMISSION_INDEXER = "hasIndexer";

	public static final String
		PORTAL_HOOK_PERMISSION_LANGUAGE_PROPERTIES_LOCALE =
			"languagePropertiesLocale";

	public static final String PORTAL_HOOK_PERMISSION_PORTAL_PROPERTIES_KEY =
		"hasPortalPropertiesKey";

	public static final String PORTAL_HOOK_PERMISSION_SERVICE = "service";

	public static final String PORTAL_HOOK_PERMISSION_SERVLET_FILTERS =
		"servletFilters";

	public static final String PORTAL_HOOK_PERMISSION_STRUTS_ACTION_PATH =
		"strutsActionPath";

	public static final String PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY =
		"setBeanProperty";

	public static final String PORTAL_SERVICE_PERMISSION_DYNAMIC_QUERY =
		"dynamicQuery";

	public static final String PORTAL_SERVICE_PERMISSION_SERVICE = "service";

	public static final String RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE =
		"accessClassInPackage";

	public static final String RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS =
		"accessDeclaredMembers";

	public static final String RUNTIME_PERMISSION_EXIT_VM = "exitVM";

	public static final String RUNTIME_PERMISSION_GET_CLASSLOADER =
		"getClassLoader";

	public static final String RUNTIME_PERMISSION_SET_SECURITY_MANAGER =
		"setSecurityManager";

	public static final String SOCKET_PERMISSION_ACCEPT = "accept";

	public static final String SOCKET_PERMISSION_CONNECT = "connect";

	public static final String SOCKET_PERMISSION_LISTEN = "listen";

}