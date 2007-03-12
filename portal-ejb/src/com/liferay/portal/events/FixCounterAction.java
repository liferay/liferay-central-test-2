package com.liferay.portal.events;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;

public class FixCounterAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {

			// Fix Resource

			long lastResourceId = ResourceLocalServiceUtil.getLastResourceId();

			long counterResourceId =
				CounterLocalServiceUtil.increment(Resource.class.getName());

			if (lastResourceId > counterResourceId - 1) {
				CounterLocalServiceUtil.reset(
					Resource.class.getName(), lastResourceId);
			}

			// Fix Permission

			long lastPermissionId =
				PermissionLocalServiceUtil.getLastPermissionId();

			long counterPermissionId =
				CounterLocalServiceUtil.increment(Permission.class.getName());

			if (lastPermissionId > counterPermissionId - 1) {
				CounterLocalServiceUtil.reset(
					Permission.class.getName(), lastPermissionId);
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

}