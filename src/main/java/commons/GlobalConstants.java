package commons;

import java.io.File;

public class GlobalConstants {
	public static final String DEV_SERVER = "https://demo.nopcommerce.com/";
	public static final String TESTING_SERVER = "https://testing.nopcommerce.com/";
	public static final String STAGING_SERVER = "https://testing.nopcommerce.com/";
	public static final long SHORT_TIMEOUT = 5;
	public static final long LONG_TIMEOUT = 30;
	public static String ROOT_FOLDER = System.getProperty("user.dir");
	public static String BROWSER_LOG_FOLDER = ROOT_FOLDER + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "browserLog";
	public static String DOWNLOAD_FOLDER = ROOT_FOLDER + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "downloadFiles";
	public static String UPLOAD_FOLDER = ROOT_FOLDER + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "uploadFiles";
	public static String AUTO_IT_FOLDER = ROOT_FOLDER + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "autoITScript";
}
