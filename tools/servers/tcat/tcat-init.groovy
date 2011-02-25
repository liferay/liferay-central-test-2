import com.mulesoft.tcat.util.InstallBuilder

import org.mule.galaxy.Item
import org.mule.galaxy.NotFoundException
import org.mule.galaxy.Registry
import org.mule.galaxy.impl.jcr.JcrUtil
import org.mule.galaxy.script.Script;
import org.mule.galaxy.script.ScriptManager
import org.mule.galaxy.type.TypeManager;
import com.mulesoft.tcat.ServerProfileManager;

import org.springframework.web.context.support.XmlWebApplicationContext
import org.springmodules.jcr.JcrCallback

import javax.jcr.RepositoryException
import javax.jcr.Session
import org.mule.galaxy.util.IOUtils

/**
 * This tcat-init.groovy script initializes the TCat admin server by
 * loading server profiles, console scripts, and web applications.
 *
 * This script must be placed into ${CATALINA_HOME}.  The TCat admin server
 * will execute the script on first startup and after successful execution,
 * delete it.
 *
 * The script loads console groovy scripts from:
 * ${CATALINA_HOME}/tcat_init/scripts.  The scripts will be available in the
 * TCat console Admin Shell.
 *
 * The script will load profiles from: ${CATALINA_HOME}/tcat_init/profiles.
 * Profiles zip files must be named liferay-portal-tcat-profile-VERSION.zip.
 * For instance, liferay-portal-tcat-profile-6.1.0.zip.  On startup, this script
 * will load the profile into the repository: /Profiles/liferay-portal-VERSION.
 *
 * The script will also load web application from
 * ${CATALINA_HOME}/tcat_init/webapps/VERSION, where VERSION should be the
 * version of Liferay Portal (e.g. 6_0_10).
 *
 * A sample configuration for Liferay Portal 6.0.10 would look like:
 * (1) Profile: ${CATALINA_HOME}/tcat_init/profiles/liferay-portal-tcat-profile-6.0.10.zip
 * (2) Webapps:
 *      (a)${CATALINA_HOME}/tcat_init/webapps/6_0_10/ROOT.war
 *      (b)${CATALINA_HOME}/tcat_init/webapps/6_0_10/kaleo-web-6.0.10.war
 *
 */
class InitializeLiferayDeployment implements JcrCallback {

    XmlWebApplicationContext _applicationContext;
	Registry _registry;
	TypeManager _typeManager;

    public InitializeLiferayDeployment(
		XmlWebApplicationContext applicationContext) {

		_applicationContext = applicationContext;

		_registry = (Registry)_applicationContext.getBean("registry");
		_typeManager = (TypeManager)_applicationContext.getBean(
			"typeManager");

    }

    public Object doInJcr(Session session)
		throws IOException, RepositoryException {

        def installBuilder = new InstallBuilder(_applicationContext);


        // Register the local Tcat agent.
        //installBuilder.registerConsoleAgent("TcatServer")

        // Import the Liferay server profile into the Tcat repository.
		_loadServerProfiles(installBuilder);

        // Loop through all WAR files and add them to the Tcat repository.
		_loadWebapps(installBuilder);

        // Loop through all scripts and add them to Tcat console
		_loadScripts(installBuilder);

        return "Completed Initialization";
    }

	private void _loadServerProfiles(InstallBuilder installBuilder) {
		println("Loading Liferay Server Profiles...");

		def profileDir = new File("tcat_init/profiles");

		def serverProfileManager =
			(ServerProfileManager)_applicationContext.getBean(
				"serverProfileManager");

		for (File file : profileDir.listFiles()) {
			String fileName = file.name;

			if (!fileName.contains("tcat-profile-")) {
				continue;
			}

			println("Loading Server Profile: " + file.name);

			def startIndex = fileName.indexOf("tcat-profile-");
			def endIndex = startIndex + "tcat-profile-".length();

			String profileName = fileName.substring(0, startIndex) +
				fileName.substring(endIndex, fileName.indexOf(".zip"));

			def profileWorkspaceItem = _getOrCreateWorkspace(
				"/Profiles/" + profileName);

			serverProfileManager.importProfile(
				new FileInputStream(file), profileWorkspaceItem);
		}
	}

	private void _loadScripts(InstallBuilder installBuilder) {
		println("Loading TCat Console Scripts...");

		def scriptsDir = new File("tcat_init/scripts");

		def scriptManager =
			(ScriptManager)_applicationContext.getBean(
				"scriptManager");

		for (File file : scriptsDir.listFiles()) {
			String scriptName = file.name;
			if (!scriptName.contains(".groovy")) {
				continue;
			}

			println("Loading Script: " + scriptName);

			String friendlyName = scriptName.substring(
				0, scriptName.indexOf(".groovy"));

			List scripts = scriptManager.find("name", friendlyName);

			FileInputStream fis = new FileInputStream(file);

			Script script = null;

			if(!scripts.isEmpty()) {
				script = (Script)scripts.get(0);
			}
			else {
				script = new Script();
				script.setName(friendlyName);
			}
			script.setRunOnStartup(true);

			script.setScript(IOUtils.toString(fis));

			scriptManager.save(script);
		}
	}

	private void _loadWebapps(InstallBuilder installBuilder) {

		println("Loading Liferay Web Applications into Repository...");

		def webappsDir = new File("tcat_init/webapps");

		for (File versionDir : webappsDir.listFiles()) {

			if (!versionDir.isDirectory()) {
				continue;
			}

			String workspaceName = versionDir.name;
			String workspaceFullPath = "/Applications/Liferay/" + workspaceName;

			println("Loading Web Applications in: " + workspaceName);

			_getOrCreateWorkspace(workspaceFullPath);

			for (File file : versionDir.listFiles()) {
				String fileName = file.name;

				if (!fileName.contains(".war")) {
					continue;
				}

				println("Loading Web Application: " + fileName);

				def start = fileName.lastIndexOf("-") + 1;
				def end = fileName.indexOf(".war");

				String fileVersion = fileName.substring(start, end);

				installBuilder.addRepositoryFile(
					file.toString(), workspaceFullPath, fileName, fileVersion);
			}
		}
	}

	private Item _getOrCreateWorkspace(String workspaceName) {
		String[] nameComponents = workspaceName.split("/");

		String currentPath = "";

		for (String nameComponent : nameComponents) {
			if (nameComponent.equals("")) {
				continue;
			}

			String proposedPath = currentPath + "/" + nameComponent;
			try {
				_registry.getItemByPath(proposedPath);
			}
			catch (NotFoundException nfe) {
				def parentItem = _registry.getItemByPath(currentPath);

				parentItem.newItem(
					nameComponent, _typeManager.getTypeByName("Workspace"));
			};

			currentPath = proposedPath;
		}

		return _registry.getItemByPath(currentPath);
	}
}

def repositoryImport = new InitializeLiferayDeployment(applicationContext) as JcrCallback;
def sf = applicationContext.getBean("sessionFactory");

JcrUtil.doInTransaction(sf, repositoryImport);

