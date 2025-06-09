package coding.assessment.job_inquiry.repository;

import coding.assessment.job_inquiry.model.entity.EmployeeRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeRepositoryEntity, Long> {
}
