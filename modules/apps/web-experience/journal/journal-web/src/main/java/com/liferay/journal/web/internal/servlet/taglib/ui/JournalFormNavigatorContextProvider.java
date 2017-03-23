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

package com.liferay.journal.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorContextConstants;
import com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = FormNavigatorContextConstants.FORM_NAVIGATOR_ID + "=" + FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL,
	service = FormNavigatorContextProvider.class
)
public class JournalFormNavigatorContextProvider
	implements FormNavigatorContextProvider<JournalArticle> {

	@Override
	public String getContext(JournalArticle article) {
		if (Validator.isNotNull(_getLanguageId())) {
			return "translate";
		}
		else if ((article != null) && (article.getId() > 0)) {
			return "update";
		}
		else if (_getClassNameId(article) >
					JournalArticleConstants.CLASSNAME_ID_DEFAULT) {

			return "default.values";
		}
		else {
			return "add";
		}
	}

	private double _getClassNameId(JournalArticle article) {
		return BeanParamUtil.getLong(
			article, _getThemeDisplay().getRequest(), "classNameId");
	}

	private String _getLanguageId() {
		return ParamUtil.getString(
			_getThemeDisplay().getRequest(), "toLanguageId");
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return serviceContext.getThemeDisplay();
	}

}