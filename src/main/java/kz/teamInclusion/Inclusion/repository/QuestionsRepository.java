package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
