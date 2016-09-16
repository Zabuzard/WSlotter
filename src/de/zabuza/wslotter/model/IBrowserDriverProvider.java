package de.zabuza.wslotter.model;

/**
 * Interface for objects that provide driver for browsers.
 * 
 * @author Zabuza
 *
 */
public interface IBrowserDriverProvider {
	/**
	 * Gets the driver for a given browser if set.
	 * 
	 * @param browser
	 *            Browser to get driver for
	 * @return The driver for the browser or <tt>null</tt> if not set
	 */
	public String getDriverForBrowser(final EBrowser browser);
}
