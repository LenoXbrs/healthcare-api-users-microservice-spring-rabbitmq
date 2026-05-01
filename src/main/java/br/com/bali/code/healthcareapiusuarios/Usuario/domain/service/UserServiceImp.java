package br.com.bali.code.healthcareapiusuarios.Usuario.domain.service;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserServiceInteface {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void save() {

    }

    @Override
    public void get() {

    }

    @Override
    public void put() {

    }

    @Override
    public void softDelete() {

    }
}
