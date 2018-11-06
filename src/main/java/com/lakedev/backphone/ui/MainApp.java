package com.lakedev.backphone.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
import com.lakedev.backphone.service.PhoneNumberSearcher;
import com.lakedev.backphone.service.SearchResult;

class MainApp
{
	
	private MultiWindowTextGUI gui;
	private Table<String> tblResults;

	MainApp() throws IOException
	{
		/*
		 * Terminal
		 * Screen
		 * Panel with vert. linear layout
		 * 		Panel with horiz. linear layout
		 * 			Label(Phone) | TextBox(Input) | Button(Search)
		 * 		Table
		 * 
		 * input has a validation pattern: https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/text_boxes.md
		 * button first checks if there's input, creating dialog if not: https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/message_dialogs.md
		 */
		
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		
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
						new MessageDialogBuilder()
						.setTitle("No Results")
						.setText(String.format("No results were found for %s.", phoneNumber))
						.addButton(MessageDialogButton.OK)
						.build()
						.showDialog(gui);
					} else
					{
						tblResults.getTableModel().getRows().clear();
						
						searchResults
						.stream()
						.forEach(searchResult -> tblResults.getTableModel().addRow(searchResult.getResourceShorthand(), searchResult.getTargetResult()));
					}
					
					txtPhoneNumber.setText("");

				} else
				{
					new MessageDialogBuilder()
					.setTitle("ERROR")
					.setText("Please provide a valid phone number with area code.")
					.addButton(MessageDialogButton.OK)
					.build()
					.showDialog(gui);
				}
			}
		});
		
		pnlSearch.addComponent(btnSearch);
		
		tblResults = new Table<String>("Source","Result");
		
		pnlParent.addComponent(pnlSearch);
		
		pnlParent.addComponent(new EmptySpace(new TerminalSize(0, 1)));
		
		pnlParent.addComponent(tblResults);
		
      // Create window to hold the panel
      BasicWindow window = new BasicWindow();
      window.setHints(Arrays.asList(Hint.EXPANDED));
      
      window.setComponent(pnlParent);

      gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
      gui.addWindowAndWait(window);
		
		// +============================================================================+
		
		// Panels
		// https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/panels.md
		
//		// Setup terminal and screen layers
//		Terminal terminal = new DefaultTerminalFactory().createTerminal();
//		Screen screen = new TerminalScreen(terminal);
//		screen.startScreen();
//
//		// Create window to hold the panel
//		BasicWindow window = new BasicWindow();
//
//		Panel mainPanel = new Panel();
//		mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
//
//		Panel leftPanel = new Panel();
//		mainPanel.addComponent(leftPanel.withBorder(Borders.singleLine("Left Panel")));
//
//		Panel rightPanel = new Panel();
//		mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Right Panel")));
//		
//		MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
//
//		window.setComponent(mainPanel.withBorder(Borders.singleLine("Main Panel")));
//		gui.addWindowAndWait(window);
		
		// +============================================================================+
		
		// Windows
		// https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/windows.md
		
//		// Setup terminal and screen layers
//		Terminal terminal = new DefaultTerminalFactory().createTerminal();
//		Screen screen = new TerminalScreen(terminal);
//		screen.startScreen();
//
//		// Create window to hold the panel
//		BasicWindow window = new BasicWindow();
//
//		// Create gui and start gui
//		MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
//		gui.addWindowAndWait(window);
		
		// +============================================================================+
		
		// Basic Form Submission Example
		// https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/basic_form_submission.md
//        // Setup terminal and screen layers
//        Terminal terminal = new DefaultTerminalFactory().createTerminal();
//        Screen screen = new TerminalScreen(terminal);
//        screen.startScreen();
//
//        // Create panel to hold components
//        Panel panel = new Panel();
//        panel.setLayoutManager(new GridLayout(2));
//
//        final Label lblOutput = new Label("");
//
//        panel.addComponent(new Label("Num 1"));
//        final TextBox txtNum1 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);
//
//        panel.addComponent(new Label("Num 2"));
//        final TextBox txtNum2 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);
//
//        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
//        new Button("Add!", new Runnable() {
//            public void run() {
//                int num1 = Integer.parseInt(txtNum1.getText());
//                int num2 = Integer.parseInt(txtNum2.getText());
//                lblOutput.setText(Integer.toString(num1 + num2));
//            }
//        }).addTo(panel);
//
//        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
//        panel.addComponent(lblOutput);
//
//        // Create window to hold the panel
//        BasicWindow window = new BasicWindow();
//        window.setComponent(panel);
//
//        // Create gui and start gui
//        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
//        gui.addWindowAndWait(window);
		
// +============================================================================+
		
		// Hello World - GUI Example
		// https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/hello_world.md
//        // Setup terminal and screen layers
//        Terminal terminal = new DefaultTerminalFactory().createTerminal();
//        Screen screen = new TerminalScreen(terminal);
//        screen.startScreen();
//
//        // Create panel to hold components
//        Panel panel = new Panel();
//        panel.setLayoutManager(new GridLayout(2));
//
//        panel.addComponent(new Label("Forename"));
//        panel.addComponent(new TextBox());
//
//        panel.addComponent(new Label("Surname"));
//        panel.addComponent(new TextBox());
//
//        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
//        panel.addComponent(new Button("Submit"));
//
//        // Create window to hold the panel
//        BasicWindow window = new BasicWindow();
//        window.setComponent(panel);
//
//        // Create gui and start gui
//        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
//        gui.addWindowAndWait(window);
        
        
	}

	public static void main(String[] args) throws IOException
	{
		new MainApp();
	}
	

}
