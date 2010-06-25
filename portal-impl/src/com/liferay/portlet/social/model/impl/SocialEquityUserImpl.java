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

package com.liferay.portlet.social.model.impl;

import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityValue;

/**
 * <a href="SocialEquityUserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialEquityUserImpl
	extends SocialEquityUserModelImpl implements SocialEquityUser {

	public SocialEquityUserImpl() {
	}

	public double getContributionEquity() {
		return new SocialEquityValue(
			getContributionK(), getContributionB()).getValue();
	}

	public double getParticipationEquity() {
		return new SocialEquityValue(
			getParticipationK(), getParticipationB()).getValue();
	}

}