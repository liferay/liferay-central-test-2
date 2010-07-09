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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityUserLocalServiceImpl
	extends SocialEquityUserLocalServiceBaseImpl {

	public SocialEquityValue getContributionEquity(long userId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("contributionK"));
		projectionList.add(ProjectionFactoryUtil.sum("contributionB"));

		return getSocialEquityValue(userId, projectionList);
	}

	public SocialEquityValue getParticipationEquity(long userId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("participationK"));
		projectionList.add(ProjectionFactoryUtil.sum("participationB"));

		return getSocialEquityValue(userId, projectionList);
	}

	public List<SocialEquityUser> getRankedSocialEquityUsers(
			long groupId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return socialEquityUserPersistence.findByGroupRanked(
			groupId, start, end, orderByComparator);
	}

	public int getRankedSocialEquityUsersCount(long groupId)
		throws SystemException {

		return socialEquityUserPersistence.countByGroupRanked(groupId);
	}

	protected SocialEquityValue getSocialEquityValue(
			long userId, ProjectionList projectionList)
		throws SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialEquityUser.class);

		dynamicQuery.setProjection(projectionList);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", userId));

		List<?> results = dynamicQuery(dynamicQuery);

		Object[] values = (Object[])results.get(0);

		SocialEquityValue socialEquityValue = null;

		if (values[0] != null) {
			socialEquityValue = new SocialEquityValue(
				(Double)values[0], (Double)values[1]);
		}
		else {
			socialEquityValue = new SocialEquityValue(0, 0);
		}

		return socialEquityValue;
	}

}