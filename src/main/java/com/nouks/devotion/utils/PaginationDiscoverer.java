package com.nouks.devotion.utils;

import java.util.ArrayList;
import java.util.List;

public class PaginationDiscoverer {
    public static List<PageLink> addLinksOnPagedResourceRetrieval(final int page, final int totalPages, final int pageSize) {
        List<PageLink> pageLinks = new ArrayList<>();
        if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = constructNextPageUri(page, pageSize);
            pageLinks.add(createPageLink(uriForNextPage, "next"));
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(page, pageSize);
            pageLinks.add(createPageLink(uriForPrevPage, "prev"));
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(pageSize);
            pageLinks.add(createPageLink(uriForFirstPage, "first"));
        }
        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = constructLastPageUri(totalPages, pageSize);
            pageLinks.add(createPageLink(uriForLastPage, "last"));
        }
        return pageLinks;
    }
    private static String constructNextPageUri(final int page, final int size) {
        return "?page=" + (page + 1) + "&size=" + size;
    }
    private static String constructPrevPageUri(final int page, final int size) {
        return "?page=" + (page - 1) + "&size=" + size;
    }
    private static String constructFirstPageUri(final int size) {
        return "?page=0&size=" + size;
    }
    private static String constructLastPageUri(final int totalPages, final int size) {
        return "?page=" + (totalPages - 1) + "&size=" + size;
    }
    public static boolean hasNextPage(final int page, final int totalPages) {
        return page < totalPages - 1;
    }

    public static boolean hasPreviousPage(final int page) {
        return page > 0;
    }
    public static boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }
    public static boolean hasLastPage(final int page, final int totalPages) {
        return  totalPages > 1 && hasNextPage(page, totalPages);
    }
    public static PageLink createPageLink(final String uri, final String rel) {
        return new PageLink(uri, rel);
    }
}
