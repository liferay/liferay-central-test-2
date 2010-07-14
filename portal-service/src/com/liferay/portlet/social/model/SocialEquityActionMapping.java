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
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class SocialEquityActionMapping implements Serializable {

	public SocialEquityActionMapping clone() {
		SocialEquityActionMapping equityActionMapping =
			new SocialEquityActionMapping();

		equityActionMapping.setActionId(_actionId);
		equityActionMapping.setClassName(_className);
		equityActionMapping.setInformationDailyLimit(_informationDailyLimit);
		equityActionMapping.setInformationLifespan(_informationLifespan);
		equityActionMapping.setInformationUnique(_informationUnique);
		equityActionMapping.setInformationValue(_informationValue);
		equityActionMapping.setParticipationDailyLimit(
			_participationDailyLimit);
		equityActionMapping.setParticipationLifespan(_participationLifespan);
		equityActionMapping.setParticipationUnique(_participationUnique);
		equityActionMapping.setParticipationValue(_participationValue);

		return equityActionMapping;
	}

	public boolean equals(
		SocialEquityActionMapping equityActionMapping, int type) {

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			return equals(
				equityActionMapping.getInformationDailyLimit(),
				equityActionMapping.getInformationLifespan(), type,
				equityActionMapping.isInformationUnique(),
				equityActionMapping.getInformationValue());
		}
		else {
			return equals(
				equityActionMapping.getParticipationDailyLimit(),
				equityActionMapping.getParticipationLifespan(), type,
				equityActionMapping.isParticipationUnique(),
				equityActionMapping.getParticipationValue());
		}
	}

	public boolean equals(SocialEquitySetting equitySetting) {
		return equals(
			equitySetting.getDailyLimit(), equitySetting.getLifespan(),
			equitySetting.getType(), equitySetting.isUniqueEntry(),
			equitySetting.getValue());
	}

	public String getActionId() {
		return _actionId;
	}

	public String getClassName() {
		return _className;
	}

	public int getInformationDailyLimit() {
		return _informationDailyLimit;
	}

	public int getInformationLifespan() {
		return _informationLifespan;
	}

	public int getInformationValue() {
		return _informationValue;
	}

	public int getParticipationDailyLimit() {
		return _participationDailyLimit;
	}

	public int getParticipationLifespan() {
		return _participationLifespan;
	}

	public int getParticipationValue() {
		return _participationValue;
	}

	public boolean isInformationUnique() {
		return _informationUnique;
	}

	public boolean isParticipationUnique() {
		return _participationUnique;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setInformationDailyLimit(int informationDailyLimit) {
		_informationDailyLimit = informationDailyLimit;
	}

	public void setInformationLifespan(int informationLifespan) {
		_informationLifespan = informationLifespan;
	}

	public void setInformationUnique(boolean informationUnique) {
		_informationUnique = informationUnique;
	}

	public void setInformationValue(int informationValue) {
		_informationValue = informationValue;
	}

	public void setParticipationDailyLimit(int participationDailyLimit) {
		_participationDailyLimit = participationDailyLimit;
	}

	public void setParticipationLifespan(int participationLifespan) {
		_participationLifespan = participationLifespan;
	}

	public void setParticipationUnique(boolean participationUnique) {
		_participationUnique = participationUnique;
	}

	public void setParticipationValue(int participationValue) {
		_participationValue = participationValue;
	}

	protected boolean equals(
		int dailyLimit, int lifeSpan, int type, boolean unique, int value) {

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			if ((_informationDailyLimit != dailyLimit) ||
				(_informationLifespan != lifeSpan) ||
				(_informationUnique == unique) ||
				(_informationValue != value)) {

				return false;
			}
		}
		else {
			if ((_participationDailyLimit != dailyLimit) ||
				(_participationLifespan != lifeSpan) ||
				(_participationUnique == unique) ||
				(_participationValue != value)) {

				return false;
			}
		}

		return true;
	}

	private String _actionId;
	private String _className;
	private int _informationDailyLimit;
	private int _informationLifespan;
	private boolean _informationUnique;
	private int _informationValue;
	private int _participationDailyLimit;
	private int _participationLifespan;
	private boolean _participationUnique;
	private int _participationValue;

}