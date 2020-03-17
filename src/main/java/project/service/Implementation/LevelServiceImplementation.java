package project.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Level;
import project.service.LevelService;
import project.persistence.repositories.LevelRepository;
import java.util.List;

@Service
public class LevelServiceImplementation implements LevelService {
    LevelRepository repository;

    @Autowired
    public LevelServiceImplementation(LevelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Level findByLvlId(Long id) {
        return repository.findByLvlId(id);
    }

    @Override
    public List<Level> findAll() {
        return repository.findAll();
    }
}
