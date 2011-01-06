/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.scheduler.quartz;

import java.io.ByteArrayOutputStream;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.quartz.impl.jdbcjobstore.MSSQLDelegate;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-14611.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SybaseDelegate extends MSSQLDelegate {

	public SybaseDelegate(
		org.apache.commons.logging.Log log, String tablePrefix,
		String instanceId) {

		super(log, tablePrefix, instanceId);
	}

	public SybaseDelegate(
		org.apache.commons.logging.Log log, String tablePrefix,
		String instanceId, Boolean useProperties) {

		super(log, tablePrefix, instanceId, useProperties);
	}

	protected void setBytes(
			PreparedStatement ps, int index, ByteArrayOutputStream baos)
		throws SQLException {

		if (baos == null) {
			ps.setBytes(index, null);
		}
		else {
			ps.setBytes(index, baos.toByteArray());
		}
	}

}