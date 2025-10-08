package com.github.marciokleber.springbatchreaderapipagination.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe genérica para representar a resposta paginada de APIs
 *
 * Exemplo de JSON esperado:
 * {
 *   "data": [...],
 *   "currentPage": 1,
 *   "totalPages": 10,
 *   "totalElements": 125,
 *   "pageSize": 50
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("currentPage")
    private int currentPage;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("pageSize")
    private int pageSize;
}