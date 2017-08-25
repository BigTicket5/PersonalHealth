package health.domain;

import java.sql.Timestamp;
import java.util.Calendar;
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
@Table(name="verification_token")
public class VerificationToken {
	private static final int EMAIL_EXPIRATION = 60 * 24;
	private static final int PASSWD_EXPIRATION = 30;
	
	@Id
	@Column(name="token_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="token")
	private String token;
	
	
	public VerificationToken(){}
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Column(name="expiry_date")
	private Date expiryDate;
	
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
	public VerificationToken(String token, User user, int flag) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.user = user;
		switch (flag){
			case 0:
				this.expiryDate = calculateExpiryDate(EMAIL_EXPIRATION);
				break;
			case 1:
				this.expiryDate = calculateExpiryDate(PASSWD_EXPIRATION);
			default:
				break;
		}
	}

}

