package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
    private EmployeeDao employeeDao; 
	
	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

	// Helper methods

	// Full replace – copies every field, even if null.
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	// Returns an existing PetStore if an ID is supplied, otherwise creates a new instance.
	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			// Create a brand new store (POST)
			return new PetStore();
		} else {
			return findByPetStoreId(petStoreId);
		}
	}

	// Update an existing store (PUT). Must exist, otherwise 404.
	private PetStore findByPetStoreId(Long petStoreId) {
		return petStoreDao.findByPetStoreId(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID " + petStoreId + " was not found."));
	}

	// READ – used by a GET (optional for the assignment)
	public PetStoreData getPetStore(Long id) {
		PetStore entity = petStoreDao.findByPetStoreId(id)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID " + id + " was not found."));
		return new PetStoreData(entity);
	}
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petstoreEmployee) {
	    employee.setEmployeeFirstName(petstoreEmployee.getEmployeeFirstName());
	    employee.setEmployeeId(petstoreEmployee.getEmployeeId());
	    employee.setEmployeeJobTitle(petstoreEmployee.getEmployeeJobTitle());
	    employee.setEmployeeLastName(petstoreEmployee.getEmployeeLastName());
	    employee.setEmployeePhone(petstoreEmployee.getEmployeePhone());
	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petstoreCustomer) {
	    customer.setCustomerEmail(petstoreCustomer.getCustomerEmail());
	    customer.setCustomerFirstName(petstoreCustomer.getCustomerFirstName());
	    customer.setCustomerId(petstoreCustomer.getCustomerId());
	    customer.setCustomerLastName(petstoreCustomer.getCustomerLastName());
	}
	
	private Employee findOrCreateEmployee(Long petstoreId, Long employeeId) {
	    if (Objects.isNull(employeeId)) {
	        return new Employee();
	    }
	    return findEmployeeById(petstoreId, employeeId);
	}

	private Customer findOrCreateCustomer(Long petstoreId, Long customerId) {
	    if (Objects.isNull(customerId)) {
	        return new Customer();
	    }
	    return findCustomerById(petstoreId, customerId);
	}
	
	private Employee findEmployeeById(Long petstoreId, Long employeeId) {
	    Employee employee = employeeDao.findById(employeeId)
	        .orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));

	    if (employee.getPetStore().getPetStoreId() != petstoreId) {
	        throw new IllegalArgumentException("The employee with ID=" + employeeId + " is not employed by the pet store with ID=" + petstoreId + ".");
	    }
	    return employee;
	}

	private Customer findCustomerById(Long petstoreId, Long customerId) {
	    Customer customer = customerDao.findById(customerId)
	        .orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));

	    boolean found = false;
	    for (PetStore petstore : customer.getPetStores()) {
	        if (petstore.getPetStoreId() == petstoreId) {
	            found = true;
	            break;
	        }
	    }

	    if (!found) {
	        throw new IllegalArgumentException("The customer with ID=" + customerId + " is not a member of the pet store with ID=" + petstoreId);
	    }
	    return customer;
	}
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petstoreId, PetStoreEmployee petstoreEmployee) {
	    PetStore petstore = findByPetStoreId(petstoreId);
	    Long employeeId = petstoreEmployee.getEmployeeId();
	    Employee employee = findOrCreateEmployee(petstoreId, employeeId);

	    copyEmployeeFields(employee, petstoreEmployee);

	    employee.setPetStore(petstore);
	    petstore.getEmployees().add(employee);

	    Employee dbEmployee = employeeDao.save(employee);

	    return new PetStoreEmployee(dbEmployee);
	}

	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petstoreId, PetStoreCustomer petstoreCustomer) {
	    PetStore petstore = findByPetStoreId(petstoreId);
	    Long customerId = petstoreCustomer.getCustomerId();
	    Customer customer = findOrCreateCustomer(petstoreId, customerId);

	    copyCustomerFields(customer, petstoreCustomer);

	    customer.getPetStores().add(petstore);
	    petstore.getCustomers().add(customer);

	    Customer dbCustomer = customerDao.save(customer);

	    return new PetStoreCustomer(dbCustomer);
	}
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
	    List<PetStore> petstores = petStoreDao.findAll();
	    List<PetStoreData> result = new LinkedList<>();

	    for (PetStore petstore : petstores) {
	        PetStoreData psd = new PetStoreData(petstore);
	        psd.getCustomers().clear();
	        psd.getEmployees().clear();
	        result.add(psd);
	    }

	    return result;
	}

	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petstoreId) {
	    return new PetStoreData(findByPetStoreId(petstoreId));
	}

	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petstoreId) {
	    PetStore petstore = findByPetStoreId(petstoreId);
	    petStoreDao.delete(petstore);
	}

	 
    // Removes the given customer from the given pet store.
    // Also deletes the Customer entity from the DB (cascade = PERSIST only, so we delete explicitly).
    // Throws NoSuchElementException if either the store or the customer does not exist,
    // or if the customer is not linked to the store.
    
    public void deleteCustomerFromStore(Long petstoreId, Long customerId) {
        // Load the store (throws if not found)
        PetStore store = petStoreDao.findById(petstoreId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Pet store with ID=" + petstoreId + " was not found."));

        // Load the customer (throws if not found)
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Customer with ID=" + customerId + " was not found."));

        // Verify the association exists
        if (!store.getCustomers().contains(customer)) {
            throw new IllegalArgumentException(
                    "Customer ID " + customerId + " is not associated with pet store ID " + petstoreId);
        }

        // Break the bi-directional link
        store.getCustomers().remove(customer);
        customer.getPetStores().remove(store);

        // Delete the customer entity (or just leave it detached if you prefer)
        customerDao.delete(customer);
    }



} // end of class
