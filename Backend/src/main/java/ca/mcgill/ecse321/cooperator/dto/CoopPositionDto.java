package ca.mcgill.ecse321.cooperator.dto;

import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.cooperator.model.Status;

public class CoopPositionDto {
	private Integer coopId;
	private Status status;
	private String description;
	private String term;
	private Date startDate;
	private Date endDate;
	private String location;
	
	private StudentDto student;
	private TermInstructorDto termInstructor;
	private List<CourseDto> courses;
	private CooperatorManagerDto cooperatorManager;
	
	public CoopPositionDto() {
		
	}
	
	//Constructor without term instructor
	public CoopPositionDto(Integer id,String desc, Date start,Date end, String location, String term, StudentDto student, CooperatorManagerDto sys) {
		this.coopId=id;
		this.description=desc;
		this.startDate=start;
		this.endDate=end;
		this.location=location;
		this.student=student;
		this.cooperatorManager=sys;
	}
	
	//Constructor with term instructor
	public CoopPositionDto(Integer id,String desc, Date start,Date end, String location, String term, StudentDto student, TermInstructorDto termInst, CooperatorManagerDto sys) {
		this.coopId=id;
		this.description=desc;
		this.startDate=start;
		this.endDate=end;
		this.location=location;
		this.student=student;
		this.termInstructor=termInst;
		this.cooperatorManager=sys;
		
	}
	
	public Integer getCoopID() {
		return coopId;
	}
	//Student
	public StudentDto getStudent() {
		return student;
	}
	
	//Term Instructor
	public TermInstructorDto getTermInstructor() {
		return termInstructor;
	}
	
	public void setTermInstructor(TermInstructorDto termInstructor) {
		this.termInstructor=termInstructor;
	}
	
	//Status
	public void setStatus(Status s) {
		this.status = s;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public CooperatorManagerDto getCooperatorManager() {
    	return cooperatorManager;
  }
}
