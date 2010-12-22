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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.service.base.QuartzLocalServiceBaseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class QuartzLocalServiceImpl extends QuartzLocalServiceBaseImpl {

	public void checkQuartzJobDetails() throws SystemException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select JOB_NAME, JOB_GROUP, JOB_DATA from QUARTZ_JOB_DETAILS",
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			rs = ps.executeQuery();

			while (rs.next()) {
				Blob jobData = rs.getBlob("JOB_DATA");

				boolean needUpdate = convertMessageToJSONString(jobData);

				if (needUpdate) {
					rs.updateBlob("JOB_DATA", jobData);
					rs.updateRow();
				}

			}
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public void checkQuartzTables() throws SystemException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from QUARTZ_JOB_DETAILS");

			rs = ps.executeQuery();

			if (rs.next()) {
				return;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		DB db = DBFactoryUtil.getDB();

		try {
			db.runSQLTemplate("quartz-tables.sql", false);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private boolean convertMessageToJSONString(Blob jobData) throws Exception {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new BackwardCompatibleObjectInputStream(
				jobData.getBinaryStream());

			Map jobDataMap = (Map)ois.readObject();

			Object messageObject = jobDataMap.get(SchedulerEngine.MESSAGE);

			if ((messageObject == null) || (messageObject instanceof String)) {
				return false;
			}

			Message message = null;
			if (messageObject instanceof Message) {
				message = (Message) messageObject;
			}
			else {
				message = new Message();
				message.setPayload(messageObject);
			}

			String jsonMessage = JSONFactoryUtil.serialize(message);
			jobDataMap.put(SchedulerEngine.MESSAGE, jsonMessage);

			oos = new ObjectOutputStream(jobData.setBinaryStream(1));
			oos.writeObject(jobDataMap);

			return true;
		}
		finally {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
		}
	}

	private class BackwardCompatibleObjectInputStream
		extends ObjectInputStream {

		public BackwardCompatibleObjectInputStream(InputStream in)
			throws IOException {
			super(in);
		}

		protected ObjectStreamClass readClassDescriptor()
			throws IOException, ClassNotFoundException {
			ObjectStreamClass osc = super.readClassDescriptor();

			if (osc.getName().equals(Message.class.getName())) {
				return ObjectStreamClass.lookup(Message.class);
			}
			else {
				return osc;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		QuartzLocalServiceImpl.class);

}