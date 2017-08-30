package health.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import health.domain.HealthRecord;
import health.domain.User;
import health.repository.HealthRecordRepository;

@Service("healthrecordService")
public class HealthRecordServiceImpl implements HealthRecordService{
	
	@Autowired
	private HealthRecordRepository healthRepository;
	
	@Override
	public HealthRecord findByRecord(String record) {
		// TODO Auto-generated method stub
		return healthRepository.findByRecord(record);
	}
	
	@Override
	public void saveRole(HealthRecord record) {
		// TODO Auto-generated method stub
		healthRepository.save(record);
	}

	@Override
	public List<HealthRecord> findByUser(User user) {
		// TODO Auto-generated method stub
		return healthRepository.findByUser(user);
	}

}
