package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.authority = :auth")
    Role findByName(@Param("auth") String name);
}
