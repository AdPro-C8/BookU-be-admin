package id.ac.ui.cs.advprog.admin.repository;
import id.ac.ui.cs.advprog.admin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository <User, String> {

}