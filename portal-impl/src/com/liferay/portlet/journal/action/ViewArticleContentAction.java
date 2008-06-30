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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.PwdGenerator;

import java.io.File;
import java.io.StringReader;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="ViewArticleContentAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class ViewArticleContentAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		UploadServletRequest uploadRequest = null;

		try {
			String cmd = ParamUtil.getString(request, Constants.CMD);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(request, "groupId");
			String articleId = ParamUtil.getString(request, "articleId");
			double version = ParamUtil.getDouble(
				request, "version", JournalArticleImpl.DEFAULT_VERSION);

			String languageId = LanguageUtil.getLanguageId(request);

			String output = null;

			if (cmd.equals(Constants.PREVIEW)) {
				uploadRequest = new UploadServletRequestImpl(request);

				String title = ParamUtil.getString(uploadRequest, "title");
				String description = ParamUtil.getString(
					uploadRequest, "description");

				Date now = new Date();

				Date createDate = now;
				Date modifiedDate = now;
				Date displayDate = now;

				User user = PortalUtil.getUser(uploadRequest);

				String xml = ParamUtil.getString(uploadRequest, "xml");

				SAXReader reader = new SAXReader();

				Document doc = reader.read(new StringReader(xml));

				Element root = doc.getRootElement();

				String previewArticleId =
					"PREVIEW_" +
					PwdGenerator.getPassword(PwdGenerator.KEY3, 10);

				format(
					groupId, articleId, version, previewArticleId, root,
					uploadRequest);

				Map<String, String> tokens = JournalUtil.getTokens(
					groupId, themeDisplay);

				tokens.put("article_resource_pk", "-1");

				JournalArticle article = new JournalArticleImpl();

				article.setUserId(user.getUserId());
				article.setCreateDate(createDate);
				article.setModifiedDate(modifiedDate);
				article.setArticleId(articleId);
				article.setVersion(version);
				article.setTitle(title);
				article.setDescription(description);
				article.setDisplayDate(displayDate);

				JournalUtil.addAllReservedEls(root, tokens, article);

				xml = JournalUtil.formatXML(doc);

				String templateId = ParamUtil.getString(
					uploadRequest, "templateId");

				JournalTemplate template =
					JournalTemplateLocalServiceUtil.getTemplate(
						groupId, templateId);

				String langType = template.getLangType();
				String script = template.getXsl();

				output = JournalUtil.transform(
					tokens, languageId, xml, script, langType);
			}
			else {
				output = JournalArticleServiceUtil.getArticleContent(
					groupId, articleId, version, languageId, themeDisplay);
			}

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT, output);

			if (output.startsWith("<?xml ")) {
				return mapping.findForward(
					"portlet.journal.raw_article_content");
			}
			else {
				return mapping.findForward(
					"portlet.journal.view_article_content");
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
		finally {
			if (uploadRequest != null) {
				uploadRequest.cleanUp();
			}
		}
	}

	protected void format(
			long groupId, String articleId, double version,
			String previewArticleId, Element root,
			UploadServletRequest uploadRequest)
		throws Exception {

		Iterator<Element> itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			Element dynamicContent = el.element("dynamic-content");

			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);
			String elContent = StringPool.BLANK;
			String elLanguage = StringPool.BLANK;

			if (dynamicContent != null) {
				elContent = dynamicContent.getTextTrim();

				elLanguage = dynamicContent.attributeValue(
					"language-id", StringPool.BLANK);

				if (!elLanguage.equals(StringPool.BLANK)) {
					elLanguage = "_" + elLanguage;
				}
			}

			if (elType.equals("image") && Validator.isNull(elContent)) {
				File file = uploadRequest.getFile(
					"structure_image_" + elName + elLanguage);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					long imageId =
						JournalArticleImageLocalServiceUtil.getArticleImageId(
							groupId, previewArticleId, version, elName,
							elLanguage, true);

					String token = ImageServletTokenUtil.getToken(imageId);

					dynamicContent.setText(
						"/image/journal/article?img_id=" + imageId + "&t=" +
							token);

					ImageLocalServiceUtil.updateImage(imageId, bytes);
				}
				else {
					if (Validator.isNotNull(articleId)) {
						long imageId = JournalArticleImageLocalServiceUtil.
							getArticleImageId(
								groupId, articleId, version, elName,
								elLanguage);

						String token = ImageServletTokenUtil.getToken(imageId);

						dynamicContent.setText(
							"/image/journal/article?img_id=" + imageId +
								"&t=" + token);
					}
				}
			}

			format(
				groupId, articleId, version, previewArticleId, el,
				uploadRequest);
		}
	}

}