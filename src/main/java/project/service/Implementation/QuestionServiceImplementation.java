package project.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Question;
import project.persistence.repositories.QuestionRepository;
import project.service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImplementation implements QuestionService {
    QuestionRepository questionRepository;


    @Autowired
    public QuestionServiceImplementation(QuestionRepository repository) { this.questionRepository = repository;}


    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {

        questionRepository.delete(question);

    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionByCatIdAndLvlId(Long cat_id, Long lvl_id) {
        return questionRepository.getQuestionByCatIdAndLvlId(cat_id, lvl_id);
    }

    @Override
    public List<Question> getAllQuestionByCat(Long cat_id, Long lvl_id){
        return questionRepository.getAllQuestionByCat(cat_id, lvl_id);
    }

    @Override
    public List<Long> getNextId() {return questionRepository.getNextId();}
}
