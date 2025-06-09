package coding.assessment.job_inquiry.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name = "employee_repository_entity")
public class EmployeeRepositoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String timeStamp;
    private String employer;
    private String location;
    private String jobTitle;
    private String yearsOfEmployer;
    private String yearsOfExperience;
    private String salary;
    private String signingBonus;
    private String annualBonus;
    private String annualStockValueBonus;
    private String gender;
    @Column(length = 1000)
    private String additionalComments;
}
