<%@ page import="com.liferay.mail.service.MailServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.mail.MailMessage" %>
<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>

<%@ page import="javax.mail.internet.InternetAddress" %>

<%!
    private boolean isTestMode() {
        return true;
    }

    private InternetAddress getSenderAddress() throws Exception {
        return new InternetAddress("community@developer.cisco.com",
                "Cisco Developer Comunity");
    }

    private InternetAddress[] getReplyTos() throws Exception {
        return new InternetAddress[] { getSenderAddress()};
    }

    private String getMessageFileName() {
        return "mail.html";
    }

    private String getSubject() {
        return "Cisco Developer Community Announcements";
    }

    private boolean isHtmlFormat() {
        return true;
    }

    private int getBatchSize() {
        return 10;
    }

    public int getNumberUsers() throws Exception {
        return UserLocalServiceUtil.getUsersCount();
    }

    private Set<InternetAddress> getAllRecipients(int start, int end)
            throws Exception {
        List<User> users = UserLocalServiceUtil.getUsers(start, end);
        Set<InternetAddress> addresses = new HashSet<InternetAddress>(users.size());
        for (User user : users) {
            addresses.add(new InternetAddress(user.getEmailAddress()));
        }
        return addresses;
    }

    private String readMessageBody(InputStream in) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String text = null;
        while ((text = reader.readLine()) != null) {
            builder.append(text);
        }
        return builder.toString();
    }

%>

<%
    InternetAddress sender = getSenderAddress();
    InternetAddress[] replyTo = getReplyTos();
    String subject = getSubject();
    String messageBody =
            readMessageBody(pageContext.getServletContext().getResourceAsStream(getMessageFileName()));

    int totalUsers = getNumberUsers();
    int batchSize = getBatchSize();
    int numBatches = (int)(totalUsers / batchSize) + 1;
    if (isTestMode()) {
        out.println("<b>Running in Test Mode</b><br>");
    }
    out.println("<b>From :</b> " + sender.getPersonal() + " [" + sender.getAddress() + "] <br>");
    out.println("<b>Reply To:</b> " + replyTo + " <br>");
    out.println("<b>Sending message:</b> " + subject + " <br>");
    out.println(messageBody + " <br>");
    out.println("<b>End message</b><br>");
    out.println("<b>Found : " + totalUsers +
            " in the system; notifying in " + numBatches +
            " batches of " + batchSize + "</b><br>");
    int currentBatch = 0;
    do {
        int end = currentBatch * batchSize + batchSize;
        Set<InternetAddress> recipients =
                getAllRecipients(currentBatch * batchSize, end);
        currentBatch++;
        for (InternetAddress recipient : recipients) {
            MailMessage notification =
                    new MailMessage(sender, recipient, subject,
                            messageBody, isHtmlFormat());
            notification.setReplyTo(replyTo);
            out.println("Sending message to: " + recipient +"<br>");
            if (!isTestMode()) {
                MailServiceUtil.sendEmail(notification);
            }
        }
        out.println("<b>Done with batch " + currentBatch + "</b><br>" );
    }
    while (currentBatch < numBatches);

%>