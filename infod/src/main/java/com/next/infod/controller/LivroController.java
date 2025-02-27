package com.next.infod.controller;

import com.next.infod.Enums.GeneroLivro;
import com.next.infod.controller.DTOS.CadastroLivroDTO;
import com.next.infod.controller.DTOS.ResultadoPesquisaLivroDTO;
import com.next.infod.controller.mappers.LivroMapper;
import com.next.infod.model.Livro;
import com.next.infod.services.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Tag(name = "Livro-controller (LivroController)")
@Validated
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;







    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo livro")
    @ApiResponses({

            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Conflito, quando o livro ja está registrado")
    })
    public ResponseEntity<Livro> create(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.create(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }






    @GetMapping("/findall")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "FindAll", description = "Pesquisa por todos os livros existentes")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso"),
    })
    public ResponseEntity<List<Livro>> findAll() {
        return service.findAll();
    }








    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "FindById", description = "procurando pelo id do livro")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> findById(@PathVariable(value = "id") UUID id) {
        return service.findById(id);
    }








    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Update", description = "Atualiza um livro já existente")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado")
    })
    public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody @Valid CadastroLivroDTO DTO){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(DTO);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setBooks(entidadeAux.getBooks());

                    service.update(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }








    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Delete", description = "Deleta por ID um livro existente")
    @ApiResponses({

            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
    })
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id){
        return service.obterPorId((UUID.fromString(id)))
                .map(livro -> {
                    service.Delete(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }






    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar por id", description = "Realiza pesquisa de livros por id")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
    })
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
            return service.obterPorId(UUID.fromString(id))
                    .map(livro -> {
                        var dto = mapper.toDTO(livro);
                        return ResponseEntity.ok(dto);
                    }).orElseGet( () -> ResponseEntity.notFound().build());
    }








    @GetMapping
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de livrospor parametros")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "autor", required = false)
            String autor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "dataPublicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "paging", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, autor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }
}
