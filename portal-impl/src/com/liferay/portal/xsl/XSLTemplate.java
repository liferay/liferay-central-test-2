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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Writer;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Tina Tian
 */
public class XSLTemplate implements Template {

	public XSLTemplate(
		XSLTemplateResource xslTemplateResource,
		TemplateResource errorTemplateResource,
		TemplateContextHelper templateContextHelper) {

		if (xslTemplateResource == null) {
			throw new IllegalArgumentException("XSL template resource is null");
		}

		if (templateContextHelper == null) {
			throw new IllegalArgumentException(
				"Template context helper is null");
		}

		_xslTemplateResource = xslTemplateResource;
		_errorTemplateResource = errorTemplateResource;
		_templateContextHelper = templateContextHelper;

		_context = new HashMap<String, Object>();
	}

	@Override
	public void doProcessTemplate(Writer writer) throws Exception {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		String languageId = null;

		XSLURIResolver xslURIResolver =
			_xslTemplateResource.getXSLURIResolver();

		if (xslURIResolver != null) {
			languageId = xslURIResolver.getLanguageId();
		}

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		_xslErrorListener = new XSLErrorListener(locale);

		transformerFactory.setErrorListener(_xslErrorListener);

		transformerFactory.setURIResolver(xslURIResolver);

		StreamSource xmlSource = new StreamSource(
			_xslTemplateResource.getXMLReader());

		Transformer transformer = null;

		if (_errorTemplateResource == null) {
			transformer = _getTransformer(
				transformerFactory, _xslTemplateResource);

			transformer.transform(xmlSource, new StreamResult(writer));

			return;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		transformer = _getTransformer(transformerFactory, _xslTemplateResource);

		transformer.setParameter(TemplateConstants.WRITER, unsyncStringWriter);

		transformer.transform(xmlSource, new StreamResult(unsyncStringWriter));

		StringBundler sb = unsyncStringWriter.getStringBundler();

		sb.writeTo(writer);
	}

	@Override
	public Object get(String key) {
		return _context.get(key);
	}

	@Override
	public String[] getKeys() {
		Set<String> keys = _context.keySet();

		return keys.toArray(new String[keys.size()]);
	}

	@Override
	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		try {
			doProcessTemplate(writer);
		}
		catch (Exception e1) {
			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			transformerFactory.setErrorListener(_xslErrorListener);

			transformerFactory.setURIResolver(
				_xslTemplateResource.getXSLURIResolver());

			Transformer errorTransformer = _getTransformer(
				transformerFactory, _errorTemplateResource);

			errorTransformer.setParameter(TemplateConstants.WRITER, writer);
			errorTransformer.setParameter(
				"exception", _xslErrorListener.getMessageAndLocation());

			if (_errorTemplateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)_errorTemplateResource;

				errorTransformer.setParameter(
					"script", stringTemplateResource.getContent());
			}

			if (_xslErrorListener.getLocation() != null) {
				errorTransformer.setParameter(
					"column", new Integer(_xslErrorListener.getColumnNumber()));
				errorTransformer.setParameter(
					"line", new Integer(_xslErrorListener.getLineNumber()));
			}

			try {
				StreamSource xmlSource = new StreamSource(
					_xslTemplateResource.getXMLReader());

				errorTransformer.transform(xmlSource, new StreamResult(writer));
			}
			catch (Exception e2) {
				throw new TemplateException(
					"Unable to process XSL template " +
						_errorTemplateResource.getTemplateId(),
					e2);
			}
		}
	}

	@Override
	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_context.put(key, value);
	}

	private Transformer _getTransformer(
			TransformerFactory transformerFactory,
			TemplateResource templateResource)
		throws TemplateException {

		try {
			StreamSource scriptSource = new StreamSource(
				templateResource.getReader());

			Transformer transformer = AccessController.doPrivileged(
				new TransformerPrivilegedExceptionAction(
					transformerFactory, scriptSource));

			for (Map.Entry<String, Object> entry : _context.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}

			return transformer;
		}
		catch (PrivilegedActionException pae) {
			throw new TemplateException(
				"Unable to get Transformer for template " +
					templateResource.getTemplateId(),
				pae.getException());
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to get Transformer for template " +
					templateResource.getTemplateId(),
				e);
		}
	}

	private Map<String, Object> _context;
	private TemplateResource _errorTemplateResource;
	private TemplateContextHelper _templateContextHelper;
	private XSLErrorListener _xslErrorListener;
	private XSLTemplateResource _xslTemplateResource;

	private class TransformerPrivilegedExceptionAction
		implements PrivilegedExceptionAction<Transformer> {

		public TransformerPrivilegedExceptionAction(
			TransformerFactory transformerFactory, StreamSource scriptSource) {

			_transformerFactory = transformerFactory;
			_scriptSource = scriptSource;
		}

		@Override
		public Transformer run() throws Exception {
			return _transformerFactory.newTransformer(_scriptSource);
		}

		private StreamSource _scriptSource;
		private TransformerFactory _transformerFactory;

	}

}