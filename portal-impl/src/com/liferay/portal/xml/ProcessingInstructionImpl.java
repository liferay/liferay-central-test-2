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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.ProcessingInstruction;

import java.util.Map;

/**
 * <a href="ProcessingInstructionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ProcessingInstructionImpl
	extends NodeImpl implements ProcessingInstruction {

	public ProcessingInstructionImpl(
		org.dom4j.ProcessingInstruction processingInstruction) {

		super(processingInstruction);

		_processingInstruction = processingInstruction;
	}

	public boolean equals(Object obj) {
		org.dom4j.ProcessingInstruction processingInstruction =
			((ProcessingInstructionImpl)obj).getWrappedProcessingInstruction();

		return _processingInstruction.equals(processingInstruction);
	}

	public String getTarget() {
		return _processingInstruction.getTarget();
	}

	public String getText() {
		return _processingInstruction.getText();
	}

	public String getValue(String name) {
		return _processingInstruction.getValue(name);
	}

	public Map<String, String> getValues() {
		return _processingInstruction.getValues();
	}

	public org.dom4j.ProcessingInstruction getWrappedProcessingInstruction() {
		return _processingInstruction;
	}

	public int hashCode() {
		return _processingInstruction.hashCode();
	}

	public boolean removeValue(String name) {
		return _processingInstruction.removeValue(name);
	}

	public void setTarget(String target) {
		_processingInstruction.setTarget(target);
	}

	public void setValue(String name, String value) {
		_processingInstruction.setValue(name, value);
	}

	public void setValues(Map<String, String> data) {
		_processingInstruction.setValues(data);
	}

	private org.dom4j.ProcessingInstruction _processingInstruction;

}