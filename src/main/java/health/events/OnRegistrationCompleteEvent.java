package health.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import health.domain.User;

public class OnRegistrationCompleteEvent  extends ApplicationEvent {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private Locale locale;
	private User user;
	 
	public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
		super(user);
		this.setUser(user);
		this.setLocale(locale);
		this.setAppUrl(appUrl);
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
