package de.zabuza.wslotter.model.tasks;

import java.io.File;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import de.zabuza.wslotter.controller.MainFrameController;
import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.AbortTaskException;
import de.zabuza.wslotter.model.EBrowser;

/**
 * The WSlotter routine which logs in to the main page, finds a thread and posts
 * a given text.
 * 
 * @author Zabuza
 *
 */
public final class RoutineTask extends Thread implements ITask {

	/**
	 * The browser to use.
	 */
	private final EBrowser mBrowser;
	/**
	 * The capabilities to use for the created browsers.
	 */
	private Capabilities mCapabilities;
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;
	/**
	 * The current executing sub task.
	 */
	private ITask mCurrentSubTask;
	/**
	 * The web driver to use.
	 */
	private WebDriver mDriver;
	/**
	 * The logger to use.
	 */
	private final Logger mLogger;
	/**
	 * The password of the user to post with.
	 */
	private final String mPassword;
	/**
	 * The text to post.
	 */
	private final String mTextToPost;
	/**
	 * The URL of the thread to post to.
	 */
	private final String mThreadUrl;
	/**
	 * The name of the user to post with.
	 */
	private final String mUsername;

	/**
	 * Creates a new routine task.
	 * 
	 * @param threadUrl
	 *            The URL of the thread to post to
	 * @param textToPost
	 *            The text to post
	 * @param username
	 *            The name of the user to post with
	 * @param password
	 *            The password of the user to post with
	 * @param browser
	 *            The browser to use
	 * @param logger
	 *            The logger to use
	 * @param controller
	 *            The controller of the main frame.
	 */
	public RoutineTask(final String threadUrl, final String textToPost, final String username, final String password,
			final EBrowser browser, final Logger logger, final MainFrameController controller) {
		mThreadUrl = threadUrl;
		mTextToPost = textToPost;
		mUsername = username;
		mPassword = password;
		mBrowser = browser;
		mLogger = logger;
		mController = controller;

		mCapabilities = null;
		mDriver = null;
		mCurrentSubTask = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		if (mCurrentSubTask != null) {
			mCurrentSubTask.interrupt();
		}
		super.interrupt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			// TODO Remove Debug
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			System.setProperty("webdriver.gecko.driver", "C:\\Windows\\System32\\geckodriver.exe");
			System.setProperty("webdriver.firefox.marionette", "C:\\Windows\\System32\\geckodriver.exe");
			capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
			File pathToBinary = new File("D:\\Program Files\\Firefox\\firefox.exe");
			FirefoxBinary binary = new FirefoxBinary(pathToBinary);
			capabilities.setCapability(FirefoxDriver.BINARY, binary);
			setCapabilities(capabilities);

			// Create browser
			mLogger.logInfo("Starting web driver...", Logger.TOP_LEVEL);
			mDriver = createWebDriver(mBrowser);
			mLogger.logInfo("Web driver started.", Logger.FIRST_LEVEL);

			// Goto thread
			registerAndStartSubTask(new GotoThreadTask(mDriver, mThreadUrl, mLogger));
			if (isInterrupted()) {
				return;
			}

			// Login to the site
			registerAndStartSubTask(new LoginTask(mDriver, mUsername, mPassword, mLogger));
			if (isInterrupted()) {
				return;
			}

			// Wait for the post-reply form and post the message
			registerAndStartSubTask(new PostReplyTask(mDriver, mTextToPost, mLogger));
			if (isInterrupted()) {
				return;
			}

		} catch (AbortTaskException e) {
			// Known exception, just terminate
		} catch (Exception e) {
			mLogger.logUnknownError(e);
		} finally {
			terminate();
			mController.routineFinished();
		}
	}

	/**
	 * Sets the capabilities to use for the created browser.
	 * 
	 * @param capabilities
	 *            Capabilities to use
	 */
	public void setCapabilities(final Capabilities capabilities) {
		mCapabilities = capabilities;
	}

	/**
	 * Creates a {@link #WebDriver} that uses the given browser. If a capability
	 * object was set using {@link #setCapabilities(Capabilities)} then it will
	 * also be passed to the created browser.
	 * 
	 * @param browser
	 *            Browser to use for the driver
	 * @return Webdriver that uses the given browser
	 */
	private WebDriver createWebDriver(final EBrowser browser) {
		// TODO Use settings to get properties and set them
		WebDriver driver;
		if (browser == EBrowser.FIREFOX) {
			if (mCapabilities != null) {
				driver = new FirefoxDriver(mCapabilities);
			} else {
				driver = new FirefoxDriver();
			}
		} else if (browser == EBrowser.CHROME) {
			if (mCapabilities != null) {
				driver = new ChromeDriver(mCapabilities);
			} else {
				driver = new ChromeDriver();
			}
		} else if (browser == EBrowser.SAFARI) {
			if (mCapabilities != null) {
				driver = new SafariDriver(mCapabilities);
			} else {
				driver = new SafariDriver();
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			if (mCapabilities != null) {
				driver = new InternetExplorerDriver(mCapabilities);
			} else {
				driver = new InternetExplorerDriver();
			}
		} else if (browser == EBrowser.OPERA) {
			if (mCapabilities != null) {
				driver = new OperaDriver(mCapabilities);
			} else {
				driver = new OperaDriver();
			}
		} else if (browser == EBrowser.MS_EDGE) {
			if (mCapabilities != null) {
				driver = new EdgeDriver(mCapabilities);
			} else {
				driver = new EdgeDriver();
			}
		} else {
			throw new IllegalArgumentException("The given browser is not supported: " + browser);
		}

		return driver;
	}

	/**
	 * Registers the given sub task as the current and starts it.
	 * 
	 * @param subTask
	 *            Sub task to register and start
	 */
	private void registerAndStartSubTask(final ITask subTask) {
		mCurrentSubTask = subTask;
		mCurrentSubTask.start();
	}

	/**
	 * Terminates the current task and shuts down the web driver.
	 */
	private void terminate() {
		if (mDriver != null) {
			mDriver.close();
		}
	}
}
