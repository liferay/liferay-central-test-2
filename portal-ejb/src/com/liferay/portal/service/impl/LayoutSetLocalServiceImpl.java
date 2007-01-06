/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.service.persistence.LayoutSetFinder;
import com.liferay.portal.service.persistence.LayoutSetUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="LayoutSetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetLocalServiceImpl implements LayoutSetLocalService {

	public LayoutSet addLayoutSet(String ownerId, String companyId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		String userId = String.valueOf(LayoutImpl.getGroupId(ownerId));
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.create(ownerId);

		layoutSet.setCompanyId(companyId);
		layoutSet.setGroupId(groupId);
		layoutSet.setUserId(userId);
		layoutSet.setPrivateLayout(privateLayout);
		layoutSet.setThemeId(ThemeImpl.getDefaultThemeId());
		layoutSet.setColorSchemeId(ColorSchemeImpl.getDefaultColorSchemeId());

		LayoutSetUtil.update(layoutSet);

		return layoutSet;
	}

	public void deleteLayoutSet(String ownerId)
		throws PortalException, SystemException {

		// Layout set

		LayoutSetUtil.remove(ownerId);

		// Layouts

		Iterator itr = LayoutUtil.findByO_P(
			ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			try {
				LayoutLocalServiceUtil.deleteLayout(layout, false);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}
	}

	public LayoutSet getLayoutSet(String ownerId)
		throws PortalException, SystemException {

		return LayoutSetUtil.findByPrimaryKey(ownerId);
	}

    public LayoutSet getLayoutSet(String companyId, String virtualHost)
        throws PortalException, SystemException {

		return LayoutSetFinder.findByC_V(companyId, virtualHost);
    }

	public Map renewOwnerIds()
		throws PortalException, SystemException {

		// Fix LayoutSet

		List layoutSets = LayoutSetUtil.findAll();

		LayoutSetUtil.removeAll();

		Map ownerIdMap = new HashMap(layoutSets.size());

		Iterator itr = layoutSets.iterator();

		while (itr.hasNext()) {
			LayoutSet oldLayoutSet = (LayoutSet)itr.next();

			String oldOwnerId = oldLayoutSet.getOwnerId();
			String ownerId;

			if (LayoutImpl.isPrivateLayout(oldOwnerId)) {
				ownerId = LayoutImpl.PRIVATE + oldLayoutSet.getGroupId();
			}
			else {
				ownerId = LayoutImpl.PUBLIC + oldLayoutSet.getGroupId();
			}

			ownerIdMap.put(oldOwnerId, ownerId);

			LayoutSet layoutSet = LayoutSetUtil.create(ownerId);

			layoutSet.setCompanyId(oldLayoutSet.getCompanyId());
			layoutSet.setGroupId(oldLayoutSet.getGroupId());
			layoutSet.setUserId(oldLayoutSet.getUserId());
			layoutSet.setPrivateLayout(oldLayoutSet.getPrivateLayout());
			layoutSet.setThemeId(oldLayoutSet.getThemeId());
			layoutSet.setColorSchemeId(oldLayoutSet.getColorSchemeId());
			layoutSet.setPageCount(oldLayoutSet.getPageCount());
			layoutSet.setVirtualHost(oldLayoutSet.getVirtualHost());

			LayoutSetUtil.update(layoutSet);
		}

		// Fix Layout

		itr = LayoutUtil.findAll().iterator();

		LayoutUtil.removeAll();

		while (itr.hasNext()) {
			Layout oldLayout = (Layout)itr.next();

			String ownerId = (String)ownerIdMap.get(oldLayout.getOwnerId());

			Layout layout =
				LayoutUtil.create(
					new LayoutPK(oldLayout.getLayoutId(), ownerId));

			layout.setCompanyId(oldLayout.getCompanyId());
			layout.setParentLayoutId(oldLayout.getParentLayoutId());
			layout.setName(oldLayout.getName());
			layout.setTitle(oldLayout.getTitle());
			layout.setType(oldLayout.getType());
			layout.setTypeSettings(oldLayout.getTypeSettings());
			layout.setHidden(oldLayout.getHidden());
			layout.setFriendlyURL(oldLayout.getFriendlyURL());
			layout.setThemeId(oldLayout.getThemeId());
			layout.setColorSchemeId(oldLayout.getColorSchemeId());
			layout.setPriority(oldLayout.getPriority());

			LayoutUtil.update(layout);
		}

		return ownerIdMap;
	}

	public LayoutSet updateLookAndFeel(
			String ownerId, String themeId, String colorSchemeId)
		throws PortalException, SystemException {

		LayoutSet layoutSet = LayoutSetUtil.findByPrimaryKey(ownerId);

		layoutSet.setThemeId(themeId);
		layoutSet.setColorSchemeId(colorSchemeId);

		LayoutSetUtil.update(layoutSet);

        if (PrefsPropsUtil.getBoolean(PropsUtil.THEME_SYNC_ON_GROUP)) {
			String otherOwnerId = null;

			if (layoutSet.isPrivateLayout()) {
                otherOwnerId = LayoutImpl.PUBLIC + layoutSet.getGroupId();
            }
            else {
                otherOwnerId = LayoutImpl.PRIVATE + layoutSet.getGroupId();
            }

            LayoutSet otherLayoutSet = LayoutSetUtil.findByPrimaryKey(
				otherOwnerId);

            otherLayoutSet.setThemeId(themeId);
            otherLayoutSet.setColorSchemeId(colorSchemeId);

            LayoutSetUtil.update(otherLayoutSet);
        }

		return layoutSet;
	}

	public LayoutSet updatePageCount(String ownerId)
		throws PortalException, SystemException {

		int pageCount = LayoutUtil.countByOwnerId(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.findByPrimaryKey(ownerId);

		layoutSet.setPageCount(pageCount);

		LayoutSetUtil.update(layoutSet);

		return layoutSet;
	}

    public LayoutSet updateVirtualHost(String ownerId, String virtualHost)
        throws PortalException, SystemException {

        LayoutSet layoutSet = LayoutSetUtil.findByPrimaryKey(ownerId);

        if (Validator.isNotNull(virtualHost)) {
			try {
				LayoutSet virtualHostLayoutSet = getLayoutSet(
					layoutSet.getCompanyId(), virtualHost);

				if (!layoutSet.equals(virtualHostLayoutSet)) {
					throw new LayoutSetVirtualHostException();
				}
			}
			catch (NoSuchLayoutSetException nslse) {
			}
        }

        layoutSet.setVirtualHost(virtualHost);

        LayoutSetUtil.update(layoutSet);

        return layoutSet;
    }

}