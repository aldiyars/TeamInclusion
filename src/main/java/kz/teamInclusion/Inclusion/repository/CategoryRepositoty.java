package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoty extends JpaRepository<Category, Long> {
}
