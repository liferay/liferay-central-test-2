/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Bowerman
 */
public class VerifyResourceActions extends VerifyProcess {

	protected void deleteDuplicateBitwiseValuesOnResource() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select ra1.resourceActionId, ra1.name, ra1.actionId, ");
		sb.append("ra1.bitwiseValue from ResourceAction ra1 inner join ");
		sb.append("ResourceAction ra2 on (ra1.name = ra2.name) and ");
		sb.append("(ra1.bitwiseValue = ra2.bitwiseValue) and (ra1.actionId ");
		sb.append("!= ra2.actionId)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			Map<String, Set<Long>> nameBitwiseValuesPairs = new HashMap<>();

			while (rs.next()) {
				String name = rs.getString("name");

				Set<Long> bitwiseValues = nameBitwiseValuesPairs.get(name);

				if (bitwiseValues == null) {
					bitwiseValues = new HashSet<>();

					nameBitwiseValuesPairs.put(name, bitwiseValues);
				}

				if (bitwiseValues.add(rs.getLong("bitwiseValue"))) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					sb = new StringBundler(7);

					sb.append("Deleting resource action ");
					sb.append(rs.getString("actionId"));
					sb.append(" from resource ");
					sb.append(name);
					sb.append(" because its bitwise value is the ");
					sb.append("same as another resource action on ");
					sb.append("the same resource");

					_log.info(sb.toString());
				}

				ResourceActionLocalServiceUtil.deleteResourceAction(
					rs.getLong("resourceActionId"));
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteDuplicateBitwiseValuesOnResource();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyResourceActions.class);

}