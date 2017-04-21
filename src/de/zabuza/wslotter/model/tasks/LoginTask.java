package de.zabuza.wslotter.model.tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.AbortTaskException;
import de.zabuza.wslotter.model.selector.CSSSelectors;
import de.zabuza.wslotter.model.selector.Names;
import de.zabuza.wslotter.model.wait.CookiePresenceWait;
import de.zabuza.wslotter.model.wait.LoginFormWait;

/**
 * Ensures the user is logged in to the 'Gruppe W' site by either logging in or
 * returning if already logged in.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class LoginTask implements ITask {

	/**
	 * Time in seconds to wait for the check if the user is already logged in.
	 */
	private static final long ALREADY_LOGGED_IN_WAIT = 1;

	/**
	 * The web driver to use.
	 */
	private WebDriver mDriver;
	/**
	 * Whether interrupted flag of the task is set.
	 */
	private boolean mInterrupted;
	/**
	 * The logger to use.
	 */
	private final Logger mLogger;
	/**
	 * The password to login with.
	 */
	private final String mPassword;
	/**
	 * The username to login with.
	 */
	private final String mUsername;

	/**
	 * Creates a task which ensures the user is logged in to the 'Gruppe W' site
	 * by either logging in or returning if already logged in.
	 * 
	 * @param driver
	 *            The web driver to use
	 * @param username
	 *            The username to login with
	 * @param password
	 *            The password to login with
	 * @param logger
	 *            The logger to use
	 */
	public LoginTask(final WebDriver driver, final String username, final String password, final Logger logger) {
		this.mDriver = driver;
		this.mUsername = username;
		this.mPassword = password;
		this.mLogger = logger;
		this.mInterrupted = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#interrupt()
	 */
	@Override
	public void interrupt() {
		this.mInterrupted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#isInterrupted()
	 */
	@Override
	public boolean isInterrupted() {
		return this.mInterrupted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#start()
	 */
	@Override
	public void start() {
		// Check if already logged in
		Cookie sessionCookie = null;
		try {
			this.mLogger.logInfo("Checking if already logged in...", Logger.TOP_LEVEL);
			sessionCookie = new CookiePresenceWait(this.mDriver, Names.COOKIE_SESSION, ALREADY_LOGGED_IN_WAIT)
					.waitUntilCondition();
		} catch (TimeoutException e) {
			// Just ignore the exception and continue
		}
		if (sessionCookie == null) {
			this.mLogger.logInfo("Not logged in.", Logger.FIRST_LEVEL);
			this.mLogger.logInfo("Logging in...", Logger.TOP_LEVEL);
			// Login
			WebElement loginSubmit = new LoginFormWait(this.mDriver).waitUntilCondition();
			WebElement loginName = this.mDriver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_NAME));
			WebElement loginPassword = this.mDriver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_PASSWORD));

			// Type in user credentials
			loginName.sendKeys(this.mUsername);
			loginPassword.sendKeys(this.mPassword);

			// Submit form
			loginSubmit.click();

			// Check if login succeeded
			try {
				sessionCookie = new CookiePresenceWait(this.mDriver, Names.COOKIE_SESSION).waitUntilCondition();
			} catch (TimeoutException e) {
				// Just ignore the exception and continue
			}
			if (sessionCookie == null) {
				this.mLogger.logError("User credentials not accepted!", Logger.FIRST_LEVEL);
				throw new AbortTaskException();
			}
		}

		// At this point the user is logged in
		this.mLogger.logInfo("Logged in.", Logger.FIRST_LEVEL);
	}

}
