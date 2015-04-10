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

package com.liferay.portal.soap.sample.handlers;

/**
 * @author Carlos Sierra Andrés
 */

import java.io.IOException;

import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.osgi.service.component.annotations.Component;
@Component(
	immediate = true, property = {"soap.address=/greeter"},
	service = Handler.class
)
public class SampleComponentHandler
	implements LogicalHandler<LogicalMessageContext> {

	public SampleComponentHandler()
		throws IOException, TransformerConfigurationException {

		_transformerFactory = TransformerFactory.newInstance();

		_templateUrl = SampleComponentHandler.class.getResource(
			"handlerTemplate.xsl");
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		try {
			boolean outboundMessage = (boolean)context.get(
				MessageContext.MESSAGE_OUTBOUND_PROPERTY);

			if (!outboundMessage) {
				return true;
			}

			Source streamSource = new StreamSource(_templateUrl.openStream());

			Transformer transformer = _transformerFactory.newTransformer(
				streamSource);

			LogicalMessage message = context.getMessage();

			Source payload = message.getPayload();

			DOMResult domResult = new DOMResult();

			transformer.transform(payload, domResult);

			message.setPayload(new DOMSource(domResult.getNode()));

			return true;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final URL _templateUrl;
	private final TransformerFactory _transformerFactory;

}