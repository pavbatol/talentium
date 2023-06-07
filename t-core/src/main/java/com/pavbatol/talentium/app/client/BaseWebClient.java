package com.pavbatol.talentium.app.client;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
public abstract class BaseWebClient {

    public static final String X_USER_ID = "X-User-Id";
    protected final WebClient client;

    public BaseWebClient(WebClient client) {
        this.client = client;
    }

    protected Mono<ResponseEntity<String>> post(String furtherPath, Object body) {
        return post(furtherPath, null, null, null, body);
    }

    protected Mono<ResponseEntity<String>> post(String furtherPath, long userId, String token, Object body) {
        return post(furtherPath, userId, token, null, body);
    }

    protected Mono<ResponseEntity<String>> post(String furtherPath, Long userId, String token,
                                                @Nullable Map<String, Object> parameters, Object body) {
        return makeAndSendRequest(HttpMethod.POST, furtherPath, userId, token, parameters, body);
    }

    protected Mono<ResponseEntity<String>> patch(String furtherPath, long userId, String token, Object body) {
        return patch(furtherPath, userId, token, null, body);
    }

    protected Mono<ResponseEntity<String>> patch(String furtherPath, Long userId, String token,
                                                 @Nullable Map<String, Object> parameters, Object body) {
        return makeAndSendRequest(HttpMethod.PATCH, furtherPath, userId, token, parameters, body);
    }

    protected Mono<ResponseEntity<String>> delete(String furtherPath, long userId, String token) {
        return delete(furtherPath, userId, token, null);
    }

    protected Mono<ResponseEntity<String>> delete(String furtherPath, Long userId, String token,
                                                  @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.DELETE, furtherPath, userId, token, parameters, null);
    }

    protected Mono<ResponseEntity<String>> get(String path) {
        return get(path, null, null, null);
    }

    protected Mono<ResponseEntity<String>> get(String furtherPath, long userId, String token) {
        return get(furtherPath, userId, token, null);
    }

    protected Mono<ResponseEntity<String>> get(String furtherPath, Long userId, String token,
                                               @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, furtherPath, userId, token, parameters, null);
    }

    private Mono<ResponseEntity<String>> makeAndSendRequest(HttpMethod method,
                                                            String furtherPath,
                                                            Long userId,
                                                            String token,
                                                            @Nullable Map<String, Object> parameters,
                                                            @Nullable Object body) {
        return client
                .method(method)
                .uri(furtherPath, parameters != null ? parameters : new HashMap<>())
                .headers(httpHeaders -> defaultHeaders(httpHeaders, userId, token))
                .body(body != null ? BodyInserters.fromValue(body) : null)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
                .doOnError(error -> log.error("An error has occurred {}", error.getMessage()))
                .onErrorResume(
                        WebClientResponseException.class,
                        ex -> Mono.just(ResponseEntity
                                .status(ex.getStatusCode())
                                .body(ex.getResponseBodyAsString())
                        )
                );
    }

    private void defaultHeaders(HttpHeaders headers, Long userId, String token) {
        Predicate<List<?>> isNullOrEmpty = l -> Objects.isNull(l) || l.isEmpty();

        if (isNullOrEmpty.test(headers.get(HttpHeaders.ACCEPT))) {
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        }
        if (isNullOrEmpty.test(headers.get(HttpHeaders.CONTENT_TYPE))) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        if (isNullOrEmpty.test(headers.get(HttpHeaders.ACCEPT_CHARSET))) {
            headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        }
        if (Objects.nonNull(userId)) {
            headers.set(X_USER_ID, String.valueOf(userId));
        }
        if (Objects.nonNull(token)) {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
    }
}