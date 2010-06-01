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

package com.liferay.portlet.social.model;

import java.io.Serializable;

/**
 * <a href="SocialEquityActionMapping.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialEquityActionMapping implements Serializable {

	public SocialEquityActionMapping clone() {
		SocialEquityActionMapping equityActionMapping =
			new SocialEquityActionMapping();

		equityActionMapping.setActionId(_actionId);
		equityActionMapping.setClassName(_className);
		equityActionMapping.setInformationLifespan(_informationLifespan);
		equityActionMapping.setInformationValue(_informationValue);
		equityActionMapping.setParticipationLifespan(_participationLifespan);
		equityActionMapping.setParticipationValue(_participationValue);

		return equityActionMapping;
	}

	public boolean equals(
		SocialEquityActionMapping equityActionMapping, int type) {

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			if ((_informationLifespan !=
					equityActionMapping.getInformationLifespan()) ||
				(_informationValue !=
					equityActionMapping.getInformationValue())) {

				return false;
			}
		}
		else {
			if ((_participationLifespan !=
					equityActionMapping.getParticipationLifespan()) ||
				(_participationValue !=
					equityActionMapping.getParticipationValue())) {

				return false;
			}
		}

		return true;
	}

	public String getActionId() {
		return _actionId;
	}

	public String getClassName() {
		return _className;
	}

	public int getInformationLifespan() {
		return _informationLifespan;
	}

	public int getInformationValue() {
		return _informationValue;
	}

	public int getParticipationLifespan() {
		return _participationLifespan;
	}

	public int getParticipationValue() {
		return _participationValue;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setInformationLifespan(int informationLifespan) {
		_informationLifespan = informationLifespan;
	}

	public void setInformationValue(int informationValue) {
		_informationValue = informationValue;
	}

	public void setParticipationLifespan(int participationLifespan) {
		_participationLifespan = participationLifespan;
	}

	public void setParticipationValue(int participationValue) {
		_participationValue = participationValue;
	}

	private String _actionId;
	private String _className;
	private int _informationLifespan;
	private int _informationValue;
	private int _participationLifespan;
	private int _participationValue;

}