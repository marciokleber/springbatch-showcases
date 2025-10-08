package com.github.marciokleber.springbatchreaderapipagination.controller;


import com.github.marciokleber.springbatchreaderapipagination.model.ApiResponse;
import com.github.marciokleber.springbatchreaderapipagination.model.UserData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller para simular uma API paginada
 * Use este endpoint para testar o Reader: http://localhost:8080/api/mock/users?page=1&size=10
 */
@RestController
@RequestMapping("/api/mock")
public class MockApiController {

    private static final int TOTAL_RECORDS = 125;

    @GetMapping("/users")
    public ApiResponse<UserData> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        int totalPages = (int) Math.ceil((double) TOTAL_RECORDS / size);

        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, TOTAL_RECORDS);

        List<UserData> users = new ArrayList<>();

        for (int i = startIndex; i < endIndex; i++) {
            UserData user = new UserData();
            user.setId((long) (i + 1));
            user.setName("Usuário " + (i + 1));
            user.setEmail("usuario" + (i + 1) + "@exemplo.com");
            user.setPhone("+55 11 9" + String.format("%04d", i) + "-" + String.format("%04d", i));
            user.setStatus(i % 2 == 0 ? "ATIVO" : "INATIVO");
            users.add(user);
        }

        ApiResponse<UserData> response = new ApiResponse<>();
        response.setData(users);
        response.setCurrentPage(page);
        response.setTotalPages(totalPages);
        response.setTotalElements(TOTAL_RECORDS);
        response.setPageSize(size);

        return response;
    }
}