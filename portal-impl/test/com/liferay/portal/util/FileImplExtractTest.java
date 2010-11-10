package com.liferay.portal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * <a href="FileImplExtractTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class FileImplExtractTest extends junit.framework.TestCase {

	public void testDocxExtract() {
		String extractedText = _extractText("test2007.docx");
		assertEquals("Extract test.", extractedText);

		 extractedText = _extractText("test2010.docx");
		assertEquals("_GoBack\nExtract test.", extractedText);
	}

	public void testHtmlExtract() {
		String extractedText = _extractText("test.html");
		assertEquals("Extract test.", extractedText);
	}

	public void testOdtExtract() {
		String extractedText = _extractText("test.odt");
		assertEquals("Extract test.", extractedText);
	}

	public void testPdfExtract() {
		String extractedText = _extractText("test2(word2010).pdf");
		assertEquals("Extract test.", extractedText);

		// https://issues.apache.org/jira/browse/PDFBOX-890
		//extractedText = _extractText("test.pdf");
		//assertEquals("Extract test.", extractedText);
	}

	public void testPptExtract() {
		String extractedText = _extractText("test2010.pptx");
		assertEquals("Extract \ntest.", extractedText);
	}

	public void testRtfExtract() {
		String extractedText = _extractText("test.rtf");
		assertEquals("Extract  test.", extractedText);
	}

	public void testTxtExtract() {
		String extractedText = _extractText("test.txt");
		assertEquals("Extract test.", extractedText);
	}

	public void testXlsxExtract() {
		String extractedText = _extractText("test2010.xlsx");
		assertEquals("Sheet1\n\tExtract test.", extractedText);
	}

	public void testXmlExtract() {
		String extractedText = _extractText("test.xml");
		assertEquals("<test>Extract test.</test>", extractedText);
	}
	
	private String _extractText(String fileName) {
		FileInputStream fis;

		try {
			fis = new FileInputStream(_TEST_FOLDER + fileName);
		}
		catch (FileNotFoundException fnfex) {
			return null;
		}

		FileImpl fi = new FileImpl();

		return fi.extractText(fis, fileName).trim();
	}

	private static final String _TEST_FOLDER =
		"portal-impl/test/com/liferay/portal/util/dependencies/";

}
