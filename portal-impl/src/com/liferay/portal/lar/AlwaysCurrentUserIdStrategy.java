package com.liferay.portal.lar;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.model.User;

public class AlwaysCurrentUserIdStrategy implements UserIdStrategy {

	public AlwaysCurrentUserIdStrategy(User currentUser) {
		_currentUser = currentUser;
	}

	public long getUserId(String userUuid) throws SystemException {
		return _currentUser.getUserId();
	}

	private User _currentUser;

}