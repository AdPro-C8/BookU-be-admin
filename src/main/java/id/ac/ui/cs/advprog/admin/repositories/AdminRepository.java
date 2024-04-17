package id.ac.ui.cs.advprog.admin.repositories;
import id.ac.ui.cs.advprog.admin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminRepository {
    private List<User> userData = new ArrayList<>();
    public Iterator<User> findAll() {
        return userData.iterator();
    }
}