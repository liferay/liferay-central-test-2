/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import com.liferay.portal.util.FileImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class Test {

	public static void main(String[] args) throws Exception {
		//com.liferay.portal.util.InitUtil.initWithSpring();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 250; i++) {
			sb.append("\t\t<D0000>\n");
			sb.append("\t\t\t<Alias dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<AudioMode dt:dt=\"ui4\">0</AudioMode>\n");
			sb.append("\t\t\t<AudioRec dt:dt=\"ui4\">0</AudioRec>\n");
			sb.append("\t\t\t<BackupServer dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<BitmapCaching dt:dt=\"ui4\">1</BitmapCaching>\n");
			sb.append("\t\t\t<CBanDelay dt:dt=\"ui4\">5</CBanDelay>\n");
			sb.append("\t\t\t<ClntOpts dt:dt=\"ui4\">0</ClntOpts>\n");
			sb.append("\t\t\t<ColorDepth dt:dt=\"ui4\">2</ColorDepth>\n");
			sb.append("\t\t\t<Compress dt:dt=\"ui4\">1</Compress>\n");
			sb.append("\t\t\t<ComputerName dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<Conn0000>\n");
			sb.append("\t\t\t\t<Mode dt:dt=\"ui4\">4</Mode>\n");
			sb.append("\t\t\t\t<Server dt:dt=\"string\">qa-1</Server>\n");
			sb.append("\t\t\t\t<ServerPort dt:dt=\"ui4\">3389</ServerPort>\n");
			sb.append("\t\t\t\t<tmpgw dt:dt=\"ui4\">80</tmpgw>\n");
			sb.append("\t\t\t\t<tmpssl dt:dt=\"ui4\">443</tmpssl>\n");
			sb.append("\t\t\t</Conn0000>\n");
			sb.append("\t\t\t<ConnConsole dt:dt=\"ui4\">0</ConnConsole>\n");
			sb.append("\t\t\t<ConnTimeout dt:dt=\"ui4\">20</ConnTimeout>\n");
			sb.append("\t\t\t<CreateShrtCut dt:dt=\"ui4\">1</CreateShrtCut>\n");
			sb.append("\t\t\t<DesktopBackground dt:dt=\"ui4\">0</DesktopBackground>\n");
			sb.append("\t\t\t<DesktopComposition dt:dt=\"ui4\">0</DesktopComposition>\n");
			sb.append("\t\t\t<DisplayBar dt:dt=\"ui4\">1</DisplayBar>\n");
			sb.append("\t\t\t<Domain dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<EmbedDesktop dt:dt=\"ui4\">1</EmbedDesktop>\n");
			sb.append("\t\t\t<EnableAutoLogon dt:dt=\"ui4\">0</EnableAutoLogon>\n");
			sb.append("\t\t\t<EnableReconnection dt:dt=\"ui4\">1</EnableReconnection>\n");
			sb.append("\t\t\t<FolderID dt:dt=\"ui4\">4294967295</FolderID>\n");
			sb.append("\t\t\t<FontSmoothing dt:dt=\"ui4\">0</FontSmoothing>\n");
			sb.append("\t\t\t<FrmOpts dt:dt=\"ui4\">0</FrmOpts>\n");
			sb.append("\t\t\t<Height dt:dt=\"ui4\">0</Height>\n");
			sb.append("\t\t\t<ID dt:dt=\"ui4\">1</ID>\n");
			sb.append("\t\t\t<IconIndex dt:dt=\"ui4\">0</IconIndex>\n");
			sb.append("\t\t\t<IconPath dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<Keyboard dt:dt=\"ui4\">1</Keyboard>\n");
			sb.append("\t\t\t<MailRedirection dt:dt=\"ui4\">0</MailRedirection>\n");
			sb.append("\t\t\t<Mode dt:dt=\"ui4\">4</Mode>\n");
			sb.append("\t\t\t<PassUnicode dt:dt=\"ui4\">1</PassUnicode>\n");
			sb.append("\t\t\t<Password dt:dt=\"bin.base64\">yZWliSgBiNgV8PhRapuhXPikVI9N7pMb</Password>\n");
			sb.append("\t\t\t<Password_Proxy dt:dt=\"bin.base64\">NwdhnvmVl5I=</Password_Proxy>\n");
			sb.append("\t\t\t<ProgramPath dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<ProxyAuthentication dt:dt=\"ui4\">0</ProxyAuthentication>\n");
			sb.append("\t\t\t<ProxyHost dt:dt=\"string\">localhost</ProxyHost>\n");
			sb.append("\t\t\t<ProxyPort dt:dt=\"ui4\">9999</ProxyPort>\n");
			sb.append("\t\t\t<ProxyType dt:dt=\"ui4\">2</ProxyType>\n");
			sb.append("\t\t\t<ProxyUseLogonCredentials dt:dt=\"ui4\">0</ProxyUseLogonCredentials>\n");
			sb.append("\t\t\t<ProxyUsername dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<ReCBanDelay dt:dt=\"ui4\">5</ReCBanDelay>\n");
			sb.append("\t\t\t<RedirectCOMPorts dt:dt=\"ui4\">0</RedirectCOMPorts>\n");
			sb.append("\t\t\t<RedirectDrives dt:dt=\"ui4\">0</RedirectDrives>\n");
			sb.append("\t\t\t<RedirectPrinters dt:dt=\"ui4\">0</RedirectPrinters>\n");
			sb.append("\t\t\t<RedirectSmartCards dt:dt=\"ui4\">0</RedirectSmartCards>\n");
			sb.append("\t\t\t<RegisterExt dt:dt=\"ui4\">1</RegisterExt>\n");
			sb.append("\t\t\t<Retries dt:dt=\"ui4\">10</Retries>\n");
			sb.append("\t\t\t<SSLMethod dt:dt=\"ui4\">0</SSLMethod>\n");
			sb.append("\t\t\t<SSO dt:dt=\"ui4\">1</SSO>\n");
			sb.append("\t\t\t<SWTimeout dt:dt=\"ui4\">5</SWTimeout>\n");
			sb.append("\t\t\t<SavePassword dt:dt=\"ui4\">1</SavePassword>\n");
			sb.append("\t\t\t<Server dt:dt=\"string\">qa-1</Server>\n");
			sb.append("\t\t\t<ServerPort dt:dt=\"ui4\">3389</ServerPort>\n");
			sb.append("\t\t\t<SettID dt:dt=\"ui4\">0</SettID>\n");
			sb.append("\t\t\t<ShowContent dt:dt=\"ui4\">0</ShowContent>\n");
			sb.append("\t\t\t<SmartSizing dt:dt=\"ui4\">0</SmartSizing>\n");
			sb.append("\t\t\t<SpanDesktops dt:dt=\"ui4\">0</SpanDesktops>\n");
			sb.append("\t\t\t<StartFolder dt:dt=\"string\"/>\n");
			sb.append("\t\t\t<StartPrograms dt:dt=\"ui4\">0</StartPrograms>\n");
			sb.append("\t\t\t<TSAuth dt:dt=\"ui4\">0</TSAuth>\n");
			sb.append("\t\t\t<Themes dt:dt=\"ui4\">0</Themes>\n");
			sb.append("\t\t\t<UrlRedirection dt:dt=\"ui4\">0</UrlRedirection>\n");
			sb.append("\t\t\t<UseAllMonitors dt:dt=\"ui4\">0</UseAllMonitors>\n");
			sb.append("\t\t\t<UseAvailSize dt:dt=\"ui4\">0</UseAvailSize>\n");
			sb.append("\t\t\t<UseClientColors dt:dt=\"ui4\">1</UseClientColors>\n");
			sb.append("\t\t\t<UseClientSettings dt:dt=\"ui4\">1</UseClientSettings>\n");
			sb.append("\t\t\t<UsePrimaryMonitor dt:dt=\"ui4\">0</UsePrimaryMonitor>\n");
			sb.append("\t\t\t<UseProxyServer dt:dt=\"ui4\">1</UseProxyServer>\n");
			sb.append("\t\t\t<UserName dt:dt=\"string\">Administrator</UserName>\n");
			sb.append("\t\t\t<Width dt:dt=\"ui4\">0</Width>\n");
			sb.append("\t\t\t<WindowMenuAnimation dt:dt=\"ui4\">0</WindowMenuAnimation>\n");
			sb.append("\t\t\t<tmpdirect dt:dt=\"ui4\">80</tmpdirect>\n");
			sb.append("\t\t\t<tmpgw dt:dt=\"ui4\">80</tmpgw>\n");
			sb.append("\t\t\t<tmpssl dt:dt=\"ui4\">443</tmpssl>\n");
			sb.append("\t\t</D0000>\n");
		}

		_fileUtil.write("content.txt", sb.toString());
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}