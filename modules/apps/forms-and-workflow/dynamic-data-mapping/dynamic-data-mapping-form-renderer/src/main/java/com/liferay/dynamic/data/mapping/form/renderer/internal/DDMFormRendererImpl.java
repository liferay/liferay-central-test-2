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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = {"templatePath=/META-INF/resources/form.soy"}
)
public class DDMFormRendererImpl implements DDMFormRenderer {

	@Override
	public String render(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		try {
			return doRender(ddmForm, ddmFormLayout, ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmfre) {
			throw ddmfre;
		}
		catch (PortalException pe) {
			throw new DDMFormRenderingException(pe);
		}
	}

	@Override
	public String render(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		try {
			return doRender(
				ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
				ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmfre) {
			throw ddmfre;
		}
		catch (PortalException pe) {
			throw new DDMFormRenderingException(pe);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		String templatePath = MapUtil.getString(properties, "templatePath");

		_templateResource = getTemplateResource(templatePath);
	}

	protected void collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

	protected String doRender(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, _templateResource, false);

		populateCommonContext(
			template, ddmForm, ddmFormLayout, ddmFormRenderingContext);

		String html = render(template, getTemplateNamespace(ddmFormLayout));

		String javaScript = render(template, "ddm.form_renderer_js");

		return html.concat(javaScript);
	}

	protected String getDDMDataProviderServletURL() {
		String servletContextPath = getServletContextPath(
			_ddmDataProviderServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-data-provider/");
	}

	protected String getDDMFormEvaluatorServletURL() {
		String servletContextPath = getServletContextPath(
			_ddmFormEvaluatorServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-evaluator/");
	}

	protected Map<String, String> getLanguageStringsMap(
		ResourceBundle resourceBundle) {

		Map<String, String> stringsMap = new HashMap<>();

		stringsMap.put("next", LanguageUtil.get(resourceBundle, "next"));
		stringsMap.put(
			"previous", LanguageUtil.get(resourceBundle, "previous"));

		return stringsMap;
	}

	protected List<Object> getPages(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		Map<String, String> renderedDDMFormFieldsMap =
			getRenderedDDMFormFieldsMap(ddmForm, ddmFormRenderingContext);

		DDMFormLayoutTransformer ddmFormLayoutTransformer =
			new DDMFormLayoutTransformer(
				ddmForm, ddmFormLayout, renderedDDMFormFieldsMap,
				ddmFormRenderingContext.isShowRequiredFieldsWarning(),
				ddmFormRenderingContext.getLocale());

		return ddmFormLayoutTransformer.getPages();
	}

	protected JSONArray getReadOnlyFieldsJSONArray(DDMForm ddmForm) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (ddmFormField.isReadOnly()) {
				jsonArray.put(ddmFormField.getName());
			}
		}

		return jsonArray;
	}

	protected Map<String, String> getRenderedDDMFormFieldsMap(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		DDMFormRendererHelper ddmFormRendererHelper = new DDMFormRendererHelper(
			ddmForm, ddmFormRenderingContext);

		ddmFormRendererHelper.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);
		ddmFormRendererHelper.setDDMFormEvaluator(_ddmFormEvaluator);

		return ddmFormRendererHelper.getRenderedDDMFormFieldsMap();
	}

	protected String getRequiredFieldsWarningMessageHTML(
		ResourceBundle resourceBundle) {

		StringBundler sb = new StringBundler(3);

		sb.append("<label class=\"required-warning\">");
		sb.append(
			LanguageUtil.format(
				resourceBundle, "all-fields-marked-with-x-are-required",
				"<i class=\"icon-asterisk text-warning\"></i>", false));
		sb.append("</label>");

		return sb.toString();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		ResourceBundle portalResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, PortalClassLoaderUtil.getClassLoader());

		resourceBundles.add(portalResourceBundle);

		collectResourceBundles(getClass(), resourceBundles, locale);

		ResourceBundle[] resourceBundlesArray = resourceBundles.toArray(
			new ResourceBundle[resourceBundles.size()]);

		return new AggregateResourceBundle(resourceBundlesArray);
	}

	protected String getServletContextPath(Servlet servlet) {
		ServletConfig servletConfig = servlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return servletContext.getContextPath();
	}

	protected String getTemplateNamespace(DDMFormLayout ddmFormLayout) {
		String paginationMode = ddmFormLayout.getPaginationMode();

		if (Objects.equals(paginationMode, DDMFormLayout.SINGLE_PAGE_MODE)) {
			return "ddm.simple_form";
		}
		else if (Objects.equals(paginationMode, DDMFormLayout.TABBED_MODE)) {
			return "ddm.tabbed_form";
		}

		return "ddm.paginated_form";
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected void populateCommonContext(
			Template template, DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		String containerId = ddmFormRenderingContext.getContainerId();

		if (Validator.isNull(containerId)) {
			containerId = StringUtil.randomId();
		}

		template.put("containerId", containerId);
		template.put("dataProviderURL", getDDMDataProviderServletURL());
		template.put("definition", _ddmFormJSONSerializer.serialize(ddmForm));

		DDMFormValues ddmFormValues =
			ddmFormRenderingContext.getDDMFormValues();

		if (ddmFormValues != null) {
			removeStaleDDMFormFieldValues(
				ddmForm.getDDMFormFieldsMap(true),
				ddmFormValues.getDDMFormFieldValues());
		}

		Locale locale = ddmFormRenderingContext.getLocale();

		DDMFormEvaluationResult ddmFormEvaluationResult =
			_ddmFormEvaluator.evaluate(
				ddmForm, ddmFormRenderingContext.getDDMFormValues(), locale);

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		template.put(
			"evaluation",
			jsonSerializer.serializeDeep(ddmFormEvaluationResult));
		template.put("evaluatorURL", getDDMFormEvaluatorServletURL());

		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		template.put(
			"fieldTypes",
			_ddmFormFieldTypesJSONSerializer.serialize(ddmFormFieldTypes));
		template.put(
			"layout", _ddmFormLayoutJSONSerializer.serialize(ddmFormLayout));

		List<Object> pages = getPages(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);

		template.put("pages", pages);

		template.put(
			"portletNamespace", ddmFormRenderingContext.getPortletNamespace());
		template.put("readOnly", ddmFormRenderingContext.isReadOnly());

		JSONArray readOnlyFieldsJSONArray = getReadOnlyFieldsJSONArray(ddmForm);

		template.put("readOnlyFields", readOnlyFieldsJSONArray.toString());

		ResourceBundle resourceBundle = getResourceBundle(locale);

		template.put(
			"requiredFieldsWarningMessageHTML",
			getRequiredFieldsWarningMessageHTML(resourceBundle));
		template.put(
			"showRequiredFieldsWarning",
			ddmFormRenderingContext.isShowRequiredFieldsWarning());

		boolean showSubmitButton = ddmFormRenderingContext.isShowSubmitButton();

		if (ddmFormRenderingContext.isReadOnly()) {
			showSubmitButton = false;
		}

		template.put("showSubmitButton", showSubmitButton);

		template.put("strings", getLanguageStringsMap(resourceBundle));

		String submitLabel = GetterUtil.getString(
			ddmFormRenderingContext.getSubmitLabel(),
			LanguageUtil.get(locale, "submit"));

		template.put("submitLabel", submitLabel);

		template.put("templateNamespace", getTemplateNamespace(ddmFormLayout));

		if (ddmFormValues != null) {
			template.put(
				"values",
				_ddmFormValuesJSONSerializer.serialize(ddmFormValues));
		}
		else {
			template.put("values", JSONFactoryUtil.getNullJSON());
		}
	}

	protected void removeStaleDDMFormFieldValues(
		Map<String, DDMFormField> ddmFormFieldsMap,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Iterator<DDMFormFieldValue> iterator = ddmFormFieldValues.iterator();

		while (iterator.hasNext()) {
			DDMFormFieldValue ddmFormFieldValue = iterator.next();

			if (!ddmFormFieldsMap.containsKey(ddmFormFieldValue.getName())) {
				iterator.remove();
			}

			removeStaleDDMFormFieldValues(
				ddmFormFieldsMap,
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}
	}

	protected String render(Template template, String namespace)
		throws TemplateException {

		Writer writer = new UnsyncStringWriter();

		template.put(TemplateConstants.NAMESPACE, namespace);
		template.put(TemplateConstants.RENDER_STRICT, Boolean.FALSE);

		template.processTemplate(writer);

		return writer.toString();
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.data.provider.internal.servlet.DDMDataProviderServlet)",
		unbind = "-"
	)
	protected void setDDMDataProviderServlet(Servlet ddmDataProviderServlet) {
		_ddmDataProviderServlet = ddmDataProviderServlet;
	}

	@Reference(unbind = "-")
	protected void setDDMFormEvaluator(DDMFormEvaluator ddmFormEvaluator) {
		_ddmFormEvaluator = ddmFormEvaluator;
	}

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.evaluator.internal.servlet.DDMFormEvaluatorServlet)",
		unbind = "-"
	)
	protected void setDDMFormEvaluatorServlet(Servlet ddmFormEvaluatorServlet) {
		_ddmFormEvaluatorServlet = ddmFormEvaluatorServlet;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypesJSONSerializer(
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer) {

		_ddmFormFieldTypesJSONSerializer = ddmFormFieldTypesJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormJSONSerializer(
		DDMFormJSONSerializer ddmFormJSONSerializer) {

		_ddmFormJSONSerializer = ddmFormJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormLayoutJSONSerializer(
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer) {

		_ddmFormLayoutJSONSerializer = ddmFormLayoutJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONSerializer(
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private DDM _ddm;
	private Servlet _ddmDataProviderServlet;
	private DDMFormEvaluator _ddmFormEvaluator;
	private Servlet _ddmFormEvaluatorServlet;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private DDMFormFieldTypesJSONSerializer _ddmFormFieldTypesJSONSerializer;
	private DDMFormJSONSerializer _ddmFormJSONSerializer;
	private DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer;
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;
	private JSONFactory _jsonFactory;
	private TemplateResource _templateResource;

}