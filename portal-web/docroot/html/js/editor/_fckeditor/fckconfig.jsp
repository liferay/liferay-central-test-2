<%
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
%>

<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PropsKeys" %>
<%@ page import="com.liferay.portal.util.PropsUtil" %>

<%
long plid = ParamUtil.getLong(request, "p_l_id");
String mainPath = ParamUtil.getString(request, "p_main_path");
String doAsUserId = ParamUtil.getString(request, "doAsUserId");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");

String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId));

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

FCKConfig.IncludeLatinEntities	= false ;

FCKConfig.ToolbarSets["liferay"] = [
	['Style', 'FontSize', '-', 'TextColor', 'BGColor'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Find', 'Replace', 'SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	'/',
	['Source'],
	['Link', 'Unlink', 'Anchor'],
	['Image', 'Flash', 'Table', '-', 'Smiley', 'SpecialChar']
];

FCKConfig.ToolbarSets["liferay-article"] = [
	['Style', 'FontSize', '-', 'TextColor', 'BGColor'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Find', 'Replace', 'SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	'/',
	['Source'],
	['Link', 'Unlink', 'Anchor'],
	['Image', 'Flash', 'Table', '-', 'Smiley', 'SpecialChar', 'LiferayPageBreak']
];

FCKConfig.ToolbarSets["edit-in-place"] = [
	['Style'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript', 'SpecialChar'],
	['Undo', 'Redo'],
	['SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'], ['Source', 'RemoveFormat'],
];

FCKConfig.ToolbarSets["email"] = [
	['FontSize', 'TextColor', 'BGColor', '-', 'Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	['SpellCheck'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Source'],
	['Link', 'Unlink'],
	['Image']
];

FCKConfig.BackgroundBlockerColor = '#000' ;
FCKConfig.BackgroundBlockerOpacity = 0.70 ;

FCKConfig.BodyClass = 'html-editor <%= cssClasses %>' ;
FCKConfig.CustomStyles = {};
FCKConfig.StylesXmlPath = FCKConfig.EditorPath + 'fckstyles.xml' ;
FCKConfig.EditorAreaCSS = '<%= HtmlUtil.escape(cssPath) %>/main.css' ;

FCKConfig.LinkBrowserURL = FCKConfig.BasePath + "filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>";
FCKConfig.ImageBrowserURL = FCKConfig.BasePath + "filemanager/browser/liferay/browser.html?Type=Image&Connector=<%= connectorURL %>";
FCKConfig.FlashBrowser = false ;
FCKConfig.LinkUpload = false ;
FCKConfig.ImageUpload = false ;
FCKConfig.FlashUpload = false ;

var sOtherPluginPath = FCKConfig.BasePath.substr(0, FCKConfig.BasePath.length - 7) + 'editor/plugins/' ;

var _TOKEN_PAGE_BREAK = '<%= _TOKEN_PAGE_BREAK %>';

FCKConfig.Plugins.Add('liferaypagebreak', null, sOtherPluginPath ) ;

FCKConfig.ProtectedSource.Add(/<[\/]{0,1}(article|aside|audio|canvas|command|datalist|details|dialog|embed|figure|footer|header|hgroup|keygen|mark|meter|nav|output|progress|rp|rt|ruby|script|section|time|video).*?>/gi);

<%!
private static final String _TOKEN_PAGE_BREAK = PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);
%>