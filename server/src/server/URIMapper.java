package server;

import java.util.HashMap;
import java.util.Map;

import server.responders.UserCreator;

/**
 * Singleton Instance of URI routing.
 */
public final class URIMapper {
	// underlying URL map
	private static Map<String, Responder> URIS = null;
	
	// prevent instantiation by other classes
	private URIMapper() {
		URIS = new HashMap<String, Responder>();
		URIS.put("/admin/reset", new UserCreator());
		// TODO add meaningful URL APIs
	}
	
	/**
	 * Return the supported URIS.
	 * @return The supported URIS.
	 */
	public static final Map<String, Responder> getInstance() {
		// initialize the map
		if (URIS == null) {
			new URIMapper();
		}
		return URIS;
	}
}
