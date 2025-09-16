package at.kaindorf.strategy;

import at.kaindorf.pojos.QueueEntry;
import at.kaindorf.pojos.StrategyResult;

public interface IStrategy {
    StrategyResult analyze(QueueEntry queueEntry);
}
