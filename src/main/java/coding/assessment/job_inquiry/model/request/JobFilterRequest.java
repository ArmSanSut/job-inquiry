package coding.assessment.job_inquiry.model.request;

import lombok.Data;

@Data
public class JobFilterRequest {
    private String jobTitle;
    private String gender;
    private Integer minSalary;
    private Integer maxSalary;
    private String fields;
    private String sortBy;
    private String sortDir;
}
