package com.lakedev.phonechecker;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.lakedev.phonechecker.service.PhoneNumberSearcher;
import com.lakedev.phonechecker.service.SearchResult;

/**
 * MainApp
 * 
 * The primary UI class.
 * 
 * @author William Lake
 *
 */
class MainApp
{
	private MultiWindowTextGUI gui;

	private Table<String> tblResults;

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 */
	MainApp() throws IOException
	{
		Terminal terminal =

				new DefaultTerminalFactory()
				.setTerminalEmulatorTitle("PhoneChecker")
				.createTerminal();

		Screen screen = new TerminalScreen(terminal);
		
		screen.startScreen();

		Panel pnlParent = new Panel();

		Panel pnlSearch = new Panel(new LinearLayout(Direction.HORIZONTAL));

		pnlSearch.addComponent(new Label("Phone #:"));

		pnlSearch.addComponent(new EmptySpace(new TerminalSize(1, 0)));

		final TextBox txtPhoneNumber = new TextBox();

		txtPhoneNumber.setValidationPattern(Pattern.compile("[0-9]*")).setPreferredSize(new TerminalSize(10, 1));

		pnlSearch.addComponent(txtPhoneNumber);

		pnlSearch.addComponent(new EmptySpace(new TerminalSize(1, 0)));

		final Button btnSearch = new Button("Search", new Runnable()
		{

			public void run()
			{
				String phoneNumber = txtPhoneNumber.getText();

				if (phoneNumber.length() == 10)
				{

					List<SearchResult> searchResults = PhoneNumberSearcher.getInstance().performSearch(phoneNumber);

					if (searchResults.isEmpty())
					{
						new MessageDialogBuilder().setTitle("No Results")
								.setText(String.format("No results were found for %s.", phoneNumber))
								.addButton(MessageDialogButton.OK).build().showDialog(gui);
					} else
					{
						tblResults.getTableModel().getRows().clear();

						searchResults.stream().forEach(searchResult -> tblResults.getTableModel()
								.addRow(searchResult.getResourceShorthand(), searchResult.getTargetResult()));
					}

					txtPhoneNumber.setText("");

				} else
				{
					new MessageDialogBuilder().setTitle("ERROR")
							.setText("Please provide a phone number with area code.").addButton(MessageDialogButton.OK)
							.build().showDialog(gui);
				}
			}
		});

		pnlSearch.addComponent(btnSearch);

		tblResults = new Table<String>("Source", "Result");

		pnlParent.addComponent(pnlSearch);

		pnlParent.addComponent(new EmptySpace(new TerminalSize(0, 1)));

		pnlParent.addComponent(tblResults);

		// Create window to hold the panel
		BasicWindow window = new BasicWindow();

		window.setHints(Arrays.asList(Hint.EXPANDED));

		window.setComponent(pnlParent);

		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

		gui.addWindowAndWait(window);

	}

	/**
	 * Main Method to launch the program.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		new MainApp();
	}

}
