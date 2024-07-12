//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    ExceptionalCareTester.java
// Course:   CS 300 Spring 2023
// Author:   Rishabh Jain
// Email:    rvjain@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;

/**
 * Tester methods to test the ExceptionalCareAdmissions.java methods.
 */
public class ExceptionalCareTester {

  /**
   * This test method is provided for you in its entirety, to give you a model for testing an
   * instantiable class. This method verifies the correctness of your PatientRecord class.
   *
   * In this test, we create two PatientRecords with different information and use the accessor
   * methods to verify that both contain the correct information and have the correct String
   * representation.
   *
   * @return true if and only if all scenarios pass, false otherwise
   * @author hobbes
   */
  public static boolean testPatientRecord() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    // (1) create two PatientRecords with different, valid input
    // no exceptions should be thrown, so let's be safe:
    PatientRecord test1 = null, test2 = null;
    try {
      test1 = new PatientRecord('M', 17, PatientRecord.YELLOW);
      test2 = new PatientRecord('X', 21, PatientRecord.GREEN);
    } catch (Exception e) {
      return false;
    }


    // (2) verify their data fields:
    {
      // CASE_NUMBER
      int expected1 = 21701;
      int expected2 = 32102;
      if (test1.CASE_NUMBER != expected1 || test2.CASE_NUMBER != expected2) {
        return false;
      }
    }
    {
      // triage
      int expected1 = PatientRecord.YELLOW;
      int expected2 = PatientRecord.GREEN;
      if (test1.getTriage() != expected1 || test2.getTriage() != expected2) {
        return false;
      }
    }
    {
      // gender
      char expected1 = 'M';
      char expected2 = 'X';
      if (test1.getGender() != expected1 || test2.getGender() != expected2) {
        return false;
      }
    }
    {
      // age
      int expected1 = 17;
      int expected2 = 21;
      if (test1.getAge() != expected1 || test2.getAge() != expected2) {
        return false;
      }
    }
    {
      // orderOfArrival
      int expected1 = 1;
      int expected2 = 2;
      if (test1.getArrivalOrder() != expected1 || test2.getArrivalOrder() != expected2) {
        return false;
      }
    }
    {
      // hasBeenSeen - try the mutator too
      if (test1.hasBeenSeen() || test2.hasBeenSeen()) {
        return false;
      }
      test1.seePatient();
      if (!test1.hasBeenSeen() || test2.hasBeenSeen()) {
        return false;
      }
    }


    // (3) verify their string representations
    {
      String expected1 = "21701: 17M (YELLOW)";
      String expected2 = "32102: 21X (GREEN)";
      if (!test1.toString().equals(expected1) || !test2.toString().equals(expected2)) {
        return false;
      }
    }


    // (4) finally, verify that the constructor throws an exception for an invalid triage value
    try {
      new PatientRecord('F', 4, -17);
      // if we get here, no exception was thrown and the test fails
      return false;
    } catch (IllegalArgumentException e) {
      // correct exception type, but it should have a message:
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      // incorrect exception type
      return false;
    }

    // if we've gotten this far, we haven't failed either of the scenarios, so our test passes!
    return true;
  }

  /**
   * This method tests that a valid input constructor call does not throw any exceptions and that a
   * newly created object has size 0, is not full, has no seen patients, and its toString() is an
   * empty string.
   *
   * @return True, if and only if all scenario's pass, and false otherwise
   */
  public static boolean testAdmissionsConstructorValid() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    // (1) verify that a normal, valid-input constructor call does NOT throw an exception
    {
      try {
        PatientRecord actual = new PatientRecord('M', 45, 1);
      } catch (Exception e) {
        return false;
      }
    }


    // (2) verify that a just-created object has size 0, is not full, has no seen patients, and
    // its toString() is an empty string
    ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(20);
    if (patientsList.size() != 0 || patientsList.isFull() ||
        patientsList.getNumberSeenPatients() != 0 || !patientsList.toString().equals("")) {
      return false;
    }

    return true;
  }

  /**
   * This method verifies that calling the constructor with capacity <= 0 causes an
   * IllegalArgumentException.
   *
   * @return true if and only if all scenario's pass and false otherwise.
   */
  public static boolean testAdmissionsConstructorError() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    // (1) verify that calling the constructor with capacity <= 0 causes an IllegalArgumentException
    try {
      ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(-10);
      //If no exception is thrown the test fails.
      return false;
    } catch (IllegalArgumentException e) {
      //This is the right type of exception, but we need to ensure that a message is also shown.
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      //If any other exception is thrown then return false.
      return false;
    }

    return true;
  }

  /**
   * This method tests the add patient method, by adding a patient to an empty list, at the end of
   * the list, and at the beginning of the list.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */
  public static boolean testAddPatientValid() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    // (1) add a new patient to an empty list - since you cannot use Arrays.deepEquals() here
    // anymore, verify the contents of the patientsList using ExceptionalCareAdmissions.toString()
    {
      try {
        ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(20);
        PatientRecord rec = new PatientRecord('M', 45, 1);
        patientsList.addPatient(rec, patientsList.getAdmissionIndex(rec));
        String expected = "24501: 45M (YELLOW)";
        String actual = patientsList.toString();
        if (!expected.equals(actual)) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }


    // (2) add a new patient to the end of the list
    {
      // FIRST: reset the patient counter so this tester method can be run independently
      PatientRecord.resetCounter();

      try {
        ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(20);
        patientsList.addPatient(new PatientRecord('M', 45, 0), 0);
        PatientRecord rec = new PatientRecord('F', 32, 2);
        patientsList.addPatient(rec, patientsList.getAdmissionIndex(rec));
        String expected = "24501: 45M (RED)\n13202: 32F (GREEN)";
        String actual = patientsList.toString();
        if (!expected.equals(actual)) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }


    // (3) add a new patient to the beginning of the list
    {
      // FIRST: reset the patient counter so this tester method can be run independently
      PatientRecord.resetCounter();
      try {
        ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(20);
        patientsList.addPatient(new PatientRecord('M', 45, 2), 0);
        PatientRecord rec = new PatientRecord('F', 32, 0);
        patientsList.addPatient(rec, patientsList.getAdmissionIndex(rec));
        String expected = "13202: 32F (RED)\n24501: 45M (GREEN)";
        String actual = patientsList.toString();
        if (!expected.equals(actual)) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }
    return true;
  }

  /**
   * This test method is provided for you in its entirety, to give you a model for verifying a
   * method which throws exceptions. This method tests addPatient() with two different, full
   * patientsList arrays; one contains seen patients and one does not.
   *
   * We assume for the purposes of this method that the ExceptionalCareAdmissions constructor and
   * PatientRecord constructor are working properly.
   *
   * This method must NOT allow ANY exceptions to be thrown from the tested method.
   *
   * @return true if and only if all scenarios pass, false otherwise
   * @author hobbes
   */
  public static boolean testAddPatientError() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    //(1) a full Admissions object that contains no seen patients

    // create a small admissions object and fill it with patients. i'm filling the list
    // in decreasing order of triage, so the addPatient() method has to do the least
    // amount of work.
    ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(3);
    full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

    // saving one patient in a local variable, so we can mark them "seen" later
    PatientRecord seenPatient = new PatientRecord('F', 20, PatientRecord.GREEN);
    full.addPatient(seenPatient, 2);

    try {
      full.addPatient(new PatientRecord('F', 17, PatientRecord.GREEN), 3);
      // if we get here, no exception was thrown and the test fails
      return false;
    } catch (IllegalStateException e) {
      // this is the correct type of exception, but for this method we expect a specific
      // error message so we have one more step to verify:
      String message = e.getMessage();
      String expected = "Cannot admit new patients";
      if (!message.equals(expected)) {
        return false;
      }
    } catch (Exception e) {
      // this is the incorrect exception type, and we can just fail the test now
      return false;
    }


    //(2) a full Admissions object that contains at least one seen patient

    // since we have a reference to the patient at index 2, we'll just mark them seen here
    seenPatient.seePatient();

    try {
      full.addPatient(new PatientRecord('F', 17, PatientRecord.GREEN), 3);
      // if we get here, no exception was thrown and the test fails
      return false;
    } catch (IllegalStateException e) {
      // this is the correct type of exception again, but we expect a different error
      // message this time:
      String message = e.getMessage();
      String expected = "cleanPatientsList()";
      if (!message.equals(expected)) {
        return false;
      }
    } catch (Exception e) {
      // this is the incorrect exception type, and the test fails here
      return false;
    }

    // if we've gotten this far, we haven't failed either of the scenarios, so our test passes!
    return true;
  }

  /**
   * This method tests the getAdmissionIndex method by calling the method on records that should be
   * added to the end of the list, start of the list, and middle of the list.
   *
   * @return true if and only if all scenario's pass, and false otherwise
   */
  public static boolean testGetIndexValid() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();

    // create an Admissions object and add a few Records to it, leaving some space
    ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(20);
    patientsList.addPatient(new PatientRecord('M', 18, PatientRecord.YELLOW), 0);
    patientsList.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);
    patientsList.addPatient(new PatientRecord('F', 17, PatientRecord.GREEN), 2);


    // (1) get the index of a PatientRecord that should go at the END
    {
      try {
        int actual =
            patientsList.getAdmissionIndex(new PatientRecord('X', 34, PatientRecord.GREEN));
        int expected = 3;

        if (actual != expected) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }


    // (2) get the index of a PatientRecord that should go at the BEGINNING
    {
      try {
        int actual = patientsList.getAdmissionIndex(new PatientRecord('X', 34, PatientRecord.RED));
        int expected = 0;

        if (actual != expected) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }


    // (3) get the index of a PatientRecord that should go in the MIDDLE
    {
      try {
        int actual =
            patientsList.getAdmissionIndex(new PatientRecord('X', 34, PatientRecord.YELLOW));
        int expected = 2;

        if (actual != expected) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method is used to test the getAdmissionIndex method, to check that it throws the correct
   * exceptions.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */
  public static boolean testGetIndexError() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();
    // create an Admissions object and add Records to it until it is full, as in testAddPatientError
    ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(3);
    patientsList.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    patientsList.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);
    PatientRecord seenPatient = new PatientRecord('F', 17, PatientRecord.GREEN);
    patientsList.addPatient(seenPatient, 2);


    // (1) verify the exception when there are no patients who have been seen in the list
    {
      try {
        patientsList.getAdmissionIndex(new PatientRecord('F', 17, PatientRecord.GREEN));
        // if we get here, no exception was thrown and the test fails
        return false;
      } catch (IllegalStateException e) {
        // this is the correct type of exception again, but we expect a different error
        // message this time:
        String message = e.getMessage();
        String expected = "Cannot admit new patients";
        if (!message.equals(expected)) {
          return false;
        }

      } catch (Exception e) {
        // this is the incorrect exception type, and the test fails here
        return false;
      }
    }


    // (2) verify the exception when there is at least one patient who has been seen
    {
      try {
        seenPatient.seePatient();
        patientsList.getAdmissionIndex(new PatientRecord('F', 17, PatientRecord.GREEN));
        // if we get here, no exception was thrown and the test fails
        return false;
      } catch (IllegalStateException e) {
        // this is the correct type of exception again, but we expect a different error
        // message this time:
        String message = e.getMessage();
        String expected = "cleanPatientsList()";
        if (!message.equals(expected)) {
          return false;
        }
      } catch (Exception e) {
        // this is the incorrect exception type, and the test fails here
        return false;
      }
    }

    return true;
  }

  /**
   * The method tests that all the accessor methods are functioning correctly.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */
  public static boolean testAdmissionsBasicAccessors() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();


    // (1) verify isFull() on a non-full and a full Admissions object
    //Full Admissions object
    {
      ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(2);
      patientsList.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
      patientsList.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

      if (!patientsList.isFull()) {
        return false;
      }
    }

    //Non-full admissions object
    {
      ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(6);
      patientsList.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
      patientsList.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

      if (patientsList.isFull()) {
        return false;
      }
    }


    // (2) verify size() before and after adding a PatientRecord
    ExceptionalCareAdmissions patientsList = new ExceptionalCareAdmissions(6);
    patientsList.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    patientsList.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);
    PatientRecord rec = new PatientRecord('F', 12, PatientRecord.GREEN);

    //Before adding
    {
      int expected = 2;
      int actual = patientsList.size();

      if (expected != actual) {
        return false;
      }
    }

    //After adding
    {
      patientsList.addPatient(rec, 2);
      int expected = 3;
      int actual = patientsList.size();
      if (expected != actual) {
        return false;
      }
    }


    // (3) verify getNumberSeenPatients() before and after seeing a patient
    // (see testAddPatientError for a model of how to do this while bypassing seePatient())
    //FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();
    // Before seeing
    {
      ExceptionalCareAdmissions patientsList2 = new ExceptionalCareAdmissions(6);
      patientsList2.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
      patientsList2.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

      int actual = patientsList2.getNumberSeenPatients();
      int expected = 0;

      if (expected != actual) {
        return false;
      }


      //After seeing
      // saving one patient in a local variable, so we can mark them "seen" later
      PatientRecord seenPatient = new PatientRecord('F', 20, PatientRecord.GREEN);
      patientsList2.addPatient(seenPatient, 2);
      patientsList2.seePatient(12003);

      int newActual = patientsList2.getNumberSeenPatients();
      int newExpected = 1;

      if (expected != actual) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method is used to test the seePatient method, to check if correctly marks the patients as
   * seen when appropriate.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */

  public static boolean testSeePatientValid() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();
    // create an Admissions object and add a few Records to it, saving a shallow copy of
    // at least one of the PatientRecord references
    ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(6);
    full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

    // saving one patient in a local variable, so we can mark them "seen" later
    PatientRecord seenPatient = new PatientRecord('F', 20, PatientRecord.GREEN);
    full.addPatient(seenPatient, 2);


    // (1) call seePatient() on the caseID of your saved reference and verify that its
    // hasBeenSeen() accessor return value changes
    {
      try {
        full.seePatient(12003);
        if (!seenPatient.hasBeenSeen()) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }


    // (2) verify getNumberSeenPatients() before and after seeing a different patient
    //Before seeing
    PatientRecord newSeenPatient = new PatientRecord('X', 56, PatientRecord.GREEN);
    full.addPatient(newSeenPatient, 3);
    {
      if (newSeenPatient.hasBeenSeen()) {
        return false;
      }
      //After seeing
      try {
        full.seePatient(35604);
        if (!newSeenPatient.hasBeenSeen()) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    return true;
  }


  /**
   * This method is used to check whether the seePatient method throws the correct exceptions.
   *
   * @return true if and only if all the scenario's pass, and false otherwise.
   */
  public static boolean testSeePatientError() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();
    // (1) verify that seeing a caseID for a patient not in the list causes an IllegalStateException
    ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(3);
    full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

    try {
      full.seePatient(99999);
      // if we get here, no exception was thrown and the test fails
      return false;
    } catch (IllegalArgumentException e) {
      // this is the correct type of exception, but for this method we expect a specific
      // error message so we have one more step to verify:
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      // this is the incorrect exception type, and we can just fail the test now
      return false;
    }

    return true;
  }

  /**
   * This method is used to test the getSummary Method. It checks whether the method returns the
   * correct string in the file.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */
  public static boolean testGetSummary() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();
    // (1) choose one getSummary() test from P01; this method has not changed much
    ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(3);
    full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
    full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

    String actual = full.getSummary();
    String expected = String.format(
        "Total number of patients: 2\n" + "Total number seen: 0\n" + "RED: 1\n" + "YELLOW: 1\n" +
            "GREEN: 0");

    if (!expected.equals(actual)) {
      return false;
    }
    return true;
  }

  /**
   * This method is used to test the cleanPatient method. It checks whether the data for seen
   * patients is accurately being written in our file.
   *
   * @return true if and only if all scenario's pass, and false otherwise.
   */
  public static boolean testCleanList() {
    // FIRST: reset the patient counter so this tester method can be run independently
    PatientRecord.resetCounter();

    // (1) using ExceptionalCareAdmissions.toString(), verify that a patientsList with NO seen
    // patients does not change after calling cleanPatientsList()
    {
      ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(5);
      full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
      full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);
      full.addPatient(new PatientRecord('M', 45, PatientRecord.GREEN), 2);

      String expected = "21801: 18M (RED)\n20502: 5M (YELLOW)\n24503: 45M (GREEN)";
      File file = new File("hello.txt");
      full.cleanPatientsList(file);
      String actual = full.toString();

      if (!expected.equals(actual)) {
        return false;
      }

      // (2) call seePatient() for at least two of the records in your patientsList, and use
      // toString() to verify that they have been removed after calling cleanPatientsList()

      PatientRecord seenPatient1 = (new PatientRecord('M', 5, PatientRecord.GREEN));
      full.addPatient(seenPatient1, 3);
      PatientRecord seenPatient2 = (new PatientRecord('M', 45, PatientRecord.GREEN));
      full.addPatient(seenPatient2, 4);

      full.seePatient(20504);
      full.seePatient(24505);

      full.cleanPatientsList(file);
      String newActual = full.toString();

      if (!expected.equals(newActual)) {
        return false;
      }
    }

    // NOTE: you do NOT need to verify file contents in this test method; please do so manually
    return true;
  }

  /**
   * Runs each of the tester methods and displays the result. Methods with two testers have their
   * output grouped for convenience; a failed test is displayed as "X" and a passed test is
   * displayed as "pass"
   *
   * @param args unused
   * @author hobbes
   */

  public static void main(String[] args) {
    System.out.println("PatientRecord: " + (testPatientRecord() ? "pass" : "X"));
    System.out.println(
        "Admissions Constructor: " + (testAdmissionsConstructorValid() ? "pass" : "X") + ", " +
            (testAdmissionsConstructorError() ? "pass" : "X"));
    System.out.println("Add Patient: " + (testAddPatientValid() ? "pass" : "X") + ", " +
        (testAddPatientError() ? "pass" : "X"));
    System.out.println("Get Admission Index: " + (testGetIndexValid() ? "pass" : "X") + ", " +
        (testGetIndexError() ? "pass" : "X"));
    System.out.println("Basic Accessors: " + (testAdmissionsBasicAccessors() ? "pass" : "X"));
    System.out.println("See Patient: " + (testSeePatientValid() ? "pass" : "X") + ", " +
        (testSeePatientError() ? "pass" : "X"));
    System.out.println("Get Summary: " + (testGetSummary() ? "pass" : "X"));
    System.out.println("Clean List: " + (testCleanList() ? "pass" : "X"));
  }
}
