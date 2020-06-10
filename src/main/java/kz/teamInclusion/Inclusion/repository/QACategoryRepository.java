package kz.teamInclusion.Inclusion.repository;


import kz.teamInclusion.Inclusion.model.QACategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QACategoryRepository extends JpaRepository<QACategory, Long> {
    QACategory getByName(String name);
}
