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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * <a href="ServerDetector.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ServerDetector {

	public static final String GERONIMO_ID = "geronimo";

	public static final String GLASSFISH_ID = "glassfish";

	public static final String JBOSS_ID = "jboss";

	public static final String JETTY_ID = "jetty";

	public static final String JONAS_ID = "jonas";

	public static final String OC4J_ID = "oc4j";

	public static final String RESIN_ID = "resin";

	public static final String TOMCAT_ID = "tomcat";

	public static final String WEBLOGIC_ID = "weblogic";

	public static final String WEBSPHERE_ID = "websphere";

	public static String getServerId() {
		ServerDetector sd = _instance;

		if (sd._serverId == null) {
			if (isGeronimo()) {
				sd._serverId = GERONIMO_ID;
			}
			else if (isGlassfish()) {
				sd._serverId = GLASSFISH_ID;
			}
			else if (isJBoss()) {
				sd._serverId = JBOSS_ID;
			}
			else if (isJOnAS()) {
				sd._serverId = JONAS_ID;
			}
			else if (isOC4J()) {
				sd._serverId = OC4J_ID;
			}
			else if (isResin()) {
				sd._serverId = RESIN_ID;
			}
			else if (isWebLogic()) {
				sd._serverId = WEBLOGIC_ID;
			}
			else if (isWebSphere()) {
				sd._serverId = WEBSPHERE_ID;
			}

			if (isJetty()) {
				if (sd._serverId == null) {
					sd._serverId = JETTY_ID;
				}
				else {
					sd._serverId += "-" + JETTY_ID;
				}
			}
			else if (isTomcat()) {
				if (sd._serverId == null) {
					sd._serverId = TOMCAT_ID;
				}
				else {
					sd._serverId += "-" + TOMCAT_ID;
				}
			}

			if (_log.isInfoEnabled()) {
				if (sd._serverId != null) {
					_log.info("Detected server " + sd._serverId);
				}
				else {
					_log.info("No server detected");
				}
			}

			if (sd._serverId == null) {
				throw new RuntimeException("Server is not supported");
			}
		}

		return sd._serverId;
	}

	public static boolean isGeronimo() {
		ServerDetector sd = _instance;

		if (sd._geronimo == null) {
			sd._geronimo = _detect(
				"/org/apache/geronimo/system/main/Daemon.class");
		}

		return sd._geronimo.booleanValue();
	}

	public static boolean isGlassfish() {
		ServerDetector sd = _instance;

		if (sd._glassfish == null) {
			String value = System.getProperty("com.sun.aas.instanceRoot");

			if (value != null) {
				sd._glassfish = Boolean.TRUE;
			}
			else {
				sd._glassfish = Boolean.FALSE;
			}
		}

		return sd._glassfish.booleanValue();
	}

	public static boolean isGlassfish2() {
		ServerDetector sd = _instance;

		if (sd._glassfish2 == null) {
			if (isGlassfish() && !isGlassfish3()) {
				sd._glassfish2 = Boolean.TRUE;
			}
			else {
				sd._glassfish2 = Boolean.FALSE;
			}
		}

		return sd._glassfish2.booleanValue();
	}

	public static boolean isGlassfish3() {
		ServerDetector sd = _instance;

		if (sd._glassfish3 == null) {
			String value = StringPool.BLANK;

			if (isGlassfish()) {
				value = GetterUtil.getString(
					System.getProperty("product.name"));
			}

			if (value.equals("GlassFish/v3")) {
				sd._glassfish3 = Boolean.TRUE;
			}
			else {
				sd._glassfish3 = Boolean.FALSE;
			}
		}

		return sd._glassfish3.booleanValue();
	}

	public static boolean isJBoss() {
		ServerDetector sd = _instance;

		if (sd._jBoss == null) {
			sd._jBoss = _detect("/org/jboss/Main.class");
		}

		return sd._jBoss.booleanValue();
	}

	public static boolean isJetty() {
		ServerDetector sd = _instance;

		if (sd._jetty == null) {
			sd._jetty = _detect("/org/mortbay/jetty/Server.class");
		}

		return sd._jetty.booleanValue();
	}

	public static boolean isJOnAS() {
		ServerDetector sd = _instance;

		if (sd._jonas == null) {
			sd._jonas = _detect("/org/objectweb/jonas/server/Server.class");
		}

		return sd._jonas.booleanValue();
	}

	public static boolean isOC4J() {
		ServerDetector sd = _instance;

		if (sd._oc4j == null) {
			sd._oc4j = _detect("oracle.oc4j.util.ClassUtils");
		}

		return sd._oc4j.booleanValue();
	}

	public static boolean isResin() {
		ServerDetector sd = _instance;

		if (sd._resin == null) {
			sd._resin = _detect("/com/caucho/server/resin/Resin.class");
		}

		return sd._resin.booleanValue();
	}

	public static boolean isSupportsComet() {
		return false;
	}

	public static boolean isTomcat() {
		ServerDetector sd = _instance;

		if (sd._tomcat == null) {
			sd._tomcat = _detect(
				"/org/apache/catalina/startup/Bootstrap.class");
		}

		if (sd._tomcat == null) {
			sd._tomcat = _detect("/org/apache/catalina/startup/Embedded.class");
		}

		return sd._tomcat.booleanValue();
	}

	public static boolean isWebLogic() {
		ServerDetector sd = _instance;

		if (sd._webLogic == null) {
			sd._webLogic = _detect("/weblogic/Server.class");
		}

		return sd._webLogic.booleanValue();
	}

	public static boolean isWebSphere() {
		ServerDetector sd = _instance;

		if (sd._webSphere == null) {
			sd._webSphere = _detect(
				"/com/ibm/websphere/product/VersionInfo.class");
		}

		return sd._webSphere.booleanValue();
	}

	private static Boolean _detect(String className) {
		try {
			ClassLoader.getSystemClassLoader().loadClass(className);

			return Boolean.TRUE;
		}
		catch (ClassNotFoundException cnfe) {
			ServerDetector sd = _instance;

			Class<?> c = sd.getClass();

			if (c.getResource(className) != null) {
				return Boolean.TRUE;
			}
			else {
				return Boolean.FALSE;
			}
		}
	}

	private ServerDetector() {
	}

	private static Log _log = LogFactoryUtil.getLog(ServerDetector.class);

	private static ServerDetector _instance = new ServerDetector();

	private String _serverId;
	private Boolean _geronimo;
	private Boolean _glassfish;
	private Boolean _glassfish2;
	private Boolean _glassfish3;
	private Boolean _jBoss;
	private Boolean _jetty;
	private Boolean _jonas;
	private Boolean _oc4j;
	private Boolean _resin;
	private Boolean _tomcat;
	private Boolean _webLogic;
	private Boolean _webSphere;

}