/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="InlineSQLPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Auge
 */
public interface InlineSQLPermission {

	/**
	 * Implements a permission check of VIEW as an INNER JOIN.
	 *
	 * @param sql the SQL String to be manipulated
	 * @param className the className of the entity being checked
	 * @param classPKField the name of the primary key column of the entity in
	 * 	the form TableName.columnName, cannot be blank or null
	 * @param classUserIdField the name of the userId column of the entity in
	 *  the form TableName.columnName, can be blank or null
	 *
	 * @return an SQL String
	 */
	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField)
		throws SystemException;

	/**
	 * Implements a permission check of VIEW as an INNER JOIN.
	 *
	 * @param sql the SQL String to be manipulated
	 * @param className the className of the entity being checked
	 * @param classPKField the name of the primary key column of the entity in
	 * 	the form TableName.columnName, cannot be blank or null
	 * @param classUserIdField the name of the userId column of the entity in
	 *  the form TableName.columnName, can be blank or null
	 * @param bridgeJoin an optional INNER JOIN that is used to bridge from an
	 *  entity with no permissions to one that can be checked for permission.
	 *  The classPKField and classUserIdField should belong to the entity in
	 *  this JOIN which can be checked for permission.
	 *
	 * @return an SQL String
	 */
	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, String bridgeJoin)
		throws SystemException;

	/**
	 * Implements a permission check of VIEW as an INNER JOIN.
	 *
	 * @param sql the SQL String to be manipulated
	 * @param className the className of the entity being checked
	 * @param classPKField the name of the primary key column of the entity in
	 * 	the form TableName.columnName, cannot be blank or null
	 * @param classUserIdField the name of the userId column of the entity in
	 *  the form TableName.columnName, can be blank or null
	 * @param groupId the groupId to which the entities are scoped
	 *
	 * @return an SQL String
	 */
	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId)
		throws SystemException;

	/**
	 * Implements a permission check of VIEW as an INNER JOIN.
	 *
	 * @param sql the SQL String to be manipulated
	 * @param className the className of the entity being checked
	 * @param classPKField the name of the primary key column of the entity in
	 * 	the form TableName.columnName, cannot be blank or null
	 * @param classUserIdField the name of the userId column of the entity in
	 *  the form TableName.columnName, can be blank or null
	 * @param groupId the groupId to which the entities are scoped
	 * @param bridgeJoin an optional INNER JOIN that is used to bridge from an
	 *  entity with no permissions to one that can be checked for permission.
	 *  The classPKField and classUserIdField should belong to the entity in
	 *  this JOIN which can be checked for permission.
	 *
	 * @return an SQL String
	 */
	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId, String bridgeJoin)
		throws SystemException;

}