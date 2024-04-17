package id.ac.ui.cs.advprog.bookubeauth.repositories;

import id.ac.ui.cs.advprog.bookubeauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {
}