package com.liferay.portal.kernel.lar;

import com.liferay.portal.SystemException;

public interface UserIdStrategy {

	public long getUserId(String userUuid) throws SystemException;

	public static final String ALWAYS_CURRENT_USER_ID = "alwaysCurrentUserId";

	public static final String CURRENT_USER_ID = "currentUserId";

}