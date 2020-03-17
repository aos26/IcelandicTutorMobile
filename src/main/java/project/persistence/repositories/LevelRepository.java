package project.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.persistence.entities.Level;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findAll();

    @Query(value = "SELECT l FROM Level l where l.id = ?1")
    Level findByLvlId(Long id);
}
