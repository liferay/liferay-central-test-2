/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.DirectSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.PortalService;
import com.liferay.portal.service.base.PortalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalServiceImpl extends PortalServiceBaseImpl {

	public void cleanUpClassName()
		throws PortalException, SystemException {
		classNamePersistence.removeByValue(PortalService.class.getName());
	}

	public String getAutoDeployDirectory() throws SystemException {
		return PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
	}

	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	public boolean hasClassName() throws SystemException {
		int count = classNamePersistence.countByValue(
			PortalService.class.getName());

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void test() {
		long userId = 0;

		try {
			userId = getUserId();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (_log.isInfoEnabled()) {
			_log.info("User id " + userId);
		}
	}

	public void testAddBar(String barText) throws SystemException {
		testClassName(PortalService.class.getName());

		_addBar(barText, false);
	}

	public void testAddBarPortalRollback(String barText)
		throws SystemException {
		testClassName(PortalService.class.getName());

		_addBar(barText, false);

		throw new SystemException();
	}

	public void testAddBarPortletRollback(String barText)
		throws SystemException {
		testClassName(PortalService.class.getName());

		_addBar(barText, true);
	}

	public void testClassName(String value) throws SystemException {
		long classNameId = counterLocalService.increment();

		ClassName className = classNamePersistence.create(classNameId);

		className.setValue(value);

		classNamePersistence.update(className, false);
	}

	public void testClassNameRollback(String value) throws SystemException {
		testClassName(value);

		throw new SystemException();
	}

	public void testCounterRollback() throws SystemException {
		int counterIncrement = PropsValues.COUNTER_INCREMENT;

		for (int i = 0; i < counterIncrement * 2; i++) {
			counterLocalService.increment();
		}

		throw new SystemException();
	}

	private void _addBar(String barText, boolean rollback)
		throws SystemException {
		Message message = new Message();

		message.setPayload(barText);

		message.put("ROLLBACK", rollback);

		SynchronousMessageSender synchronousMessageSender =
			(SynchronousMessageSender)PortalBeanLocatorUtil.locate(
				DirectSynchronousMessageSender.class.getName());
		try {
			synchronousMessageSender.send(_DESTINATION_NAME, message);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final String _DESTINATION_NAME = "liferay/test_transaction";

	private static Log _log = LogFactoryUtil.getLog(PortalServiceImpl.class);

}