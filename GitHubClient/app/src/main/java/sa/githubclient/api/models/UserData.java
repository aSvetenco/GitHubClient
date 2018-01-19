package sa.githubclient.api.models;

public class UserData {

    private String dataName;
    private String dataValue;

    public UserData(String dataName, String dataValue) {
        this.dataName = dataName;
        this.dataValue = dataValue;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
}
