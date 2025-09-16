package at.kaindorf.observer;

import at.kaindorf.pojos.Result;

import java.util.HashSet;
import java.util.Set;

public abstract class Subject {
    protected Set<Observer> observerSet = new HashSet<>();

    public void register(Observer observer) {
        observerSet.add(observer);
    }

    public void unregister(Observer observer) {
        observerSet.remove(observer);
    }

    public void notifyObserver(Result result) {
        for (Observer observer : observerSet) {
            observer.printResult(result);
        }
    }
}
