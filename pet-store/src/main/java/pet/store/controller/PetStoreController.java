package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petStoreService;

	// Create pet store record (POST)
	@PostMapping // no extra "/pet_store" here - use the class-level path
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}

	// Update existing pet store (PUT)
	// If the request body does not contain all fields of the store, 
	// omitted fields will be overwritten with null.
	// For partial updates, could use PATCH instead (not implemented in this assignment).
	@PutMapping("/{storeId}")
	@ResponseStatus(HttpStatus.OK)
	public PetStoreData updatePetStore(@PathVariable("storeId") Long storeId, 
			@RequestBody PetStoreData petStoreData) {
		log.info("Updating Pet Store {} with data {}", storeId, petStoreData);
		petStoreData.setPetStoreId(storeId);
		return petStoreService.savePetStore(petStoreData);
	}

	// Read one pet store by primary key (GET)
	@GetMapping("/{petstoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petstoreId) {
	    log.info("Retrieving pet store with ID={}", petstoreId);
	    return petStoreService.retrievePetStoreById(petstoreId);
	}
	
	//Read all pet stores in the system (GET)
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
	    log.info("Retrieving all pet stores");
	    return petStoreService.retrieveAllPetStores();
	}
	
	// ADD RELATIONS
	
	//Associates a new employee with the specified pet store.
	@PostMapping("/{petstoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petstoreId, 
	                                                 @RequestBody PetStoreEmployee petstoreEmployee) {
	    log.info("Adding employee {} to pet store with ID {}", petstoreEmployee, petstoreId);
	    return petStoreService.saveEmployee(petstoreId, petstoreEmployee);
	}
	
	//Associates a new customer with the specified pet store.
	@PostMapping("/{petstoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petstoreId, 
	                                                 @RequestBody PetStoreCustomer petstoreCustomer) {
	    log.info("Adding customer {} to pet store with ID {}", petstoreCustomer, petstoreId);
	    return petStoreService.saveCustomer(petstoreId, petstoreCustomer);
	}

	

	// DELETE
	
	// Deletes the pet store associated with the provided ID.
	@DeleteMapping("/{petstoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petstoreId) {
	    log.info("Deleting pet with ID {}", petstoreId);
	    petStoreService.deletePetStoreById(petstoreId);
	    return Map.of("message", "Pet store with ID " + petstoreId + " was deleted.");
	}
	
	// NEW: Delete a customer from a specific store
    @DeleteMapping("/{petstoreId}/customer/{customerId}")
    public Map<String, String> deleteCustomerFromStore(
            @PathVariable Long petstoreId,
            @PathVariable Long customerId) {

        log.info("Deleting customer {} from pet store {}", customerId, petstoreId);
        petStoreService.deleteCustomerFromStore(petstoreId, customerId);
        return Map.of(
                "message",
                String.format("Customer with ID %d was removed from pet store %d.", customerId, petstoreId));
    }




	// Health check to confirm the service is reachable.
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

} // end of class
