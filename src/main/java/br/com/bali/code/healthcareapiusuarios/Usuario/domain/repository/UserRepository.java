package br.com.bali.code.healthcareapiusuarios.Usuario.domain.repository;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

}
