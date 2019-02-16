package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.*;

@Entity
public class EmployerContract extends RequiredDocument {
	private Employer employer;
	   
	   @ManyToOne(optional=false)
	   public Employer getEmployer() {
	      return this.employer;
	   }
	   
	   public void setEmployer(Employer employer) {
	      this.employer = employer;
	   }
}
