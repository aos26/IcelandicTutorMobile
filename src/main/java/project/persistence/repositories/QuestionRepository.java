package project.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.persistence.entities.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long > {
    Question save(Question question);
    void delete(Question question);

    List<Question> findAll();

    @Query(value = "SELECT q FROM Question q where q.cat_id = ?1 and q.lvl_id= ?2")
    List<Question> getQuestionByCatIdAndLvlId(Long cat_id, Long lvl_id);

    @Query(value = "SELECT q FROM Question q WHERE q.cat_id = ?1 and q.lvl_id= ?2 order by q.questionWord")
    List<Question> getAllQuestionByCat(Long cat_id, Long lvl_id);

    @Query(value = "SELECT q.id FROM Question q ORDER BY q.id DESC")
    List<Long> getNextId();
}
