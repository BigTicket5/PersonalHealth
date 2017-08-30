package health.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import health.domain.HealthRecord;
import health.domain.User;

@Repository("healthrecordRepository")
public interface HealthRecordRepository  extends JpaRepository<HealthRecord,Long>{
	HealthRecord findById(int id);
	HealthRecord findByRecord(String record);
	HealthRecord findByInputdate(Date date);
	List<HealthRecord> findByUser(User user);
	HealthRecord findByClassId(int classid);
}