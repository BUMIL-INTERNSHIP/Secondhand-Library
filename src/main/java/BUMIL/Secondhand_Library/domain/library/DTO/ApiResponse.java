package BUMIL.Secondhand_Library.domain.library.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    private boolean success;
    private String message;

    @JsonCreator
    public ApiResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

