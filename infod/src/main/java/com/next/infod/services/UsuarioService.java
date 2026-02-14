package com.next.infod.services;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.model.Usuario;
import com.next.infod.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void create(Usuario usuario){
        if(repository.existsByEmail(usuario.getEmail())) {
            throw new ArquivoDuplicado("Email ja existente");

        } else if (repository.existsByLogin(usuario.getLogin())) {
            throw new ArquivoDuplicado("Login Ja existente");
        }
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
        return repository.findByEmail(email);
    }
}
