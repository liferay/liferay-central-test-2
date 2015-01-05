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

package com.liferay.portal.kernel.xml;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SAXReaderUtil {

	public static Attribute createAttribute(
		Element element, QName qName, String value) {

		return getSAXReader().createAttribute(element, qName, value);
	}

	public static Attribute createAttribute(
		Element element, String name, String value) {

		return getSAXReader().createAttribute(element, name, value);
	}

	public static Document createDocument() {
		return getSAXReader().createDocument();
	}

	public static Document createDocument(Element rootElement) {
		return getSAXReader().createDocument(rootElement);
	}

	public static Document createDocument(String encoding) {
		return getSAXReader().createDocument(encoding);
	}

	public static Element createElement(QName qName) {
		return getSAXReader().createElement(qName);
	}

	public static Element createElement(String name) {
		return getSAXReader().createElement(name);
	}

	public static Entity createEntity(String name, String text) {
		return getSAXReader().createEntity(name, text);
	}

	public static Namespace createNamespace(String uri) {
		return getSAXReader().createNamespace(uri);
	}

	public static Namespace createNamespace(String prefix, String uri) {
		return getSAXReader().createNamespace(prefix, uri);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, Map<String, String> data) {

		return getSAXReader().createProcessingInstruction(target, data);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, String data) {

		return getSAXReader().createProcessingInstruction(target, data);
	}

	public static QName createQName(String localName) {
		return getSAXReader().createQName(localName);
	}

	public static QName createQName(String localName, Namespace namespace) {
		return getSAXReader().createQName(localName, namespace);
	}

	public static Text createText(String text) {
		return getSAXReader().createText(text);
	}

	public static XPath createXPath(String xPathExpression) {
		return getSAXReader().createXPath(xPathExpression);
	}

	public static XPath createXPath(
		String xPathExpression, Map<String, String> namespaceContextMap) {

		return getSAXReader().createXPath(xPathExpression, namespaceContextMap);
	}

	public static XPath createXPath(
		String xPathExpression, String prefix, String namespace) {

		return getSAXReader().createXPath(xPathExpression, prefix, namespace);
	}

	public static SAXReader getSAXReader() {
		PortalRuntimePermission.checkGetBeanProperty(SAXReaderUtil.class);

		return _saxReader;
	}

	public static Document read(File file) throws DocumentException {
		return getSAXReader().read(file);
	}

	public static Document read(File file, boolean validate)
		throws DocumentException {

		return getSAXReader().read(file, validate);
	}

	public static Document read(InputStream is) throws DocumentException {
		return getSAXReader().read(is);
	}

	public static Document read(InputStream is, boolean validate)
		throws DocumentException {

		return getSAXReader().read(is, validate);
	}

	public static Document read(Reader reader) throws DocumentException {
		return getSAXReader().read(reader);
	}

	public static Document read(Reader reader, boolean validate)
		throws DocumentException {

		return getSAXReader().read(reader, validate);
	}

	public static Document read(String xml) throws DocumentException {
		return getSAXReader().read(xml);
	}

	public static Document read(String xml, boolean validate)
		throws DocumentException {

		return getSAXReader().read(xml, validate);
	}

	public static Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSAXReader().read(xml, xmlSchema);
	}

	public static Document read(URL url) throws DocumentException {
		return getSAXReader().read(url);
	}

	public static Document read(URL url, boolean validate)
		throws DocumentException {

		return getSAXReader().read(url, validate);
	}

	public static Document readTrusted(File file) throws DocumentException {
		return getSAXReader().readTrusted(file);
	}

	public static Document readTrusted(File file, boolean validate)
		throws DocumentException {

		return getSAXReader().readTrusted(file, validate);
	}

	public static Document readTrusted(InputStream is)
		throws DocumentException {

		return getSAXReader().readTrusted(is);
	}

	public static Document readTrusted(InputStream is, boolean validate)
		throws DocumentException {

		return getSAXReader().readTrusted(is, validate);
	}

	public static Document readTrusted(Reader reader) throws DocumentException {
		return getSAXReader().readTrusted(reader);
	}

	public static Document readTrusted(Reader reader, boolean validate)
		throws DocumentException {

		return getSAXReader().readTrusted(reader, validate);
	}

	public static Document readTrusted(String xml) throws DocumentException {
		return getSAXReader().readTrusted(xml);
	}

	public static Document readTrusted(String xml, boolean validate)
		throws DocumentException {

		return getSAXReader().readTrusted(xml, validate);
	}

	public static Document readTrusted(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSAXReader().readTrusted(xml, xmlSchema);
	}

	public static Document readTrusted(URL url) throws DocumentException {
		return getSAXReader().readTrusted(url);
	}

	public static Document readTrusted(URL url, boolean validate)
		throws DocumentException {

		return getSAXReader().readTrusted(url, validate);
	}

	public static Document readTrustedURL(String url)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readTrustedURL(url);
	}

	public static Document readTrustedURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readTrustedURL(url, validate);
	}

	public static Document readUntrusted(File file) throws DocumentException {
		return getSAXReader().readUntrusted(file);
	}

	public static Document readUntrusted(File file, boolean validate)
		throws DocumentException {

		return getSAXReader().readUntrusted(file, validate);
	}

	public static Document readUntrusted(InputStream is)
		throws DocumentException {

		return getSAXReader().readUntrusted(is);
	}

	public static Document readUntrusted(InputStream is, boolean validate)
		throws DocumentException {

		return getSAXReader().readUntrusted(is, validate);
	}

	public static Document readUntrusted(Reader reader)
		throws DocumentException {

		return getSAXReader().readUntrusted(reader);
	}

	public static Document readUntrusted(Reader reader, boolean validate)
		throws DocumentException {

		return getSAXReader().readUntrusted(reader, validate);
	}

	public static Document readUntrusted(String xml) throws DocumentException {
		return getSAXReader().readUntrusted(xml);
	}

	public static Document readUntrusted(String xml, boolean validate)
		throws DocumentException {

		return getSAXReader().readUntrusted(xml, validate);
	}

	public static Document readUntrusted(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSAXReader().readUntrusted(xml, xmlSchema);
	}

	public static Document readUntrusted(URL url) throws DocumentException {
		return getSAXReader().readUntrusted(url);
	}

	public static Document readUntrusted(URL url, boolean validate)
		throws DocumentException {

		return getSAXReader().readUntrusted(url, validate);
	}

	public static Document readUntrustedURL(String url)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readUntrustedURL(url);
	}

	public static Document readUntrustedURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readUntrustedURL(url, validate);
	}

	public static Document readURL(String url)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url);
	}

	public static Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url, validate);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, List<Node> nodes) {

		return getSAXReader().selectNodes(xPathFilterExpression, nodes);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, Node node) {

		return getSAXReader().selectNodes(xPathFilterExpression, node);
	}

	public static void sort(List<Node> nodes, String xPathExpression) {
		getSAXReader().sort(nodes, xPathExpression);
	}

	public static void sort(
		List<Node> nodes, String xPathExpression, boolean distinct) {

		getSAXReader().sort(nodes, xPathExpression, distinct);
	}

	public void setSAXReader(SAXReader saxReader) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_saxReader = saxReader;
	}

	private static SAXReader _saxReader;

}