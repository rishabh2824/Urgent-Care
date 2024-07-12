//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    PatientRecord.java
// Course:   CS 300 Spring 2023
//
// Author:   Rishabh Jain
// Email:    rvjain@wisc.edu
// Lecturer: Hobbes Legault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////


/**
 * An instantiable class which models a patient's record for the admissions program. This class
 * automatically generates a caseID number from provided patient data, which is not exactly
 * HIPAA-compliant.
 */
public class PatientRecord {
  static final int RED = 0; //One of the triage levels.
  static final int YELLOW = 1; //One of the triage levels.
  static final int GREEN = 2; //One of the triage levels.
  private static int patientCounter; //Counts the number of patients created.
  private int age; //This patient's age.
  public final int CASE_NUMBER; //The generated case number associated with this patient record.
  private char gender; //This patient's single-character gender marker.
  private boolean hasBeenSeen; //Whether this patient has been marked as "seen".
  private int orderOfArrival; //The order in which this patient arrived; taken from the value of
  // patientCounter when this record was created.
  private int triage; //This patient's triage level, one of [RED, YELLOW, GREEN].

  /**
   * Creates a new patient record and assigns it a CASE_NUMBER using the provided information.
   *
   * @param gender This patient's single-character gender marker.
   * @param age    This patient's age.
   * @param triage This patient's triage level, one of [RED, YELLOW, GREEN].
   * @throws IllegalArgumentException - with a descriptive error message if the provided triage
   *                                  value is not one of the class constants
   */
  public PatientRecord(char gender, int age, int triage) throws IllegalArgumentException {
    //Throw exception at invalid triagle value
    if (triage != 2 && triage != 1 && triage != 0) {
      throw new IllegalArgumentException("Invalid triagle value");
    }
    //Assign all fields
    this.orderOfArrival = patientCounter;
    this.triage = triage;
    this.age = age;
    this.gender = gender;
    //Generate case number
    CASE_NUMBER = generateCaseNumber(gender, age);
  }

  /**
   * Generates a five-digit case number for this patient using their reported gender and age.
   *
   * @param gender This patient's single-character gender marker.
   * @param age    This patient's age.
   * @return A five digit case number for the patient
   */
  public static int generateCaseNumber(char gender, int age) {
    //Determine the first digit of the caseID based on the gender
    String caseID = "";
    switch (gender) {
      case 'F':
        caseID = "1";
        break;
      case 'M':
        caseID = "2";
        break;
      case 'X':
        caseID = "3";
        break;
      default:
        caseID = "4";
    }
    //Convert age to string for concatenation
    String patientAge = String.valueOf(age);
    //If the age is a 3-digit number, then add the last two digits to caseID.
    if (patientAge.length() == 3) {
      caseID = caseID + patientAge.charAt(1) + patientAge.charAt(2);
      //If the age is a 1-digit number, then add a 0 before adding the age
    } else if (patientAge.length() == 1) {
      caseID = caseID + "0" + patientAge;
      //Else normally add the age
    } else {
      caseID = caseID + patientAge;
    }
    //Convert patient counter to string for concatenation
    String numPatients = String.valueOf(patientCounter);
    //Increment patient counter to point at next patient
    patientCounter++;
    //If the patient counter is a 1-digit number, then add a 0 before adding the patient counter
    if (numPatients.length() == 1) {
      caseID = caseID + "0" + numPatients;
      //If the patient counter is a 3-digit number, then add 00 signifying the 100th patient
    } else if (numPatients.length() == 3){
      caseID = caseID + "00";
      //Else normally add the patient counter
    } else {
      caseID = caseID + numPatients;
    }
    //convert string to integer
    return Integer.parseInt(caseID);
  }

  /**
   * For tester class purposes only: resents PatientRecord.patientCounter to 1.
   */
  public static void resetCounter() {
    PatientRecord.patientCounter = 1;
  }

  /**
   * Accessor method for age
   *
   * @return this PatientRecord's age value
   */
  public int getAge() {
    return this.age;
  }

  /**
   * Accessor method for order of arrival
   *
   * @return this PatientRecord's orderOfArrival value
   */
  public int getArrivalOrder() {
    return this.orderOfArrival;
  }

  /**
   * Accessor method for gender
   *
   * @return this PatientRecord's gender marker
   */
  public char getGender() {
    return this.gender;
  }

  /**
   * Accessor method for hasBeenSeen
   *
   * @return true if this patient has been seen, false otherwise
   */
  public boolean hasBeenSeen() {
    return this.hasBeenSeen;
  }

  /**
   * Accessor method for triage
   *
   * @return this PatientRecord's triage value
   */
  public int getTriage() {
    return this.triage;
  }

  /**
   * Marks this patient as having been seen.
   */
  public void seePatient() {
    this.hasBeenSeen = true;
  }

  /**
   * Creates and returns a String representation of this PatientRecord using its data field values:
   * [CASE_NUMBER]: [AGE][GENDER] ([TRIAGE]) Note that the [] are not actually included in the
   * result. For example, a 17-year-old male who was the first person to arrive and has triage level
   * YELLOW would have the toString():
   *
   * 21701: 17M (YELLOW)
   *
   * @return a String representation of this PatientRecord
   */

  @Override
  public String toString() {
    String stringRepresentation;
    if (triage == 0) {
      stringRepresentation = CASE_NUMBER + ": " + age + gender + " (RED)";
    } else if (triage == 1) {
      stringRepresentation = CASE_NUMBER + ": " + age + gender + " (YELLOW)";
    } else {
      stringRepresentation = CASE_NUMBER + ": " + age + gender + " (GREEN)";
    }

    return stringRepresentation;
  }
}
