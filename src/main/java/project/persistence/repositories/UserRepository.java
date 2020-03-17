package project.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.persistence.entities.Users;


public interface UserRepository extends JpaRepository<Users, Long > {

    Users save(Users users);

    void delete(Users users);

    List<Users> findAll();

    @Query(value = "SELECT p FROM Users p WHERE p.userName = ?1")
    Users getByUserName(String userName);

    @Query(value = "SELECT p FROM Users p order by p.score desc")
    List<Users> getScoreOrder();
}
