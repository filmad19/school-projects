package at.kaindorf.observer;

import at.kaindorf.pojos.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class Observer {
    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
    }

    protected static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public abstract void printResult(Result result);
}
