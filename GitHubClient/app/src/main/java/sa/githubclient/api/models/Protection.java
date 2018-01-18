
package sa.githubclient.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Protection {

    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("required_status_checks")
    @Expose
    private RequiredStatusChecks requiredStatusChecks;

    /**
     * @return The enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return The requiredStatusChecks
     */
    public RequiredStatusChecks getRequiredStatusChecks() {
        return requiredStatusChecks;
    }

    /**
     * @param requiredStatusChecks The required_status_checks
     */
    public void setRequiredStatusChecks(RequiredStatusChecks requiredStatusChecks) {
        this.requiredStatusChecks = requiredStatusChecks;
    }

}
