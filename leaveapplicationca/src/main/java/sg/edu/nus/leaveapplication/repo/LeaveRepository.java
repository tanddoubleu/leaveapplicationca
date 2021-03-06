package sg.edu.nus.leaveapplication.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveApplication;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Long>{
	List<LeaveApplication> findByEmployee(Employee e);
	
	@Query(value="select lt from Employee e join e.leaveList lt where e.reportsTo = ?1 and lt.status= 'Applied'")
	List<LeaveApplication> findByManagerId(long id);
	
	@Query(value="select lt from Employee e join e.leaveList lt where e.reportsTo = ?1")
	Page<LeaveApplication> findByManagerId(long id, Pageable pageable);

	LeaveApplication findById(long id);
	
	@Query(value="select lt from LeaveApplication lt where lt.employee.id = ?1")
	List<LeaveApplication> findByUserId(long id);
	
	@Query(value="select lt from LeaveApplication lt where lt.employee.id = ?1")
	Page<LeaveApplication> findByUserIdPage(long id, Pageable pageable);

	@Query(value="select  lt from Employee e join e.leaveList lt where e.reportsTo = ?1 ")
	List<LeaveApplication> findSubLeaveHistory(long id);

}
