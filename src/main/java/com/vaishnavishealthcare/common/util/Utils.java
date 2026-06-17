package com.vaishnavishealthcare.common.util;

/**
 * Shared low-level constants and helpers (e.g. well-known request headers).
 */
public final class Utils {

    private Utils() {
    }

    /** Well-known request header names. */
    public static final class Header {

        private Header() {
        }

        public static final String API_KEY = "x-api-key";
        public static final String AUTHORIZATION = "authorization";
    }
}
