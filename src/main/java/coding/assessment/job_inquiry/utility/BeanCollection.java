package coding.assessment.job_inquiry.utility;

import coding.assessment.job_inquiry.model.JobModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

@Configuration
public class BeanCollection {

    @Bean
    public List<JobModel> jobDataLists() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/salary_survey.json");

            if (input == null) {
                throw new FileNotFoundException("data/salary_survey.json not found in process");
            }

            return  mapper.readValue(input, new TypeReference<List<JobModel>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load data from salary_survey.json",e);
        }
    }
}
