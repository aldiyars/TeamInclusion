package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepositoty extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
    Optional<Category> findById(Long id);
}
