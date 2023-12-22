package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.model.Usr;

public interface UsrRepository extends JpaRepository<Usr, Long> {
    @Query("select u from Usr u where u.email = :email")
    Usr findByUsername(@Param("email") String email);
}
