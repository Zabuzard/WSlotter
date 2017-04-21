package de.zabuza.wslotter.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import de.zabuza.wslotter.model.EBrowser;

/**
 * Dialog window for changing the settings of the tool.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SettingsDialog extends JDialog {
	/**
	 * The title of the binary panel.
	 */
	private final static String BINARY_TITLE = "Binary";
	/**
	 * The default amount of columns for fields of the view.
	 */
	private static final int DEFAULT_FIELD_COLUMNS = 10;
	/**
	 * The default font of the view.
	 */
	private static final String DEFAULT_FONT = "Tahoma";
	/**
	 * The default font size of the view.
	 */
	private static final int DEFAULT_FONT_SIZE = 11;
	/**
	 * The title of the dialog window.
	 */
	private final static String DIALOG_TITLE = "Settings";
	/**
	 * The title of the driver panel.
	 */
	private final static String DRIVER_TITLE = "Driver";
	/**
	 * The height of the dialog.
	 */
	private final static int FRAME_HEIGHT = 380;
	/**
	 * The width of the dialog.
	 */
	private final static int FRAME_WIDTH = 400;
	/**
	 * The origin offset of the dialog to the owner, in both directions.
	 */
	private final static int OWNER_OFFSET = 50;
	/**
	 * The title of the buttons for selection.
	 */
	private static final String SELECT_TITLE = "Search";
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The binary panel of the dialog.
	 */
	private JPanel mBinaryPanel;
	/**
	 * Select binary button of the dialog for browsers.
	 */
	private JButton mBrowserBinaryBtn;
	/**
	 * The browser binary input field.
	 */
	private JTextField mBrowserBinaryField;
	/**
	 * The button of the dialog for canceling.
	 */
	private JButton mCancelBtn;
	/**
	 * Select driver button of the dialog for Chrome.
	 */
	private JButton mChromeBtn;
	/**
	 * The driver input field for Chrome.
	 */
	private JTextField mChromeDriverField;
	/**
	 * Container of the dialog.
	 */
	private final Container mContainer;
	/**
	 * The driver panel of the dialog.
	 */
	private JPanel mDriverPanel;
	/**
	 * List of all elements of this dialog.
	 */
	private final List<JComponent> mElements;
	/**
	 * Select driver button of the dialog for Firefox.
	 */
	private JButton mFirefoxBtn;
	/**
	 * The driver input field for Firefox.
	 */
	private JTextField mFirefoxDriverField;
	/**
	 * Select driver button of the dialog for Internet Explorer.
	 */
	private JButton mInternetExplorerBtn;
	/**
	 * The driver input field for Internet Explorer.
	 */
	private JTextField mInternetExplorerDriverField;
	/**
	 * Select driver button of the dialog for Microsoft Edge.
	 */
	private JButton mMsEdgeBtn;
	/**
	 * The driver input field for Microsoft Edge.
	 */
	private JTextField mMsEdgeDriverField;
	/**
	 * Select driver button of the dialog for Opera.
	 */
	private JButton mOperaBtn;
	/**
	 * The driver input field for Opera.
	 */
	private JTextField mOperaDriverField;
	/**
	 * Select driver button of the dialog for Safari.
	 */
	private JButton mSafariBtn;
	/**
	 * The driver input field for Safari.
	 */
	private JTextField mSafariDriverField;
	/**
	 * The button of the dialog for saving.
	 */
	private JButton mSaveBtn;
	/**
	 * The trailer panel of the dialog.
	 */
	private JPanel mTrailerPanel;

	/**
	 * Creates a new settings dialog window.
	 * 
	 * @param owner
	 *            The owning frame of this dialog
	 */
	public SettingsDialog(final JFrame owner) {
		super(owner, DIALOG_TITLE);
		this.mContainer = getContentPane();
		this.mElements = new LinkedList<>();
		initialize(owner);
	}

	/**
	 * Adds an action listener to the given browser binary selection action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToBrowserBinarySelectionAction(final ActionListener listener) {
		this.mBrowserBinaryBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the given browser driver selection action.
	 * 
	 * @param browser
	 *            Browser to add listener to its corresponding driver selection
	 *            action
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToBrowserDriverSelectionAction(final EBrowser browser, final ActionListener listener) {
		if (browser == EBrowser.CHROME) {
			this.mChromeBtn.addActionListener(listener);
		} else if (browser == EBrowser.FIREFOX) {
			this.mFirefoxBtn.addActionListener(listener);
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			this.mInternetExplorerBtn.addActionListener(listener);
		} else if (browser == EBrowser.MS_EDGE) {
			this.mMsEdgeBtn.addActionListener(listener);
		} else if (browser == EBrowser.OPERA) {
			this.mOperaBtn.addActionListener(listener);
		} else if (browser == EBrowser.SAFARI) {
			this.mSafariBtn.addActionListener(listener);
		} else {
			throw new IllegalArgumentException("The given browser is not supported by this operation: " + browser);
		}
	}

	/**
	 * Adds an action listener to the cancel action
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToCancelAction(final ActionListener listener) {
		this.mCancelBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the save action
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToSaveAction(final ActionListener listener) {
		this.mSaveBtn.addActionListener(listener);
	}

	/**
	 * Gets the browser binary field.
	 * 
	 * @return The browser binary field
	 */
	public JTextField getBrowserBinaryField() {
		return this.mBrowserBinaryField;
	}

	/**
	 * Gets the driver field of the corresponding given browser.
	 * 
	 * @param browser
	 *            Browser to get its driver field
	 * @return The driver field corresponding to the given browser
	 */
	public JTextField getBrowserDriverField(final EBrowser browser) {
		if (browser == EBrowser.CHROME) {
			return this.mChromeDriverField;
		} else if (browser == EBrowser.FIREFOX) {
			return this.mFirefoxDriverField;
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			return this.mInternetExplorerDriverField;
		} else if (browser == EBrowser.MS_EDGE) {
			return this.mMsEdgeDriverField;
		} else if (browser == EBrowser.OPERA) {
			return this.mOperaDriverField;
		} else if (browser == EBrowser.SAFARI) {
			return this.mSafariDriverField;
		} else {
			throw new IllegalArgumentException("The given browser is not supported by this operation: " + browser);
		}
	}

	/**
	 * Enables or disables all elements of the dialog.
	 * 
	 * @param enabled
	 *            Whether the elements should be enabled or disabled
	 */
	public void setAllElementsEnabled(final boolean enabled) {
		for (JComponent element : this.mElements) {
			element.setEnabled(enabled);
		}
	}

	/**
	 * Initialize the contents of the view.
	 * 
	 * @param owner
	 *            The owning frame of this dialog
	 */
	private void initialize(final JFrame owner) {
		setBounds(owner.getX() + OWNER_OFFSET, owner.getY() + OWNER_OFFSET, FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		initializePanels();
		initializeLabels();
		initializeButtons();
		initializeInputFields();
	}

	/**
	 * Initialize the buttons.
	 */
	private void initializeButtons() {
		this.mChromeBtn = new JButton(SELECT_TITLE);
		this.mChromeBtn.setBounds(290, 30, 80, 20);
		this.mDriverPanel.add(this.mChromeBtn);
		this.mElements.add(this.mChromeBtn);

		this.mFirefoxBtn = new JButton(SELECT_TITLE);
		this.mFirefoxBtn.setBounds(290, 60, 80, 20);
		this.mDriverPanel.add(this.mFirefoxBtn);
		this.mElements.add(this.mFirefoxBtn);

		this.mInternetExplorerBtn = new JButton(SELECT_TITLE);
		this.mInternetExplorerBtn.setBounds(290, 90, 80, 20);
		this.mDriverPanel.add(this.mInternetExplorerBtn);
		this.mElements.add(this.mInternetExplorerBtn);

		this.mMsEdgeBtn = new JButton(SELECT_TITLE);
		this.mMsEdgeBtn.setBounds(290, 120, 80, 20);
		this.mDriverPanel.add(this.mMsEdgeBtn);
		this.mElements.add(this.mMsEdgeBtn);

		this.mOperaBtn = new JButton(SELECT_TITLE);
		this.mOperaBtn.setBounds(290, 150, 80, 20);
		this.mDriverPanel.add(this.mOperaBtn);
		this.mElements.add(this.mOperaBtn);

		this.mSafariBtn = new JButton(SELECT_TITLE);
		this.mSafariBtn.setBounds(290, 180, 80, 20);
		this.mDriverPanel.add(this.mSafariBtn);
		this.mElements.add(this.mSafariBtn);

		this.mBrowserBinaryBtn = new JButton(SELECT_TITLE);
		this.mBrowserBinaryBtn.setBounds(290, 30, 80, 20);
		this.mBinaryPanel.add(this.mBrowserBinaryBtn);
		this.mElements.add(this.mBrowserBinaryBtn);

		this.mSaveBtn = new JButton("Save");
		this.mSaveBtn.setBounds((FRAME_WIDTH / 2) - 100, 310, 80, 20);
		this.mTrailerPanel.add(this.mSaveBtn);
		this.mElements.add(this.mSaveBtn);

		this.mCancelBtn = new JButton("Cancel");
		this.mCancelBtn.setBounds((FRAME_WIDTH / 2) + 20, 310, 80, 20);
		this.mTrailerPanel.add(this.mCancelBtn);
		this.mElements.add(this.mCancelBtn);
	}

	/**
	 * Initialize the text fields.
	 */
	private void initializeInputFields() {
		this.mChromeDriverField = new JTextField();
		this.mChromeDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mChromeDriverField.setBounds(80, 30, 200, 20);
		this.mDriverPanel.add(this.mChromeDriverField);
		this.mElements.add(this.mChromeDriverField);
		this.mChromeDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mFirefoxDriverField = new JTextField();
		this.mFirefoxDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mFirefoxDriverField.setBounds(80, 60, 200, 20);
		this.mDriverPanel.add(this.mFirefoxDriverField);
		this.mElements.add(this.mFirefoxDriverField);
		this.mFirefoxDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mInternetExplorerDriverField = new JTextField();
		this.mInternetExplorerDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mInternetExplorerDriverField.setBounds(80, 90, 200, 20);
		this.mDriverPanel.add(this.mInternetExplorerDriverField);
		this.mElements.add(this.mInternetExplorerDriverField);
		this.mInternetExplorerDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mMsEdgeDriverField = new JTextField();
		this.mMsEdgeDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mMsEdgeDriverField.setBounds(80, 120, 200, 20);
		this.mDriverPanel.add(this.mMsEdgeDriverField);
		this.mElements.add(this.mMsEdgeDriverField);
		this.mMsEdgeDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mOperaDriverField = new JTextField();
		this.mOperaDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mOperaDriverField.setBounds(80, 150, 200, 20);
		this.mDriverPanel.add(this.mOperaDriverField);
		this.mElements.add(this.mOperaDriverField);
		this.mOperaDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mSafariDriverField = new JTextField();
		this.mSafariDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mSafariDriverField.setBounds(80, 180, 200, 20);
		this.mDriverPanel.add(this.mSafariDriverField);
		this.mElements.add(this.mSafariDriverField);
		this.mSafariDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mBrowserBinaryField = new JTextField();
		this.mBrowserBinaryField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mBrowserBinaryField.setBounds(80, 30, 200, 20);
		this.mBinaryPanel.add(this.mBrowserBinaryField);
		this.mElements.add(this.mBrowserBinaryField);
		this.mBrowserBinaryField.setColumns(DEFAULT_FIELD_COLUMNS);
	}

	/**
	 * Initialize the labels.
	 */
	private void initializeLabels() {
		JLabel mChromeDriverLbl = new JLabel("Chrome:");
		mChromeDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mChromeDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mChromeDriverLbl.setBounds(10, 30, 60, 14);
		this.mDriverPanel.add(mChromeDriverLbl);

		JLabel mFirefoxDriverLbl = new JLabel("Firefox:");
		mFirefoxDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mFirefoxDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mFirefoxDriverLbl.setBounds(10, 60, 60, 14);
		this.mDriverPanel.add(mFirefoxDriverLbl);

		JLabel mInternetExplorerDriverLbl = new JLabel("IE:");
		mInternetExplorerDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mInternetExplorerDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mInternetExplorerDriverLbl.setBounds(10, 90, 60, 14);
		this.mDriverPanel.add(mInternetExplorerDriverLbl);

		JLabel mMsEdgeDriverLbl = new JLabel("MS Edge:");
		mMsEdgeDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mMsEdgeDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mMsEdgeDriverLbl.setBounds(10, 120, 60, 14);
		this.mDriverPanel.add(mMsEdgeDriverLbl);

		JLabel mOperaDriverLbl = new JLabel("Opera:");
		mOperaDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mOperaDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mOperaDriverLbl.setBounds(10, 150, 60, 14);
		this.mDriverPanel.add(mOperaDriverLbl);

		JLabel mSafariDriverLbl = new JLabel("Safari:");
		mSafariDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mSafariDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mSafariDriverLbl.setBounds(10, 180, 60, 14);
		this.mDriverPanel.add(mSafariDriverLbl);

		JLabel mBrowserBinaryLbl = new JLabel("Browser:");
		mBrowserBinaryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mBrowserBinaryLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mBrowserBinaryLbl.setBounds(10, 30, 60, 14);
		this.mBinaryPanel.add(mBrowserBinaryLbl);
	}

	/**
	 * Initialize the panels.
	 */
	private void initializePanels() {
		this.mDriverPanel = new JPanel();
		this.mDriverPanel.setBounds(10, 10, FRAME_WIDTH - 25, 220);
		TitledBorder titledBorderDriver = BorderFactory.createTitledBorder(DRIVER_TITLE);
		this.mDriverPanel.setBorder(titledBorderDriver);
		this.mContainer.add(this.mDriverPanel);
		this.mDriverPanel.setLayout(null);

		this.mBinaryPanel = new JPanel();
		this.mBinaryPanel.setBounds(10, 230, FRAME_WIDTH - 25, 70);
		TitledBorder titledBorderBinary = BorderFactory.createTitledBorder(BINARY_TITLE);
		this.mBinaryPanel.setBorder(titledBorderBinary);
		this.mContainer.add(this.mBinaryPanel);
		this.mBinaryPanel.setLayout(null);

		this.mTrailerPanel = new JPanel();
		this.mTrailerPanel.setBounds(10, 300, FRAME_WIDTH - 25, 80);
		this.mContainer.add(this.mTrailerPanel);
		this.mTrailerPanel.setLayout(null);
	}
}
