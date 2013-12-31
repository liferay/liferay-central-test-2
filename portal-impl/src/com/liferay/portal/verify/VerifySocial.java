/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.portlet.social.service.persistence.SocialRequestActionableDynamicQuery;

/**
 * @author Bryan Engler
 * @auther Sherry Yang
 */
public class VerifySocial extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ActionableDynamicQuery socialRequestActionableDynamicQuery =
			new SocialRequestActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = PortalUtil.getClassNameId(Group.class);

				dynamicQuery.add(classNameIdProperty.eq(classNameId));

				Property classPKProperty = PropertyFactoryUtil.forName(
					"classPK");

				DynamicQuery groupDynamicQuery =
					DynamicQueryFactoryUtil.forClass(Group.class);

				Projection projection = ProjectionFactoryUtil.property(
					"groupId");

				groupDynamicQuery.setProjection(projection);

				dynamicQuery.add(classPKProperty.notIn(groupDynamicQuery));
			}

			@Override
			protected void performAction(Object object) throws SystemException {
				SocialRequestLocalServiceUtil.deleteRequest(
					(SocialRequest)object);
			}

		};

		socialRequestActionableDynamicQuery.performActions();
	}

}