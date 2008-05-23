/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.sso.opensso;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.auth.AutoLoginException;
import com.liferay.util.CookieUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="OpenSSOUtil.java.html"><b><i>View Source</i></b></a>
 *
 * OpenSSO REST services:
 * http://localhost/opensso/identity/attributes
 * http://localhost/opensso/identity/istokenvalid
 * http://localhost/opensso/identity/getCookieNameForToken
 * http://localhost/opensso/identity/getCookieNamesToForward
 *
 * REST service return codes:
 * 200 OK
 * 401 AuthN Failed, AuthZ Failed, Permission Denied, Identity not present
 *     TokenExpired, Invalid Token, etc.
 * 500 GenericFailure (Internal server failure, exceptions on server)
 * 501 Unsupported Operations
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class OpenSSOUtil {

    public static Map<String, String> getAttributes(HttpServletRequest req,
        String serviceUrl) throws AutoLoginException {

        Map<String, String> nameValues = new HashMap<String, String>();

        String url = serviceUrl + _GET_ATTRIBUTES;

        try {
            String formData = "dummy";
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();

            List<String> cookieNames = getCookieForwardList(serviceUrl);
            forwardCookies(req, cookieNames, conn);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");

            conn.setDoOutput(true);
            OutputStreamWriter osw =
                new OutputStreamWriter(conn.getOutputStream());
            osw.write(formData);
            osw.flush();

            int ret = conn.getResponseCode();
            if (ret == HttpURLConnection.HTTP_OK ) {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader((InputStream)conn.getContent()));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("userdetails.attribute.name=")) {
                        String aname = line.replaceFirst(
                            "userdetails.attribute.name=", "");
                        line = reader.readLine();
                        if (line.startsWith("userdetails.attribute.value=")) {
                            String avalue = line.replaceFirst(
                                "userdetails.attribute.value=", "");
                            nameValues.put(aname, avalue);
                        } else {
                            throw new AutoLoginException(
                                "Error reading user attributes");
                        }
                    }
                }

            } else if (_log.isDebugEnabled()) {
                _log.debug("OpenSSO getAttributes returned " + ret);
            }
        } catch(MalformedURLException mfue) {
            _log.error(mfue.getMessage());
            if (_log.isDebugEnabled()) {
                _log.debug(mfue);
            }
        } catch(IOException ioe) {
            _log.error(ioe.getMessage());
            if (_log.isDebugEnabled()) {
                _log.debug(ioe);
            }
        }
        return nameValues;
    }

    /*
     * @returns subject identifier string for an authenticated user
     */
    public static String getSubjectId(HttpServletRequest req,
        String serviceUrl) {
        String cookie = getCookieForwardList(serviceUrl).get(0);
        return CookieUtil.get(req, cookie);
    }

    /*
     * Returns true if the user is authenticated.
     */
    public static boolean isAuthenticated(HttpServletRequest req,
        String serviceUrl) throws Exception {

        boolean authenticated = false;

        String url = serviceUrl + _VALIDATE_OP;
        try {
            URL urlObj = new URL(url);
            String formData = "dummy";

            HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();

            List<String> cookieNames = getCookieForwardList(serviceUrl);
            forwardCookies(req, cookieNames, conn);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");

            conn.setDoOutput(true);
            OutputStreamWriter osw =
                new OutputStreamWriter(conn.getOutputStream());
            osw.write(formData);
            osw.flush();

            int ret = conn.getResponseCode();
            if (ret == HttpURLConnection.HTTP_OK ) {
                authenticated = true;
            } else if (_log.isDebugEnabled()) {
                _log.debug("OpenSSO Auth returned " + ret);
            }
        } catch(MalformedURLException mfue) {
            _log.error(mfue.getMessage());
            throw new Exception(mfue);
        } catch(IOException ioe) {
            _log.error(ioe.getMessage());
            throw new Exception(ioe);
        }

        return authenticated;
    }

    private static void forwardCookies(HttpServletRequest req,
        List<String> cookieNames, HttpURLConnection conn) {
        StringBuffer cookies = new StringBuffer();

        for(String cName: cookieNames) {
            String cValue = CookieUtil.get(req, cName);
            cookies.append(cName)
                   .append('=')
                   .append(cValue)
                   .append(';');
        }

        if (cookies.length() > 0) {
            conn.setRequestProperty("Cookie", cookies.toString());
        }
    }
    
    /*
     * Retrieves and returns the names of cookies from the service
     */
    private static List<String> getCookieForwardList(String serviceUrl) {

        if (_cookieForwardList.isEmpty()) {

            //this block should execute only once in the lifetime of JVM

            String cookieName = null;
            try {
                String url = serviceUrl + _GET_COOKIE_NAME;
                URL urlObj = new URL(url);
                HttpURLConnection conn =
                    (HttpURLConnection)urlObj.openConnection();
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader((InputStream)conn.getContent()));

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK &&
                    _log.isDebugEnabled()) {
                    _log.debug("Error during operation " + _GET_COOKIE_NAME
                        + ", response = " + conn.getResponseCode());
                } else {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("string=")) {
                            line = line.replaceFirst("string=", "");
                            cookieName = line;
                        }
                    }
                }

                url = serviceUrl + _GET_COOKIE_LIST;
                urlObj = new URL(url);
                conn = (HttpURLConnection)urlObj.openConnection();
                reader = new BufferedReader(
                    new InputStreamReader((InputStream)conn.getContent()));

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK &&
                    _log.isDebugEnabled()) {
                    _log.debug("Error during operation " + _GET_COOKIE_LIST
                        + ", response = " + conn.getResponseCode());
                } else {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("string=")) {
                            line = line.replaceFirst("string=", "");
                            if (cookieName.equals(line)) {

                                //first in the list is the subjectId cookie name

                                _cookieForwardList.add(0, cookieName);
                            } else {
                                _cookieForwardList.add(line);
                            }
                        }
                    }
                }
            } catch(IOException ioe) {
                if (_log.isDebugEnabled()) {
                    _log.debug(ioe);
                }
            }
        }

        return _cookieForwardList;
    }    
    
    private static final String _GET_ATTRIBUTES = "/identity/attributes";
    private static final String _VALIDATE_OP = "/identity/isTokenValid";
    private static final String _GET_COOKIE_NAME =
        "/identity/getCookieNameForToken";
    private static final String _GET_COOKIE_LIST =
        "/identity/getCookieNamesToForward";

    /*
     * Cached item - use getter method only
     * The first in the list is the subject cookie name
     */
    private static List<String> _cookieForwardList =
        Collections.synchronizedList(new ArrayList<String>());

	private static Log _log = LogFactoryUtil.getLog(OpenSSOUtil.class);

}
