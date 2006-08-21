/**
 * Copyright (c) 2000-2005 Liferay, LLC. All rights reserved.
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

import com.liferay.util.FileUtil;
import com.liferay.util.Html;
import com.liferay.util.StringUtil;

import java.io.*;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <a href="MusicBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @version $Revision: 1.13 $
 *
 */
public class MusicBuilder {

	public MusicBuilder(String rootDir) {
		try {
			_rootDir = rootDir;

			_createCategories(new File(_rootDir));
			_createSongsBatList(new File(_rootDir));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MusicBuilder(args[0]);
	}

	private void _createAlbums(File file)
		throws IOException, TransformerException {

		String artist = _formatMP3(file.getName());
		String category = file.getParentFile().getName();

		String albums =
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
			"\n" +
			"<albums" +
			" artist=\"" + artist + "\"" +
			" category=\"" + category + "\"" +
			">\n";

		File[] files = file.listFiles();

		Arrays.sort(files);

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				String albumName = _formatTitle(files[i].getName());

				albums +=
					"\t<album" +
					" file-name=\"" + albumName + "\"" +
					" name=\"" + _formatMP3(albumName) + "\"" +
					" />\n";

				files[i].renameTo(new File(_formatTitle(files[i].toString())));

				_createSongs(new File(files[i].getPath() + "\\_songs.m3u"));
			}
		}

		albums += "</albums>";

		File albumsXml = new File(file.getPath() + "\\_albums.xml");

		FileUtil.write(albumsXml, albums);

		System.out.println(file.toString());

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer(
			new StreamSource(_rootDir + "/_albums.xsl"));

		trans.transform(
			new StreamSource(albumsXml),
			new StreamResult(
				new FileOutputStream(file.getPath() + "\\_index.html")));
	}

	private void _createArtists(File file)
		throws IOException, TransformerException {

		String category = file.getName();

		String artistsXml =
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
			"\n" +
			"<artists" +
			" category=\"" + category + "\"" +
			">\n";

		File[] files = file.listFiles();

		Arrays.sort(files);

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				artistsXml +=
					"\t<artist" +
					" file-name=\"" + files[i].getName() + "\"" +
					" name=\"" + _formatMP3(files[i].getName()) + "\"" +
					" />\n";
				_createAlbums(files[i]);
			}
		}

		artistsXml += "</artists>";

		File artistsXmlFile = new File(file.getPath() + "\\_artists.xml");

		FileUtil.write(artistsXmlFile, artistsXml);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer(
			new StreamSource(_rootDir + "/_artists.xsl"));

		trans.transform(
			new StreamSource(artistsXmlFile),
			new StreamResult(
				new FileOutputStream(file.getPath() + "\\_index.html")));
	}

	private void _createCategories(File file)
		throws IOException, TransformerException {

		String categoriesXml =
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
			"\n" +
			"<categories" +
			">\n";

		File[] files = file.listFiles();

		Arrays.sort(files);

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory() &&
				!files[i].getName().equals("WEB-INF")) {

				categoriesXml +=
					"\t<category" +
					" file-name=\"" + files[i].getName() + "\"" +
					" name=\"" + _formatMP3(files[i].getName()) + "\"" +
					" />\n";
				_createArtists(files[i]);
			}
		}

		categoriesXml += "</categories>";

		File categoriesXmlFile = new File(file.getPath() + "\\_categories.xml");

		FileUtil.write(categoriesXmlFile, categoriesXml);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer(
			new StreamSource(_rootDir + "/_categories.xsl"));

		trans.transform(
			new StreamSource(categoriesXmlFile),
			new StreamResult(
				new FileOutputStream(file.getPath() + "\\_index.html")));
	}

	private void _createSongs(File file)
		throws IOException, TransformerException {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);

		String album = _formatMP3(file.getParentFile().getName());
		String artist = _formatMP3(
			file.getParentFile().getParentFile().getName());
		String category =
			file.getParentFile().getParentFile().getParentFile().getName();

		int track = 0;

		List songsList = new ArrayList();

		String songsPlayList = "";

		String songsXml =
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
			"\n" +
			"<songs" +
			" album=\"" + album + "\"" +
			" artist=\"" + artist + "\"" +
			" category=\"" + category + "\"" +
			">\n";

		String genre = "12";

		if (category.equals("Bible")) {
			genre = "87";
		}
		else if (category.equals("Country")) {
			genre = "2";
		}
		else if (category.equals("Christian")) {
			genre = "38";
		}
		else if (category.equals("Classical")) {
			genre = "32";
		}
		else if (category.equals("Foreign")) {
			genre = "86";
		}
		else if (category.equals("Popular")) {
			genre = "13";
		}
		else if (category.equals("Sermons")) {
			genre = "101";
		}
		else if (category.equals("Soundtracks")) {
			genre = "24";
		}
		
		String songsBat = "@echo off\n";
		songsBat += "@echo " + file.toString() + "\n";

		BufferedReader br =
			new BufferedReader(new StringReader(FileUtil.read(file)));

		String line = "";

		while ((line = br.readLine()) != null) {
			songsList.add(_formatTitle(line.substring(0, line.length() - 4)));
		}

		for (int i = 0; i < songsList.size(); i++) {
			String songFileName = (String)songsList.get(i);

			songsPlayList += songFileName + ".mp3\n";

			songsXml +=
				"\t<song" +
				" track=\"" + nf.format(++track) + "\"" +
				" file-name=\"" + Html.formatTo(songFileName) + "\"" +
				" name=\"" + Html.formatTo(_formatMP3(songFileName)) + "\"" +
				" />\n";

			/*songsBat +=
				"\"C:\\Program Files\\Tag Pro\\Tag Pro.exe\" /c \"" +
				file.getParent() + "\\" + songFileName + ".mp3\" " +
				"/3 \"" + Html.formatFrom(_formatMP3(songFileName)) + "\" " +
				"/3 \"" + Html.formatFrom(artist) + "\" " +
				"/3 \"" + Html.formatFrom(album) + "\" " +
				"/0 \"\" " +
				"/0 \"\" " +
				"/3 \"" + genre + "\"" +
				"\n";*/

			songsBat +=
				"\"C:\\Program Files\\mp3info\\mp3info.exe\" " +
				"-g \"" + genre + "\" " +
				"-a \"" + Html.formatFrom(artist) + "\" " +
				"-l \"" + Html.formatFrom(album) + "\" " +
				"-t \"" + Html.formatFrom(_formatMP3(songFileName)) + "\" " +
				"-n \"" + (i + 1) + "\" " +
				"\"" + file.getParent() + "\\" + songFileName + ".mp3\"" +
				"\n";

			String mp3Path = file.getParent() + "\\" + songFileName + ".mp3";
			File mp3File = new File(mp3Path);
			mp3File.renameTo(new File(mp3Path));
		}

		songsXml += "</songs>";

		File songsPlayListFile = new File(file.getParent() + "\\_songs.m3u");
		FileUtil.write(songsPlayListFile, songsPlayList);

		File songsXmlFile = new File(file.getParent() + "\\_songs.xml");
		FileUtil.write(songsXmlFile, songsXml);

		File songsBatFile = new File(file.getParent() + "\\_songs.bat");
		FileUtil.write(songsBatFile, songsBat);

		_songsBatList.add(songsBatFile.getPath());

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer(
			new StreamSource(_rootDir + "/_songs.xsl"));

		trans.transform(
			new StreamSource(songsXmlFile),
			new StreamResult(
				new FileOutputStream(file.getParent() + "\\_index.html")));
	}

	private void _createSongsBatList(File file) throws IOException {
		String songsBatList = "@echo off\n";

		for (int i = 0; i < _songsBatList.size(); i++) {
			String songBat = (String)_songsBatList.get(i);

			songsBatList += "call \"" + songBat + "\"\n";
		}

		File songsBatFile = new File(file.getPath() + "\\_songs.bat");
		FileUtil.write(songsBatFile, songsBatList);
	}

	private String _formatTitle(String s) {

		/*
		s = StringUtil.replace(s, " A ", " a ");
		s = StringUtil.replace(s, " An ", " an ");
		s = StringUtil.replace(s, " And ", " and ");
		s = StringUtil.replace(s, " As ", " as ");
		s = StringUtil.replace(s, " At ", " at ");
		s = StringUtil.replace(s, " By ", " by ");
		s = StringUtil.replace(s, " But ", " but ");
		s = StringUtil.replace(s, " For ", " for ");
		s = StringUtil.replace(s, " In ", " in ");
		s = StringUtil.replace(s, " Nor ", " nor ");
		s = StringUtil.replace(s, " Of ", " of ");
		s = StringUtil.replace(s, " On ", " on ");
		s = StringUtil.replace(s, " Or ", " or ");
		s = StringUtil.replace(s, " So ", " so ");
		s = StringUtil.replace(s, " The ", " the ");

		s = StringUtil.replace(s, "; a ", "; A ");
		s = StringUtil.replace(s, "; an ", "; An ");
		s = StringUtil.replace(s, "; and ", "; And ");
		s = StringUtil.replace(s, "; as ", "; As ");
		s = StringUtil.replace(s, "; at ", "; At ");
		s = StringUtil.replace(s, "; by ", "; By ");
		s = StringUtil.replace(s, "; but ", "; But ");
		s = StringUtil.replace(s, "; for ", "; For ");
		s = StringUtil.replace(s, "; in ", "; In ");
		s = StringUtil.replace(s, "; nor ", "; Nor ");
		s = StringUtil.replace(s, "; of ", "; Of ");
		s = StringUtil.replace(s, "; on ", "; On ");
		s = StringUtil.replace(s, "; or ", "; Or ");
		s = StringUtil.replace(s, "; so ", "; So ");
		s = StringUtil.replace(s, "; the ", "; The ");
		*/

		char[] c = s.toLowerCase().trim().toCharArray();

		if (c.length > 0) {
			c[0] = Character.toUpperCase(c[0]);
		}

		for (int i = 0; i < c.length; i++) {
			if ((c[i] == ' ') || (c[i] == '(') || (c[i] == '\\')) {
				c[i + 1] = Character.toUpperCase(c[i + 1]);
			}
		}

		return new String(c);
	}

	private String _formatMP3(String s) {
		return StringUtil.replace(s, "$$", "&#");
	}

	private String _rootDir;
	private List _songsBatList = new ArrayList();

}