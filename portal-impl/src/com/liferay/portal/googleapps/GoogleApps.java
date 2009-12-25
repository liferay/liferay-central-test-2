/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.googleapps;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Properties;

/**
 * <a href="GoogleApps.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GoogleApps {

	public GoogleApps(long companyId) {
		try {
			_companyId = companyId;

			init(true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addNickname(long userId, String nickname) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element atomEntry = _addAtomEntry(document);

		_addAtomCategory(atomEntry, "nickname");

		Element appsLogin = atomEntry.addElement("apps:login");

		appsLogin.addAttribute("userName", String.valueOf(userId));

		Element appsNickname = atomEntry.addElement("apps:nickname");

		appsNickname.addAttribute("name", nickname);

		Http.Options options = _getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(_getNicknameURL());
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	public void addSendAs(long userId, String fullName, String emailAddress)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element atomEntry = _addAtomEntry(document);

		_addAppsProperty(atomEntry, "name", fullName);
		_addAppsProperty(atomEntry, "address", emailAddress);
		_addAppsProperty(atomEntry, "makeDefault", Boolean.TRUE.toString());

		Http.Options options = _getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(
			_URL + "emailsettings/2.0/" + _domain + "/" + userId + "/sendas");
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	public void addUser(
			long userId, String password, String firstName, String lastName)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element atomEntry = _addAtomEntry(document);

		_addAtomCategory(atomEntry, "user");

		Element appsLogin = atomEntry.addElement("apps:login");

		appsLogin.addAttribute("password", password);
		appsLogin.addAttribute("userName", String.valueOf(userId));

		Element appsName = atomEntry.addElement("apps:name");

		appsName.addAttribute("familyName", lastName);
		appsName.addAttribute("givenName", firstName);

		Http.Options options = _getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(_getUserURL());
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	public void deleteNickname(String nickname) throws Exception {
		Http.Options options = _getOptions();

		options.setDelete(true);
		options.setLocation(_getNicknameURL(nickname));

		HttpUtil.URLtoString(options);
	}

	public void deleteUser(long userId) throws Exception {
		Http.Options options = _getOptions();

		options.setDelete(true);
		options.setLocation(_getUserURL(userId));

		HttpUtil.URLtoString(options);
	}

	public void init(boolean manual) throws Exception {
		if (manual || _isStale()) {
			_init();
		}
	}

	public void updatePassword(long userId, String password) throws Exception {
		String userXML = _getUserXML(userId);

		Document document = SAXReaderUtil.read(new UnsyncStringReader(userXML));

		Element atomEntry = document.getRootElement();

		Element appsLogin = atomEntry.element(_getAppsQName("login"));

		appsLogin.addAttribute("password", password);

		Http.Options options = _getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(_getUserURL(userId));
		options.setPut(true);

		HttpUtil.URLtoString(options);
	}

	private Element _addAppsProperty(
		Element parentElement, String name, String value) {

		Element element = parentElement.addElement("apps:property");

		element.addAttribute("name", name);
		element.addAttribute("value", value);

		return element;
	}

	private Element _addAtomCategory(Element parentElement, String type) {
		Element element = parentElement.addElement("atom:category");

		element.addAttribute("scheme", "http://schemas.google.com/g/2005#kind");
		element.addAttribute(
			"term", "http://schemas.google.com/apps/2006#" + type);

		return element;
	}

	private Element _addAtomEntry(Document document) {
		Element element = document.addElement("atom:entry");

		element.add(_getAppsNamespace());
		element.add(_getAtomNamespace());

		return element;
	}

	private Namespace _getAppsNamespace() {
		return SAXReaderUtil.createNamespace(_APPS_PREFIX, _APPS_URI);
	}

	private QName _getAppsQName(String localName) {
		return SAXReaderUtil.createQName(localName, _getAppsNamespace());
	}

	private Namespace _getAtomNamespace() {
		return SAXReaderUtil.createNamespace(_ATOM_PREFIX, _ATOM_URI);
	}

	private String _getNicknameURL() {
		return _URL + _domain + "/nickname/2.0";
	}

	private String _getNicknameURL(String nickname) {
		return _getNicknameURL() + StringPool.SLASH + nickname;
	}

	private Http.Options _getOptions() {
		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.AUTHORIZATION,
			"GoogleLogin auth=" + _authenticationToken);

		return options;
	}

	private String _getUserURL() {
		return _URL + _domain + "/user/2.0";
	}

	private String _getUserURL(long userId) {
		return _getUserURL() + StringPool.SLASH + userId;
	}

	private String _getUserXML(long userId) throws Exception {
		Http.Options options = _getOptions();

		options.setLocation(_getUserURL(userId));

		return HttpUtil.URLtoString(options);
	}

	private void _init() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(_companyId);

		_domain = company.getMx();
		_userName = PrefsPropsUtil.getString(
			_companyId, PropsKeys.GOOGLE_APPS_USERNAME);
		_password = PrefsPropsUtil.getString(
			_companyId, PropsKeys.GOOGLE_APPS_PASSWORD);

		if (!_userName.contains(StringPool.AT)) {
			_userName += StringPool.AT + _domain;
		}

		Http.Options options = new Http.Options();

		options.addPart("Email", _userName);
		options.addPart("Passwd", _password);
		options.addPart("accountType", "HOSTED");
		options.addPart("service", "apps");
		options.setLocation("https://www.google.com/accounts/ClientLogin");
		options.setPost(true);

		String content = HttpUtil.URLtoString(options);

		Properties properties = PropertiesUtil.load(content);

		_authenticationToken = properties.getProperty("Auth");

		_initTime = System.currentTimeMillis();
	}

	private boolean _isStale() {
		if ((_initTime + (Time.HOUR * 23)) > System.currentTimeMillis()) {
			return false;
		}
		else {
			return true;
		}
	}

	private static final String _APPS_PREFIX = "apps";

	private static final String _APPS_URI =
		"http://schemas.google.com/apps/2006";

	private static final String _ATOM_PREFIX = "atom";

	private static final String _ATOM_URI = "http://www.w3.org/2005/Atom";

	private static final String _URL = "https://apps-apis.google.com/a/feeds/";

	private static Log _log = LogFactoryUtil.getLog(GoogleApps.class);

	private String _authenticationToken;
	private long _companyId;
	private String _domain;
	private long _initTime;
	private String _password;
	private String _userName;

}