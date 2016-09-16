package de.zabuza.wslotter.model.selector;

/**
 * Utility class that provides CSS selectors.
 * 
 * @author Zabuza
 *
 */
public final class CSSSelectors {
	/**
	 * Selector that corresponds to the name input field of the login form.
	 */
	public static final String LOGIN_FORM_NAME = ".textbox[name=user_name]";
	/**
	 * Selector that corresponds to the password input field of the login form.
	 */
	public static final String LOGIN_FORM_PASSWORD = ".textbox[name=user_pass]";
	/**
	 * Selector that corresponds to the submit button of the login form.
	 */
	public static final String LOGIN_FORM_SUBMIT = ".button[name=login]";
	/**
	 * Selector that corresponds to the message input box of the post-reply
	 * form.
	 */
	public static final String POST_REPLY_FORM_MESSAGE_BOX = ".textbox[name=message]";
	/**
	 * Selector that corresponds to the submit button of the post-reply form.
	 */
	public static final String POST_REPLY_FORM_SUBMIT = ".button[name=postreply]";

	/**
	 * Utility class. No implementation.
	 */
	private CSSSelectors() {

	}
}
