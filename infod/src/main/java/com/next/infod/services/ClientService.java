package com.next.infod.services;


import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.model.Client;
import com.next.infod.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client) {
        if(repository.existsByClientId(client.getClientId())) {
            throw new ArquivoDuplicado("Usuario já existente!!!");
        }
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        if(clientId.isEmpty()){
            throw new RuntimeException("Nao encontrado!");
        }
        return repository.findByClientId(clientId);
    }
}
