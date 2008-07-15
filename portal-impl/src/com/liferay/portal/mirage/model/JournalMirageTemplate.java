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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.model;

import com.liferay.portlet.journal.model.JournalTemplate;

import com.sun.portal.cms.mirage.model.custom.Template;

import java.io.File;

/**
 * <a href="JournalMirageTemplate.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public class JournalMirageTemplate extends Template {

	public class CreationAttributes {

		public CreationAttributes(
			boolean formatXsl, boolean autoTemplateId, File smallFile) {

			_formatXsl = formatXsl;
			_autoTemplateId = autoTemplateId;
			_smallFile = smallFile;
		}

		public boolean isAutoTemplateId() {

			return _autoTemplateId;
		}

		public void setAutoTemplateId(boolean autoTemplateId) {

			_autoTemplateId = autoTemplateId;
		}

		public boolean isFormatXsl() {

			return _formatXsl;
		}

		public void setFormatXsl(boolean formatXsl) {

			_formatXsl = formatXsl;
		}

		public File getSmallFile() {

			return _smallFile;
		}

		public void setSmallFile(File smallFile) {

			_smallFile = smallFile;
		}

		private boolean _formatXsl;
		private boolean _autoTemplateId;
		private File _smallFile;

	}

	public class UpdateAttributes {

		public UpdateAttributes(boolean formatXsl, File smallFile) {

			_formatXsl = formatXsl;
			_smallFile = smallFile;
		}

		public boolean isFormatXsl() {

			return _formatXsl;
		}

		public void setFormatXsl(boolean formatXsl) {

			_formatXsl = formatXsl;
		}

		public File getSmallFile() {

			return _smallFile;
		}

		public void setSmallFile(File smallFile) {

			_smallFile = smallFile;
		}

		private boolean _formatXsl;
		private File _smallFile;

	}

	public JournalMirageTemplate(JournalTemplate template) {

		_template = template;
	}

	public JournalTemplate getTemplate() {

		return _template;
	}

	public void setTemplate(JournalTemplate template) {

		_template = template;
	}

	public JournalMirageTemplate.CreationAttributes getCreationAttributes() {

		return _creationAttributes;
	}

	public void setCreationAttributes(
		JournalMirageTemplate.CreationAttributes creationAttributes) {

		_creationAttributes = creationAttributes;
	}

	public JournalMirageTemplate.UpdateAttributes getUpdateAttributes() {

		return _updateAttributes;
	}

	public void setUpdateAttributes(
		JournalMirageTemplate.UpdateAttributes updateAttributes) {

		_updateAttributes = updateAttributes;
	}

	private JournalTemplate _template;
	private CreationAttributes _creationAttributes;
	private UpdateAttributes _updateAttributes;

}