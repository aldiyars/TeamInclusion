package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepositoty extends JpaRepository<Blog, Long> {
}
