package health.service;

import java.util.List;

import health.domain.HealthRecord;
import health.domain.User;

public interface HealthRecordService {
	public HealthRecord findByRecord(String record);
	public void saveRole(HealthRecord record);
	public List<HealthRecord> findByUser(User user);

}
