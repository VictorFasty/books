package com.next.infod.controller;


import com.next.infod.controller.DTOS.ClientDTO;
import com.next.infod.controller.mappers.ClientMapper;
import com.next.infod.model.Client;
import com.next.infod.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Client-controller (Client controller)")
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo client")
    @ApiResponses({

            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Conflito, quando o cliente ja está cadastrado")
    })
    public void salvar(@RequestBody @Valid ClientDTO DTO) {
        Client client = mapper.toEntity(DTO);

        log.info("Registrando novo client: {} com scope: {} ", client.getClientId(), client.getScope());
        service.salvar(client);
    }
}
