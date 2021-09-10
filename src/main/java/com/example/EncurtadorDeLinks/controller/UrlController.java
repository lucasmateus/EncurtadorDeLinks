package com.example.EncurtadorDeLinks.controller;

import com.example.EncurtadorDeLinks.dto.UrlLongRequest;
import com.example.EncurtadorDeLinks.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Cacheable;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("criar-link")
    public String convertToShortUrl(@RequestBody UrlLongRequest request) {
        return urlService.converterParaUrlCurto(request);
    }

    @GetMapping(value = "{urlCurto}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String urlCurto) {
        String url = urlService.getOriginalUrl(urlCurto);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
