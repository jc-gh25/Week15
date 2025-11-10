package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.store.entity.Employee;


 // Spring Data JPA repository for {@link Employee} entities.

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    // Can add custom queries later if needed.

}
