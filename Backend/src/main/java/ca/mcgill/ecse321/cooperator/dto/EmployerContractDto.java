package ca.mcgill.ecse321.cooperator.dto;

import java.util.Date;


public class EmployerContractDto extends RequiredDocumentDto {
	private EmployerDto employer;
	
	public EmployerContractDto() {
		
	}
	
	public EmployerContractDto(Integer id, String name,Date dueDate, CooperatorManagerDto sys) {
		super(id,name,dueDate,sys);
		
	}
	
	public EmployerDto getEmployer() {
		return employer;
	}
	
	public void setEmployer(EmployerDto employer) {
		this.employer=employer;
	}
}
