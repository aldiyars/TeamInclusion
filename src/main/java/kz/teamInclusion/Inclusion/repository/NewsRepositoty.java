package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepositoty extends JpaRepository<News, Long> {
}
