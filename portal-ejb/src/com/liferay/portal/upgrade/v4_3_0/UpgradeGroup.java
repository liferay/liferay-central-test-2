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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.StringUtil;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradeGroup extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeGroupIds();
			_upgradeOwnerIds();
			_upgradeResourceTable();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeGroupIds() throws Exception {
		_log.info("Upgrade group IDs");

		_groupIdMap = GroupLocalServiceUtil.renewGroupIds();
	}

	private void _upgradeOwnerIds() throws Exception {
		_log.info("Upgrade owner IDs");

		_ownerIdMap = LayoutSetLocalServiceUtil.renewOwnerIds();

		// Fix PortletPreferences

		Iterator itr =
			PortletPreferencesLocalServiceUtil.getPortletPreferences().
				iterator();

		PortletPreferencesLocalServiceUtil.deletePortletPreferences();

		while (itr.hasNext()) {
			PortletPreferences prefs = (PortletPreferences)itr.next();

			String portletId = prefs.getPortletId();
			String layoutId = prefs.getLayoutId();
			String ownerId = prefs.getOwnerId();

			if (!layoutId.equals(PortletKeys.PREFS_LAYOUT_ID_SHARED)) {
				ownerId = (String)_ownerIdMap.get(ownerId);
			}
			else if (ownerId.startsWith(PortletKeys.PREFS_OWNER_ID_GROUP)) {
				Long groupId =
					new Long(StringUtil.split(ownerId, StringPool.PERIOD)[1]);

				ownerId = PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
					_groupIdMap.get(groupId);
			}

			PortletPreferencesPK pk =
				new PortletPreferencesPK(portletId, layoutId, ownerId);

			PortletPreferencesImpl prefsImpl =
				(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
					prefs.getPreferences());

			long groupId = Long.parseLong(prefsImpl.getValue("group-id", "-1"));

			if (groupId > 0) {
				prefsImpl.setValue(
					"group-id",
					String.valueOf(_groupIdMap.get(new Long(groupId))));
			}

			PortletPreferencesLocalServiceUtil.updatePreferences(pk, prefsImpl);
		}

		// Fix JournalContentSearch

		itr = JournalContentSearchLocalServiceUtil.getArticleContentSearches().
			iterator();

		JournalContentSearchLocalServiceUtil.deleteArticleContentSearches();

		while (itr.hasNext()) {
			JournalContentSearch articleContentSearch =
				(JournalContentSearch)itr.next();

			String portletId = articleContentSearch.getPortletId();
			String layoutId = articleContentSearch.getLayoutId();
			String ownerId =
				(String)_ownerIdMap.get(articleContentSearch.getOwnerId());
			String companyId = articleContentSearch.getCompanyId();
			long groupId = articleContentSearch.getGroupId();
			String articleId = articleContentSearch.getArticleId();

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				portletId, layoutId, ownerId, companyId, groupId, articleId);
		}
	}

	private void _upgradeResourceTable() throws Exception {
		_log.info("Upgrade Resource_ table");

		Iterator itr = ResourceLocalServiceUtil.getResources().iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			String primKey = resource.getPrimKey();

			if (ResourceImpl.SCOPE_GROUP.equals(resource.getScope())) {
				Long groupId = (Long)_groupIdMap.get(new Long(primKey));

				primKey = String.valueOf(groupId);

				ResourceLocalServiceUtil.updatePrimKey(
					resource.getResourceId(), primKey);
			}
			else if (ResourceImpl.SCOPE_INDIVIDUAL.equals(
				resource.getScope())) {

				if (primKey.startsWith(LayoutImpl.PUBLIC) ||
					primKey.startsWith(LayoutImpl.PRIVATE)) {

					// PRI.1234.1_LAYOUT_56

					String [] keyParts =
						StringUtil.split(primKey, StringPool.PERIOD);

					Long groupId = (Long)_groupIdMap.get(new Long(keyParts[1]));
					keyParts[1] = String.valueOf(groupId);

					primKey = StringUtil.merge(keyParts, StringPool.PERIOD);
				}
				else if (primKey.indexOf("groupId=") != -1 ||
					primKey.indexOf("ownerId=") != -1) {

					// {layoutId=1234, ownerId=PRI.5678}

					String [] keyParts = StringUtil.split(
						primKey.substring(1, primKey.length() - 1),
						StringPool.COMMA + StringPool.SPACE);

					for (int i = 0; i < keyParts.length; i++) {
						String [] kvp =
							StringUtil.split(keyParts[i], StringPool.EQUAL);

						if (kvp[0].equals("groupId")) {
							kvp[1] =
								String.valueOf(
									_groupIdMap.get(new Long(kvp[1])));

							keyParts[i] =
								StringUtil.merge(kvp, StringPool.EQUAL);
						}
						else if (kvp[0].equals("ownerId")) {
							kvp[1] = (String)_ownerIdMap.get(kvp[1]);

							keyParts[i] =
								StringUtil.merge(kvp, StringPool.EQUAL);
						}
					}

					primKey = StringPool.OPEN_CURLY_BRACE +
						StringUtil.merge(
							keyParts, StringPool.COMMA + StringPool.SPACE) +
						StringPool.CLOSE_CURLY_BRACE;
				}
				else {
					continue;
				}

				ResourceLocalServiceUtil.updatePrimKey(
					resource.getResourceId(), primKey);
			}
		}
	}

	private Map _groupIdMap;
	private Map _ownerIdMap;

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}