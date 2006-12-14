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
package com.liferay.portlet.journal.lar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

/**
 * Contains utility functions for support of the Journal CMS import/export functionality.
 * 
 * @author Joel Kozikowski
 */
public class PortletDataHandlerUtils {
    
    
    public static NewJournalContentCreationStrategy getContentCreationStrategy() throws PortletDataException {

        if (creationStrategy == null) {
            if (Validator.isNotNull(STRATEGY_CLASS)) {
                try {
                    creationStrategy = (NewJournalContentCreationStrategy)Class.forName(STRATEGY_CLASS).newInstance();
                }
                catch (Exception e) {
                    throw new PortletDataException("Can not create " + STRATEGY_CLASS, e);
                }
            }
            else {
                throw new PortletDataException("No creation strategy available: " + STRATEGY_CLASS + " is not defined in portal.properties");
            }
        }
        
        return creationStrategy;
    }
    
    
    private static final String STRATEGY_CLASS = GetterUtil.getString(PropsUtil.get("journal.new.content.strategy.class"));

    private static NewJournalContentCreationStrategy creationStrategy = null; 

    private static Log _log = LogFactory.getLog(PortletDataHandlerUtils.class);
}
