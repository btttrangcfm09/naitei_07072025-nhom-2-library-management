package com.group2.library_management.common.constants;

public final class UrlConstants {

    private UrlConstants() {}

    // --- Path Constants ---
    public static final String BORROW_REQUESTS_LIST = "/admin/borrow-requests";
    public static final String BORROW_REQUESTS_DETAIL = "/admin/borrow-requests/{id}";

    // --- Redirect View Constants ---
    public static final String REDIRECT_BORROW_REQUESTS_LIST = "redirect:" + BORROW_REQUESTS_LIST;
    public static final String REDIRECT_BORROW_REQUESTS_PREFIX = "redirect:" + BORROW_REQUESTS_LIST;
}
