package coding.assessment.job_inquiry.utility;

import coding.assessment.job_inquiry.model.JobModel;
import coding.assessment.job_inquiry.model.entity.EmployeeRepositoryEntity;
import coding.assessment.job_inquiry.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader {
    private final EmployeeRepository employeeRepository;
    private List<EmployeeRepositoryEntity> cachedDataList;

    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/salary_survey.json");

            if (input == null) {
                throw new FileNotFoundException("data/salary_survey.json not found in process");
            }

            List<JobModel> jobModel = mapper.readValue(input, new TypeReference<List<JobModel>>() {});

            List<EmployeeRepositoryEntity> jobEntities = jobModel.stream()
                    .map(model -> {
                        EmployeeRepositoryEntity entity = new EmployeeRepositoryEntity();

                        entity.setTimeStamp(model.getTimeStamp());
                        entity.setEmployer(model.getEmployer());
                        entity.setLocation(model.getLocation());
                        entity.setJobTitle(model.getJobTiltle());
                        entity.setYearsOfEmployer(model.getYearsAtEmployer());
                        entity.setYearsOfExperience(model.getYearsOfExperience());
                        entity.setSalary(model.getSalary());
                        entity.setSigningBonus(model.getSigningBonus());
                        entity.setAnnualBonus(model.getAnnualBonus());
                        entity.setAnnualStockValueBonus(model.getAnnualStockValueBonus());
                        entity.setGender(model.getGender());
                        entity.setAdditionalComments(model.getAdditionalComments());

                        return entity;
                    })
                    .toList();

            employeeRepository.saveAll(jobEntities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load data from salary_survey.json",e);
        }

        this.cachedDataList = employeeRepository.findAll();
    }

    public List<EmployeeRepositoryEntity> getCachedDataList() {
        return cachedDataList;
    }
}
