package de.zabuza.wslotter.model.tasks;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.AbortTaskException;
import de.zabuza.wslotter.model.selector.Patterns;
import de.zabuza.wslotter.model.wait.TitleContainsWait;

/**
 * A task which opens a given thread in a given web driver.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class GotoThreadTask implements ITask {

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
	 * The URL of the thread to post to.
	 */
	private final String mThreadUrl;

	/**
	 * Creates a task which opens the given thread in web driver.
	 * 
	 * @param driver
	 *            The web driver to use
	 * @param threadUrl
	 *            The URL of the thread to go to
	 * @param logger
	 *            The logger to use
	 */
	public GotoThreadTask(final WebDriver driver, final String threadUrl, final Logger logger) {
		mDriver = driver;
		mThreadUrl = threadUrl;
		mLogger = logger;
		mInterrupted = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#interrupt()
	 */
	@Override
	public void interrupt() {
		mInterrupted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#isInterrupted()
	 */
	@Override
	public boolean isInterrupted() {
		return mInterrupted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.tasks.ITask#start()
	 */
	@Override
	public void start() {
		mLogger.logInfo("Opening thread...", Logger.TOP_LEVEL);
		mDriver.get(mThreadUrl);
		try {
			new TitleContainsWait(mDriver, Patterns.SITE_TITLE).waitUntilCondition();
		} catch (TimeoutException e) {
			mLogger.logError("The site is no 'Gruppe W' thread.", Logger.FIRST_LEVEL);
			throw new AbortTaskException();
		}
		mLogger.logInfo("Thread opened.", Logger.FIRST_LEVEL);
	}

}
