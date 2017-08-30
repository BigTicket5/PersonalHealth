package health.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_healthtrack")
public class HealthRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="track_id")
	private int id;
	
	@Column(name="date")
	private Date inputdate;
	
	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}

	@Column(name="record")
	private String record;
	
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Column(name="classification_id")
	private int classId;
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
