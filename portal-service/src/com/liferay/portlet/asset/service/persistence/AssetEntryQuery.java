/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * <a href="AssetEntryQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetEntryQuery {

	public static String[] ORDER_BY_COLUMNS = new String[] {
		"title", "createDate", "modifiedDate", "publishDate", "expirationDate",
		"priority", "viewCount"
	};

	public static String checkOrderByCol(String orderByCol) {
		if (orderByCol == null) {
			return ORDER_BY_COLUMNS[2];
		}

		for (int i = 0; i < ORDER_BY_COLUMNS.length; i++) {
			if (orderByCol.equals(ORDER_BY_COLUMNS[i])) {
				return orderByCol;
			}
		}

		return ORDER_BY_COLUMNS[2];
	}

	public static String checkOrderByType(String orderByType) {
		if ((orderByType == null) || orderByType.equalsIgnoreCase("DESC")) {
			return "DESC";
		}
		else {
			return "ASC";
		}
	}

	public AssetEntryQuery() {
		Date now = new Date();

		_expirationDate = now;
		_publishDate = now;
	}

	public AssetEntryQuery(String className, SearchContainer<?> searchContainer)
		throws PortalException, SystemException {

		this();

		setClassName(className);
		_start = searchContainer.getStart();
		_end = searchContainer.getEnd();

		PortletRequest portletRequest = searchContainer.getPortletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupIds = new long[] {themeDisplay.getScopeGroupId()};

		long categoryId = ParamUtil.getLong(portletRequest, "categoryId");

		if (categoryId > 0) {
			_allCategoryIds = new long[] {categoryId};
		}

		String tagName = ParamUtil.getString(portletRequest, "tag");

		if (Validator.isNotNull(tagName)) {
			_allTagIds = AssetTagLocalServiceUtil.getTagIds(
				_groupIds[0], new String[] {tagName});
		}
	}

	public long[] getAllCategoryIds() {
		return _allCategoryIds;
	}

	public long[] getAllLeftAndRightCategoryIds() {
		return _getLeftAndRightCategoryIds(_allCategoryIds);
	}

	public long[] getAllTagIds() {
		return _allTagIds;
	}

	public long[] getAnyCategoryIds() {
		return _anyCategoryIds;
	}

	public long[] getAnyLeftAndRightCategoryIds() {
		return _getLeftAndRightCategoryIds(_anyCategoryIds);
	}

	public long[] getAnyTagIds() {
		return _anyTagIds;
	}

	public long[] getClassNameIds() {
		return _classNameIds;
	}

	public int getEnd() {
		return _end;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public long[] getNotAllCategoryIds() {
		return _notAllCategoryIds;
	}

	public long[] getNotAllLeftAndRightCategoryIds() {
		return _getLeftAndRightCategoryIds(_notAllCategoryIds);
	}

	public long[] getNotAllTagIds() {
		return _notAllTagIds;
	}

	public long[] getNotAnyCategoryIds() {
		return _notAnyCategoryIds;
	}

	public long[] getNotAnyLeftAndRightCategoryIds() {
		return _getLeftAndRightCategoryIds(_notAnyCategoryIds);
	}

	public long[] getNotAnyTagIds() {
		return _notAnyTagIds;
	}

	public String getOrderByCol1() {
		return checkOrderByCol(_orderByCol1);
	}

	public String getOrderByCol2() {
		return checkOrderByCol(_orderByCol2);
	}

	public String getOrderByType1() {
		return checkOrderByType(_orderByType1);
	}

	public String getOrderByType2() {
		return checkOrderByType(_orderByType2);
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public int getStart() {
		return _start;
	}

	public boolean isExcludeZeroViewCount() {
		return _excludeZeroViewCount;
	}

	public Boolean isVisible() {
		return _visible;
	}

	public void setAllCategoryIds(long[] allCategoryIds) {
		_allCategoryIds = allCategoryIds;
	}

	public void setAllTagIds(long[] allTagIds) {
		_allTagIds = allTagIds;
	}

	public void setAnyCategoryIds(long[] anyCategoryIds) {
		_anyCategoryIds = anyCategoryIds;
	}

	public void setAnyTagIds(long[] anyTagIds) {
		_anyTagIds = anyTagIds;
	}

	public void setClassName(String className) {
		long classNameId = PortalUtil.getClassNameId(className);

		_classNameIds = new long[] {classNameId};
	}

	public void setClassNameIds(long[] classNameIds) {
		_classNameIds = classNameIds;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setExcludeZeroViewCount(boolean excludeZeroViewCount) {
		_excludeZeroViewCount = excludeZeroViewCount;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setNotAllCategoryIds(long[] notAllCategoryIds) {
		_notAllCategoryIds = notAllCategoryIds;
	}

	public void setNotAllTagIds(long[] notAllTagIds) {
		_notAllTagIds = notAllTagIds;
	}

	public void setNotAnyCategoryIds(long[] notAnyCategoryIds) {
		_notAnyCategoryIds = notAnyCategoryIds;
	}

	public void setNotAnyTagIds(long[] notAnyTagIds) {
		_notAnyTagIds = notAnyTagIds;
	}

	public void setOrderByCol1(String orderByCol1) {
		_orderByCol1 = orderByCol1;
	}

	public void setOrderByCol2(String orderByCol2) {
		_orderByCol2 = orderByCol2;
	}

	public void setOrderByType1(String orderByType1) {
		_orderByType1 = orderByType1;
	}

	public void setOrderByType2(String orderByType2) {
		_orderByType2 = orderByType2;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setVisible(Boolean visible) {
		_visible = visible;
	}

	private long[] _getLeftAndRightCategoryIds(long[] categoryIds) {
		long[] leftRightIds = new long[categoryIds.length * 2];

		for (int i = 0; i < categoryIds.length; i++) {
			long categoryId = categoryIds[i];

			try {
				AssetCategory category =
					AssetCategoryLocalServiceUtil.getCategory(categoryId);

				leftRightIds[2 * i] = category.getLeftCategoryId();
				leftRightIds[2 * i + 1] = category.getRightCategoryId();
			}
			catch (Exception e) {
				_log.warn("Error retrieving category " + categoryId);
			}
		}

		return leftRightIds;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryQuery.class);

	private long[] _allCategoryIds = new long[0];
	private long[] _allTagIds = new long[0];
	private long[] _anyCategoryIds = new long[0];
	private long[] _anyTagIds = new long[0];
	private long[] _classNameIds = new long[0];
	private int _end = QueryUtil.ALL_POS;
	private boolean _excludeZeroViewCount;
	private Date _expirationDate;
	private long[] _groupIds = new long[0];
	private long[] _notAllCategoryIds = new long[0];
	private long[] _notAllTagIds = new long[0];
	private long[] _notAnyCategoryIds = new long[0];
	private long[] _notAnyTagIds = new long[0];
	private String _orderByCol1;
	private String _orderByCol2;
	private String _orderByType1;
	private String _orderByType2;
	private Date _publishDate;
	private int _start = QueryUtil.ALL_POS;
	private Boolean _visible = Boolean.TRUE;

}