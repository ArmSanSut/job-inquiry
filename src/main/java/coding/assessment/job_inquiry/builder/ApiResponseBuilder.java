package coding.assessment.job_inquiry.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseBuilder<T> {

    private boolean success;
    private String statusDescription;
    private T data;

    public static <T> ApiResponseBuilder<T> success(T data) {
        return new ApiResponseBuilder<>(true, "SUCCESS", data);
    }
}
