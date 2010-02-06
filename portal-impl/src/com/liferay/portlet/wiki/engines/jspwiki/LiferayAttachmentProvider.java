/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.QueryItem;
import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.WikiPage;
import com.ecyrd.jspwiki.attachment.Attachment;
import com.ecyrd.jspwiki.providers.ProviderException;
import com.ecyrd.jspwiki.providers.WikiAttachmentProvider;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * <a href="LiferayAttachmentProvider.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class LiferayAttachmentProvider implements WikiAttachmentProvider {

	public void deleteAttachment(Attachment attachment) {
	}

	public void deleteVersion(Attachment attachment) {
	}

	public Collection<Attachment> findAttachments(QueryItem[] query) {
		return Collections.emptyList();
	}

	public InputStream getAttachmentData(Attachment attachment) {
		return _EMPTY_STREAM;
	}

	public Attachment getAttachmentInfo(WikiPage page, String name, int version)
		throws ProviderException {

		com.liferay.portlet.wiki.model.WikiPage wikiPage = null;

		try {
			wikiPage = WikiPageLocalServiceUtil.getPage(
				_nodeId, page.getName());

			String[] attachments = wikiPage.getAttachmentsFiles();

			for (int i = 0; i < attachments.length; i++) {
				String fileName = FileUtil.getShortFileName(attachments[i]);

				if (fileName.equals(name)) {
					return new Attachment(_engine, page.getName(), name);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	public String getProviderInfo() {
		return LiferayAttachmentProvider.class.getName();
	}

	public List<Attachment> getVersionHistory(Attachment attachment) {
		List<Attachment> history = new ArrayList<Attachment>();

		history.add(attachment);

		return history;
	}

	public void initialize(WikiEngine engine, Properties props) {
		_engine = engine;
		_nodeId = GetterUtil.getLong(props.getProperty("nodeId"));
	}

	public List<Attachment> listAllChanged(Date timestamp) {
		return Collections.emptyList();
	}

	public Collection<Attachment> listAttachments(WikiPage page) {
		return Collections.emptyList();
	}

	public void moveAttachmentsForPage(String oldParent, String newParent) {
	}

	public void putAttachmentData(Attachment attachment, InputStream data) {
	}

	private static final InputStream _EMPTY_STREAM =
		new UnsyncByteArrayInputStream(new byte[0]);

	private WikiEngine _engine;
	private long _nodeId;

}