package de.zabuza.wslotter.model.wait.conditions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Condition that outputs the value of a given cookie.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class CookieCondition implements ExpectedCondition<Cookie> {

	/**
	 * Name of the cookie.
	 */
	private final String mName;

	/**
	 * Creates a new condition which outputs the value of a given cookie.
	 * 
	 * @param name
	 *            Name of the cookie
	 */
	public CookieCondition(final String name) {
		mName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public Cookie apply(final WebDriver driver) {
		return driver.manage().getCookieNamed(mName);
	}
}
