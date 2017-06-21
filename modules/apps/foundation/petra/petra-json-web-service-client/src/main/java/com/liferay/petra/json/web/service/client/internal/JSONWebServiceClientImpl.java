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

package com.liferay.petra.json.web.service.client.internal;

import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.internal.jcifs.JCIFSNTLMSchemeFactory;

import java.io.IOException;
import java.io.InterruptedIOException;

import java.net.ProxySelector;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.nio.charset.StandardCharsets;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(factory = "JSONWebServiceClient")
public class JSONWebServiceClientImpl implements JSONWebServiceClient {

	@Activate
	public void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_setHeaders(String.valueOf(properties.get("headers")));

		setHostName(String.valueOf(properties.get("hostName")));
		setHostPort(
			Integer.parseInt(String.valueOf(properties.get("hostPort"))));
		setKeyStore((KeyStore)properties.get("keyStore"));
		setLogin(String.valueOf(properties.get("login")));
		setPassword(String.valueOf(properties.get("password")));
		setProtocol(String.valueOf(properties.get("protocol")));

		if (properties.containsKey("proxyAuthType")) {
			setProxyAuthType(String.valueOf(properties.get("proxyAuthType")));
			setProxyDomain(String.valueOf(properties.get("proxyDomain")));
			setProxyWorkstation(
				String.valueOf(properties.get("proxyWorkstation")));
		}

		if (properties.containsKey("proxyHostName")) {
			setProxyHostName(String.valueOf(properties.get("proxyHostName")));
			setProxyHostPort(
				Integer.parseInt(
					String.valueOf(properties.get("proxyHostPort"))));
			setProxyLogin(String.valueOf(properties.get("proxyLogin")));
			setProxyPassword(String.valueOf(properties.get("proxyPassword")));
		}

		afterPropertiesSet();
	}

	public void afterPropertiesSet() {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		httpClientBuilder = httpClientBuilder.useSystemProperties();

		HttpClientConnectionManager httpClientConnectionManager =
			getPoolingHttpClientConnectionManager();

		httpClientBuilder.setConnectionManager(httpClientConnectionManager);

		if ((!isNull(_login) && !isNull(_password)) ||
			(!isNull(_proxyLogin) && !isNull(_proxyPassword))) {

			CredentialsProvider credentialsProvider =
				new BasicCredentialsProvider();

			if (!isNull(_login)) {
				credentialsProvider.setCredentials(
					new AuthScope(_hostName, _hostPort),
					new UsernamePasswordCredentials(_login, _password));
			}
			else {
				if (_logger.isInfoEnabled()) {
					_logger.info("No credentials are used");
				}
			}

			if (!isNull(_proxyLogin)) {
				credentialsProvider.setCredentials(
					new AuthScope(_proxyHostName, _proxyHostPort),
					new UsernamePasswordCredentials(
						_proxyLogin, _proxyPassword));
			}

			httpClientBuilder.setDefaultCredentialsProvider(
				credentialsProvider);
			httpClientBuilder.setRetryHandler(
				new HttpRequestRetryHandlerImpl());
		}

		try {
			if (_proxySelector != null) {
				httpClientBuilder.setRoutePlanner(
					new SystemDefaultRoutePlanner(_proxySelector));
			}
			else {
				setProxyHost(httpClientBuilder);
			}

			if (!isNull(_proxyAuthType) &&
				_proxyAuthType.equalsIgnoreCase("ntlm")) {

				RegistryBuilder registerBuilder =
					RegistryBuilder.<AuthSchemeProvider>create();

				registerBuilder = registerBuilder.register(
					AuthSchemes.NTLM,
					new JCIFSNTLMSchemeFactory(
						_proxyDomain, _proxyWorkstation));

				Lookup<AuthSchemeProvider> authSchemeRegistry =
					registerBuilder.build();

				httpClientBuilder.setDefaultAuthSchemeRegistry(
					authSchemeRegistry);
			}

			_closeableHttpClient = httpClientBuilder.build();

			_idleConnectionMonitorThread = new IdleConnectionMonitorThread(
				httpClientConnectionManager);

			_idleConnectionMonitorThread.start();

			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Configured client for " + _protocol + "://" + _hostName);
			}
		}
		catch (Exception e) {
			_logger.error("Unable to configure client", e);
		}
	}

	@Override
	public void destroy() {
		try {
			_closeableHttpClient.close();
		}
		catch (IOException ioe) {
			_logger.error("Unable to close client", ioe);
		}

		_closeableHttpClient = null;

		_idleConnectionMonitorThread.shutdown();
	}

	@Override
	public String doDelete(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doDelete(
			url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doDelete(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		if (!nameValuePairs.isEmpty()) {
			String queryString = URLEncodedUtils.format(
				nameValuePairs, StandardCharsets.UTF_8);

			url += "?" + queryString;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending DELETE request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpDelete httpDelete = new HttpDelete(url);

		addHeaders(httpDelete, headers);

		return execute(httpDelete);
	}

	@Override
	public String doGet(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doGet(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doGet(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		if (!nameValuePairs.isEmpty()) {
			String queryString = URLEncodedUtils.format(
				nameValuePairs, StandardCharsets.UTF_8);

			url += "?" + queryString;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending GET request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpGet httpGet = new HttpGet(url);

		addHeaders(httpGet, headers);

		return execute(httpGet);
	}

	@Override
	public String doPost(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doPost(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPost(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending POST request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		HttpEntity httpEntity = new UrlEncodedFormEntity(
			nameValuePairs, StandardCharsets.UTF_8);

		addHeaders(httpPost, headers);

		httpPost.setEntity(httpEntity);

		return execute(httpPost);
	}

	@Override
	public String doPostAsJSON(String url, String json)
		throws JSONWebServiceTransportException {

		return doPostAsJSON(url, json, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPostAsJSON(
			String url, String json, Map<String, String> headers)
		throws JSONWebServiceTransportException {

		HttpPost httpPost = new HttpPost(url);

		addHeaders(httpPost, headers);

		StringEntity stringEntity = new StringEntity(
			json.toString(), StandardCharsets.UTF_8);

		stringEntity.setContentType("application/json");

		httpPost.setEntity(stringEntity);

		return execute(httpPost);
	}

	@Override
	public String doPut(String url, Map<String, String> parameters)
		throws JSONWebServiceTransportException {

		return doPut(url, parameters, Collections.<String, String>emptyMap());
	}

	@Override
	public String doPut(
			String url, Map<String, String> parameters,
			Map<String, String> headers)
		throws JSONWebServiceTransportException {

		if (!isNull(_contextPath)) {
			url = _contextPath + url;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Sending PUT request to " + _login + "@" + _hostName + url);

			log("HTTP parameters", parameters);

			log("HTTP headers", headers);
		}

		HttpPut httpPut = new HttpPut(url);

		List<NameValuePair> nameValuePairs = toNameValuePairs(parameters);

		HttpEntity httpEntity = new UrlEncodedFormEntity(
			nameValuePairs, StandardCharsets.UTF_8);

		addHeaders(httpPut, headers);

		httpPut.setEntity(httpEntity);

		return execute(httpPut);
	}

	public Map<String, String> getHeaders() {
		return _headers;
	}

	@Override
	public String getHostName() {
		return _hostName;
	}

	@Override
	public int getHostPort() {
		return _hostPort;
	}

	@Override
	public String getProtocol() {
		return _protocol;
	}

	public String getProxyAuthType() {
		return _proxyAuthType;
	}

	public String getProxyDomain() {
		return _proxyDomain;
	}

	public String getProxyHostName() {
		return _proxyHostName;
	}

	public int getProxyHostPort() {
		return _proxyHostPort;
	}

	public String getProxyLogin() {
		return _proxyLogin;
	}

	public String getProxyPassword() {
		return _proxyPassword;
	}

	public String getProxyWorkstation() {
		return _proxyWorkstation;
	}

	@Override
	public void resetHttpClient() {
		destroy();

		afterPropertiesSet();
	}

	public void setContextPath(String contextPath) {
		_contextPath = contextPath;
	}

	public void setHeaders(Map<String, String> headers) {
		_headers = headers;
	}

	@Override
	public void setHostName(String hostName) {
		_hostName = hostName;
	}

	@Override
	public void setHostPort(int hostPort) {
		_hostPort = hostPort;
	}

	@Override
	public void setKeyStore(KeyStore keyStore) {
		_keyStore = keyStore;
	}

	@Override
	public void setLogin(String login) {
		_login = login;
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	public void setProxyAuthType(String proxyAuthType) {
		_proxyAuthType = proxyAuthType;
	}

	public void setProxyDomain(String proxyDomain) {
		_proxyDomain = proxyDomain;
	}

	public void setProxyHostName(String proxyHostName) {
		_proxyHostName = proxyHostName;
	}

	public void setProxyHostPort(int proxyHostPort) {
		_proxyHostPort = proxyHostPort;
	}

	public void setProxyLogin(String proxyLogin) {
		_proxyLogin = proxyLogin;
	}

	public void setProxyPassword(String proxyPassword) {
		_proxyPassword = proxyPassword;
	}

	public void setProxySelector(ProxySelector proxySelector) {
		_proxySelector = proxySelector;
	}

	public void setProxyWorkstation(String proxyWorkstation) {
		_proxyWorkstation = proxyWorkstation;
	}

	protected void addHeaders(
		HttpMessage httpMessage, Map<String, String> headers) {

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpMessage.addHeader(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : _headers.entrySet()) {
			httpMessage.addHeader(entry.getKey(), entry.getValue());
		}
	}

	protected String execute(HttpRequestBase httpRequestBase)
		throws JSONWebServiceTransportException {

		HttpHost httpHost = new HttpHost(_hostName, _hostPort, _protocol);

		try {
			if (_closeableHttpClient == null) {
				afterPropertiesSet();
			}

			HttpResponse httpResponse = null;

			if (!isNull(_login) && !isNull(_password)) {
				HttpClientContext httpClientContext =
					HttpClientContext.create();

				AuthCache authCache = new BasicAuthCache();

				AuthScheme authScheme = null;

				if (!isNull(_proxyHostName)) {
					authScheme = new BasicScheme(ChallengeState.PROXY);
				}
				else {
					authScheme = new BasicScheme(ChallengeState.TARGET);
				}

				authCache.put(httpHost, authScheme);

				httpClientContext.setAttribute(
					ClientContext.AUTH_CACHE, authCache);

				httpResponse = _closeableHttpClient.execute(
					httpHost, httpRequestBase, httpClientContext);
			}
			else {
				httpResponse = _closeableHttpClient.execute(
					httpHost, httpRequestBase);
			}

			StatusLine statusLine = httpResponse.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if ((statusCode == HttpServletResponse.SC_BAD_REQUEST) ||
				(statusCode == HttpServletResponse.SC_FORBIDDEN) ||
				(statusCode == HttpServletResponse.SC_NOT_ACCEPTABLE) ||
				(statusCode == HttpServletResponse.SC_NOT_FOUND)) {

				if (httpResponse.getEntity() != null) {
					if (_logger.isDebugEnabled()) {
						_logger.debug("Server returned status " + statusCode);
					}

					return EntityUtils.toString(
						httpResponse.getEntity(), StandardCharsets.UTF_8);
				}
			}
			else if (statusCode == HttpServletResponse.SC_OK) {
				return EntityUtils.toString(
					httpResponse.getEntity(), StandardCharsets.UTF_8);
			}
			else if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
				throw new JSONWebServiceTransportException.
					AuthenticationFailure(
						"Not authorized to access JSON web service");
			}

			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Server returned status " + statusCode, statusCode);
		}
		catch (IOException ioe) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to transmit request", ioe);
		}
		finally {
			httpRequestBase.releaseConnection();
		}
	}

	protected PoolingHttpClientConnectionManager
		getPoolingHttpClientConnectionManager() {

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
			null;

		if (_keyStore != null) {
			poolingHttpClientConnectionManager =
				new PoolingHttpClientConnectionManager(
					getSocketFactoryRegistry(), null, null, null, 60000,
					TimeUnit.MILLISECONDS);
		}
		else {
			poolingHttpClientConnectionManager =
				new PoolingHttpClientConnectionManager(
					60000, TimeUnit.MILLISECONDS);
		}

		poolingHttpClientConnectionManager.setMaxTotal(20);

		return poolingHttpClientConnectionManager;
	}

	protected Registry<ConnectionSocketFactory> getSocketFactoryRegistry() {
		RegistryBuilder<ConnectionSocketFactory> registryBuilder =
			RegistryBuilder.<ConnectionSocketFactory>create();

		registryBuilder.register("http", new PlainConnectionSocketFactory());
		registryBuilder.register("https", getSSLConnectionSocketFactory());

		return registryBuilder.build();
	}

	protected SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
		SSLContextBuilder sslContextBuilder = SSLContexts.custom();

		SSLContext sslContext = null;

		try {
			sslContextBuilder.loadTrustMaterial(_keyStore);

			sslContext = sslContextBuilder.build();

			sslContext.init(
				null, new TrustManager[] {new X509TrustManagerImpl()}, null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new SSLConnectionSocketFactory(
			sslContext, new String[] {"TLSv1"}, null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}

	protected boolean isNull(String s) {
		if ((s == null) || s.equals("")) {
			return true;
		}

		return false;
	}

	protected void log(String message, Map<String, String> map) {
		if (!_logger.isDebugEnabled() || map.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder((map.size() * 4) + 2);

		sb.append(message);
		sb.append(":");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				key = "-" + key;
				value = "";
			}

			sb.append("\n");
			sb.append(key);
			sb.append("=");
			sb.append(value);
		}

		_logger.debug(sb.toString());
	}

	protected void setProxyHost(HttpClientBuilder httpClientBuilder) {
		if ((_proxyHostName == null) || _proxyHostName.equals("")) {
			return;
		}

		httpClientBuilder.setProxy(
			new HttpHost(_proxyHostName, _proxyHostPort));
		httpClientBuilder.setProxyAuthenticationStrategy(
			new ProxyAuthenticationStrategy());
	}

	protected List<NameValuePair> toNameValuePairs(
		Map<String, String> parameters) {

		List<NameValuePair> nameValuePairs = new LinkedList<>();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String key = entry.getKey();

			String value = entry.getValue();

			if (value == null) {
				key = "-" + key;

				value = "";
			}

			NameValuePair nameValuePair = new BasicNameValuePair(key, value);

			nameValuePairs.add(nameValuePair);
		}

		return nameValuePairs;
	}

	private void _setHeaders(String headersString) {
		if (headersString == null) {
			return;
		}

		headersString = headersString.trim();

		if (headersString.length() < 3) {
			return;
		}

		Map<String, String> headers = new HashMap<>();

		for (String header : headersString.split(";")) {
			String[] headerParts = header.split("=");

			if (headerParts.length != 2) {
				if (_logger.isDebugEnabled()) {
					_logger.debug("Ignoring invalid header " + header);
				}

				continue;
			}

			headers.put(headerParts[0], headerParts[1]);
		}

		setHeaders(headers);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		JSONWebServiceClientImpl.class);

	private CloseableHttpClient _closeableHttpClient;
	private String _contextPath;
	private Map<String, String> _headers = Collections.emptyMap();
	private String _hostName;
	private int _hostPort = 80;
	private IdleConnectionMonitorThread _idleConnectionMonitorThread;
	private KeyStore _keyStore;
	private String _login;
	private String _password;
	private String _protocol = "http";
	private String _proxyAuthType;
	private String _proxyDomain;
	private String _proxyHostName;
	private int _proxyHostPort;
	private String _proxyLogin;
	private String _proxyPassword;
	private ProxySelector _proxySelector;
	private String _proxyWorkstation;

	private class HttpRequestRetryHandlerImpl
		implements HttpRequestRetryHandler {

		public boolean retryRequest(
			IOException ioe, int retryCount, HttpContext httpContext) {

			if (retryCount >= 5) {
				return false;
			}

			if (ioe instanceof ConnectTimeoutException) {
				return false;
			}

			if (ioe instanceof InterruptedIOException) {
				return false;
			}

			if (ioe instanceof SocketException) {
				return true;
			}

			if (ioe instanceof SSLException) {
				return false;
			}

			if (ioe instanceof UnknownHostException) {
				return false;
			}

			HttpClientContext httpClientContext = HttpClientContext.adapt(
				httpContext);

			HttpRequest httpRequest = httpClientContext.getRequest();

			if (httpRequest instanceof HttpEntityEnclosingRequest) {
				return false;
			}

			return true;
		}

	}

	private class IdleConnectionMonitorThread extends Thread {

		public IdleConnectionMonitorThread(
			HttpClientConnectionManager httpClientConnectionManager) {

			_httpClientConnectionManager = httpClientConnectionManager;
		}

		@Override
		public void run() {
			try {
				while (!_shutdown) {
					synchronized (this) {
						wait(5000);

						_httpClientConnectionManager.closeExpiredConnections();

						_httpClientConnectionManager.closeIdleConnections(
							30, TimeUnit.SECONDS);
					}
				}
			}
			catch (InterruptedException ie) {
			}
		}

		public void shutdown() {
			_shutdown = true;

			synchronized (this) {
				notifyAll();
			}
		}

		private final HttpClientConnectionManager _httpClientConnectionManager;
		private volatile boolean _shutdown;

	}

	private class X509TrustManagerImpl implements X509TrustManager {

		public X509TrustManagerImpl() {
			try {
				X509TrustManager x509TrustManager = null;

				TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(
						TrustManagerFactory.getDefaultAlgorithm());

				trustManagerFactory.init((KeyStore)null);

				for (TrustManager trustManager :
						trustManagerFactory.getTrustManagers()) {

					if (trustManager instanceof X509TrustManager) {
						x509TrustManager = (X509TrustManager)trustManager;

						break;
					}
				}

				_x509TrustManager = x509TrustManager;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void checkClientTrusted(
				X509Certificate[] x509Certificates, String authType)
			throws CertificateException {

			if (x509Certificates.length != 1) {
				_x509TrustManager.checkClientTrusted(
					x509Certificates, authType);
			}
		}

		@Override
		public void checkServerTrusted(
				X509Certificate[] x509Certificates, String authType)
			throws CertificateException {

			if (x509Certificates.length != 1) {
				_x509TrustManager.checkServerTrusted(
					x509Certificates, authType);
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return _x509TrustManager.getAcceptedIssuers();
		}

		private final X509TrustManager _x509TrustManager;

	}

}