package com.github.marciokleber.springbatchreaderapipagination.batch;


import com.github.marciokleber.springbatchreaderapipagination.model.ApiResponse;
import com.github.marciokleber.springbatchreaderapipagination.model.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class PaginatedApiItemReader implements ItemReader<UserData> {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final int pageSize;

    private Queue<UserData> buffer = new LinkedList<>();
    private int currentPage = 1;
    private boolean hasMorePages = true;

    public PaginatedApiItemReader(RestTemplate restTemplate, String apiUrl, int pageSize) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.pageSize = pageSize;
    }

    @Override
    public UserData read() throws Exception {
        // Se o buffer está vazio e ainda há páginas, busca a próxima página
        if (buffer.isEmpty() && hasMorePages) {
            fetchNextPage();
        }

        // Retorna o próximo item do buffer ou null se não houver mais dados
        return buffer.poll();
    }

    private void fetchNextPage() {
        try {
            String url = String.format("%s?page=%d&size=%d", apiUrl, currentPage, pageSize);
            log.info("Buscando página {} da API: {}", currentPage, url);

            ResponseEntity<ApiResponse<UserData>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<UserData>>() {
                    }
            );

            ApiResponse<UserData> apiResponse = response.getBody();

            if (apiResponse != null && apiResponse.getData() != null && !apiResponse.getData().isEmpty()) {
                buffer.addAll(apiResponse.getData());
                log.info("Página {} carregada com {} registros", currentPage, apiResponse.getData().size());

                // Verifica se há mais páginas
                hasMorePages = currentPage < apiResponse.getTotalPages();
                currentPage++;
            } else {
                hasMorePages = false;
                log.info("Não há mais páginas para processar");
            }

        } catch (Exception e) {
            log.error("Erro ao buscar página {} da API", currentPage, e);
            hasMorePages = false;
            throw new RuntimeException("Erro ao consumir API paginada", e);
        }
    }
}