<%@ page import="com.liferay.portal.kernel.bi.reporting.ReportRequest" %>
<%@ page import="com.liferay.portal.kernel.bi.reporting.ReportFormat" %>
<%@ page
	import="com.liferay.portal.kernel.bi.reporting.ContextClassloaderReportDesignRetriever" %>
<%@ page import="com.liferay.portal.kernel.bi.reporting.MemoryReportDesignRetriever" %>
<%@ page import="com.liferay.portal.kernel.messaging.MessageBusUtil" %>
<%@ page import="com.liferay.portal.kernel.messaging.Message" %>
<%@ page import="com.liferay.portal.kernel.bi.reporting.ReportResultContainer" %>
<%

	String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<!--\n" +
"  ~ Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.\n" +
"  ~\n" +
"  ~ Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
"  ~ of this software and associated documentation files (the \"Software\"), to deal\n" +
"  ~ in the Software without restriction, including without limitation the rights\n" +
"  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
"  ~ copies of the Software, and to permit persons to whom the Software is\n" +
"  ~ furnished to do so, subject to the following conditions:\n" +
"  ~\n" +
"  ~ The above copyright notice and this permission notice shall be included in\n" +
"  ~ all copies or substantial portions of the Software.\n" +
"  ~\n" +
"  ~ THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
"  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
"  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
"  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
"  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
"  ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
"  ~ SOFTWARE.\n" +
"  -->\n" +
"\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"user_activity\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">\n" +
"\t<queryString language=\"SQL\">\n" +
"\t\t<![CDATA[SELECT\n" +
"     contact_.`userName` AS contact__userName,\n" +
"     contact_.`firstName` AS contact__firstName,\n" +
"     contact_.`lastName` AS contact__lastName,\n" +
"     user_.`emailAddress` AS user__emailAddress,\n" +
"     user_.`active_` AS user__active_,\n" +
"     contact_.`facebookSn` AS contact__facebookSn\n" +
"FROM\n" +
"     `user_` user_,\n" +
"     `contact_` contact_\n" +
"WHERE\n" +
"     contact_.userId = user_.userId]]>\n" +
"\t</queryString>\n" +
"\t<field name=\"contact__userName\" class=\"java.lang.String\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<field name=\"contact__firstName\" class=\"java.lang.String\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<field name=\"contact__lastName\" class=\"java.lang.String\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<field name=\"user__emailAddress\" class=\"java.lang.String\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<field name=\"user__active_\" class=\"java.lang.Integer\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<field name=\"contact__facebookSn\" class=\"java.lang.String\">\n" +
"\t\t<fieldDescription><![CDATA[]]></fieldDescription>\n" +
"\t</field>\n" +
"\t<background>\n" +
"\t\t<band/>\n" +
"\t</background>\n" +
"\t<title>\n" +
"\t\t<band height=\"58\">\n" +
"\t\t\t<line>\n" +
"\t\t\t\t<reportElement x=\"0\" y=\"8\" width=\"555\" height=\"1\" />\n" +
"\t\t\t</line>\n" +
"\t\t\t<line>\n" +
"\t\t\t\t<reportElement positionType=\"FixRelativeToBottom\" x=\"0\" y=\"51\" width=\"555\" height=\"1\" />\n" +
"\t\t\t</line>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement x=\"65\" y=\"13\" width=\"424\" height=\"35\" />\n" +
"\t\t\t\t<textElement textAlignment=\"Center\">\n" +
"\t\t\t\t\t<font size=\"26\" isBold=\"true\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[Classic template]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t</band>\n" +
"\t</title>\n" +
"\t<pageHeader>\n" +
"\t\t<band/>\n" +
"\t</pageHeader>\n" +
"\t<columnHeader>\n" +
"\t\t<band height=\"18\">\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"82\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[User Name]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"82\" y=\"0\" width=\"89\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[Email Address]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"171\" y=\"0\" width=\"39\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[Active]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"210\" y=\"0\" width=\"138\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[Last Name]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"210\" y=\"0\" width=\"138\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[First Name]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t\t<staticText>\n" +
"\t\t\t\t<reportElement mode=\"Opaque\" x=\"210\" y=\"0\" width=\"138\" height=\"18\" forecolor=\"#FFFFFF\" backcolor=\"#999999\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<text><![CDATA[Facebook Screen Name]]></text>\n" +
"\t\t\t</staticText>\n" +
"\t\t</band>\n" +
"\t</columnHeader>\n" +
"\t<detail>\n" +
"\t\t<band height=\"20\">\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"0\" y=\"0\" width=\"138\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{contact__userName}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"82\" y=\"0\" width=\"194\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{user__emailAddress}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"276\" y=\"0\" width=\"138\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.Integer\"><![CDATA[$F{user__active_}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"414\" y=\"0\" width=\"138\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{contact__firstName}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"414\" y=\"0\" width=\"138\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{contact__lastName}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField>\n" +
"\t\t\t\t<reportElement x=\"414\" y=\"0\" width=\"138\" height=\"20\" />\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"12\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{contact__facebookSn}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t</band>\n" +
"\t</detail>\n" +
"\t<columnFooter>\n" +
"\t\t<band/>\n" +
"\t</columnFooter>\n" +
"\t<pageFooter>\n" +
"\t\t<band height=\"26\">\n" +
"\t\t\t<textField evaluationTime=\"Report\" pattern=\"\" isBlankWhenNull=\"false\">\n" +
"\t\t\t\t<reportElement key=\"textField\" x=\"516\" y=\"6\" width=\"36\" height=\"19\" forecolor=\"#000000\" backcolor=\"#FFFFFF\" />\n" +
"\t\t\t\t<box>\n" +
"\t\t\t\t\t<topPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<leftPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<bottomPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<rightPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t</box>\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"10\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[\"\" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField pattern=\"\" isBlankWhenNull=\"false\">\n" +
"\t\t\t\t<reportElement key=\"textField\" x=\"342\" y=\"6\" width=\"170\" height=\"19\" forecolor=\"#000000\" backcolor=\"#FFFFFF\" />\n" +
"\t\t\t\t<box>\n" +
"\t\t\t\t\t<topPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<leftPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<bottomPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<rightPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t</box>\n" +
"\t\t\t\t<textElement textAlignment=\"Right\">\n" +
"\t\t\t\t\t<font size=\"10\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.lang.String\"><![CDATA[\"Page \" + $V{PAGE_NUMBER} + \" of \"]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t\t<textField pattern=\"\" isBlankWhenNull=\"false\">\n" +
"\t\t\t\t<reportElement key=\"textField\" x=\"1\" y=\"6\" width=\"209\" height=\"19\" forecolor=\"#000000\" backcolor=\"#FFFFFF\" />\n" +
"\t\t\t\t<box>\n" +
"\t\t\t\t\t<topPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<leftPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<bottomPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t\t<rightPen lineWidth=\"0.0\" lineStyle=\"Solid\" lineColor=\"#000000\" />\n" +
"\t\t\t\t</box>\n" +
"\t\t\t\t<textElement>\n" +
"\t\t\t\t\t<font size=\"10\" />\n" +
"\t\t\t\t</textElement>\n" +
"\t\t\t\t<textFieldExpression class=\"java.util.Date\"><![CDATA[new Date()]]></textFieldExpression>\n" +
"\t\t\t</textField>\n" +
"\t\t</band>\n" +
"\t</pageFooter>\n" +
"\t<summary>\n" +
"\t\t<band/>\n" +
"\t</summary>\n" +
"</jasperReport>";
	MemoryReportDesignRetriever retriever =
		new MemoryReportDesignRetriever("sample_user_activity.jrxml", test.getBytes());
	ReportRequest reportReq =
		new ReportRequest(retriever, ReportFormat.HTML.toString(), "c/temp", "c:/temp");
	Message message = new Message();
	message.setPayload(reportReq);
	message.setResponseId("123");
	message.setResponseDestination("liferay/report_results");
	message.setDestination("liferay/report_requests");
	ReportResultContainer reportResponse =
		(ReportResultContainer)MessageBusUtil.sendSynchronousMessage(message.getDestination(), message);
	out.println(new String(reportResponse.getResults()));
	//MessageBusUtil.sendMessage(message.getDestination(), message);



%>