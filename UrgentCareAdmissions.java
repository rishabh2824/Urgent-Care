//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    ExceptionalCareAdmissions.java
// Course:   CS 300 Spring 2023
//
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
//import statements
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * An object-oriented iteration of our UrgentCareAdmissions program. Patient records in the
 * patientsList are now single PatientRecord objects, containing the same data as before but with a
 * few additional components, namely: age, gender, and a boolean value indicating whether they have
 * been seen by a doctor. PatientRecords are still maintained as an oversize array.
 */
public class ExceptionalCareAdmissions {

  private PatientRecord[] patientsList; //An oversize array containing the PatientRecords
  // currently active in this object
  private int size; //The number of values in the oversize array

  /**
   * Creates a new, empty ExceptionalCareAdmissions object with the given capacity
   *
   * @param capacity the maximum number of patient records this ExceptionalCareAdmissions object can
   *                 hold
   * @throws IllegalArgumentException if the provided capacity is less than or equal to zero
   */
  public ExceptionalCareAdmissions(int capacity) throws IllegalArgumentException {
    //Throw exception at invalid capacity
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity must be greater than 0");
    }
    //Create a new patient list of the given capacity
    this.patientsList = new PatientRecord[capacity];
    this.size = 0;
  }

  /**
   * An accessor method to determine if the patientsList is currently full
   *
   * @return true if the patientsList is full, false otherwise
   */
  public boolean isFull() {
    if (size == patientsList.length) {
      return true;
    }
    return false;
  }

  /**
   * Accesses the current number of patient records in the patientsList
   *
   * @return the current number of PatientRecord objects in this ExceptionalCareAdmissions object
   */
  public int size() {

    return this.size;
  }

  /**
   * Accesses the current number of patient records in this patientsList representing patients who
   * have already been seen (and could be removed from the list)
   *
   * @return the current count of patientRecords for which the hasBeenSeen() method returns true
   */
  public int getNumberSeenPatients() {
    int numPatientsSeen = 0;
    for (int i = 0; i < size; i++) {
      if (patientsList[i].hasBeenSeen()) {
        numPatientsSeen++;
      }
    }
    return numPatientsSeen;
  }

  /**
   * As before, this helper method will find the correct index to insert the new patient record into
   * patientsList, maintaining triage priority order. It uses objects and exceptions rather than
   * primitive values.
   *
   * @param rec the PatientRecord to be added to the list
   * @return the correct index of patientsList at which rec should be added
   * @throws IllegalStateException if the patientsList is full: - with the message
   *                               "cleanPatientsList()" if the patientsList contains any patients
   *                               who have been seen, or - with the message "Cannot admit new
   *                               patients" if there are NO seen patients in the patientsList
   */
  public int getAdmissionIndex(PatientRecord rec) throws IllegalStateException {
    //check if patients list is full
    if (size == patientsList.length) {
      //check if there are any scene patients
      if (getNumberSeenPatients() > 0) {
        //Throw appropriate exception
        throw new IllegalStateException("cleanPatientsList()");
      } else {
        throw new IllegalStateException("Cannot admit new patients");
      }
    }
    //The index for the record to be inserted
    int index = 0;
    //Check if the triagle is red
    if (rec.getTriage() == 0) {
      //Loop through patients list
      for (int i = 0; i < size; i++) {
        //The index will be the first non-red triagle patient
        if (patientsList[i].getTriage() != PatientRecord.RED) {
          index = i;
          break;
        }
      }
      //check if the triagle is yellow
    } else if (rec.getTriage() == PatientRecord.YELLOW) {
      //Loop through patients list
      for (int i = 0; i < size; i++) {
        //The index will be the first green patient
        if (patientsList[i].getTriage() == PatientRecord.GREEN) {
          index = i;
          break;
        }
      }
      //If triagle is green then index is the last element (size)
    } else {
      index = size;
    }

    return index;
  }

  /**
   * Adds the provided PatientRecord at the provided position in the oversize patientsList array.
   * This method must maintain the ordering of the patientsList as before, and rather than returning
   * the new size, maintains the size field in this ExceptionalCareAdmissions object appropriately.
   *
   * @param rec   the PatientRecord to be added
   * @param index the index at which the PatientRecord should be added to patientsList, which you
   *              may assume is the same as the output of getAdmissionIndex()
   * @throws IllegalStateException    if the patientsList is full: - with the message
   *                                  "cleanPatientsList()" if the patientsList contains any
   *                                  patients who have been seen, or - with the message "Cannot
   *                                  admit new patients" if there are NO seen patients in the
   *                                  patientsList
   * @throws IllegalArgumentException with a descriptive error message if the patientsList is NOT
   *                                  full and the index is not a valid index into the oversize
   *                                  array
   */
  public void addPatient(PatientRecord rec, int index)
      throws IllegalStateException, IllegalArgumentException {
    //check if patients List is full
    if (size == patientsList.length) {
      //check if there are any scene patients
      if (getNumberSeenPatients() > 0) {
        //Throw appropriate exception
        throw new IllegalStateException("cleanPatientsList()");
      } else {
        throw new IllegalStateException("Cannot admit new patients");
      }
    } else if (index > patientsList.length) {
      throw new IllegalArgumentException("Invalid index");
    }
    //Shift all elements after the index to one place right to free up index
    for (int i = patientsList.length - 1; i > index; i--) {
      patientsList[i] = patientsList[i - 1];
    }
    //Insert record at the index
    patientsList[index] = rec;
    size++;
  }

  /**
   * Marks the patient with the given caseID as having been seen. This method will require you to
   * find the patient with the given caseID within the patientsList before you can modify their
   * status. This method may only modify one PatientRecord, and may not modify the patientsList
   * array or its size.
   *
   * @param caseID the CASE_NUMBER of the PatientRecord to be marked as having been seen
   * @throws IllegalStateException    if the patientsList is currently empty
   * @throws IllegalArgumentException if no PatientRecord with the given caseID is found
   */
  public void seePatient(int caseID) throws IllegalStateException, IllegalArgumentException {
    if (size == 0) {
      throw new IllegalStateException("The patients list is empty");
    }
    //Variable to check if the patient exists
    boolean exists = false;
    //Loop through patients List
    for (int i = 0; i < size; i++) {
      //Find the patient
      if (patientsList[i].CASE_NUMBER == caseID) {
        //Mark the patient as seen
        patientsList[i].seePatient();
        exists = true;
        break;
      }
    }

    if (!exists) {
      throw new IllegalArgumentException("No patient record with this case ID");
    }

  }

  /**
   * Creates a formatted String summary of the current state of the patientsList, incorporating an
   * additional component from the PatientRecord class. The first line displays the current size of
   * the array. The next displays the current number of patients who have been seen already,
   * followed by the number of patients at each triage level. Any of these numbers may be 0.
   *
   * @return a String summarizing the patientsList
   */
  public String getSummary() {
    int numSeen = 0;
    int numRed = 0;
    int numYellow = 0;
    int numGreen = 0;
    for (int i = 0; i < size; i++) {
      if (patientsList[i].hasBeenSeen()) {
        numSeen++;
      }

      if (patientsList[i].getTriage() == PatientRecord.RED) {
        numRed++;
      } else if (patientsList[i].getTriage() == PatientRecord.YELLOW) {
        numYellow++;
      } else {
        numGreen++;
      }
    }

    //Create the summary message
    String summary = String.format(
        "Total number of patients: %d\n" + "Total number seen: %d\n" + "RED: %d\n" +
            "YELLOW: %d\n" + "GREEN: %d", size, numSeen, numRed, numYellow, numGreen);
    return summary;
  }

  /**
   * This method runs occasionally to record the current state of the patientsList and save any
   * records for seen patients to a file, while removing them from the patientsList to make more
   * room.
   *
   * Every output file begins with the current summary of the patientsList, followed by the string
   * representation of each SEEN patient on a single line (PatientRecord's toString() method will be
   * helpful here). The patient records do not need to be in any particular order. If there are NO
   * seen patients when this method is called, the file will contain only the patientsList summary
   * (with "Total number seen: 0" as a part of it). If the provided file cannot be written to, do
   * not modify the patientsList in any way and just return from the method.
   *
   * By the end of this method, all SEEN patients should be recorded in the file and removed from
   * the patientsList array, and the array's size should be updated accordingly.
   *
   * For an example of how this method works, please refer to page 6 of the write-up.
   *
   * @param file the file object to use for recording the data
   */
  public void cleanPatientsList(File file) {
    //Create a new patients list to add the records that are not seen
    PatientRecord[] newList = new PatientRecord[patientsList.length];
    int newIndex = 0;
    int newSize = 0;
    try {
      //Set up a file and write the summary message to it
      PrintWriter writer = new PrintWriter(file);
      writer.println(getSummary());
      //Loop through the patients list
      for (int i = 0; i < size; i++) {
        //If patient is seen then add its details to the file
        if (patientsList[i].hasBeenSeen()) {
          writer.println(patientsList[i].toString());
          //If patient is not seen then add it to our new list
        } else {
          newList[newIndex] = patientsList[i];
          newSize++;
          newIndex++;
        }
      }
      //Equate new list and size to original one
      size = newSize;
      patientsList = newList;
      writer.flush();
      writer.close();
    } catch (IOException e) {
      return;
    }
  }

  /**
   * For testing purposes: this method creates and returns a string representation of the
   * patientsList, as the in-order string representation of each patient in the list on a
   * separate line. If patientsList is empty, returns an empty string.
   *
   * @return a string representation of the contents of patientsList
   */

  @Override
  public String toString() {
    String returnValue = "";
    for (PatientRecord r : patientsList) {
      returnValue += (r != null) ? r.toString() + "\n" : "";
    }
    return returnValue.trim();
  }
}
