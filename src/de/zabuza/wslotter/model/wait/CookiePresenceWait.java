package de.zabuza.wslotter.model.wait;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.wslotter.model.wait.conditions.CookieCondition;

/**
 * Class for waiting until a given cookie is present. Start waiting using the
 * {@link #waitUntilCondition()} method.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class CookiePresenceWait extends AConditionalWait<Cookie> {
	/**
	 * Condition to wait for.
	 */
	private final ExpectedCondition<Cookie> mCondition;

	/**
	 * Creates a new instance of this object with a given web driver and the
	 * name of a cookie to wait for its presence.
	 * 
	 * @param driver
	 *            Driver to use for waiting
	 * @param name
	 *            Name of the cookie to wait for
	 */
	public CookiePresenceWait(final WebDriver driver, final String name) {
		super(driver);
		this.mCondition = new CookieCondition(name);
	}

	/**
	 * Creates a new instance of this object with a given web driver and the
	 * name of a cookie to wait for its presence.
	 * 
	 * @param driver
	 *            Driver to use for waiting
	 * @param name
	 *            Name of the cookie to wait for
	 * @param timeOutInSeconds
	 *            Timeout in seconds to wait for the condition to resolve to
	 *            <tt>true</tt> until a {@link TimeoutException} is thrown.
	 */
	public CookiePresenceWait(final WebDriver driver, final String name, final long timeOutInSeconds) {
		super(driver, timeOutInSeconds);
		this.mCondition = new CookieCondition(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.wait.AConditionalWait#getCondition()
	 */
	@Override
	protected ExpectedCondition<Cookie> getCondition() {
		return this.mCondition;
	}
}
