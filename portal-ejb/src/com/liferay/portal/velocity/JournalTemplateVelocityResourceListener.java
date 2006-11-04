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

package com.liferay.portal.velocity;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;
import com.liferay.portlet.journal.service.spring.JournalTemplateLocalServiceUtil;
import com.liferay.util.StringPool;
import com.liferay.util.velocity.VelocityResourceListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * <a href="JournalTemplateVelocityResourceListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class JournalTemplateVelocityResourceListener
	extends VelocityResourceListener {

	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		InputStream is = null;

		try {
			int pos = source.indexOf(JOURNAL_SEPARATOR + StringPool.SLASH);

			if (pos != -1) {
				int x = source.indexOf(StringPool.SLASH, pos);
				int y = source.indexOf(StringPool.SLASH, x + 1);

				String companyId = source.substring(x + 1, y);
				String templateId = source.substring(y + 1);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Loading " +
							new JournalTemplatePK(companyId, templateId));
				}

				JournalTemplate template =
					JournalTemplateLocalServiceUtil.getTemplate(
						companyId, templateId);

				String buffer = template.getXsl();

				is = new ByteArrayInputStream(buffer.getBytes());
			}
		}
		catch (PortalException pe) {
			throw new ResourceNotFoundException(source);
		}
		catch (SystemException se) {
			throw new ResourceNotFoundException(source);
		}

		return is;
	}

	private static Log _log =
		LogFactory.getLog(JournalTemplateVelocityResourceListener.class);

}