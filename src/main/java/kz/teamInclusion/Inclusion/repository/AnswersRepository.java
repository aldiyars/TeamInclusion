package kz.teamInclusion.Inclusion.repository;

import kz.teamInclusion.Inclusion.model.Answers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswersRepository extends JpaRepository<Answers, Long> {
    List<Answers> getByQuestion(Long id);
}
