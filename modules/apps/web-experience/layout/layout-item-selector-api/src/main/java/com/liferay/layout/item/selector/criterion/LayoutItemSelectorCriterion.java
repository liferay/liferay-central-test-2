/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.layout.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Sergio González
 */
public class LayoutItemSelectorCriterion extends BaseItemSelectorCriterion {

	public LayoutItemSelectorCriterion() {
		_showPrivatePages = true;
		_showPublicPages = true;
	}

	public boolean isCheckDisplayPage() {
		return _checkDisplayPage;
	}

	public boolean isEnableCurrentPage() {
		return _enableCurrentPage;
	}

	public boolean isShowPrivatePages() {
		return _showPrivatePages;
	}

	public boolean isShowPublicPages() {
		return _showPublicPages;
	}

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		_checkDisplayPage = checkDisplayPage;
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		_enableCurrentPage = enableCurrentPage;
	}

	public void setShowPrivatePages(boolean showPrivatePages) {
		_showPrivatePages = showPrivatePages;
	}

	public void setShowPublicPages(boolean showPublicPages) {
		_showPublicPages = showPublicPages;
	}

	private boolean _checkDisplayPage;
	private boolean _enableCurrentPage;
	private boolean _showPrivatePages;
	private boolean _showPublicPages;

}