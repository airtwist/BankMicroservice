package org.mtroyanov.api;

import org.mtroyanov.dto.CurrencyRate;
import org.mtroyanov.entity.id.CurrencyId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class HttpClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${currency.client.url.usd}")
    private String url;

    private String templateURL(String baseURL, Map<String, String> uriVariables) {
        return UriComponentsBuilder.fromUriString(baseURL)
                .buildAndExpand(uriVariables)
                .toUriString();
    }

    public CurrencyRate getExchangeRate(CurrencyId from,CurrencyId to) {
        String exchangeUrl = templateURL(url,Map.of("from", from.name(), "to", to.name()));
        ResponseEntity<CurrencyRate> response = restTemplate.getForEntity(exchangeUrl, CurrencyRate.class);
        return response.getBody();
    }

    public String post(String url, Object request) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}