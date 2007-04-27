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

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
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

	public void deletePortletPreferences(String layoutId, String ownerId)
		throws SystemException {

		PortletPreferencesUtil.removeByL_O(layoutId, ownerId);

		PortletPreferencesLocalUtil.clearPreferencesPool(ownerId);
	}

	public void deletePortletPreferences(PortletPreferencesPK pk)
		throws PortalException, SystemException {

		PortletPreferencesUtil.remove(pk);

		PortletPreferencesLocalUtil.clearPreferencesPool(pk.ownerId);
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

	public PortletPreferences getPortletPreferences(PortletPreferencesPK pk)
		throws PortalException, SystemException {

		return PortletPreferencesUtil.findByPrimaryKey(pk);
	}

	public List getPortletPreferencesByLayout(String layoutId, String ownerId)
		throws SystemException {

		return PortletPreferencesUtil.findByL_O(layoutId, ownerId);
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
			long companyId, PortletPreferencesPK pk)
		throws PortalException, SystemException {

		Map prefsPool = PortletPreferencesLocalUtil.getPreferencesPool(
			pk.ownerId);

		PortletPreferencesImpl prefs =
			(PortletPreferencesImpl)prefsPool.get(pk);

		if (prefs == null) {
			PortletPreferences portletPreferences = null;

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, pk.portletId);

			try {
				portletPreferences =
					PortletPreferencesUtil.findByPrimaryKey(pk);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
				portletPreferences = PortletPreferencesUtil.create(pk);

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
				companyId, pk, portletPreferences.getPreferences());

			prefsPool.put(pk, prefs);
		}

		return (PortletPreferencesImpl)prefs.clone();
	}

	public PortletPreferences updatePreferences(
			PortletPreferencesPK pk, javax.portlet.PortletPreferences prefs)
		throws PortalException, SystemException {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = PortletPreferencesUtil.findByPrimaryKey(pk);
		}
		catch (NoSuchPortletPreferencesException nsppe) {
			portletPreferences = PortletPreferencesUtil.create(pk);
		}

		PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)prefs;

		String xml = PortletPreferencesSerializer.toXML(prefsImpl);

		portletPreferences.setPreferences(xml);

		PortletPreferencesUtil.update(portletPreferences);

		PortletPreferencesLocalUtil.clearPreferencesPool(pk.ownerId);

		return portletPreferences;
	}

}