package com.github.diegonighty.http.util;

public class StatusCode {

	public static boolean isSuccessful(int code) {
		return code >= 200 && code <= 206;
	}

}
