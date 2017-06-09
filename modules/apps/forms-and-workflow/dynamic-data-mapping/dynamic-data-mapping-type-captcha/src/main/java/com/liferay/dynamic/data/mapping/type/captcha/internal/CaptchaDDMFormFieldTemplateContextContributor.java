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

package com.liferay.dynamic.data.mapping.type.captcha.internal;

import com.liferay.captcha.taglib.servlet.taglib.CaptchaTag;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=captcha",
	service = {
		CaptchaDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class CaptchaDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		String html = StringPool.BLANK;

		try {
			html = renderCaptchaTag(ddmFormField, ddmFormFieldRenderingContext);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		parameters.put("html", html);

		return parameters;
	}

	protected String renderCaptchaTag(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws Exception {

		CaptchaTag captchaTag = new CaptchaTag();

		captchaTag.setUrl(
			GetterUtil.getString(ddmFormField.getProperty("url")));

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();
		HttpServletResponse httpServletResponse =
			ddmFormFieldRenderingContext.getHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PageContext pageContext = PageContextFactoryUtil.create(
			httpServletRequest,
			new PipingServletResponse(httpServletResponse, unsyncStringWriter));

		captchaTag.setPageContext(pageContext);

		captchaTag.runTag();

		return unsyncStringWriter.toString();
	}

}