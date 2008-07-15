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

import com.liferay.portlet.journal.model.JournalStructure;

import com.sun.portal.cms.mirage.model.custom.ContentType;

/**
 * <a href="JournalStructureContentType.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public class JournalStructureContentType extends ContentType {

	public class CreationAttributes {

		public Boolean getAddCommunityPermissions() {

			return _addCommunityPermissions;
		}

		public void setAddCommunityPermissions(
			Boolean addCommunityPermissions) {

			_addCommunityPermissions = addCommunityPermissions;
		}

		public Boolean getAddGuestPermissions() {

			return _addGuestPermissions;
		}

		public void setAddGuestPermissions(Boolean addGuestPermissions) {

			_addGuestPermissions = addGuestPermissions;
		}

		public boolean isAutoStructureId() {

			return _autoStructureId;
		}

		public void setAutoStructureId(boolean autoStructureId) {

			_autoStructureId = autoStructureId;
		}

		private boolean _autoStructureId;
		private Boolean _addCommunityPermissions;
		private Boolean _addGuestPermissions;

	}

	public JournalStructureContentType(JournalStructure structure) {

		_structure = structure;
	}

	public JournalStructureContentType(
		JournalStructure structure, String[] communityPermissions,
		String[] guestPermissions) {

		_structure = structure;
		_communityPermissions = communityPermissions;
		_guestPermissions = guestPermissions;
	}

	public JournalStructure getStructure() {

		return _structure;
	}

	public void setStructure(JournalStructure structure) {

		_structure = structure;
	}

	public String[] getCommunityPermissions() {

		return _communityPermissions;
	}

	public void setCommunityPermissions(String[] communityPermissions) {

		_communityPermissions = communityPermissions;
	}

	public String[] getGuestPermissions() {

		return _guestPermissions;
	}

	public void setGuestPermissions(String[] guestPermissions) {

		_guestPermissions = guestPermissions;
	}

	public JournalStructureContentType.CreationAttributes
		getCreationAttributes() {

		return _creationAttributes;
	}

	public void setCreationAttributes(
		JournalStructureContentType.CreationAttributes creationAttributes) {

		_creationAttributes = creationAttributes;
	}

	private JournalStructure _structure;
	private String[] _communityPermissions;
	private String[] _guestPermissions;
	private CreationAttributes _creationAttributes;

}