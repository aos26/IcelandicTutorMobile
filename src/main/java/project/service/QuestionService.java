package project.service;

import project.persistence.entities.Question;

import java.util.List;

public interface QuestionService {
    /**
     * save a {@Link Question}
     * @param question {@Link Question} to be saved
     * @return {@Link Question} that was saved
     */


    Question save(Question question);

    /**
     * Delete {@link Question}
     * @param question {@link Question} to be deleted
     */



    void delete(Question question);

    /**
     * Get all {@link Question}s
     * @return A list of {@link Question}s
     */


    List<Question> findAll();

    List<Question> getQuestionByCatIdAndLvlId(Long cat_id, Long lvl_id);


    List<Long> getNextId();
    List<Question> getAllQuestionByCat(Long cat_id, Long lvl_id);
}
