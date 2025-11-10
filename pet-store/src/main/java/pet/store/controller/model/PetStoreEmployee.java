package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
	private Long employeeId;
	//private PetStore petStore; // owning side of the one to many relation
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;

	public PetStoreEmployee(Employee employee) {
		this.employeeId = employee.getEmployeeId();
		//this.petStore = employee.getPetStore();
		this.employeeFirstName = employee.getEmployeeFirstName();
		this.employeeLastName = employee.getEmployeeLastName();
		this.employeePhone = employee.getEmployeePhone();
		this.employeeJobTitle = employee.getEmployeeJobTitle();
	}

} // end of class
