/*
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

package com.liferay.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

/**
 * <a href="MockMailMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MockMailMessage extends Message {
	public MockMailMessage() {
		super();
		_fromAddresses = new HashSet<Address>();
		_recipients = new HashMap<RecipientType, Set<Address>>();
		_flags = new Flags();
		_headers = new Hashtable<String, Set<String>>();
	}


	public Address[] getFrom() throws MessagingException {
		return _fromAddresses.toArray(new Address[_fromAddresses.size()]);
	}

	public void setFrom() throws MessagingException {
		_fromAddresses.clear();
	}

	public void setFrom(Address address) throws MessagingException {
		_fromAddresses.clear();
		_fromAddresses.add(address);
	}

	public void addFrom(Address[] addresses) throws MessagingException {
		_fromAddresses.addAll(Arrays.asList(addresses));
	}

	public Address[] getRecipients(RecipientType recipientType)
		throws MessagingException {
		Set<Address> recipients = _recipients.get(recipientType);
		if (recipients == null) {
			return new Address[0];
		}
		return recipients.toArray(new Address[recipients.size()]);
	}

	public void setRecipients(
		RecipientType recipientType, Address[] addresses)
		throws MessagingException {
		_recipients.put(
			recipientType, new HashSet<Address>(Arrays.asList(addresses)));
	}

	public void addRecipients(
		RecipientType recipientType, Address[] addresses)
		throws MessagingException {
		Set<Address> recipients = _recipients.get(recipientType);
		if (recipients == null) {
			recipients = new HashSet<Address>();
			_recipients.put(recipientType, recipients);
		}
		recipients.addAll(Arrays.asList(addresses));
	}

	public String getSubject() throws MessagingException {
		return _subject;
	}

	public void setSubject(String subject) throws MessagingException {
		_subject = subject;
	}

	public Date getSentDate() throws MessagingException {
		return _sentDate;
	}

	public void setSentDate(Date date) throws MessagingException {
		_sentDate = date;
	}

	public Date getReceivedDate() throws MessagingException {
		return _receivedDate;
	}

	public Flags getFlags() throws MessagingException {
		throw new UnsupportedOperationException();
	}

	public void setFlags(Flags flags, boolean b) throws MessagingException {
		throw new UnsupportedOperationException();
	}

	public Message reply(boolean b) throws MessagingException {
		return this;
	}

	public void saveChanges() throws MessagingException {
		throw new UnsupportedOperationException();
	}

	public int getSize() throws MessagingException {
		return _size;
	}

	public int getLineCount() throws MessagingException {
		return _lineCount;
	}

	public String getContentType() throws MessagingException {
		return _getContentType;
	}

	public boolean isMimeType(String mimeType) throws MessagingException {
		return _getContentType.equals(mimeType);
	}

	public String getDisposition() throws MessagingException {
		return _disposition;
	}

	public void setDisposition(String disposition) throws MessagingException {
		_disposition = disposition;
	}

	public String getDescription() throws MessagingException {
		return _description;
	}

	public void setDescription(String description) throws MessagingException {
		_description = description;
	}

	public String getFileName() throws MessagingException {
		return _fileName;
	}

	public void setFileName(String fileName) throws MessagingException {
		_fileName = fileName;
	}

	public InputStream getInputStream()
		throws IOException, MessagingException {
		return _inputStream;
	}

	public DataHandler getDataHandler() throws MessagingException {
		return null;
	}

	public Object getContent() throws IOException, MessagingException {
		return null;
	}

	public void setDataHandler(DataHandler dataHandler)
		throws MessagingException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void setContent(Object o, String s) throws MessagingException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void setText(String s) throws MessagingException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void setContent(Multipart multipart) throws MessagingException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void writeTo(OutputStream outputStream)
		throws IOException, MessagingException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public String[] getHeader(String name) throws MessagingException {
		if (_headers.containsKey(name)) {
			return _headers.get(name).toArray(
				new String[_headers.get(name).size()]);
		}
		return new String[0];
	}

	public void setHeader(String name, String value) throws MessagingException {
		Set<String> headerValues = _headers.get(name);
		if (headerValues == null) {
			headerValues = new HashSet<String>();
			_headers.put(name, headerValues);
		}
		headerValues.clear();
		headerValues.add(value);
	}

	public void addHeader(String name, String value) throws MessagingException {
		Set<String> headerValues = _headers.get(name);
		if (headerValues == null) {
			headerValues = new HashSet<String>();
			_headers.put(name, headerValues);
		}
		headerValues.add(value);
	}

	public void removeHeader(String name) throws MessagingException {
		_headers.remove(name);
	}

	public Enumeration getAllHeaders() throws MessagingException {
		return _headers.elements();

	}

	public Enumeration getMatchingHeaders(String[] strings)
		throws MessagingException {
		throw new UnsupportedOperationException();
	}

	public Enumeration getNonMatchingHeaders(String[] strings)
		throws MessagingException {
		throw new UnsupportedOperationException();
	}

	private Set<Address> _fromAddresses;
	private Map<RecipientType, Set<Address>> _recipients;
	private String _subject;
	private Flags _flags;
	private Date _sentDate;
	private Date _receivedDate;
	private int _size;
	private int _lineCount;
	private String _getContentType;
	private String _disposition;
	private String _description;

	private Hashtable<String, Set<String>> _headers;
	private String _fileName;
	private InputStream _inputStream = new ByteArrayInputStream(new byte[200]);
}
