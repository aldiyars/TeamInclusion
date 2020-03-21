package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
}
