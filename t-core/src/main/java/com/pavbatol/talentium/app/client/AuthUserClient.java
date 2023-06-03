package com.pavbatol.talentium.app.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class AuthUserClient extends BaseWebClient {

    @Autowired
    public AuthUserClient(@Value("${app.auth-service.url}") String serverUrl,
                          @Value("${app.auth-service.path.private-user}") String userPath,
                          @Value("${app.connect.read_timeout_millis:5000}") int readTimeout,
                          @Value("${app.connect.write_timeout_millis:5000}") int writeTimeout,
                          @Value("${app.connect.connect_timeout_millis:5000}") int connectTimeout,
                          @Value("${app.connect.response_timeout_millis:5000}") int responseTimeout,
                          WebClient.Builder builder) {
        super(builder
                .baseUrl(serverUrl + userPath)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                                .responseTimeout(Duration.ofMillis(responseTimeout))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS))
                                )
                ))
                .build()
        );
    }

    public Mono<ResponseEntity<String>> update(long userId, String token, UserDtoUpdateShort dto) {
        return patch("/" + userId, userId, token, dto);
    }

}
