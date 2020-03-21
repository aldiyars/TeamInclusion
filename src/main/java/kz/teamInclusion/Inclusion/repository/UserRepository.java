package kz.teamInclusion.Inclusion.repository;


import kz.teamInclusion.Inclusion.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users> findAll();
    Users findByEmail(String email);


}
