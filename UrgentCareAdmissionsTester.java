//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Urgent Care Admissions
// Course:   CS 300 Spring 2023
//
// Author:   Rishabh Jain
// Email:    rvjain@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Arrays; // consider using Arrays.deepEquals() to test the contents of a 2D array

/**
 * This class contains tester methods that are used to test the methods provided in the
 * UrgentCareAdmissions.java file. It has one tester method corresponding to each method in the main
 * file, and an extra tester method to test all the edge cases. This class tells which methods are
 * working correctly and which are working incorrectly by returning true or false accordingly.
 *
 * @author Hobbes
 * @author Rishabh Jain
 */
public class UrgentCareAdmissionsTester {

  /**
   * This test method is provided for you in its entirety, to give you a model for the rest of this
   * class. This method tests the getAdmissionIndex() method on a non-empty, non-full array of
   * patient records which we create and maintain here.
   *
   * This method tests three scenarios:
   *
   * 1. Adding a patient with a HIGHER triage priority than any currently present in the array. To
   * do this, we create an array with no RED priority patients and get the index to add a RED
   * priority patient. We expect this to be 0, so if we get any other value, the test fails.
   *
   * 2. Adding a patient with a LOWER triage priority than any currently present in the array. To do
   * this, we create an array with no GREEN priority patients and get the index to add a GREEN
   * priority patient. We expect this to be the current size of the oversize array, so if we get any
   * other value, the test fails.
   *
   * 3. Adding a patient with the SAME triage priority as existing patients. New patients at the
   * same priority should be added AFTER any existing patients. We test this for all three triage
   * levels on an array containing patients at all three levels.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author hobbes
   */
  public static boolean testGetIndex() {

    // The non-empty, non-full oversize arrays to use in this test.
    // Note that we're using the UrgentCareAdmissions named constants to create these test records,
    // rather than their corresponding literal int values. 
    // This way if the numbers were to change in UrgentCareAdmissions, our test will still be valid.
    int[][] patientsListAllLevels =
        new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.YELLOW},
            {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
            {31501, 1, UrgentCareAdmissions.GREEN}, null, null, null};
    int allLevelsSize = 5;

    int[][] patientsListOnlyYellow = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
        {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}, null,
        null, null, null, null};
    int onlyYellowSize = 3;

    // scenario 1: add a patient with a higher priority than any existing patient
    {
      int expected = 0;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListOnlyYellow,
              onlyYellowSize);
      if (expected != actual) {
        return false;
      }
    }

    // scenario 2: add a patient with a lower priority than any existing patient
    {
      int expected = onlyYellowSize;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListOnlyYellow,
              onlyYellowSize);
      if (expected != actual) {
        return false;
      }
    }

    // scenario 3: verify that a patient with the same priority as existing patients gets
    // added after all of those patients
    {
      int expected = 1;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual) {
        return false;
      }

      expected = 4;
      actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.YELLOW, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual) {
        return false;
      }

      expected = allLevelsSize;
      actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual) {
        return false;
      }
    }

    // and finally, verify that the arrays were not changed at all
    {
      int[][] allLevelsCopy = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      if (!Arrays.deepEquals(patientsListAllLevels, allLevelsCopy)) {
        return false;
      }

      int[][] onlyYellowCopy = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}, null,
          null, null, null, null};
      if (!Arrays.deepEquals(patientsListOnlyYellow, onlyYellowCopy)) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method tests the addPatient() method using the following 3 scenarios:
   *
   * 1. Adding a patient record at the end of patient list. To do this, we create a patient record
   * with the lowest triagle level (GREEN) and the highest arrival order of all the other patient
   * records in the patient list. We create a patient list with random patient records and one empty
   * position at the end where our new record should be added. We expect the size of the patients
   * list to increase by 1, and if we get any other value for the size the test fails.
   *
   * 2. Adding a patient record in the middle of the patient list. To do this, we create a patient
   * record with the YELLOW triagle level and the highest arrival order of all the other patient
   * records in the patient list. We create a patient list such that there is atleast one patient
   * with the GREEN triagle level, and one empty position on the list, so that there is space for
   * new patient with the YELLOW triagle, and it gets added in the middle of the list. We expect the
   * size of the patients list to increase by 1, and if we get any other value for the size the test
   * fails.
   *
   * 3. Adding a patient record with an invalid index value. To do this we create a patient record
   * with an invalid index and try adding it to the patients list with random values and atleast one
   * empty position in a try/catch block. If no ArrayIndexOutOfBoundsException is thrown, then the
   * test fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author Rishabh Jain
   */
  public static boolean testAddPatient() {
    // (1) add a patient to the END of the patientsList
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN}, null};
      int size = 5;
      int[] patientRecord = new int[] {32789, 6, 0};
      int index = 5;
      int expected = 6;
      int actual = UrgentCareAdmissions.addPatient(patientRecord, index, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (2) add a patient to the MIDDLE of the patientsList
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN}, null};
      int size = 5;
      int[] patientRecord = new int[] {32789, 6, 1};
      int index = 4;
      int expected = 6;
      int actual = UrgentCareAdmissions.addPatient(patientRecord, index, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (3) add a patient using an invalid (out-of-bounds) index
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN}, null, null, null, null, null};
      int size = 5;
      int[] patientRecord = new int[] {32789, 6, 0};
      int index = 20;
      try {
        int actual = UrgentCareAdmissions.addPatient(patientRecord, index, patientsList, size);
        // If no exception is thrown then return false
        return false;
      } catch (ArrayIndexOutOfBoundsException e) {
        // Program works, move to next test case
      }
    }
    return true;
  }

  /**
   * This method tests the removeNextPatient method using the following two scenarios:
   *
   * 1. Removes a patient from the patient list containing more than 1 patient record. We expect the
   * first patient to be removed from the list and then all the other records will shift one
   * position left. To do this, we create a patient list with multiple records and remove the first
   * one. The size of the patients list will decrease by 1.
   *
   * 2. Removes a patient from a patient list containing only one record. We expect the first
   * patient to be removed from the list, and all values to become null. To do this, we create a
   * patient list with just 1 record. The size of the patients list will become 0.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author Rishabh Jain
   */
  public static boolean testRemovePatient() {
    // (1) remove a patient from a patientsList containing more than one record
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN}};
      int size = 5;
      int expected = 4;
      int actual = UrgentCareAdmissions.removeNextPatient(patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (2) remove a patient from a patientsList containing only one record
    int[][] patientsList = new int[][] {{32702, 1, UrgentCareAdmissions.RED}};
    int size = 1;
    int expected = 0;
    int actual = UrgentCareAdmissions.removeNextPatient(patientsList, size);
    if (expected != actual) {
      return false;
    }

    return true;
  }

  /**
   * This method tests the getPatientIndex() method using the following 3 scenarios:
   *
   * 1. Looks for a patient at the end of the list. To do this, we create a patients list with
   * random records. Then we call the method on the case ID of the last record in the patients list.
   * This should return the index of the last patient record in the list.
   *
   * 2. Looks for a patient in the middle of the list. To do this, we create a patients list with
   * random records. Then we call the method on the case ID of a record in the middle of the list
   * This should return the index of this patient record.
   *
   * 3. Looks for a patient not in the list. To do this, we create a patients list with random
   * records. Then we call the method on a random case ID that does not belong in the list. The
   * method should return -1.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author Rishabh Jain
   */
  public static boolean testGetPatientIndex() {
    // (1) look for a patient at the end of the list
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN}};
      int size = 5;
      int expected = 4;
      int caseID = 31501;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (2) look for a patient in the middle of the list
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      int expected = 2;
      int caseID = 22002;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (3) look for a patient not present in the list
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      int expected = -1;
      int caseID = 49495;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method tests the getLongestWaitingPatientIndex() method using the following 2 scenarios:
   *
   * 1. Calls the method on a patientsList with only one patient. To do this, we create a patient
   * list with only one record, and call the method. This should return the index 0 of the record.
   *
   * 2. Calls the method on a patientsList with at least three patients. To do this, we create a
   * patient list with multiple records and call the method. This then returns the index of the
   * record that has the highest arrival order.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author Rishabh Jain
   */
  public static boolean testLongestWaitingPatient() {
    // (1) call the method on a patientsList with only one patient
    {
      int[][] patientsList = new int[][] {{32702, 1, UrgentCareAdmissions.RED}};
      int size = 1;
      int expected = 0;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (2) call the method on a patientsList with at least three patients
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      int expected = 4;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method is used to test the edge cases of all the other methods. It tests the following
   * scenarios:
   *
   * 1. tests the getAdmissionIndex() method using an empty patientsList array and any triage level.
   * To do this, we create an empty patient list and call the method. The method should return the
   * index 0.
   *
   * 2. tests getAdmissionIndex() using a full patientsList array and any triage level. To do this,
   * we create a full patient list with random patient records. We call the method, and it should
   * return -1 because there is no space to add a new record.
   *
   * 3. tests addPatient() using a full patientsList array. To do this, we create a full patient
   * list with random patient records. Then we call the method using a random patient record, and it
   * will simply return the size of the original array because there is no space to add the new
   * record and the array will not be modified in any way.
   *
   * 4. tests removeNextPatient() using an empty patientsList array. To do this, we create an empty
   * patient list and call the method. This method should return 0 because the size will be zero and
   * no patient record will be removed.
   *
   * 5. tests getPatientIndex() using an empty patientsList array. To do this, we create an empty
   * patient list and call the method. The method will return -1, because the patient list is
   * empty.
   *
   * 6. tests getLongestWaitingPatientIndex() using an empty patientsList array. To do this, we
   * create an empty patient list and call the method. The method will return -1, because the
   * patient list is empty.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author Rishabh Jain
   */
  public static boolean testEmptyAndFullArrays() {
    // (1) test getAdmissionIndex using an empty patientsList array and any triage level
    {
      int[][] patientsList = new int[4][];
      int size = 0;
      int expected = 0;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (2) test getAdmissionIndex using a full patientsList array and any triage level
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      int expected = -1;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (3) test addPatient using a full patientsList array
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      int expected = 5;
      int[] patientRecord = new int[] {32789, 6, 0};
      int index = 1;
      int actual = UrgentCareAdmissions.addPatient(patientRecord, index, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (4) test removeNextPatient using an empty patientsList array
    {
      int[][] patientsList = new int[4][];
      int size = 0;
      int expected = 0;
      int actual = UrgentCareAdmissions.removeNextPatient(patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (5) test getPatientIndex using an empty patientsList array
    {
      int[][] patientsList = new int[4][];
      int size = 0;
      int expected = -1;
      int caseID = 45454;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    // (6) test getLongestWaitingPatientIndex using an empty patientsList array
    {
      int[][] patientsList = new int[4][];
      int size = 0;
      int expected = -1;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method is used to test the getSummary() method. It tests the following scenarios:
   *
   * 1. tests getSummary using an empty patientsList. To do this, we create an empty patient list
   * and call the method. This would be the string where the values for the total number of
   * patients, RED patients, YELLOW patients, and GREEN patients would all be 0.
   *
   * 2. tests getSummary using a patientsList with multiple patients at a single triage level. To do
   * this, we define a patients list such that all the patients have the same "RED" triagle level.
   * This would return a string where the values for the total number of patients and RED patients
   * will be 5 and for YELLOW and GREEN patients the values are 0.
   *
   * 3. tests getSummary using a patientsList with patients at all triage levels. To do this, we
   * define a patients list such that the list contains patients with "RED", "YELLOW" and "GREEN"
   * triagle levels. This would return the string with values accordingly.
   *
   * @return
   */
  public static boolean testGetSummary() {
    // (1) test getSummary using an empty patientsList
    {
      int[][] patientsList = new int[4][];
      int size = 0;
      String expected = "Total number of patients: 0\nRED: 0\nYELLOW: 0\nGREEN: 0";
      String actual = UrgentCareAdmissions.getSummary(patientsList, size);
      if (!expected.equals(actual)) {
        return false;
      }
    }

    // (2) test getSummary using a patientsList with multiple patients at a single triage level
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.RED}, {11901, 5, UrgentCareAdmissions.RED},
              {31501, 1, UrgentCareAdmissions.RED}};
      int size = 5;
      String expected = "Total number of patients: 5\nRED: 5\nYELLOW: 0\nGREEN: 0";
      String actual = UrgentCareAdmissions.getSummary(patientsList, size);
      if (!expected.equals(actual)) {
        return false;
      }
    }

    // (3) test getSummary using a patientsList with patients at all triage levels
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
              {31501, 1, UrgentCareAdmissions.GREEN},};
      int size = 5;
      String expected = "Total number of patients: 5\nRED: 2\nYELLOW: 2\nGREEN: 1";
      String actual = UrgentCareAdmissions.getSummary(patientsList, size);
      if (!expected.equals(actual)) {
        return false;
      }
    }
    {
      int[][] patientsList =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.RED},
              {22002, 4, UrgentCareAdmissions.GREEN}, {11901, 5, UrgentCareAdmissions.GREEN},
              null,};
      int size = 4;
      String expected = "Total number of patients: 4\nRED: 2\nYELLOW: 0\nGREEN: 2";
      String actual = UrgentCareAdmissions.getSummary(patientsList, size);
      if (!expected.equals(actual)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Runs each of the tester methods and displays their result
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("get index: " + testGetIndex());
    System.out.println("add patient: " + testAddPatient());
    System.out.println("remove patient: " + testRemovePatient());
    System.out.println("get patient: " + testGetPatientIndex());
    System.out.println("longest wait: " + testLongestWaitingPatient());
    System.out.println("empty/full: " + testEmptyAndFullArrays());
    System.out.println("get summary: " + testGetSummary());
  }

}
