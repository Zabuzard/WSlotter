package de.zabuza.wslotter.model.tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.selector.CSSSelectors;

/**
 * Waits for the post-reply text field at a thread and posts a given message.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class PostReplyTask implements ITask {
	/**
	 * Determines after how many attempts, at waiting for the post-reply form to
	 * appear, a logging message will be printed.
	 */
	private static final int ATTEMPT_LOG_EVERY = 5;
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
	 * The text to post.
	 */
	private final String mTextToPost;

	/**
	 * Creates a task which waits for the post-reply text field at the current
	 * site and posts a given message.
	 * 
	 * @param driver
	 *            The web driver to use
	 * @param textToPost
	 *            The text to post
	 * @param logger
	 *            The logger to use
	 */
	public PostReplyTask(final WebDriver driver, final String textToPost, final Logger logger) {
		this.mDriver = driver;
		this.mTextToPost = textToPost;
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
		// Check if post-reply form is already present
		WebElement postReplySubmit = null;
		this.mLogger.logInfo("Checking if post-reply is possible...", Logger.TOP_LEVEL);
		try {
			postReplySubmit = this.mDriver.findElement(By.cssSelector(CSSSelectors.POST_REPLY_FORM_SUBMIT));
		} catch (NoSuchElementException e) {
			// Just ignore the exception and continue with an error message
		}

		if (postReplySubmit == null) {
			this.mLogger.logInfo("Not possible.", Logger.FIRST_LEVEL);
			this.mLogger.logInfo("Attempting again...", Logger.TOP_LEVEL);
			int attemptNumber = 0;

			// Attempt again until interrupted or post-reply form found
			while (!isInterrupted() && postReplySubmit == null) {
				// Update the site
				if (attemptNumber == 0) {
					// Hard refresh at first attempt to delete the login POST
					// parameters from the refresh query
					String currentUrl = this.mDriver.getCurrentUrl();
					this.mDriver.get(currentUrl);
				} else {
					this.mDriver.navigate().refresh();
				}

				attemptNumber++;
				if (attemptNumber % ATTEMPT_LOG_EVERY == 0) {
					this.mLogger.logInfo("Attempt #" + attemptNumber, Logger.SECOND_LEVEL);
				}

				// Search for the post-reply form
				try {
					postReplySubmit = this.mDriver.findElement(By.cssSelector(CSSSelectors.POST_REPLY_FORM_SUBMIT));
				} catch (NoSuchElementException e) {
					// Just ignore the exception and continue with an error
					// message
				}
			}
		}

		if (isInterrupted()) {
			return;
		}

		this.mLogger.logInfo("Post-reply is possible.", Logger.FIRST_LEVEL);
		this.mLogger.logInfo("Posting text...", Logger.TOP_LEVEL);

		// At this point the post-reply form is present
		WebElement messageBox = this.mDriver.findElement(By.cssSelector(CSSSelectors.POST_REPLY_FORM_MESSAGE_BOX));

		// Type in message
		messageBox.sendKeys(this.mTextToPost);

		// Submit form
		if (postReplySubmit == null) {
			throw new AssertionError();
		}
		postReplySubmit.click();

		this.mLogger.logInfo("Text posted.", Logger.FIRST_LEVEL);
	}

}
