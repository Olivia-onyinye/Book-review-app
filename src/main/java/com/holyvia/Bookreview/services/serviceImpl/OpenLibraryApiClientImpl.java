//package com.holyvia.Bookreview.services.serviceImpl;
//
//import com.holyvia.Bookreview.services.OpenLibraryApiClient;
//import com.holyvia.Bookreview.utils.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class OpenLibraryApiClientImpl implements OpenLibraryApiClient {
//    private final RestTemplate restTemplate;
//
//    @Override
//    public ApiResponse<String> searchBooks(String query) {
//       return new ApiResponse<>("Book found", true,
//               restTemplate.getForObject("https://openlibrary.org/search/inside.json?q=" + query, OpenLibraryApiClientImpl.class));
//    }
//}
