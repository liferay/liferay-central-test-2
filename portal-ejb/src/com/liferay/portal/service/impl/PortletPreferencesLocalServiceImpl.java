/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import java.util.List;
import java.util.Map;

/**
 * <a href="PortletPreferencesLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesLocalServiceImpl
	extends PortletPreferencesLocalServiceBaseImpl {

	public void deletePortletPreferences(String ownerId)
		throws SystemException {

		PortletPreferencesUtil.removeByOwnerId(ownerId);

		PortletPreferencesLocalUtil.clearPreferencesPool(ownerId);
	}

	public void deletePortletPreferences(String ownerId, String layoutId)
		throws SystemException {

		PortletPreferencesUtil.removeByO_L(layoutId, ownerId);

		PortletPreferencesLocalUtil.clearPreferencesPool(ownerId);
	}

	public void deletePortletPreferences(
			String ownerId, String layoutId, String portletId)
		throws PortalException, SystemException {

		PortletPreferencesUtil.removeByO_L_P(ownerId, layoutId, portletId);

		PortletPreferencesLocalUtil.clearPreferencesPool(ownerId);
	}

	public javax.portlet.PortletPreferences getDefaultPreferences(
			long companyId, String portletId)
		throws PortalException, SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		return PortletPreferencesSerializer.fromDefaultXML(
			portlet.getDefaultPreferences());
	}

	public List getPortletPreferences() throws SystemException {
		return PortletPreferencesUtil.findAll();
	}

	public PortletPreferences getPortletPreferences(
			String ownerId, String layoutId, String portletId)
		throws PortalException, SystemException {

		return PortletPreferencesUtil.findByO_L_P(ownerId, layoutId, portletId);
	}

	public List getPortletPreferencesByLayout(String ownerId, String layoutId)
		throws SystemException {

		return PortletPreferencesUtil.findByO_L(ownerId, layoutId);
	}

	public List getPortletPreferencesByOwnerId(String ownerId)
		throws SystemException {

		return PortletPreferencesUtil.findByOwnerId(ownerId);
	}

	public List getPortletPreferencesByPortletId(String portletId)
		throws SystemException {

		return PortletPreferencesFinder.findByPortletId(portletId);
	}

	public javax.portlet.PortletPreferences getPreferences(
			long companyId, String ownerId, String layoutId, String portletId)
		throws PortalException, SystemException {

		Map prefsPool = PortletPreferencesLocalUtil.getPreferencesPool(
			ownerId);

		String key = encodeKey(ownerId, layoutId, portletId);

		PortletPreferencesImpl prefs =
			(PortletPreferencesImpl)prefsPool.get(key);

		if (prefs == null) {
			PortletPreferences portletPreferences = null;

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			try {
				portletPreferences = PortletPreferencesUtil.findByO_L_P(
					ownerId, layoutId, portletId);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
				long portletPreferencesId = CounterLocalServiceUtil.increment();

				portletPreferences = PortletPreferencesUtil.create(
					portletPreferencesId);

				portletPreferences.setOwnerId(ownerId);
				portletPreferences.setLayoutId(layoutId);
				portletPreferences.setPortletId(portletId);

				if (portlet == null) {
					portletPreferences.setPreferences(
						PortletImpl.DEFAULT_PREFERENCES);
				}
				else {
					portletPreferences.setPreferences(
						portlet.getDefaultPreferences());
				}

				PortletPreferencesUtil.update(portletPreferences);
			}

			prefs = PortletPreferencesSerializer.fromXML(
				companyId, ownerId, layoutId, portletId,
				portletPreferences.getPreferences());

			prefsPool.put(key, prefs);
		}

		return (PortletPreferencesImpl)prefs.clone();
	}

	public PortletPreferences updatePreferences(
			String ownerId, String layoutId, String portletId,
			javax.portlet.PortletPreferences prefs)
		throws PortalException, SystemException {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = PortletPreferencesUtil.findByO_L_P(
				ownerId, layoutId, portletId);
		}
		catch (NoSuchPortletPreferencesException nsppe) {
			long portletPreferencesId = CounterLocalServiceUtil.increment();

			portletPreferences = PortletPreferencesUtil.create(
				portletPreferencesId);

			portletPreferences.setOwnerId(ownerId);
			portletPreferences.setLayoutId(layoutId);
			portletPreferences.setPortletId(portletId);
		}

		PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)prefs;

		String xml = PortletPreferencesSerializer.toXML(prefsImpl);

		portletPreferences.setPreferences(xml);

		PortletPreferencesUtil.update(portletPreferences);

		PortletPreferencesLocalUtil.clearPreferencesPool(ownerId);

		return portletPreferences;
	}

	protected String encodeKey(
		String ownerId, String layoutId, String portletId) {

		StringMaker sm = new StringMaker();

		sm.append(ownerId);
		sm.append(StringPool.POUND);
		sm.append(layoutId);
		sm.append(StringPool.POUND);
		sm.append(portletId);

		return sm.toString();
	}

}