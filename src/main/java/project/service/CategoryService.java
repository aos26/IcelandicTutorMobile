package project.service;
import project.persistence.entities.Category;

import java.util.List;

public interface CategoryService {
    Category findByCatId(Long id);
    List<Category> findAll();
}
