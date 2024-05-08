package id.ac.ui.cs.advprog.admin.repositories;
import id.ac.ui.cs.advprog.admin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository <User, String> {

}