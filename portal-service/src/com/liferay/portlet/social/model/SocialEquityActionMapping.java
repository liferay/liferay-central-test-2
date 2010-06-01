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

		SocialEquityActionMapping clone = new SocialEquityActionMapping();

		clone.setActionKey(_actionKey);
		clone.setInformationLifespan(_informationLifespan);
		clone.setInformationValue(_informationValue);
		clone.setParticipationLifespan(_participationLifespan);
		clone.setParticipationValue(_participationValue);

		return clone;
	}

	public boolean equals(SocialEquityActionMapping mapping, int type) {
		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			if (_informationValue != mapping.getInformationValue()) {
				return false;
			}

			if (_informationLifespan != mapping.getInformationLifespan()) {
				return false;
			}
		}
		else {
			if (_participationValue != mapping.getParticipationValue()) {
				return false;
			}

			if (_participationLifespan != mapping.getParticipationLifespan()) {
				return false;
			}
		}

		return true;
	}

	public String getActionKey() {
		return _actionKey;
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

	public void setActionKey(String actionKey) {
		_actionKey = actionKey;
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

	private String _actionKey;
	private int _informationLifespan;
	private int _informationValue;
	private int _participationLifespan;
	private int _participationValue;

}