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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.SocialEquity;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="SocialEquityUserLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityUserLocalServiceImpl
	extends SocialEquityUserLocalServiceBaseImpl {

	public SocialEquity getContributionEquity(long userId)
		throws SystemException {

		ProjectionList projections = ProjectionFactoryUtil.projectionList();

		projections.add(ProjectionFactoryUtil.sum("contributionK"));
		projections.add(ProjectionFactoryUtil.sum("contributionB"));

		return _getSocialEquityAggregate(userId, projections);
	}

	public SocialEquity getParticipationEquity(long userId)
		throws SystemException {

		ProjectionList projections = ProjectionFactoryUtil.projectionList();

		projections.add(ProjectionFactoryUtil.sum("participationK"));
		projections.add(ProjectionFactoryUtil.sum("participationB"));

		return _getSocialEquityAggregate(userId, projections);
	}

	private SocialEquity _getSocialEquityAggregate(
			long userId, ProjectionList projections)
		throws SystemException {

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(
			SocialEquityUser.class);

		query.setProjection(projections);

		query.add(RestrictionsFactoryUtil.eq("userId", userId));

		List<?> result = dynamicQuery(query);

		Object[] values = (Object[])result.get(0);

		SocialEquity socialEquity = null;

		if (values[0] != null) {
			socialEquity = new SocialEquity(
				(Double)values[0], (Double)values[1]);
		} else {
			socialEquity = new SocialEquity(0, 0);
		}

		return socialEquity;
	}

}