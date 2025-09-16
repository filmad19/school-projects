package at.kaindorf.observer;

import at.kaindorf.pojos.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public class LogObserver extends Observer {

    public LogObserver(Subject subject) {
        super(subject);
        subject.register(this);
    }

    @Override
    public void printResult(Result result) {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result) + ",");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
