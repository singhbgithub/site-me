package server;

import java.util.HashMap;
import java.util.Map;

import server.responders.UserCreator;

/**
 * Singleton Instance of URI routing. This is a list
 * of supported web API calls.
 */
public final class URIMapper {
	// underlying URL map
	private static Map<String, Responder> URIS = null;
	
	// prevent instantiation by other classes
	private URIMapper() {
		URIS = new HashMap<String, Responder>();
		URIS.put("/admin/reset", new UserCreator());
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
