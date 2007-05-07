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
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.LayoutSetFinder;
import com.liferay.portal.service.persistence.LayoutSetUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.ImageUtil;
import com.liferay.util.Validator;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;

import javax.imageio.ImageIO;

/**
 * <a href="LayoutSetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutSetLocalServiceImpl extends LayoutSetLocalServiceBaseImpl {

	public LayoutSet addLayoutSet(String ownerId, long companyId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		long userId = groupId;
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		long layoutSetId = CounterLocalServiceUtil.increment();

		LayoutSet layoutSet = LayoutSetUtil.create(layoutSetId);

		layoutSet.setCompanyId(companyId);
		layoutSet.setGroupId(groupId);
		layoutSet.setUserId(userId);
		layoutSet.setPrivateLayout(privateLayout);
		layoutSet.setThemeId(ThemeImpl.getDefaultRegularThemeId());
		layoutSet.setColorSchemeId(
			ColorSchemeImpl.getDefaultRegularColorSchemeId());
		layoutSet.setWapThemeId(ThemeImpl.getDefaultWapThemeId());
		layoutSet.setWapColorSchemeId(
			ColorSchemeImpl.getDefaultWapColorSchemeId());
		layoutSet.setCss(StringPool.BLANK);

		LayoutSetUtil.update(layoutSet);

		return layoutSet;
	}

	public void deleteLayoutSet(String ownerId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.findByG_P(groupId, privateLayout);

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

		// Logo

		ImageLocalUtil.remove(layoutSet.getLogoImageId());
		ImageLocalUtil.remove(layoutSet.getPngLogoImageId());
		ImageLocalUtil.remove(layoutSet.getWbmpLogoImageId());

		// Layout set

		LayoutSetUtil.removeByG_P(groupId, privateLayout);
	}

	public LayoutSet getLayoutSet(String ownerId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		return LayoutSetUtil.findByG_P(groupId, privateLayout);
	}

	public LayoutSet getLayoutSet(long companyId, String virtualHost)
		throws PortalException, SystemException {

		return LayoutSetFinder.findByC_V(companyId, virtualHost);
	}

	public void updateLogo(String ownerId, boolean logo, File file)
		throws PortalException, SystemException {

		try {

			// Layout set

			long groupId = LayoutImpl.getGroupId(ownerId);
			boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

			LayoutSet layoutSet = LayoutSetUtil.findByG_P(
				groupId, privateLayout);

			layoutSet.setLogo(logo);

			LayoutSetUtil.update(layoutSet);

			if (logo) {

				// Logo

				ImageLocalUtil.put(
					layoutSet.getLogoImageId(), FileUtil.getBytes(file));

				BufferedImage thumbnail = ImageUtil.scale(
					ImageIO.read(file), .6);

				// PNG logo

				ByteArrayMaker bam = new ByteArrayMaker();

				ImageIO.write(thumbnail, "png", bam);

				ImageLocalUtil.put(
					layoutSet.getPngLogoImageId(), bam.toByteArray());

				// WBMP logo

				bam = new ByteArrayMaker();

				ImageUtil.encodeWBMP(thumbnail, bam);

				ImageLocalUtil.put(
					layoutSet.getWbmpLogoImageId(), bam.toByteArray());
			}
			else {
				ImageLocalUtil.remove(layoutSet.getLogoImageId());
				ImageLocalUtil.remove(layoutSet.getPngLogoImageId());
				ImageLocalUtil.remove(layoutSet.getWbmpLogoImageId());
			}
		}
		catch (InterruptedException ie) {
			throw new SystemException(ie);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public LayoutSet updateLookAndFeel(
			String ownerId, String themeId, String colorSchemeId, String css,
			boolean wapTheme)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.findByG_P(groupId, privateLayout);

		if (wapTheme) {
			layoutSet.setWapThemeId(themeId);
			layoutSet.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layoutSet.setThemeId(themeId);
			layoutSet.setColorSchemeId(colorSchemeId);
			layoutSet.setCss(css);
		}

		LayoutSetUtil.update(layoutSet);

		if (PrefsPropsUtil.getBoolean(PropsUtil.THEME_SYNC_ON_GROUP)) {
			LayoutSet otherLayoutSet = LayoutSetUtil.findByG_P(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout());

			if (wapTheme) {
				otherLayoutSet.setWapThemeId(themeId);
				otherLayoutSet.setWapColorSchemeId(colorSchemeId);
			}
			else {
				otherLayoutSet.setThemeId(themeId);
				otherLayoutSet.setColorSchemeId(colorSchemeId);
			}

			LayoutSetUtil.update(otherLayoutSet);
		}

		return layoutSet;
	}

	public LayoutSet updatePageCount(String ownerId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		int pageCount = LayoutUtil.countByOwnerId(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.findByG_P(groupId, privateLayout);

		layoutSet.setPageCount(pageCount);

		LayoutSetUtil.update(layoutSet);

		return layoutSet;
	}

	public LayoutSet updateVirtualHost(String ownerId, String virtualHost)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);
		boolean privateLayout = LayoutImpl.isPrivateLayout(ownerId);

		LayoutSet layoutSet = LayoutSetUtil.findByG_P(groupId, privateLayout);

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