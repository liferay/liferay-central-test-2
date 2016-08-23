<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html lang="en">
		<head>
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
			<meta charset="utf-8" />
			<meta content="IE=edge" http-equiv="X-UA-Compatible" />
			<meta content="initial-scale=1, width=device-width" name="viewport" />
			<style>
				body {
					padding: 10px;
				}

				th.version {
					border-top: none !important;
					font-size: 20px;
					font-weight: bold;
					height: 60px;
					vertical-align: bottom !important;
				}
			</style>


			<title>Liferay Third Party Libraries</title>
		</head>
		<body>
			<div id="container">
				<img alt="Liferay" src="data:image/png;base64,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" />

				<h1>Third Party Software List</h1>

				<table class="table table-condensed">
					<xsl:for-each select="versions/version">
						<tr>
							<th class="version" colspan="5">
								<xsl:value-of select="@name" />
							</th>
						</tr>

						<xsl:choose>
							<xsl:when test="libraries">
								<tr>
									<th>
										File Name
									</th>
									<th>
										Version
									</th>
									<th>
										Project
									</th>
									<th>
										License
									</th>
									<th>
										Comments
									</th>
								</tr>

								<xsl:apply-templates />
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<td colspan="5">
										<i>There were no third party library changes in this version.</i>
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</table>
			</div>

			<h4>Written Offer for Source Code</h4>

			<p>
				For binaries that you receive from Liferay that are licensed under any version of the GNU General Public License (GPL) or the GNU LGPL, you may receive a complete machine readable copy of the source code by either downloading it from the binary's website or sending a written request to:
			</p>

			<address>
				<b>Liferay, Inc.</b><br />
				Attn: Legal<br />
				1400 Montefino Ave<br />
				Diamond Bar, CA 91765<br />
			</address>

			<p>
				Your request should include: (i) the name of the covered binary, (ii) the name and version number of the Liferay product containing the covered binary, (iii) your name, (iv) your company name (if applicable) and (v) your return mailing and email address (if available).
			</p>

			<p>
				We may charge you a nominal fee to cover the cost of the media and distribution.
			</p>

			<p>
				Your request must be sent within three years of the date you received the GPL or LGPL covered code.
			</p>
		</body>

		</html>
	</xsl:template>

	<xsl:template match="library">
		<tr>
			<td nowrap="nowrap">
				<xsl:value-of select="file-name" />
			</td>
			<td nowrap="nowrap">
				<xsl:value-of select="version" />
			</td>
			<td nowrap="nowrap">
				<a>
					<xsl:attribute name="href">
						<xsl:value-of disable-output-escaping="yes" select="project-url" />
					</xsl:attribute>
					<xsl:value-of select="project-name" />
				</a>
			</td>
			<td nowrap="nowrap">
				<xsl:apply-templates select="licenses/license" />
			</td>
			<td>
				<xsl:value-of select="comments" />
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="licenses/license">
		<a>
			<xsl:attribute name="href">
				<xsl:value-of disable-output-escaping="yes" select="license-url" />
			</xsl:attribute>
			<xsl:value-of select="license-name" />
		</a>

		<xsl:if test="copyright-notice">
			<br />

			<xsl:variable name="copyrightNotice" select="copyright-notice" />

			<xsl:copy-of select="$copyrightNotice" />
		</xsl:if>

		<br />
	</xsl:template>
</xsl:stylesheet>