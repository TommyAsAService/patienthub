package patienthub.binary.com.patienthub.data;

/**
 * Created by Mark Aziz on 7/10/2015.
 */
public class Dosage {

    private int patient_id;
    private int id;
    private String label;
    private String start_time;
    private String notes;
    private int time_take_per_day;
    private int amount_given;
    private String created_at;
    private String updated_at;
    private String medication_name;
    private Treatment treatment;

    public Dosage(int patient_id, int id, String label, String start_time, String notes, int time_take_per_day, int amount_given, String created_at, String updated_at, String medication_name, Treatment treatment) {
        this.patient_id = patient_id;
        this.id = id;
        this.label = label;
        this.start_time = start_time;
        this.notes = notes;
        this.time_take_per_day = time_take_per_day;
        this.amount_given = amount_given;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.medication_name = medication_name;
        this.treatment = treatment;

    }

    public Dosage(){}

    public Dosage(int patient_id){
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTime_take_per_day() {
        return time_take_per_day;
    }

    public void setTime_take_per_day(int time_take_per_day) {
        this.time_take_per_day = time_take_per_day;
    }

    public int getAmount_given() {
        return amount_given;
    }

    public void setAmount_given(int amount_given) {
        this.amount_given = amount_given;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getMedication_name() {
        return medication_name;
    }

    public void setMedication_name(String medication_name) {
        this.medication_name = medication_name;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }



}
