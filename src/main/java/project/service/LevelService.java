package project.service;
import project.persistence.entities.Level;

import java.util.List;

public interface LevelService {
    Level findByLvlId(Long id);
    List<Level> findAll();
}
