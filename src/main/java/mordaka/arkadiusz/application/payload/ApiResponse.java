package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse {
    @Getter
    @Setter
    private Boolean success;
    @Getter
    @Setter
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
