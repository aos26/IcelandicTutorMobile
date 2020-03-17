package project.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Category;
import project.service.CategoryService;
import project.persistence.repositories.CategoryRepository;
import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {
    CategoryRepository repository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category findByCatId(Long id) {
        return repository.findByCatId(id);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
}
