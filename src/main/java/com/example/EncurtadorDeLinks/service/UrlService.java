package com.example.EncurtadorDeLinks.service;

import com.example.EncurtadorDeLinks.dto.UrlLongRequest;
import com.example.EncurtadorDeLinks.entity.Url;
import com.example.EncurtadorDeLinks.repository.UrlRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final ConverterBase62 converterBase62;

    public UrlService(UrlRepository urlRepository, ConverterBase62 baseConversion) {
        this.urlRepository = urlRepository;
        this.converterBase62 = baseConversion;
    }

    public String converterParaUrlCurto(UrlLongRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        urlRepository.save(url);

        return converterBase62.codificar(url.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        long id = converterBase62.decodificar(shortUrl);
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não há uma url com: " + shortUrl));

        if (url.getExpiresDate() != null && url.getExpiresDate().before(new Date())){
            urlRepository.delete(url);
            throw new EntityNotFoundException("Link expirado!");
        }

        return url.getLongUrl();
    }
}
