package health.service;

import health.domain.HealthRecord;

public interface HealthRecordService {
	public HealthRecord findByRecord(String record);
	public void saveRole(HealthRecord record);
}
