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
		UserCreator userRegistrationHandler = new UserCreator();
		URIS.put("/admin/create", userRegistrationHandler);
		URIS.put("/admin/delete", userRegistrationHandler);
		URIS.put("/admin/update", userRegistrationHandler);
		URIS.put("/admin/login", userRegistrationHandler);
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
