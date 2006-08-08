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

package com.liferay.portlet.mail.util;

import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mail.ContentException;
import com.liferay.portlet.mail.ContentPathException;
import com.liferay.portlet.mail.FolderException;
import com.liferay.portlet.mail.RecipientException;
import com.liferay.portlet.mail.StoreException;
import com.liferay.portlet.mail.model.MailAttachment;
import com.liferay.portlet.mail.model.MailEnvelope;
import com.liferay.portlet.mail.model.MailFolder;
import com.liferay.portlet.mail.model.MailMessage;
import com.liferay.portlet.mail.model.RemoteMailAttachment;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.mail.MailEngine;

import com.sun.mail.imap.IMAPFolder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags.Flag;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message.RecipientType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MailUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class MailUtil {

	public static final String MAIL_BOX_STYLE =
		PropsUtil.get(PropsUtil.MAIL_BOX_STYLE);

	public static final String MAIL_INBOX_NAME =
		PropsUtil.get(PropsUtil.MAIL_INBOX_NAME);

	public static final String MAIL_SPAM_NAME =
		MAIL_BOX_STYLE + PropsUtil.get(PropsUtil.MAIL_SPAM_NAME);

	public static final String MAIL_SENT_NAME =
		MAIL_BOX_STYLE + PropsUtil.get(PropsUtil.MAIL_SENT_NAME);

	public static final String MAIL_DRAFTS_NAME =
		MAIL_BOX_STYLE + PropsUtil.get(PropsUtil.MAIL_DRAFTS_NAME);

	public static final String MAIL_TRASH_NAME =
		MAIL_BOX_STYLE + PropsUtil.get(PropsUtil.MAIL_TRASH_NAME);

	public static final String[] DEFAULT_FOLDERS = {
		MAIL_INBOX_NAME, MAIL_SPAM_NAME, MAIL_SENT_NAME, MAIL_DRAFTS_NAME,
		MAIL_TRASH_NAME
	};

	public static void completeMessage(
			HttpServletRequest req, MailMessage mailMessage, boolean send,
			String originalId, boolean wasDraft)
		throws ContentException, ContentPathException, FolderException,
			   RecipientException, StoreException {

		HttpSession ses = req.getSession();

		try {
			if (send && Validator.isNull(mailMessage.getTo()) &&
				Validator.isNull(mailMessage.getCc()) &&
				Validator.isNull(mailMessage.getBcc())) {

				_log.error("A message with no recipients cannot be sent");

				throw new RecipientException();
			}

			Message message = new MimeMessage(MailEngine.getSession());

			message.setFrom(mailMessage.getFrom());

			if (!Validator.isNull(mailMessage.getTo())) {
				message.setRecipients(
					Message.RecipientType.TO,
					_resolveAddresses(req, mailMessage.getTo()));
			}

			if (!Validator.isNull(mailMessage.getCc())) {
				message.setRecipients(
					Message.RecipientType.CC,
					_resolveAddresses(req, mailMessage.getCc()));
			}

			if (!Validator.isNull(mailMessage.getBcc())) {
				message.setRecipients(
					Message.RecipientType.BCC,
					_resolveAddresses(req, mailMessage.getBcc()));
			}

			message.setSubject(mailMessage.getSubject());

			_replaceEmbeddedImages(ses, mailMessage, _getAttachmentURL(req));

			Multipart multipart = new MimeMultipart();

			BodyPart bodyPart = new MimeBodyPart();

			bodyPart.setContent(mailMessage.getHtmlBody(), Constants.TEXT_HTML);

			multipart.addBodyPart(bodyPart);

			List attachments = mailMessage.getAttachments();

			Iterator itr = attachments.iterator();

			while (itr.hasNext()) {
				MailAttachment mailAttachment = (MailAttachment)itr.next();

				DataSource dataSource = new ByteArrayDataSource(
					mailAttachment.getContent(),
					mailAttachment.getContentType());

				BodyPart attachment = new MimeBodyPart();

				attachment.setFileName(mailAttachment.getFilename());
				attachment.setDataHandler(new DataHandler(dataSource));

				if (Validator.isNotNull(mailAttachment.getContentId())) {
					attachment.addHeader(
						Constants.CONTENT_ID, mailAttachment.getContentId());
				}

				multipart.addBodyPart(attachment);
			}

			List remoteAttachments = mailMessage.getRemoteAttachments();

			itr = remoteAttachments.iterator();

			while (itr.hasNext()) {
				RemoteMailAttachment remoteMailAttachment =
					(RemoteMailAttachment)itr.next();

				Object[] parts = getAttachment(
					ses, remoteMailAttachment.getContentPath());

				DataSource dataSource = new ByteArrayDataSource(
					(byte[])parts[0], (String)parts[1]);

				BodyPart attachment = new MimeBodyPart();

				attachment.setFileName(remoteMailAttachment.getFilename());
				attachment.setDataHandler(new DataHandler(dataSource));

				multipart.addBodyPart(attachment);
			}

			message.setContent(multipart);
			message.setSentDate(new Date());

			if (send) {
				Transport.send(message);
			}

			try {
				MailSessionLock.lock(ses.getId());

				String lastFolderName = getFolderName(ses);

				IMAPFolder folder = null;

				if (send) {
					folder = _getFolder(ses, MAIL_SENT_NAME);

					message.setFlag(Flag.SEEN, true);
				}
				else {
					folder = _getFolder(ses, MAIL_DRAFTS_NAME);
				}

				folder.appendMessages(new Message[] {message});

				long origId = GetterUtil.getLong(originalId);

				if (wasDraft) {
					folder = _getFolder(ses, MAIL_DRAFTS_NAME);

					Message msg =
						folder.getMessageByUID(origId);

					folder.setFlags(new Message[] {msg},
						new Flags(Flags.Flag.DELETED), true);

					folder.expunge();
				}
				else if (origId > 0L) {
					folder = _getFolder(ses, lastFolderName);

					Message msg =
						folder.getMessageByUID(origId);

					folder.setFlags(new Message[] {msg},
						new Flags(Flags.Flag.ANSWERED), true);
				}

				// Make sure to explicitly close and open the correct folder

				_closeFolder(ses);

				setFolder(ses, lastFolderName);
			}
			finally {
				MailSessionLock.unlock(ses.getId());
			}
		}
		catch (MessagingException me) {
			throw new ContentException(me);
		}
		catch (NamingException ne) {
			throw new ContentException(ne);
		}
	}

	public static void createFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

		Folder folder = null;

		try {
			Iterator itr = getFolders(ses).iterator();

			while (itr.hasNext()) {
				MailFolder mailFolder = (MailFolder)itr.next();

				if (mailFolder.getName().equals(folderName)) {
					throw new FolderException(
						"Folder " + folderName + " already exists");
				}
			}

			Store store = _getStore(ses);

			folderName = _getResolvedFolderName(folderName);

			folder = store.getFolder(folderName);

			folder.create(Folder.HOLDS_MESSAGES);
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			try {
				if ((folder != null) && folder.isOpen()) {
					folder.close(false);
				}
			}
			catch (Exception e) {
			}
		}
	}

	public static void deleteMessages(HttpSession ses, long[] messageIds)
		throws FolderException, StoreException {

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			String folderName = _getResolvedFolderName(folder.getName());

			if (!folderName.equals(MAIL_TRASH_NAME)) {
				moveMessages(ses, messageIds, MAIL_TRASH_NAME);
			}
			else {
				Message[] messages = folder.getMessagesByUID(messageIds);

				folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);

				folder.expunge();
			}
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static Object[] getAttachment(HttpSession ses, String contentPath)
		throws ContentException, ContentPathException, FolderException,
			   StoreException {

		Object[] parts = null;

		try {
			MailSessionLock.lock(ses.getId());

			String[] path = RemoteMailAttachment.parsePath(contentPath);

			String folderName = path[0];
			long messageId = GetterUtil.getLong(path[1]);
			String mimePath = path[2];

			IMAPFolder folder = _getFolder(ses, folderName);

			Message message = folder.getMessageByUID(messageId);

			parts = _getAttachmentFromPath(message, mimePath);
		}
		catch (ContentPathException cpe) {
			throw cpe;
		}
		catch (IOException ioe) {
			throw new ContentException(ioe);
		}
		catch (MessagingException me) {
			throw new ContentException(me);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}

		return parts;
	}

	public static Set getEnvelopes(HttpSession ses, Comparator comparator)
		throws FolderException {

		Set envelopes = new TreeSet(comparator);

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			String folderName = _getResolvedFolderName(folder.getName());

			Message[] messages = folder.getMessages();

			FetchProfile fetchProfile = new FetchProfile();

			fetchProfile.add(FetchProfile.Item.ENVELOPE);
			fetchProfile.add(FetchProfile.Item.FLAGS);

			folder.fetch(messages, fetchProfile);

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];

				if (message.isExpunged()) {
					continue;
				}

				MailEnvelope mailEnvelope = new MailEnvelope();

				mailEnvelope.setMessageId(folder.getUID(message));

				if (MAIL_SENT_NAME.equals(folderName) ||
					MAIL_DRAFTS_NAME.equals(folderName)) {

					Address[] recipients = message.getAllRecipients();

					StringBuffer sb = new StringBuffer();

					if (!Validator.isNull(recipients)) {
						for (int j = 0; j < recipients.length; j++) {
							InternetAddress address =
								(InternetAddress)recipients[j];

							String recipient = GetterUtil.getString(
								address.getPersonal(), address.getAddress());

							sb.append(recipient);

							if (j < (recipients.length - 1)) {
								sb.append(", ");
							}
						}
					}

					if (sb.length() > 0) {
						mailEnvelope.setRecipient(sb.toString());
					}
				}
				else {
					Address[] from = message.getFrom();

					if (from.length > 0) {
						InternetAddress address = (InternetAddress)from[0];

						String recipient = GetterUtil.getString(
							address.getPersonal(), address.getAddress());

						mailEnvelope.setRecipient(recipient);
					}
				}

				mailEnvelope.setSubject(message.getSubject());
				mailEnvelope.setDate(message.getSentDate());
				mailEnvelope.setRead(message.isSet(Flag.SEEN));
				mailEnvelope.setFlagged(message.isSet(Flag.FLAGGED));
				mailEnvelope.setAnswered(message.isSet(Flag.ANSWERED));

				envelopes.add(mailEnvelope);
			}

			return envelopes;
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static MailFolder getFolder(HttpSession ses)
		throws FolderException, MessagingException, StoreException {

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			List list = new ArrayList();

			_getFolders(list, new IMAPFolder[] {folder});

			return (MailFolder)list.get(0);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static String getFolderName(HttpSession ses)
		throws FolderException, StoreException {

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			return folder.getName();
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static List getFolders(HttpSession ses)
		throws FolderException, StoreException {

		List list = new ArrayList();

		IMAPFolder root = null;

		try {
			Store store = _getStore(ses);

			root = (IMAPFolder)store.getDefaultFolder();

			Folder[] folders = root.list();

			_getFolders(list, folders);
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			try {
				if ((root != null) && root.isOpen()) {
					root.close(false);
				}
			}
			catch (Exception ex) {
			}
		}

		return list;
	}

	public static MailMessage getMessage(HttpServletRequest req)
		throws ContentException, FolderException, StoreException {

		HttpSession ses = req.getSession();

		try {
			MailSessionLock.lock(ses.getId());

			long messageId = getMessageId(ses);
			if (messageId != -1L) {
				return getMessage(req, messageId);
			}
			else {
				return null;
			}
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static MailMessage getMessage(HttpServletRequest req, long messageId)
		throws ContentException, FolderException, StoreException {

		HttpSession ses = req.getSession();

		MailMessage mailMessage = new MailMessage();

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			Message message = folder.getMessageByUID(messageId);

			mailMessage.setMessageId(messageId);
			if (!Validator.isNull(message.getFrom())) {
				mailMessage.setFrom(message.getFrom()[0]);
			}
			mailMessage.setTo(message.getRecipients(RecipientType.TO));
			mailMessage.setCc(message.getRecipients(RecipientType.CC));
			mailMessage.setBcc(message.getRecipients(RecipientType.BCC));
			mailMessage.setReplyTo(message.getReplyTo());
			mailMessage.setSubject(message.getSubject());
			mailMessage.setSentDate(message.getSentDate());

			String contentPath = RemoteMailAttachment.buildContentPath(
				folder.getName(), messageId);

			mailMessage = _getContent(message, mailMessage, contentPath);

			_replaceContentIds(mailMessage, _getAttachmentURL(req));

			ses.setAttribute(WebKeys.MAIL_MESSAGE_ID, new Long(messageId));

			return mailMessage;
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static long getMessageId(HttpSession ses)
		throws ContentException, FolderException, StoreException {

		try {
			MailSessionLock.lock(ses.getId());

			Long messageId = (Long)ses.getAttribute(WebKeys.MAIL_MESSAGE_ID);

			if (messageId != null) {
				return messageId.longValue();
			}
			else {
				return -1L;
			}
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	public static void moveMessages(
			HttpSession ses, long[] messageIds, String toFolderName)
		throws FolderException, StoreException {

		IMAPFolder toFolder = null;

		toFolderName = _getResolvedFolderName(toFolderName);

		try {
			MailSessionLock.lock(ses.getId());

			IMAPFolder folder = _getFolder(ses);

			String folderName = _getResolvedFolderName(folder.getName());

			if (folderName.equals(toFolderName)) {
				return;
			}

			if ((folderName.equals(MAIL_DRAFTS_NAME) ||
					toFolderName.equals(MAIL_DRAFTS_NAME)) &&
				(!toFolderName.equals(MAIL_TRASH_NAME))) {

				throw new FolderException();
			}

			Store store = _getStore(ses);

			toFolder = (IMAPFolder)store.getFolder(toFolderName);

			toFolder.open(IMAPFolder.READ_WRITE);

			Message[] messages = folder.getMessagesByUID(messageIds);

			folder.copyMessages(messages, toFolder);

			folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);

			folder.expunge();
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
		finally {
			try {
				if ((toFolder != null) && toFolder.isOpen()) {
					toFolder.close(true);
				}
			}
			catch (Exception e) {
			}

			MailSessionLock.unlock(ses.getId());
		}
	}

	public static void removeFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

		try {
			folderName = _getResolvedFolderName(folderName);

			for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
				if (DEFAULT_FOLDERS[i].equals(folderName)) {
					_log.error(
						"Folder " + folderName +
							" is a system folder and cannot be changed");

					throw new FolderException();
				}
			}

			Store store = _getStore(ses);

			Folder folder = store.getFolder(folderName);

			if (!folder.exists()) {
				_log.error("Folder " + folderName + " does not exist");

				throw new FolderException();
			}

			folder.delete(true);
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
	}

	public static void renameFolder(
			HttpSession ses, String oldFolderName, String newFolderName)
		throws FolderException, StoreException {

		try {
			oldFolderName = _getResolvedFolderName(oldFolderName);
			newFolderName = _getResolvedFolderName(newFolderName);

			for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
				if (DEFAULT_FOLDERS[i].equals(oldFolderName)) {
					_log.error(
						"Folder " + oldFolderName +
							" is a system folder and cannot be changed");

					throw new FolderException();
				}
				else if (DEFAULT_FOLDERS[i].equals(newFolderName)) {
					_log.error(
						"Folder " + newFolderName +
							" is a system folder and cannot be changed");

					throw new FolderException();
				}
			}

			Store store = _getStore(ses);

			Folder oldFolder = store.getFolder(oldFolderName);
			Folder newFolder = store.getFolder(newFolderName);

			if (!oldFolder.exists()) {
				_log.error("Folder " + oldFolderName + " does not exist");

				throw new FolderException();
			}
			else if (newFolder.exists()) {
				_log.error("Folder " + newFolderName + " already exists");

				throw new FolderException();
			}

			if (!oldFolder.isOpen()) {
				oldFolder.open(Folder.READ_WRITE);
			}

			oldFolder.renameTo(newFolder);

			try {
				MailSessionLock.lock(ses.getId());

				Folder curFolder = _getFolder(ses);

				String folderName = _getResolvedFolderName(curFolder.getName());

				if (folderName.equals(oldFolderName)) {
					setFolder(ses, newFolderName);
				}
			}
			finally {
				MailSessionLock.unlock(ses.getId());
			}
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
	}

	public static void setFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

		try {
			MailSessionLock.lock(ses.getId());

			_getFolder(ses, folderName);
		}
		finally {
			MailSessionLock.unlock(ses.getId());
		}
	}

	protected static void cleanUp(HttpSession ses) throws StoreException {
		try {
			_closeFolder(ses);

			Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);

			if (store != null) {
				store.close();

				ses.removeAttribute(WebKeys.MAIL_STORE);
			}

			ses.removeAttribute(WebKeys.MAIL_MESSAGE_ID);
		}
		catch (MessagingException me) {
			throw new StoreException(me);
		}
	}

	private static void _closeFolder(HttpSession ses) {
		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);

		if ((folder != null) && folder.isOpen()) {
			try {
				folder.close(false);
			}
			catch (MessagingException me) {
				_log.warn(me);
			}

			ses.removeAttribute(WebKeys.MAIL_FOLDER);
		}
	}

	private static String _customizeHtml(String html) {
		for (int i = 0; i < _HTML_START_TAGS.length; i++) {
			Pattern startPattern = Pattern.compile(
				_HTML_START_TAGS[i], Pattern.CASE_INSENSITIVE);

			Matcher startMatcher = startPattern.matcher(html);

			while (startMatcher.find()) {
				int start = startMatcher.start();
				int end = html.indexOf(">", start);

				if (end == -1) {
					html = StringUtil.replace(
						html, html.substring(start), StringPool.BLANK);
				}
				else {
					html = StringUtil.replace(
						html, html.substring(start, end + 1), StringPool.BLANK);
				}

				if (i < _HTML_END_TAGS.length) {
					Pattern endPattern = Pattern.compile(
						_HTML_END_TAGS[i], Pattern.CASE_INSENSITIVE);

					Matcher endMatcher = endPattern.matcher(html);

					html = endMatcher.replaceFirst(StringPool.BLANK);
				}
				else {
					startMatcher.reset(html);
				}
			}
		}

		// TODO: create new window on hyperlink clicks and replace mailto with
		// action to compose new message

		return html.trim();
	}

	private static Object[] _getAttachmentFromPath(Part part, String mimePath)
		throws ContentPathException, IOException, MessagingException {

		int index = GetterUtil.getInteger(
			StringUtil.split(mimePath.substring(1), StringPool.PERIOD)[0]);

		if (part.getContent() instanceof Multipart) {
			String prefix = String.valueOf(index) + StringPool.PERIOD;

			Multipart multipart = (Multipart)part.getContent();

			for (int i = 0; i < multipart.getCount(); i++) {
				if (index == i) {
					return _getAttachmentFromPath(
						multipart.getBodyPart(i),
						mimePath.substring(prefix.length()));
				}
			}

			throw new ContentPathException();
		}
		else if (index != -1) {
			throw new ContentPathException();
		}

		InputStream is = part.getInputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[8192];
		int count = 0;

		while ((count = is.read(buffer)) >= 0) {
			baos.write(buffer,0,count);
		}

		is.close();

		Object[] parts = new Object[] {
			baos.toByteArray(), part.getContentType()
		};

		return parts;
	}

	private static String _getAttachmentURL(HttpServletRequest req) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() + "/mail/get_attachment?";
	}

	private static MailMessage _getContent(
			Part part, MailMessage mailMessage, String contentPath)
		throws ContentException {

		try {
			String contentType = part.getContentType().toLowerCase();

			boolean attachment = true;

			if (part.getContent() instanceof Multipart) {
				attachment = false;

				Multipart multipart = (Multipart)part.getContent();

				for (int i = 0; i < multipart.getCount(); i++) {
					Part curPart = multipart.getBodyPart(i);

					mailMessage = _getContent(
						curPart, mailMessage,
						contentPath + StringPool.PERIOD + i);
				}
			}
			else if (Validator.isNull(part.getFileName())) {
				attachment = false;

				if (contentType.startsWith(Constants.TEXT_PLAIN)) {
					mailMessage.appendPlainBody((String)part.getContent());
				}
				else if (contentType.startsWith(Constants.TEXT_HTML)) {
					mailMessage.appendHtmlBody(
						_customizeHtml((String)part.getContent()));
				}
				else if (contentType.startsWith(Constants.MESSAGE_RFC822)) {

					// FIX ME

				}
			}

			if (attachment) {
				mailMessage.appendRemoteAttachment(
					_getRemoteAttachment(
						part, contentPath + StringPool.PERIOD + -1));
			}
		}
		catch (IOException ioe) {
			throw new ContentException(ioe);
		}
		catch (MessagingException me) {
			throw new ContentException(me);
		}

		return mailMessage;
	}

	private static IMAPFolder _getFolder(HttpSession ses)
		throws FolderException {

		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);

		if (folder != null) {
			return folder;
		}
		else {
			throw new FolderException();
		}
	}

	private static IMAPFolder _getFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

		try {
			folderName = _getResolvedFolderName(folderName);

			IMAPFolder folder = (IMAPFolder)ses.getAttribute(
				WebKeys.MAIL_FOLDER);

			if (folder != null) {
				String currFolderName =
					_getResolvedFolderName(folder.getName());

				if (!currFolderName.equals(folderName)) {
					_closeFolder(ses);

					folder = null;
				}
				else if (!folder.isOpen()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"The folder is closed and needs to be reopened");
					}

					_closeFolder(ses);

					folder = null;
				}
			}

			if (folder == null) {
				String userId = (String)ses.getAttribute(WebKeys.USER_ID);

				String serviceName = userId + StringPool.COLON +
					WebKeys.MAIL_FOLDER + StringPool.PERIOD + folderName;

				Store store = _getStore(ses);

				folder = (IMAPFolder)store.getFolder(folderName);

				folder.addConnectionListener(
					new ConnectionListener(serviceName));

				folder.open(IMAPFolder.READ_WRITE);

				ses.setAttribute(WebKeys.MAIL_FOLDER, folder);

				ses.removeAttribute(WebKeys.MAIL_MESSAGE_ID);
			}

			return folder;
		}
		catch (MessagingException me) {
			throw new FolderException(me);
		}
	}

	private static void _getFolders(List list, Folder[] folders)
		throws MessagingException {

		for (int i = 0; i < folders.length; i++) {
			Folder folder = folders[i];

			int folderType = folder.getType();

			if ((folderType & IMAPFolder.HOLDS_MESSAGES) != 0) {
				MailFolder mailFolder = new MailFolder(
					folder.getName(), folder.getMessageCount(),
					folder.getUnreadMessageCount());

				list.add(mailFolder);
			}

			if ((folderType & IMAPFolder.HOLDS_FOLDERS) != 0) {
				_getFolders(list, folder.list());
			}
		}
	}

	private static RemoteMailAttachment _getRemoteAttachment(
			Part part, String contentPath)
		throws ContentException {

		RemoteMailAttachment remoteMailAttachment = new RemoteMailAttachment();

		try {
			remoteMailAttachment.setFilename(part.getFileName());
			remoteMailAttachment.setContentPath(contentPath);

			String[] contentId = part.getHeader(Constants.CONTENT_ID);

			if ((contentId != null) && (contentId.length == 1)) {
				remoteMailAttachment.setContentId(contentId[0]);
			}
		}
		catch (MessagingException me) {
			_log.error("Unable to properly get file name of MIME attachment");

			throw new ContentException(me);
		}

		return remoteMailAttachment;
	}

	private static String _getResolvedFolderName(String folderName) {
		String resolvedName = folderName;

		if (Validator.isNull(folderName)) {
			resolvedName = MAIL_INBOX_NAME;
		}
		else if (!folderName.equals(MAIL_INBOX_NAME) &&
				 !folderName.startsWith(MAIL_BOX_STYLE)) {

			resolvedName = MAIL_BOX_STYLE + folderName;
		}

		return resolvedName;
	}

	private static Store _getStore(HttpSession ses)
		throws FolderException, StoreException {

		try {
			Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);

			if (store != null && !store.isConnected()) {
				if (_log.isInfoEnabled()) {
					_log.info("The store needs to be reconnected");
				}

				cleanUp(ses);

				store = null;
			}

			if (store == null) {
				Session session = MailEngine.getSession();

				String imapHost = session.getProperty("mail.imap.host");

				String userId = (String)ses.getAttribute(WebKeys.USER_ID);

				String serviceName =
					userId + StringPool.COLON + WebKeys.MAIL_STORE;

				if (GetterUtil.getBoolean(
						PropsUtil.get(PropsUtil.MAIL_USERNAME_REPLACE))) {

					userId = StringUtil.replace(userId, ".", "_");
				}

				String password = (String)ses.getAttribute(
					WebKeys.USER_PASSWORD);

				store = session.getStore("imap");

				store.addConnectionListener(
					new ConnectionListener(serviceName));

				store.connect(imapHost, userId, password);

				ses.setAttribute(WebKeys.MAIL_STORE, store);

				List list = getFolders(ses);

				for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
					boolean exists = false;

					Iterator itr = list.iterator();

					while (itr.hasNext()) {
						MailFolder mailFolder = (MailFolder)itr.next();

						if (DEFAULT_FOLDERS[i].equals(mailFolder.getName())) {
							exists = true;

							break;
						}
					}

					if (!exists) {
						createFolder(ses, DEFAULT_FOLDERS[i]);
					}
				}

				if (ses.getAttribute(WebKeys.MAIL_FOLDER) == null) {
					setFolder(ses, MAIL_INBOX_NAME);
				}
			}

			return store;
		}
		catch (MessagingException me) {
			throw new StoreException(me);
		}
		catch (NamingException ne) {
			throw new StoreException(ne);
		}
	}

	private static void _replaceContentIds(
		MailMessage mailMessage, String url) {

		List list = mailMessage.getRemoteAttachments();

		String body = mailMessage.getHtmlBody();

		if (_log.isDebugEnabled()) {
			_log.debug("Body before replacing content ids\n" + body);
		}

		for (int i = 0; i < list.size(); i++) {
			RemoteMailAttachment remoteMailAttachment =
				(RemoteMailAttachment)list.get(i);

			if (Validator.isNotNull(remoteMailAttachment.getContentId())) {
				String contentId = remoteMailAttachment.getContentId();

				if (contentId.startsWith(StringPool.LESS_THAN) &&
					contentId.endsWith(StringPool.GREATER_THAN)) {

					contentId =
						"cid:" + contentId.substring(1, contentId.length() - 1);
				}

				String remotePath =
					url + "fileName=" + remoteMailAttachment.getFilename() +
						"&contentPath=" + remoteMailAttachment.getContentPath();

				body = StringUtil.replace(body, contentId, remotePath);

				list.remove(i);

				i--;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Body after replacing content ids\n" + body);
		}

		mailMessage.setHtmlBody(body);
	}

	private static void _replaceEmbeddedImages(
			HttpSession ses, MailMessage mailMessage, String url)
		throws ContentException, ContentPathException, FolderException,
			   StoreException {

		String prefix = ses.getId() + System.currentTimeMillis();

		int count = 0;

		String body = mailMessage.getHtmlBody();

		if (_log.isDebugEnabled()) {
			_log.debug("Body before replacing embedded images\n" + body);
		}

		int x = body.indexOf(url);

		while (x >= 0) {
			int y = body.indexOf("-1", x);

			if (y > 0) {
				y += 2;

				String attachmentPath = body.substring(x, y);

				String fileName = Http.getParameter(attachmentPath, "fileName");
				String contentPath = Http.getParameter(
					attachmentPath, "contentPath");

				String contentId = prefix + count;

				Object[] parts = getAttachment(ses, contentPath);

				MailAttachment mailAttachment = new MailAttachment();

				mailAttachment.setFilename(fileName);
				mailAttachment.setContent((byte[])parts[0]);
				mailAttachment.setContentType((String)parts[1]);
				mailAttachment.setContentId(
					StringPool.LESS_THAN + contentId + StringPool.GREATER_THAN);

				mailMessage.appendAttachment(mailAttachment);

				body = StringUtil.replace(
					body, attachmentPath, "cid:" + contentId);

				count++;

				x = body.indexOf(url);
			}
			else {
				x = body.indexOf(url, x + 1);
			}
		}

		if (count > 0) {
			mailMessage.setHtmlBody(body);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Body after replacing embedded images\n" + body);
		}
	}

	private static Address[] _resolveAddresses(
			HttpServletRequest req, Address[] addresses)
		throws RecipientException {

		Company company = null;

		try {
			company = PortalUtil.getCompany(req);
		}
		catch (Exception e) {
			return addresses;
		}

		for (int i = 0; i < addresses.length; i++) {
			InternetAddress address = (InternetAddress)addresses[i];

			if ((address.getPersonal() == null) &&
				!Validator.isEmailAddress(address.getAddress())) {

				try {
					User user = UserLocalServiceUtil.getUserByEmailAddress(
						company.getCompanyId(), address.getAddress().trim() +
							StringPool.AT + company.getMx());

					addresses[i] = new InternetAddress(
						user.getEmailAddress(), user.getFullName());
				}
				catch (Exception e) {
					_log.error(
						"Problems found trying to resolve email address " +
							address);

					throw new RecipientException(e);
				}
			}
		}

		return addresses;
	}

	private static final String[] _HTML_START_TAGS = new String[] {
		"<html", "<head", "<body", "<meta", "<o:SmartTagType"
	};

	private static final String[] _HTML_END_TAGS = new String[] {
		"</html>", "</head>", "</body>"
	};

	private static Log _log = LogFactory.getLog(MailUtil.class);

}