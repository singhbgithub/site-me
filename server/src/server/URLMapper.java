package server;

import java.util.HashMap;
import java.util.Map;

import server.responders.UserCreator;

/**
 * Singleton Instance of URL routing.
 */
public final class URLMapper {
	// underlying URL map
	private static Map<String, Responder> URLS = null;
	
	// prevent instantiation by other classes
	private URLMapper() {
		URLS = new HashMap<String, Responder>();
		URLS.put("/admin/reset", new UserCreator());
		// TODO add meaningful URL APIs
	}
	
	/**
	 * Return the supported URLS.
	 * @return The supported URLS.
	 */
	public static final Map<String, Responder> getInstance() {
		// initialize the map
		if (URLS == null) {
			new URLMapper();
		}
		return URLS;
	}
}
