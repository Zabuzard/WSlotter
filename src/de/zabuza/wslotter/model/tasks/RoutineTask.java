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
import de.zabuza.wslotter.model.IBrowserSettingsProvider;

/**
 * The WSlotter routine which logs in to the main page, finds a thread and posts
 * a given text.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoutineTask extends Thread implements ITask {

	/**
	 * Creates the capabilities to use with a browser for the given arguments.
	 * 
	 * @param browser
	 *            Browser to create capabilities for
	 * @param driverPath
	 *            Path to the driver or <tt>null</tt> if not set
	 * @param binaryPath
	 *            Path to the binary or <tt>null</tt> if not set
	 * @return The capabilities to use or <tt>null</tt> if there are no
	 */
	private static Capabilities createCapabilities(final EBrowser browser, final String driverPath,
			final String binaryPath) {
		DesiredCapabilities capabilities = null;

		if (browser == EBrowser.FIREFOX) {
			capabilities = DesiredCapabilities.firefox();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.gecko.driver", driverPath);
				System.setProperty("webdriver.firefox.marionette", driverPath);
				capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
			}

			// Set the binary
			if (binaryPath != null) {
				File pathToBinary = new File(binaryPath);
				FirefoxBinary binary = new FirefoxBinary(pathToBinary);
				capabilities.setCapability(FirefoxDriver.BINARY, binary);
			}
		} else if (browser == EBrowser.CHROME) {
			capabilities = DesiredCapabilities.chrome();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.chrome.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("chrome.binary", binaryPath);
			}
		} else if (browser == EBrowser.SAFARI) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.safari.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("safari.binary", binaryPath);
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.ie.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("ie.binary", binaryPath);
			}
		} else if (browser == EBrowser.OPERA) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.opera.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("opera.binary", binaryPath);
			}
		} else if (browser == EBrowser.MS_EDGE) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.edge.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("edge.binary", binaryPath);
			}
		} else {
			throw new IllegalArgumentException("The given browser is not supported: " + browser);
		}

		return capabilities;
	}

	/**
	 * The browser to use.
	 */
	private final EBrowser mBrowser;
	/**
	 * The browser driver provider.
	 */
	private IBrowserSettingsProvider mBrowserSettingsProvider;
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
	 *            The controller of the main frame
	 * @param browserSettingsProvider
	 *            The browser settings provider
	 */
	public RoutineTask(final String threadUrl, final String textToPost, final String username, final String password,
			final EBrowser browser, final Logger logger, final MainFrameController controller,
			final IBrowserSettingsProvider browserSettingsProvider) {
		this.mThreadUrl = threadUrl;
		this.mTextToPost = textToPost;
		this.mUsername = username;
		this.mPassword = password;
		this.mBrowser = browser;
		this.mLogger = logger;
		this.mController = controller;
		this.mBrowserSettingsProvider = browserSettingsProvider;

		this.mDriver = null;
		this.mCurrentSubTask = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		if (this.mCurrentSubTask != null) {
			this.mCurrentSubTask.interrupt();
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
			// Create browser
			this.mLogger.logInfo("Starting web driver...", Logger.TOP_LEVEL);
			this.mDriver = createWebDriver(this.mBrowser);
			this.mLogger.logInfo("Web driver started.", Logger.FIRST_LEVEL);

			// Goto thread
			registerAndStartSubTask(new GotoThreadTask(this.mDriver, this.mThreadUrl, this.mLogger));
			if (isInterrupted()) {
				return;
			}

			// Login to the site
			registerAndStartSubTask(new LoginTask(this.mDriver, this.mUsername, this.mPassword, this.mLogger));
			if (isInterrupted()) {
				return;
			}

			// Wait for the post-reply form and post the message
			registerAndStartSubTask(new PostReplyTask(this.mDriver, this.mTextToPost, this.mLogger));
			if (isInterrupted()) {
				return;
			}

		} catch (AbortTaskException e) {
			// Known exception, just terminate
		} catch (Exception e) {
			this.mLogger.logUnknownError(e);
		} finally {
			terminate();
			this.mController.routineFinished();
		}
	}

	/**
	 * Creates a {@link WebDriver} that uses the given browser.
	 * 
	 * @param browser
	 *            Browser to use for the driver
	 * @return Webdriver that uses the given browser
	 */
	private WebDriver createWebDriver(final EBrowser browser) {
		String driverPath = this.mBrowserSettingsProvider.getDriverForBrowser(browser);
		String binaryPath = this.mBrowserSettingsProvider.getBrowserBinary();
		Capabilities capabilities = createCapabilities(browser, driverPath, binaryPath);

		WebDriver driver;
		if (browser == EBrowser.FIREFOX) {
			if (capabilities != null) {
				driver = new FirefoxDriver(capabilities);
			} else {
				driver = new FirefoxDriver();
			}
		} else if (browser == EBrowser.CHROME) {
			if (capabilities != null) {
				driver = new ChromeDriver(capabilities);
			} else {
				driver = new ChromeDriver();
			}
		} else if (browser == EBrowser.SAFARI) {
			if (capabilities != null) {
				driver = new SafariDriver(capabilities);
			} else {
				driver = new SafariDriver();
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			if (capabilities != null) {
				driver = new InternetExplorerDriver(capabilities);
			} else {
				driver = new InternetExplorerDriver();
			}
		} else if (browser == EBrowser.OPERA) {
			if (capabilities != null) {
				driver = new OperaDriver(capabilities);
			} else {
				driver = new OperaDriver();
			}
		} else if (browser == EBrowser.MS_EDGE) {
			if (capabilities != null) {
				driver = new EdgeDriver(capabilities);
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
		this.mCurrentSubTask = subTask;
		this.mCurrentSubTask.start();
	}

	/**
	 * Terminates the current task and shuts down the web driver.
	 */
	private void terminate() {
		if (this.mDriver != null) {
			this.mDriver.close();
		}
	}
}
