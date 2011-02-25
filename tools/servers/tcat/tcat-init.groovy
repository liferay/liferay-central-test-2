import com.mulesoft.tcat.util.InstallBuilder

import org.mule.galaxy.Item
import org.mule.galaxy.NotFoundException
import org.mule.galaxy.Registry
import org.mule.galaxy.impl.jcr.JcrUtil
import org.mule.galaxy.type.TypeManager;
import com.mulesoft.tcat.ServerProfileManager;

import org.springframework.web.context.support.XmlWebApplicationContext
import org.springmodules.jcr.JcrCallback

import javax.jcr.RepositoryException
import javax.jcr.Session

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

        // Import the Liferay server profile into the Tcat console repository.
		_loadServerProfiles(installBuilder);

        // Loop through all WAR files and add them to the Tcat console repository.
		_loadWebapps(installBuilder);

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

