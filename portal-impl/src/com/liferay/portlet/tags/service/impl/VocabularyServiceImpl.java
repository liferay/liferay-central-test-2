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

package com.liferay.portlet.tags.service.impl;

import java.util.List;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.tags.model.Vocabulary;
import com.liferay.portlet.tags.service.base.VocabularyServiceBaseImpl;

/**
 * <a href="VocabularyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alvaro del Castillo
 *
 */
public class VocabularyServiceImpl extends VocabularyServiceBaseImpl {
	
	public Vocabulary addVocabulary(String name) throws PortalException,
			SystemException {

		return vocabularyLocalService.addVocabulary(getUserId(), name, false);
	}

	public Vocabulary addVocabulary(String name, boolean folksonomy)
			throws PortalException, SystemException {
		
		return vocabularyLocalService.addVocabulary(getUserId(), name, folksonomy);
	}

	
	
	public void deleteVocabulary(long vocabularyId) throws PortalException,
			SystemException {

		vocabularyLocalService.deleteVocabularyTags(getUserId(), vocabularyId);
		vocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	public List<Vocabulary> getVocabularies() throws SystemException {

		return vocabularyLocalService.getVocabularies();
	}

	public Vocabulary getVocabulary(long vocabularyId) throws SystemException,
			PortalException {

		return vocabularyLocalService.getVocabulary(vocabularyId);
	}

	
	public List<Vocabulary> search(long companyId)
			throws SystemException {
	
		return vocabularyLocalService.search(companyId);
	}
		
	public Vocabulary updateVocabulary(long vocabularyId, String name,
			boolean folksonomy) throws PortalException, SystemException {

		return vocabularyLocalService.updateVocabulary(vocabularyId, name,
				folksonomy);
	}
	
}