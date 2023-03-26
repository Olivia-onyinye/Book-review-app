package com.holyvia.Bookreview.services;

import com.holyvia.Bookreview.utils.ApiResponse;

public interface OpenLibraryApiClient {
    ApiResponse<String> searchBooks(String query);
}
