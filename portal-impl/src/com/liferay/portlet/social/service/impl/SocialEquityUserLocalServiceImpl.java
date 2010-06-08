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

import java.util.List;

import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Projections;

import com.liferay.portal.dao.orm.hibernate.CriterionImpl;
import com.liferay.portal.dao.orm.hibernate.ProjectionListImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.impl.SocialEquityUserImpl;
import com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl;

/**
 * <a href="SocialEquityUserLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityUserLocalServiceImpl
	extends SocialEquityUserLocalServiceBaseImpl {
	
	public SocialEquityUser getSocialEquityUserAggregate(long userId)
		throws SystemException {
		
		SocialEquityUser socialEquityUser = new SocialEquityUserImpl();
		
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(
			SocialEquityUser.class);
		
		ProjectionList projections = ProjectionFactoryUtil.projectionList();
		
		projections.add(ProjectionFactoryUtil.sum("contributionK"));
		projections.add(ProjectionFactoryUtil.sum("contributionB"));
		projections.add(ProjectionFactoryUtil.sum("participationK"));
		projections.add(ProjectionFactoryUtil.sum("participationB"));
		
		query.setProjection(projections);
		
		query.add(RestrictionsFactoryUtil.eq("userId", userId));
		
		List<?> result = dynamicQuery(query);
		
		Object[] values = (Object[])result.get(0);
			
		if (values[0] != null)
			socialEquityUser.setContributionK((Double)values[0]);
		
		if (values[1] != null)
			socialEquityUser.setContributionB((Double)values[1]);
		
		if (values[2] != null)
			socialEquityUser.setParticipationK((Double)values[2]);
		
		if (values[3] != null)
			socialEquityUser.setParticipationB((Double)values[3]);
				
		return socialEquityUser;
	}
}