/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.editor.fckeditor.receiver.CommandReceiver;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.servlet.UploadServletRequest;
import com.liferay.util.servlet.fileupload.LiferayFileItemFactory;

import java.io.File;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <a href="BaseCommandReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 *
 */
public abstract class BaseCommandReceiver implements CommandReceiver {

	public void createFolder(
		CommandArgument arg, HttpServletRequest req, HttpServletResponse res) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, arg.getCommand(), arg.getType(), arg.getCurrentFolder(),
			"");

		Element errorEl = doc.createElement("Error");

		root.appendChild(errorEl);

		String returnValue = "0";

		try {
			returnValue = createFolder(arg);
		}
		catch (FCKException fcke) {
			returnValue = "110";
		}

		errorEl.setAttribute("number", returnValue);

		_writeDocument(doc, res);
	}

	public void getFolders(
		CommandArgument arg, HttpServletRequest req, HttpServletResponse res) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, arg.getCommand(), arg.getType(), arg.getCurrentFolder(),
			getPath(arg));

		getFolders(arg, root, doc);

		_writeDocument(doc, res);
	}

	public void getFoldersAndFiles(
		CommandArgument arg, HttpServletRequest req, HttpServletResponse res) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, arg.getCommand(), arg.getType(), arg.getCurrentFolder(),
			getPath(arg));

		getFoldersAndFiles(arg, root, doc);

		_writeDocument(doc, res);
	}

	public void fileUpload(
		CommandArgument arg, HttpServletRequest req, HttpServletResponse res) {

		ServletFileUpload upload = new ServletFileUpload(
			new LiferayFileItemFactory(UploadServletRequest.DEFAULT_TEMP_DIR));

		List items = null;

		try {
			items = upload.parseRequest(req);
		}
		catch (FileUploadException fue) {
			throw new FCKException(fue);
		}

		Map fields = new HashMap();

		Iterator itr = items.iterator();

		while (itr.hasNext()) {
			FileItem item = (FileItem)itr.next();

			if (item.isFormField()) {
				fields.put(item.getFieldName(), item.getString());
			}
			else {
				fields.put(item.getFieldName(), item);
			}
		}

		DiskFileItem fileItem = (DiskFileItem)fields.get("NewFile");

		String fileName = StringUtil.replace(fileItem.getName(), "\\", "/");
		String[] fileNameArray = StringUtil.split(fileName, "/");
		fileName = fileNameArray[fileNameArray.length - 1];

		String extension = _getExtension(fileName);

		String returnValue = null;

		try {
			returnValue = fileUpload(
				arg, fileName, fileItem.getStoreLocation(), extension);
		}
		catch (FCKException fcke) {
			_writeUploadResponse("203", res);

			throw fcke;
		}

		_writeUploadResponse(returnValue, res);
	}

	protected abstract String createFolder(CommandArgument arg);

	protected abstract String fileUpload(
		CommandArgument arg, String fileName, File file, String extension);

	protected abstract void getFolders(
		CommandArgument arg, Node root, Document doc);

	protected abstract void getFoldersAndFiles(
		CommandArgument arg, Node root, Document doc);

	protected String getPath(CommandArgument arg) {
		return StringPool.BLANK;
	}

	protected String getSize() {
		return getSize(0);
	}

	protected String getSize(int size) {
		return String.valueOf(Math.ceil(size / 1000));
	}

	private Document _createDocument() {
		try {
			Document doc = null;

			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = null;

			builder = factory.newDocumentBuilder();

			doc = builder.newDocument();

			return doc;
		}
		catch (ParserConfigurationException pce) {
			throw new FCKException(pce);
		}
	}

	private Node _createRoot(
		Document doc, String commandStr, String typeStr, String currentPath,
		String currentUrl) {

		Element root = doc.createElement("Connector");

		doc.appendChild(root);

		root.setAttribute("command", commandStr);
		root.setAttribute("resourceType", typeStr);

		Element el = doc.createElement("CurrentFolder");

		root.appendChild(el);

		el.setAttribute("path", currentPath);
		el.setAttribute("url", currentUrl);

		return root;
	}

	private String _getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private void _writeDocument(Document doc, HttpServletResponse res) {
		try {
			doc.getDocumentElement().normalize();

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);

			if (_log.isDebugEnabled()){
				StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);
			}

			res.setContentType("text/xml; charset=UTF-8");
			res.setHeader("Cache-Control", "no-cache");

			PrintWriter out = res.getWriter();

			StreamResult result = new StreamResult(out);

			transformer.transform(source, result);

			out.flush();
			out.close();
		}
		catch (Exception e){
			throw new FCKException(e);
		}
	}

	private void _writeUploadResponse(
		String returnValue, HttpServletResponse res) {

		try {
			StringBuffer sb = new StringBuffer();

			String newName = StringPool.BLANK;

			sb.append("<script type=\"text/javascript\">");
			sb.append("window.parent.frames['frmUpload'].OnUploadCompleted(");
			sb.append(returnValue);
			sb.append(",'");
			sb.append(newName);
			sb.append("');");
			sb.append("</script>");

			res.setContentType("text/html; charset=UTF-8");
			res.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;

			out = res.getWriter();

			out.print(sb.toString());

			out.flush();
			out.close();
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private static final Log _log =
		LogFactory.getLog(BaseCommandReceiver.class);

}