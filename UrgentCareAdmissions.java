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


/**
 * This class contains various methods to perform different tasks of the Urgent care program. It
 * performs tasks such as removing records from the start of the list, finding the correct index for
 * adding a new record based on the triagle level, adding the record, finding the longest waiting
 * patient and displaying a summary of the patient data.
 */

public class UrgentCareAdmissions {

  public static final int RED = 0;
  public static final int YELLOW = 1;
  public static final int GREEN = 2;

  /**
   * A helper method to find the correct index to insert a new patient at the given triage level.
   * This should be the index AFTER the last index currently occupied by a patient at that level.
   * <p>
   * For example, if the list currently contains: [RED, RED, GREEN, GREEN, GREEN, GREEN, null, null,
   * null, null] with a size of 6, a RED patient would be inserted at index 2, a YELLOW patient
   * would also be inserted at index 2, and a GREEN patient would be inserted at index 6.
   * <p>
   * If the list is FULL, the method should return -1 regardless of the triage value. This method
   * must not modify patientsList in any way.
   *
   * @param triage       the urgency level of the next patient's issue, RED YELLOW or GREEN
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index at which the patient should be inserted into the list, or -1 if the list is
   * full
   */
  public static int getAdmissionIndex(int triage, int[][] patientsList, int size) {
    if (patientsList.length == size) {
      return -1;
    }

    if (triage == RED) {
      for (int i = 0; i < size; i++) {
        if (patientsList[i][2] != RED) {
          return i;
        }
      }

    } else if (triage == YELLOW) {
      for (int i = 0; i < size; i++) {
        if (patientsList[i][2] != RED && patientsList[i][2] != YELLOW) {
          return i;
        }
      }

    } else {
      return size;
    }

    return 0;
  }

  /**
   * Adds the patient record, a three-element perfect size array of ints, to the patients list in
   * the given position. This method must maintain the ordering of the rest of the array, so any
   * patients in higher index positions must be shifted out of the way.
   * <p>
   * If there is no room to add a new patient to the array or the index provided is not a valid
   * index into the oversize array (for adding, valid indexes are 0 through size), the method should
   * not modify the patientsList array in any way.
   *
   * @param patientRecord a three-element perfect size array of ints, containing the patient's
   *                      5-digit case ID, their admission order number, and their triage level
   * @param index         the index at which the patientRecord should be added to patientsList,
   *                      assumed to correctly follow the requirements of getAdmissionIndex()
   * @param patientsList  the current, active list of patient records
   * @param size          the number of patients currently in the list
   * @return the number of patients in patientsList after this method has finished running
   */

  public static int addPatient(int[] patientRecord, int index, int[][] patientsList, int size) {
    if (patientsList.length == size) {
      return size;
    }

    for (int i = size; i > index; i--) {
      patientsList[i] = patientsList[i - 1];
    }

    patientsList[index] = patientRecord;
    size++;
    return size;
  }

  /**
   * Removes the patient record at index 0 of the patientsList, if there is one, and updates the
   * rest of the list to maintain the oversize array in its current ordering.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the number of patients in patientsList after this method has finished running
   */
  public static int removeNextPatient(int[][] patientsList, int size) {
    if (size == 0) {
      return 0;
    }
    for (int i = 0; i < size - 1; i++) {
      patientsList[i] = patientsList[i + 1];
    }

    patientsList[--size] = null;
    return size;
  }

  /**
   * Finds the index of a patient given their caseID number. This method must not modify
   * patientsList in any way.
   *
   * @param caseID       the five-digit case number assigned to the patient record to find
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index of the patient record matching the given caseID number, or -1 if the list is
   * empty or the caseID is not found
   */

  public static int getPatientIndex(int caseID, int[][] patientsList, int size) {
    if (size == 0) {
      return -1;
    }

    for (int i = 0; i < size; i++) {
      if (patientsList[i][0] == caseID) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Finds the patient who arrived earliest still currently present in the patientsList, and returns
   * the index of their patient record within the patientsList. The arrival value is strictly
   * increasing for each new patient, so you will not need to handle the case where two values are
   * equal.
   * <p>
   * That is, for all patient records [ caseID, arrivalOrder, triage ], this method should find and
   * return the one with the minimum arrivalOrder value.
   * <p>
   * This method must not modify patientsList in any way.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index of the patient record with the smallest value in their arrival integer, or -1
   * if the list is empty
   */

  public static int getLongestWaitingPatientIndex(int[][] patientsList, int size) {
    if (size == 0) {
      return -1;
    }

    int earliest = Integer.MAX_VALUE;
    int index = 0;
    for (int i = 0; i < size; i++) {
      if (patientsList[i][1] < earliest) {
        earliest = patientsList[i][1];
        index = i;
      }
    }

    return index;
  }

  /**
   * Creates a formatted String summary of the current state of the patientsList array, as follows:
   * <p>
   * Total number of patients: 5 RED: 1 YELLOW: 3 GREEN: 1
   * <p>
   * The first line displays the current size of the array. The next three lines display counts of
   * patients at each of the three triage levels currently in the patientsList. Any or all of these
   * numbers may be 0.
   * <p>
   * This method must not modify the patientsList array in any way.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return a String summarizing the patientsList as shown in this comment
   */

  public static String getSummary(int[][] patientsList, int size) {
    int numRed = 0;
    int numYellow = 0;
    int numGreen = 0;

    for (int i = 0; i < size; i++) {
      if (patientsList[i][2] == RED) {
        numRed++;
      } else if (patientsList[i][2] == YELLOW) {
        numYellow++;
      } else {
        numGreen++;
      }
    }

    return "Total number of patients: " + size + "\nRED: " + numRed + "\nYELLOW: " + numYellow +
        "\nGREEN: " + numGreen;
  }


}
