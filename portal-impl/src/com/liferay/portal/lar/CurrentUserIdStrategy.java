package com.liferay.portal.lar;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;

import java.util.Iterator;
import java.util.List;

public class CurrentUserIdStrategy implements UserIdStrategy {

	public CurrentUserIdStrategy(User currentUser) {
		_currentUser = currentUser;
	}

	public long getUserId(String userUuid) throws SystemException {

		if (StringPool.BLANK.equals(userUuid)) {
			return _currentUser.getUserId();
		}

		List users = UserUtil.findByUuid(userUuid);

		Iterator itr = users.iterator();

		if (itr.hasNext()) {
			User user = (User)itr.next();

			return user.getUserId();
		}
		else {
			return _currentUser.getUserId();
		}
	}

	private User _currentUser;

}