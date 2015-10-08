package patienthub.binary.com.patienthub.data;

/**
 * Created by Mark Aziz on 7/10/2015.
 */
public class Medications {

    private Patient patient;

    public Medications(Patient patient){
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


}
