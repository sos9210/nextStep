package next.model;

import java.util.Date;

public class Answer {
    private int answerId;
    private String writer;
    private String content;
    private Date createDate;
    private int questionId;

    public Answer() {
    }
    public Answer(String writer, String content, int questionId) {
        this.writer = writer;
        this.content = content;
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", questionId=" + questionId +
                '}';
    }
}
