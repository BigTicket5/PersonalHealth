package health.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import health.domain.HealthRecord;
import health.domain.User;

@Repository("roleRepository")
public interface HealthRecordRepository  extends JpaRepository<HealthRecord,Long>{
	HealthRecord findById(int id);
	HealthRecord findByRecord(String record);
	HealthRecord findByInputdate(Date date);
	HealthRecord findByUser(User user);
	HealthRecord findByClassId(int classid);
}