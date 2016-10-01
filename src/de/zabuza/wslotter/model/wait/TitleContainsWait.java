package de.zabuza.wslotter.model.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class for waiting until the sites title contains a given needle. Start
 * waiting using the {@link #waitUntilCondition()} method.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class TitleContainsWait extends AConditionalWait<Boolean> {
	/**
	 * Condition to wait for.
	 */
	private final ExpectedCondition<Boolean> mCondition;

	/**
	 * Creates a new instance of this object using a given web driver and name.
	 * 
	 * @param driver
	 *            Driver to use for waiting
	 * @param needle
	 *            Needle to wait for until the title contains it
	 */
	public TitleContainsWait(final WebDriver driver, final String needle) {
		super(driver);
		mCondition = ExpectedConditions.titleContains(needle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.wslotter.model.wait.AConditionalWait#getCondition()
	 */
	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return mCondition;
	}
}
