package coding.assessment.job_inquiry.service;

import coding.assessment.job_inquiry.model.entity.EmployeeRepositoryEntity;
import coding.assessment.job_inquiry.model.request.JobFilterRequest;
import coding.assessment.job_inquiry.model.JobModel;
import coding.assessment.job_inquiry.utility.DataLoader;
import coding.assessment.job_inquiry.utility.Utils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobService {
    private final List<JobModel> jobDataLists;
    private final DataLoader dataLoader;

    public JobService(@Qualifier("jobDataLists") List<JobModel> jobDataLists,
                      DataLoader dataLoader) {
        this.jobDataLists = jobDataLists;
        this.dataLoader = dataLoader;
    }

    public List<Map<String, Object>> filteredJobs(
            JobFilterRequest request
    ) {
        List<EmployeeRepositoryEntity> cachedEmployeeList = dataLoader.getCachedDataList();
        Stream<EmployeeRepositoryEntity> stream = cachedEmployeeList.stream();

        String jobTitle = Optional.ofNullable(request)
                .map(JobFilterRequest::getJobTitle)
                .orElse(null);
        String gender = Optional.ofNullable(request)
                .map(JobFilterRequest::getGender)
                .orElse(null);
        Integer minSalary = Optional.ofNullable(request)
                .map(JobFilterRequest::getMinSalary)
                .orElse(null);
        Integer maxSalary = Optional.ofNullable(request)
                .map(JobFilterRequest::getMaxSalary)
                .orElse(null);
        String fields = Optional.ofNullable(request)
                .map(JobFilterRequest::getFields)
                .orElse(null);

        if (!StringUtils.isBlank(jobTitle)) {
            stream = stream.filter(job -> job.getJobTitle().equalsIgnoreCase(jobTitle));
        }

        if (!StringUtils.isBlank(gender)) {
            stream = stream.filter(job -> job.getGender().equalsIgnoreCase(gender));
        }

        if (minSalary != null) {
            stream = stream.filter(job -> {
                Integer salary = Utils.parseSalary(job.getSalary());
                return salary != null && salary >= minSalary;
            });
        }

        if (maxSalary != null) {
            stream = stream.filter(job -> {
                Integer salary = Utils.parseSalary(job.getSalary());
                return salary != null && salary <= maxSalary;
            });
        }

        List<EmployeeRepositoryEntity> filteredList = stream.toList();

        Set<String> selectedFields = fields != null ?
                Arrays.stream(fields.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet())
                : null;

        List<Map<String, Object>> result = new ArrayList<>();

        for (EmployeeRepositoryEntity job : filteredList) {
            Map<String, Object> map = new LinkedHashMap<>();

            if (selectedFields == null || selectedFields.contains("jobTitle")) {
                map.put("jobTitle", job.getJobTitle());
            }
            if (selectedFields == null || selectedFields.contains("salary")) {
                map.put("salary", job.getSalary());
            }
            if (selectedFields == null || selectedFields.contains("gender")) {
                map.put("gender", job.getGender());
            }

            result.add(map);
        }

        return result;
    }
}
