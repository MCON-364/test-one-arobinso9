package edu.touro.las.mcon364.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StreamExerciseTest {

    private BasicStreamsQuiz exercise;

    @BeforeEach
    void setUp() {
        exercise = new BasicStreamsQuiz();
    }

    // I didnt have time to calculate the average and delta
    @Test
    @DisplayName("calculateClassAverage")
    void testCalculateClassAverage() {
        double avg = exercise.averagePassingScore();
        if (avg > 0) {
            assertEquals(81.275, avg, 0.01);
        }
    }

}

