/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.LayoutLocalService;
import com.liferay.portal.service.spring.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.service.spring.JournalContentSearchLocalServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.LocaleUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="LayoutLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutLocalServiceImpl implements LayoutLocalService {

	public Layout addLayout(
			String groupId, String userId, boolean privateLayout,
			String parentLayoutId, String name, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		String ownerId = null;

		if (privateLayout) {
			ownerId = Layout.PRIVATE + groupId;
		}
		else {
			ownerId = Layout.PUBLIC + groupId;
		}

		String layoutId = getNextLayoutId(ownerId);
		User user = UserUtil.findByPrimaryKey(userId);
		parentLayoutId = getParentLayoutId(ownerId, parentLayoutId);
		int priority = getNextPriority(ownerId, parentLayoutId);

		validate(
			layoutId, ownerId, parentLayoutId, name, type, hidden, friendlyURL);

		Layout layout = LayoutUtil.create(new LayoutPK(layoutId, ownerId));

		layout.setCompanyId(user.getActualCompanyId());
		layout.setParentLayoutId(parentLayoutId);
		layout.setName(name, null);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);

		LayoutUtil.update(layout);

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);

		return layout;
	}

	public void deleteLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		deleteLayout(layout, true);
	}

	public void deleteLayout(Layout layout, boolean updateLayoutSet)
		throws PortalException, SystemException {

		// Child layouts

		List childLayouts = LayoutUtil.findByO_P(
			layout.getOwnerId(), layout.getLayoutId());

		for (int i = 0; i < childLayouts.size(); i++) {
			Layout childLayout = (Layout)childLayouts.get(i);

			deleteLayout(childLayout, updateLayoutSet);
		}

		// Portlet preferences

		PortletPreferencesLocalServiceUtil.deleteAllByLayout(
			layout.getPrimaryKey());

		// Journal content searches

		JournalContentSearchLocalServiceUtil.deleteLayoutContentSearches(
			layout.getLayoutId(), layout.getOwnerId());

		// Layout set

		if (updateLayoutSet) {
			LayoutSetLocalServiceUtil.updatePageCount(layout.getOwnerId());
		}

		// Layout

		LayoutUtil.remove(layout.getPrimaryKey());
	}

	public void deleteLayouts(String ownerId)
		throws PortalException, SystemException {

		// Layouts

		Iterator itr = LayoutUtil.findByO_P(
			ownerId, Layout.DEFAULT_PARENT_LAYOUT_ID).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			try {
				deleteLayout(layout, false);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	public Layout getFriendlyURLLayout(String ownerId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchLayoutException();
		}

		return LayoutUtil.findByO_F(ownerId, friendlyURL);
	}

	public Layout getLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		return LayoutUtil.findByPrimaryKey(new LayoutPK(layoutId, ownerId));
	}

	public List getLayouts(String ownerId) throws SystemException {
		return LayoutUtil.findByOwnerId(ownerId);
	}

	public List getLayouts(String ownerId, String parentLayoutId)
		throws SystemException {

		return LayoutUtil.findByO_P(ownerId, parentLayoutId);
	}

	public void setLayouts(
			String ownerId, String parentLayoutId, String[] layoutIds)
		throws PortalException, SystemException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId.equals(Layout.DEFAULT_PARENT_LAYOUT_ID)) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = LayoutUtil.findByPrimaryKey(
				new LayoutPK(layoutIds[0], ownerId));

			if (!layout.getType().equals(Layout.TYPE_PORTLET)) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}

			if (layout.isHidden()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_HIDDEN);
			}
		}

		Set layoutIdsSet = new LinkedHashSet();

		for (int i = 0; i < layoutIds.length; i++) {
			layoutIdsSet.add(layoutIds[i]);
		}

		Set newLayoutIdsSet = CollectionFactory.getHashSet();

		Iterator itr = LayoutUtil.findByO_P(ownerId, parentLayoutId).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		itr = layoutIdsSet.iterator();

		while (itr.hasNext()) {
			String layoutId = (String)itr.next();

			Layout layout = LayoutUtil.findByPrimaryKey(
				new LayoutPK(layoutId, ownerId));

			layout.setPriority(priority++);

			LayoutUtil.update(layout);
		}

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String languageId, String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		parentLayoutId = getParentLayoutId(ownerId, parentLayoutId);

		validate(
			layoutId, ownerId, parentLayoutId, name, type, hidden, friendlyURL);

		validateParentLayoutId(layoutId, ownerId, parentLayoutId);

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (!parentLayoutId.equals(layout.getParentLayoutId())) {
			layout.setPriority(getNextPriority(ownerId, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);
		layout.setName(name, LocaleUtil.fromLanguageId(languageId));
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		LayoutUtil.update(layout);

		return layout;
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String typeSettings)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setTypeSettings(typeSettings);

		LayoutUtil.update(layout);

		return layout;
	}

	public Layout updateLookAndFeel(
			String layoutId, String ownerId, String themeId,
			String colorSchemeId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setThemeId(themeId);
		layout.setColorSchemeId(colorSchemeId);

		LayoutUtil.update(layout);

		return layout;
	}

	protected String getNextLayoutId(String ownerId) throws SystemException {
		int layoutId = 0;

		List layouts = LayoutUtil.findByOwnerId(ownerId);

		for (int i = 0; i < layouts.size(); i++) {
			Layout curLayout = (Layout)layouts.get(i);

			String curLayoutId = curLayout.getLayoutId();

			int x = Integer.parseInt(curLayoutId);

			if (x > layoutId) {
				layoutId = x;
			}
		}

		return String.valueOf(++layoutId);
	}

	protected int getNextPriority(String ownerId, String parentLayoutId)
		throws SystemException {

		List layouts = LayoutUtil.findByO_P(ownerId, parentLayoutId);

		if (layouts.size() == 0) {
			return 0;
		}

		Layout layout = (Layout)layouts.get(layouts.size() - 1);

		return layout.getPriority() + 1;
	}

	protected String getParentLayoutId(String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		if (!parentLayoutId.equals(Layout.DEFAULT_PARENT_LAYOUT_ID)) {

			// Ensure parent layout exists

			try {
				Layout layout = LayoutUtil.findByPrimaryKey(
					new LayoutPK(parentLayoutId, ownerId));
			}
			catch (NoSuchLayoutException nsfe) {
				parentLayoutId = Layout.DEFAULT_PARENT_LAYOUT_ID;
			}
		}

		return parentLayoutId;
	}

	protected boolean isDescendant(Layout layout, String layoutId)
		throws PortalException, SystemException {

		if (layout.getLayoutId().equals(layoutId)) {
			return true;
		}
		else {
			Iterator itr = layout.getChildren().iterator();

			while (itr.hasNext()) {
				Layout childLayout = (Layout)itr.next();

				if (isDescendant(childLayout, layoutId)) {
					return true;
				}
			}

			return false;
		}
	}

	protected void validate(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		boolean firstLayout = false;

		if (parentLayoutId.equals(Layout.DEFAULT_PARENT_LAYOUT_ID)) {
			List layouts = LayoutUtil.findByO_P(ownerId, parentLayoutId);

			if (layouts.size() == 0) {
				firstLayout = true;
			}
			else {
				String firstLayoutId = ((Layout)layouts.get(0)).getLayoutId();

				if (firstLayoutId.equals(layoutId)) {
					firstLayout = true;
				}
			}
		}

		if (firstLayout) {
			validateFirstLayout(type, hidden);
		}

		if (Validator.isNull(name) || name.endsWith(StringPool.STAR)) {
			throw new LayoutNameException();
		}

		if (!PortalUtil.isLayoutParentable(type)) {
			if (LayoutUtil.countByO_P(ownerId, layoutId) > 0) {
				throw new LayoutTypeException(
					LayoutTypeException.NOT_PARENTABLE);
			}
		}

		if (Validator.isNotNull(friendlyURL)) {
			int exceptionType =
				LayoutFriendlyURLException.validate(friendlyURL);

			if (exceptionType != -1) {
				throw new LayoutFriendlyURLException(exceptionType);
			}

			try {
				Layout layout = LayoutUtil.findByO_F(ownerId, friendlyURL);

				if (!layout.getLayoutId().equals(layoutId)) {
					throw new LayoutFriendlyURLException(
						LayoutFriendlyURLException.DUPLICATE);
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}
	}

	protected void validateFirstLayout(String type, boolean hidden)
		throws PortalException {

		if (!type.equals(Layout.TYPE_PORTLET)) {
			throw new LayoutTypeException(LayoutTypeException.FIRST_LAYOUT);
		}

		if (hidden) {
			throw new LayoutHiddenException();
		}
	}

	protected void validateParentLayoutId(
			String layoutId, String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (!parentLayoutId.equals(layout.getParentLayoutId())) {

			// Layouts can always be moved to the root level

			if (parentLayoutId.equals(Layout.DEFAULT_PARENT_LAYOUT_ID)) {
				return;
			}

			// Layout cannot become a child of a layout that is not parentable

			Layout parentLayout = LayoutUtil.findByPrimaryKey(new LayoutPK(
				parentLayoutId, layout.getOwnerId()));

			if (!PortalUtil.isLayoutParentable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_PARENTABLE);
			}

			// Layout cannot become descendant of itself

			if (isDescendant(layout, parentLayoutId)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.SELF_DESCENDANT);
			}

			// If layout is moved, the new first layout must be valid

			if (layout.getParentLayoutId().equals(
					Layout.DEFAULT_PARENT_LAYOUT_ID)) {

				List layouts = LayoutUtil.findByO_P(
					ownerId, Layout.DEFAULT_PARENT_LAYOUT_ID);

				// You can only reach this point if there are more than two
				// layouts at the root level because of the descendant check

				String firstLayoutId = ((Layout)layouts.get(0)).getLayoutId();

				if (firstLayoutId.equals(layoutId)) {
					Layout secondLayout = (Layout)layouts.get(1);

					try {
						validateFirstLayout(
							secondLayout.getType(), secondLayout.getHidden());
					}
					catch (LayoutHiddenException lhe) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN);
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

}