package Models;

public class ModelFindJob {

    private String id,name,email,phone,jobType,budget,address;

    public ModelFindJob() {
    }

    public ModelFindJob(String id, String name, String email, String phone, String jobType, String budget, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.jobType = jobType;
        this.budget = budget;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
