package patienthub.binary.com.patienthub.data;

/**
 * Created by Mark Aziz on 7/10/2015.
 */
public class Treatment {

    private int id;
    private String name;
    private String description;
    private String label;
    private String created_at;
    private String updated_at;

    public Treatment(int id, String name, String description, String label, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.label = label;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
}
