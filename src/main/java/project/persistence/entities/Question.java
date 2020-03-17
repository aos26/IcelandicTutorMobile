package project.persistence.entities;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String questionWord;
    private String answer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private Long cat_id;
    private Long lvl_id;
    private String question_image;

    public Question() {
    }

    public Question(Long id, String questionWord, String answer, String wrongAnswer1,
                    String wrongAnswer2, Long cat_id, Long lvl_id, String question_image) {
        this.id = id;
        this.questionWord = questionWord;
        this.answer = answer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.cat_id = cat_id;
        this.lvl_id = lvl_id;
        this.question_image = question_image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionWord() {
        return questionWord;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionWord(String questionWord) {
        this.questionWord = questionWord;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public Long getCat_id() {
        return cat_id;
    }

    public void setCat_id(Long cat_id) {
        this.cat_id = cat_id;
    }

    public Long getLvl_id() {
        return lvl_id;
    }

    public void setLvl_id(Long lvl_id) {
        this.lvl_id = lvl_id;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }
}
