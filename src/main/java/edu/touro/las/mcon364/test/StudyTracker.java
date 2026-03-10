package edu.touro.las.mcon364.test;

import java.util.*;

public class StudyTracker {

    private final Map<String, List<Integer>> scoresByLearner = new HashMap<>();
    private final Deque<UndoStep> undoStack = new ArrayDeque<>();

    // Helper methods already provided for tests and local inspection.
    public Optional<List<Integer>> scoresFor(String name) {
        return Optional.ofNullable(scoresByLearner.get(name));
    }

    public Set<String> learnerNames() {
        return scoresByLearner.keySet();
    }
    /**
     * Problem 11
     * Add a learner with an empty score list.
     *
     * Return:
     * - true if the learner was added
     * - false if the learner already exists
     *
     * Throw IllegalArgumentException if name is null or blank.
     */
    public boolean addLearner(String name) {
        if (name == null || name.isEmpty())
            throw  new IllegalArgumentException(" name cannot be null or empty");

        if (!scoresByLearner.containsKey(name)){
            scoresByLearner.put(name, new ArrayList<>());
            return true;
        }
        return false;
    }

    /**
     * Problem 12
     * Add a score to an existing learner.
     *
     * Return:
     * - true if the score was added
     * - false if the learner does not exist
     *
     * Valid scores are 0 through 100 inclusive.
     * Throw IllegalArgumentException for invalid scores.
     *
     * This operation should be undoable.
     */
    public boolean addScore(String name, int score) {

        if(score <0 || score > 100)
            throw new IllegalArgumentException("score must be between 0 and 100");

        if(scoresByLearner.containsKey(name)){
            var scores = scoresByLearner.get(name);
            scores.add(score);
            undoStack.push(() -> scores.removeLast());
            return true;
        }
        return false;
    }

    /**
     * Problem 13
     * Return the average score for one learner.
     *
     * Return Optional.empty() if:
     * - the learner does not exist, or
     * - the learner has no scores
     */
    public Optional<Double> averageFor(String name) {
        // if the student is NOT in the system or the student has no grades, return Optional.Empty() bc we hv no avg
        if(!scoresByLearner.containsKey(name) || scoresByLearner.get(name).isEmpty())
            return Optional.empty();
        var scores = scoresByLearner.get(name);
        int scoreTtl=0;
        for (var score: scores){
            scoreTtl+=score;
        }
        return Optional.ofNullable((double) scoreTtl/scores.size());
    }

    /**
     * Problem 14
     * Convert a learner average into a letter band.
     *
     * A: 90+
     * B: 80-89.999...
     * C: 70-79.999...
     * D: 60-69.999...
     * F: below 60
     *
     * Return Optional.empty() when no average exists.
     */
    public Optional<String> letterBandFor(String name) {
        Optional<Double> avgOptional= averageFor(name);
        if(avgOptional.isEmpty())
            return Optional.empty();

        double avg= avgOptional.get();
        var score = switch((int) avg /10){
            case 10,9 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> {
                yield "F";
            }
        };
        return Optional.ofNullable(score);
    }

    /**
     * Problem 15
     * Undo the most recent state-changing operation.
     *
     * Return true if something was undone.
     * Return false if there is nothing to undo.
     */
    public boolean undoLastChange() {
        if(undoStack.isEmpty())
            return false;
        // we pop the instruction out of the stack
        var recentAction= undoStack.pop();
        recentAction.undo();
        return true;
    }


}
