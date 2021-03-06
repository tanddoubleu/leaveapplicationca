	package sg.edu.nus.leaveapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;	
	@NotNull
	@Size(min=2,max=100)
	private String name;
	@OneToOne
	@PrimaryKeyJoinColumn
	private Credentials credential;
	private String designation;	
	private int leaveEntitled;	
	private long contact;	
	private String email;
	private long compensationhours;
	private long reportsTo;
	@OneToMany(mappedBy="employee")
	private List<LeaveApplication> leaveList;
	

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}	
	



	public Employee(String name, String designation, int leaveEntitled, long contact, String email, long compensationhours, 
			long reportsTo, List<LeaveApplication> leaveList) {
		super();
		this.name = name;
		this.designation = designation;
		this.leaveEntitled = leaveEntitled;
		this.contact=contact;
		this.email = email;
		this.compensationhours=compensationhours;
		this.reportsTo = reportsTo;
		this.leaveList=leaveList;
	}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public String getDesignation() {
		return designation;
	}




	public void setDesignation(String designation) {
		this.designation = designation;
	}




	public int getLeaveEntitled() {
		return leaveEntitled;
	}

	public void setLeaveEntitled(int leaveEntitled) {
		this.leaveEntitled = leaveEntitled;
	}

	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCompensationhours() {
		return compensationhours;
	}
	public void setCompensationhours(long compensationhours) {
		this.compensationhours = compensationhours;
	}
	
	public long getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(long reportsTo) {
		this.reportsTo = reportsTo;
	}
	public Credentials getCredential() {
		return credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}
	public List<LeaveApplication> getLeaveList() {
		return leaveList;
	}

	public void setLeaveList(List<LeaveApplication> leaveList) {
		this.leaveList = leaveList;
	}
	
}
