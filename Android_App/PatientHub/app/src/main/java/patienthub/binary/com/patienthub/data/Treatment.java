package patienthub.binary.com.patienthub.data;

/**
 * Created by Mark Aziz on 7/10/2015.
 */
public class Treatment {

    private int id;
    private String name;
    private String treatment_type;
    private String created_at;
    private String updated_at;

    public Treatment(int id, String name, String created_at, String updated_at, String treatment_type) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.treatment_type = treatment_type;
    }

    public Treatment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTreatment_type() {
        return treatment_type;
    }

    public void setTreatment_type(String treatment_type) {
        this.treatment_type = treatment_type;
    }
}
