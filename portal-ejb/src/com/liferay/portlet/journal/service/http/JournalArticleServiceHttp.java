/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.servlet.TunnelUtil;

import com.liferay.portlet.journal.service.spring.JournalArticleServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalArticleServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleServiceHttp {
	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		HttpPrincipal httpPrincipal, java.lang.String articleId,
		boolean autoArticleId, java.lang.String plid, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		java.util.Map images, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = articleId;

			if (articleId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = new BooleanWrapper(autoArticleId);
			Object paramObj2 = plid;

			if (plid == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = title;

			if (title == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = description;

			if (description == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = content;

			if (content == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = type;

			if (type == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = structureId;

			if (structureId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = templateId;

			if (templateId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = new IntegerWrapper(displayDateMonth);
			Object paramObj10 = new IntegerWrapper(displayDateDay);
			Object paramObj11 = new IntegerWrapper(displayDateYear);
			Object paramObj12 = new IntegerWrapper(displayDateHour);
			Object paramObj13 = new IntegerWrapper(displayDateMinute);
			Object paramObj14 = new IntegerWrapper(expirationDateMonth);
			Object paramObj15 = new IntegerWrapper(expirationDateDay);
			Object paramObj16 = new IntegerWrapper(expirationDateYear);
			Object paramObj17 = new IntegerWrapper(expirationDateHour);
			Object paramObj18 = new IntegerWrapper(expirationDateMinute);
			Object paramObj19 = new BooleanWrapper(neverExpire);
			Object paramObj20 = new IntegerWrapper(reviewDateMonth);
			Object paramObj21 = new IntegerWrapper(reviewDateDay);
			Object paramObj22 = new IntegerWrapper(reviewDateYear);
			Object paramObj23 = new IntegerWrapper(reviewDateHour);
			Object paramObj24 = new IntegerWrapper(reviewDateMinute);
			Object paramObj25 = new BooleanWrapper(neverReview);
			Object paramObj26 = images;

			if (images == null) {
				paramObj26 = new NullWrapper("java.util.Map");
			}

			Object paramObj27 = articleURL;

			if (articleURL == null) {
				paramObj27 = new NullWrapper("java.lang.String");
			}

			Object paramObj28 = prefs;

			if (prefs == null) {
				paramObj28 = new NullWrapper("javax.portlet.PortletPreferences");
			}

			Object paramObj29 = new BooleanWrapper(addCommunityPermissions);
			Object paramObj30 = new BooleanWrapper(addGuestPermissions);
			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"addArticle",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24, paramObj25,
						paramObj26, paramObj27, paramObj28, paramObj29,
						paramObj30
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalArticle approveArticle(
		HttpPrincipal httpPrincipal, java.lang.String articleId,
		double version, java.lang.String plid, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = articleId;

			if (articleId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = new DoubleWrapper(version);
			Object paramObj2 = plid;

			if (plid == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = articleURL;

			if (articleURL == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = prefs;

			if (prefs == null) {
				paramObj4 = new NullWrapper("javax.portlet.PortletPreferences");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"approveArticle",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"getArticle",
					new Object[] { paramObj0, paramObj1, paramObj2 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = languageId;

			if (languageId == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = themeDisplay;

			if (themeDisplay == null) {
				paramObj3 = new NullWrapper(
						"com.liferay.portal.theme.ThemeDisplay");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"getArticleContent",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getArticleContent(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			Object paramObj3 = languageId;

			if (languageId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = themeDisplay;

			if (themeDisplay == null) {
				paramObj4 = new NullWrapper(
						"com.liferay.portal.theme.ThemeDisplay");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"getArticleContent",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void deleteArticle(HttpPrincipal httpPrincipal,
		java.lang.String companyId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			Object paramObj3 = articleURL;

			if (articleURL == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = prefs;

			if (prefs == null) {
				paramObj4 = new NullWrapper("javax.portlet.PortletPreferences");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"deleteArticle",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void expireArticle(HttpPrincipal httpPrincipal,
		java.lang.String companyId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			Object paramObj3 = articleURL;

			if (articleURL == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = prefs;

			if (prefs == null) {
				paramObj4 = new NullWrapper("javax.portlet.PortletPreferences");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"expireArticle",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void removeArticleLocale(HttpPrincipal httpPrincipal,
		java.lang.String companyId, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = languageId;

			if (languageId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"removeArticleLocale", new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String articleId, double version, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			Object paramObj3 = languageId;

			if (languageId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"removeArticleLocale",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String articleId, double version, boolean incrementVersion,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, java.util.Map images,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = articleId;

			if (articleId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new DoubleWrapper(version);
			Object paramObj3 = new BooleanWrapper(incrementVersion);
			Object paramObj4 = title;

			if (title == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = description;

			if (description == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = content;

			if (content == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = type;

			if (type == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = structureId;

			if (structureId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = templateId;

			if (templateId == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = new IntegerWrapper(displayDateMonth);
			Object paramObj11 = new IntegerWrapper(displayDateDay);
			Object paramObj12 = new IntegerWrapper(displayDateYear);
			Object paramObj13 = new IntegerWrapper(displayDateHour);
			Object paramObj14 = new IntegerWrapper(displayDateMinute);
			Object paramObj15 = new IntegerWrapper(expirationDateMonth);
			Object paramObj16 = new IntegerWrapper(expirationDateDay);
			Object paramObj17 = new IntegerWrapper(expirationDateYear);
			Object paramObj18 = new IntegerWrapper(expirationDateHour);
			Object paramObj19 = new IntegerWrapper(expirationDateMinute);
			Object paramObj20 = new BooleanWrapper(neverExpire);
			Object paramObj21 = new IntegerWrapper(reviewDateMonth);
			Object paramObj22 = new IntegerWrapper(reviewDateDay);
			Object paramObj23 = new IntegerWrapper(reviewDateYear);
			Object paramObj24 = new IntegerWrapper(reviewDateHour);
			Object paramObj25 = new IntegerWrapper(reviewDateMinute);
			Object paramObj26 = new BooleanWrapper(neverReview);
			Object paramObj27 = images;

			if (images == null) {
				paramObj27 = new NullWrapper("java.util.Map");
			}

			Object paramObj28 = articleURL;

			if (articleURL == null) {
				paramObj28 = new NullWrapper("java.lang.String");
			}

			Object paramObj29 = prefs;

			if (prefs == null) {
				paramObj29 = new NullWrapper("javax.portlet.PortletPreferences");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalArticleServiceUtil.class.getName(),
					"updateArticle",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24, paramObj25,
						paramObj26, paramObj27, paramObj28, paramObj29
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactory.getLog(JournalArticleServiceHttp.class);
}