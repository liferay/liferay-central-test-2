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

package com.liferay.portal.kernel.workflow;

import java.util.Date;
import java.util.Map;

/**
 * <a href="WorkflowInstanceHistory.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A single workflow history entry as being retrieved through the {@link
 * WorkflowInstanceManager} for a certain workflow instance, optionally
 * including its children as well.
 * </p>
 *
 * <p>
 * How history entries are being created and what kind of type they represent is
 * totally engine specific and might even be configured.
 * </p>
 *
 * @author Micha Kiener
 */
public interface WorkflowInstanceHistory {

	/**
	 * Returns any additional data attached to this entry. The map and its
	 * information is optional and depending on the underlying engine used. It
	 * might support additional attributes of this history entry dependent on
	 * the type it represents.
	 *
	 * @return the optional, additional attributes, if any, an empty map or even
	 *		   <code>null</code> otherwise, depending on the underlying engine
	 */
	public Map<String, Object> getAttributes();

	/**
	 * Returns the creation date of this history entry.
	 *
	 * @return the creation date
	 */
	public Date getCreateDate();

	/**
	 * Returns the description of this history entry. The content and formating
	 * of the description is dependent on the underlying workflow engine.
	 *
	 * @return the description of this entry
	 */
	public String getDescription();

	/**
	 * Returns the id (usually the primary key) of this history entry. It could
	 * be used for sorting as the creation date and time could be in a different
	 * order than the id (saving time). It will most likely be the same but if
	 * parallel processing is done, the creation date and the saving id could
	 * slightly be different.
	 *
	 * @return the id of the history entry
	 */
	public long getHistoryEntryId();

	/**
	 * Returns the type of this entry, depending on the underlying engine.
	 * Usually there are different types of history entries (like comments,
	 * transitions, activities and so on). This method returns a type id of this
	 * entry.
	 *
	 * @return the type id or name of this history entry
	 */
	public String getType();

	/**
	 * Returns the user id creating this history entry. As a history entry could
	 * also be created by the system (e.g. in an asynchronous thread processing
	 * a job or branches), this method could return <code>null</code> or a
	 * system user id, depending on the underlying engine.
	 *
	 * @return the user id creating this entry or <code>0</code>, if none
	 *		   available
	 */
	public long getUserId();

	/**
	 * Returns the workflow instance id in relation to which this history entry
	 * was created. This might be important, if the complete history of a
	 * workflow instance (including the children) is requested in order to group
	 * the entries together for a certain instance.
	 *
	 * @return the workflow instance id to which this history entry belongs
	 */
	public long getWorkflowInstanceId();

}