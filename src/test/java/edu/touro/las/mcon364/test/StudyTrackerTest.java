package edu.touro.las.mcon364.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



class StudyTrackerTest {
    private StudyTracker studyTracker;

    @BeforeEach
    void setUp() {
        studyTracker = new StudyTracker();
    }

    @Test
    void addStudent_ifDuplicateStudentThenReturnFalse() {
        assertTrue(studyTracker.addLearner("Aviva"));
        assertFalse(studyTracker.addLearner("Aviva"));
    }

    @Test
    void addStudent_createsEmptyGradeList() {
        assertTrue(studyTracker.addLearner("Aviva"));
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        // grades.isEmpty()	Is the Optional box empty?	To see if the student was found.
        // grades.get().isEmpty()	Is the List inside empty?	To see if the student has grades yet.
        //You only use .get() when you are 100% sure the value is there.
        assertTrue(grades.get().isEmpty());
    }
    @Test
    void addGrade_ifStudentNotThereReturnFalse() {
        assertFalse(studyTracker.addScore("Aviva", 100));
    }
    @Test
    void addGrade_ifStudentExistsThenAddGrade() {
        studyTracker.addLearner("Aviva");
        assertTrue(studyTracker.addScore("Aviva", 105));
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        assertEquals(1, grades.get().size());
        assertEquals(105, grades.get().getFirst());

    }
    @Test
    void addGrade_canAddMultipleGradesPerStudent(){
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 105);
        studyTracker.addScore("Aviva", 102);
        studyTracker.addScore("Aviva", 103);
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        assertEquals(3, grades.get().size());
        assertEquals(105, grades.get().get(0));
        assertEquals(102, grades.get().get(1));
        assertEquals(103, grades.get().get(2));
    }
    @Test
    void averageFor_inValidStudentReturnsEmpty(){
        assertTrue(studyTracker.averageFor("Aviva").isEmpty());
    }
    @Test
    void averageFor_ValidStudentWithNoGradeListReturnsEmpty(){
        studyTracker.addLearner("Aviva");
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        assertTrue(grades.get().isEmpty());
    }
    @Test
    void averageFor_ValidStudentWithMultipleGradeListsReturnsAverage(){
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 110);
        studyTracker.addScore("Aviva", 100);
        studyTracker.addScore("Aviva", 90);
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        assertEquals(3, grades.get().size());
        Optional<Double> avg = studyTracker.averageFor("Aviva");
        assertTrue(avg.isPresent());
        assertEquals(100.0, avg.get());
    }
    @Test
    void letterGradeFor_InvalidStudentReturnsEmpty(){
        assertTrue(studyTracker.scoresFor("Aviva").isEmpty());
    }
    @Test
    void letterGradeFor_ValidStudentWithNoGradeListReturnsEmpty(){
        studyTracker.addLearner("Aviva");
        Optional<List<Integer>> grades = studyTracker.scoresFor("Aviva");
        assertTrue(grades.isPresent());
        assertTrue(grades.get().isEmpty());
    }
    @Test
    void letterGradeFor_DefaultWorksCorrectly(){
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 32);
        Optional<String> letter = studyTracker.letterBandFor("Aviva");
        assertTrue(letter.isPresent());
        assertEquals("F", letter.get());
    }
    @Test
    void letterGradeFor_returnsA_from90to100() {
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 90);
        studyTracker.addScore("Alice", 100);
        Optional<String> letter = studyTracker.letterBandFor("Aviva");
        assertTrue(letter.isPresent());
        assertEquals("A", letter.get());
    }
    @Test
    void letterGradeFor_returnsB_from80to89() {
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 80);
        studyTracker.addScore("Aviva", 89);

        Optional<String> letter = studyTracker.letterBandFor("Aviva");
        assertTrue(letter.isPresent());
        assertEquals("B", letter.get());
    }
    @Test
    void letterGradeFor_returnsC_from70to79() {
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 70);
        studyTracker.addScore("Aviva", 79);

        Optional<String> letter = studyTracker.letterBandFor("Aviva");
        assertTrue(letter.isPresent());
        assertEquals("C", letter.get());
    }
    @Test
    void letterGradeFor_returnsD_from60to69() {
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 60);
        studyTracker.addScore("Aviva", 69);

        Optional<String> letter = studyTracker.letterBandFor("Aviva");
        assertTrue(letter.isPresent());
        assertEquals("D", letter.get());
    }
    @Test
    void undo_WhenNothingToUndoThenReturnFalse() {
        assertFalse(studyTracker.undoLastChange());
    }
    @Test
    void undo_AddGrade() {
        studyTracker.addLearner("Aviva");
        studyTracker.addScore("Aviva", 100);
        Optional<List<Integer>> gradesBefore = studyTracker.scoresFor("Aviva");
        assertEquals(1, gradesBefore.get().size());
        assertTrue(studyTracker.undoLastChange());
        Optional<List<Integer>> gradesAfter = studyTracker.scoresFor("Aviva");
        assertTrue(gradesAfter.isPresent());
        assertEquals(0, gradesAfter.get().size());
    }
}



