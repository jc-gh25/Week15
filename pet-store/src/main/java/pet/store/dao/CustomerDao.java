package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.store.entity.Customer;


// Spring Data JPA repository for {@link Customer} entities.

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
    // The default CRUD methods are sufficient for now.
	
}
