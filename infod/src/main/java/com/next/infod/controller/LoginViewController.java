package com.next.infod.controller;

import com.next.infod.security.CustomAuthentication; // Corrigido a importação
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Login  (LoginViewController)", description = "Endpoints relacionados ao login e autenticação") // Define a categoria no Swagger
public class LoginViewController {







    @GetMapping("/login")
    @Operation(summary = "Página de login", description = "Retorna a página de login")
    public String pagingLogin() {
        return "login";
    }







    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "Página inicial", description = "Retorna uma mensagem de boas-vindas com o nome do usuário autenticado")
    public String paginaHome(
            @Parameter(hidden = true) Authentication authentication // Oculta esse parâmetro do Swagger
    ) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        return "Olá " + authentication.getName();
    }







    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Obter código de autorização", description = "Recebe o código de autorização como parâmetro e o retorna na resposta")
    public String getAuthorizationCode(
            @Parameter(description = "Código de autorização recebido do OAuth", required = true)
            @RequestParam("code") String code
    ) {
        return "Seu authorization code: " + code;
    }
}
