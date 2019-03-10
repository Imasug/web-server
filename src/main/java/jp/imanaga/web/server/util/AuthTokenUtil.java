package jp.imanaga.web.server.util;

import jp.imanaga.web.server.data.AuthTokenObject;

public class AuthTokenUtil {

	public static AuthTokenObject parse(String authToken) {
		// TODO
		AuthTokenObject authTokenObject = new AuthTokenObject();
		return authTokenObject;
	}

	public static boolean isValid(AuthTokenObject authTokenObject) {
		// TODO
		return true;
	}

	public static String create(AuthTokenObject authTokenObject) {
		// TODO
		return "authToken";
	}

}
