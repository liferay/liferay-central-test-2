/**
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

package com.liferay.maven.plugins;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;
import org.codehaus.plexus.components.io.fileselectors.FileSelector;
import org.codehaus.plexus.components.io.fileselectors.IncludeExcludeFileSelector;

/**
 * <a href="ThemeMergeMojo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 * @goal theme-merge
 * @phase process-sources
 */
public class ThemeMergeMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {

		File f = workDir;

		if (!f.exists()) {
			f.mkdirs();
		}

		String parentThemeGroupId = "com.liferay.portal";
		String parentThemeArtifactId = "portal-web";
		String parentThemeVersion = liferayVersion;

		String[] selectorIncludesPattern =
			new String[] {
				"html/themes/_unstyled/**", "html/themes/_styled/**",
				"html/themes/classic/**", "html/themes/control_panel/**"
			};

		String[] selectorExcludesPattern =
			new String[] {
				"html/themes/classic/_diffs/**",
				"html/themes/control_panel/_diffs/**"
			};

		if (!("_styled".equals(parentTheme) ||
			"_unstyled".equals(parentTheme) || "classic".equals(parentTheme) ||
			"control_panel".equals(parentTheme))) {

			String[] pieces = parentTheme.split(":");
			parentThemeGroupId = pieces[0];
			parentThemeArtifactId = pieces[1];
			parentThemeVersion = pieces[2];
			selectorExcludesPattern = new String[] {
				"WEB-INF/**"
			};
		}

		Artifact artifact =
			factory.createArtifact(
				parentThemeGroupId, parentThemeArtifactId, parentThemeVersion,
				"", "war");

		try {
			resolver.resolve(
				artifact, remoteRepos, local);
		}
		catch (ArtifactResolutionException are) {
			throw new MojoExecutionException("Artifact resolution failed", are);
		}
		catch (ArtifactNotFoundException anfe) {
			throw new MojoExecutionException("Artifact not found", anfe);
		}

		workDir.mkdirs();

		try {
			UnArchiver unArchiver =
				archiverManager.getUnArchiver(artifact.getFile());

			unArchiver.setSourceFile(artifact.getFile());

			unArchiver.setDestDirectory(workDir);

			IncludeExcludeFileSelector selector =
				new IncludeExcludeFileSelector();

			selector.setIncludes(selectorIncludesPattern);
			selector.setExcludes(selectorExcludesPattern);

			unArchiver.setFileSelectors(new FileSelector[] {
				selector
			});

			unArchiver.extract();

			webappDirectory.mkdirs();

			try {
				if (parentThemeArtifactId.equals("portal-web")) {
					FileUtils.copyDirectory(
						new File(workDir, "html/themes/_unstyled"),
						webappDirectory);

					getLog().info(
						"Copying html/themes/_unstyled to " + webappDirectory);

					if (!"_unstyled".equals(parentTheme)) {
						FileUtils.copyDirectory(
							new File(workDir, "html/themes/_styled"),
							webappDirectory);
						getLog().info(
							"Copying html/themes/_styled to " + webappDirectory);
					}

					if (!"_unstyled".equals(parentTheme) &&
						!"_styled".equals(parentTheme)) {
						FileUtils.copyDirectory(
							new File(workDir, "html/themes/" + parentTheme),
							webappDirectory);
						getLog().info(
							"Copying html/themes/" + parentTheme + " to " +
								webappDirectory);
					}
				}
				else {
					FileUtils.copyDirectory(
						workDir, webappDirectory);
				}

				// Cleanup

				File initFile = new File(
					webappDirectory, "templates/init." + themeType);
				File templatesDirectory = new File(
					webappDirectory, "templates/");
				String[] extensions = null;

				if ("ftl".equals(themeType)) {
					extensions = new String[] {"vm"};
				}
				else {
					extensions = new String[] {"ftl"};
				}

				FileUtils.deleteQuietly(initFile);

				Iterator<File> iterator = FileUtils.iterateFiles(
					templatesDirectory, extensions, false);

				while (iterator.hasNext()) {
					File file = iterator.next();
					FileUtils.deleteQuietly(file);
				}
			}
			catch (IOException e) {
				throw new MojoExecutionException("Theme merge failed", e);
			}

		}
		catch (ArchiverException ae) {
			throw new MojoExecutionException("Artifact extraction failed", ae);
		}
		catch (NoSuchArchiverException nsae) {
			throw new MojoExecutionException(
				"Archiver not found for artifact", nsae);
		}
	}

	/**
	 * @component
	 */
	private ArchiverManager archiverManager;

	/**
	 * @component
	 */
	private org.apache.maven.artifact.factory.ArtifactFactory factory;

	/**
	 * @parameter
	 * @required
	 */
	private String liferayVersion;

	/**
	 * @parameter expression="${localRepository}"
	 * @readonly
	 * @required
	 */
	private org.apache.maven.artifact.repository.ArtifactRepository local;

	/**
	 * Parent theme. Can be _styled | _unstyled | classic | control_panel |
	 * artifactGroupId:artifactId:artifactVersion
	 *
	 * @parameter default-value="_styled"
	 */
	private String parentTheme;

	/**
	 * @parameter expression="${project.remoteArtifactRepositories}"
	 * @readonly
	 * @required
	 */
	private java.util.List remoteRepos;

	/**
	 * @component
	 */
	private org.apache.maven.artifact.resolver.ArtifactResolver resolver;

	/**
	 * @parameter default-value="vm"
	 * @required
	 */
	private String themeType;

	/**
	 * @parameter expression=
	 *			  "${project.build.directory}/${project.build.finalName}"
	 * @required
	 */
	private File webappDirectory;

	/**
	 * @parameter expression="${project.build.directory}/liferay-theme/work"
	 * @required
	 */
	private File workDir;

}
