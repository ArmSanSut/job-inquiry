package coding.assessment.job_inquiry.controller;

import coding.assessment.job_inquiry.builder.ApiResponseBuilder;
import coding.assessment.job_inquiry.model.request.JobFilterRequest;
import coding.assessment.job_inquiry.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobDataController {
    private final JobService jobService;

    public JobDataController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseBuilder<List<Map<String, Object>>>> getFilteredData(
            @ModelAttribute JobFilterRequest request
            ) {
        List<Map<String, Object>> filtered = jobService.filteredJobs(request);
        return ResponseEntity.ok(ApiResponseBuilder.success(filtered));
    }
}
