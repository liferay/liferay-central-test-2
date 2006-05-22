import com.liferay.portal.model.LayoutModel;
import com.liferay.portal.service.http.LayoutServiceSoap;
import com.liferay.portal.service.http.LayoutServiceSoapServiceLocator;

import java.net.URL;

public class Test {

    public static void main(String[] args) {
		try {
			LayoutServiceSoapServiceLocator locator =
				new LayoutServiceSoapServiceLocator();

			LayoutServiceSoap soap = locator.getPortal_LayoutService(new URL(
				"http://localhost:8080/tunnel/axis/Portal_LayoutService"));

			LayoutModel[] layouts = soap.getLayouts(
				"liferay.com", "56", "article-id", "PRODUCTS-LICENSING");

			for (int i = 0; i < layouts.length; i++) {
				System.out.println(layouts[i].getLayoutId());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}