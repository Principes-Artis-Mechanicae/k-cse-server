package knu.univ.cse.server.global.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
	public static Cookie createCookie(String key, String value, int expiry, String path, boolean isHttpsOnly) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expiry);
		cookie.setPath(path);
		cookie.setHttpOnly(isHttpsOnly);

		return cookie;
	}
}
