package com.lakedev.backphone.ui;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.TextArea;
import org.hexworks.zircon.api.component.TextBox;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

class MainApp
{
    private static final int TERMINAL_WIDTH = 25;
    
    private static final int TERMINAL_HEIGHT = 20;
    
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    
//    private static final ColorTheme DEFAULT_THEME = 
//    		
//    		ColorThemes
//    		.newBuilder()
//    		.withPrimaryForegroundColor(TileColors.fromString("#acb296"))
//    		.withPrimaryBackgroundColor(TileColors.fromString("#414f35"))
//    		.withSecondaryForegroundColor(TileColors.fromString("#659075"))
//    		.withSecondaryBackgroundColor(TileColors.fromString("#1c2b17"))
//    		.withAccentColor(TileColors.fromString("#2f5e53"))
//    		.build();
	
	MainApp()
	{
		// Displaying a cursor
		final AppConfig appConfig = 
        		AppConfigs
        		.newConfig()
                .withCursorColor(TileColors.fromString("#66FF66"))
                .withBlinkLengthInMilliSeconds(500)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorBlinking(true)
                .withSize(SIZE)
                .withDefaultTileset(BuiltInCP437TilesetResource.TAFFER_20X20)
                .build();

        final TileGrid tileGrid = SwingApplications.startTileGrid(appConfig);
        
        final Screen screen = Screens.createScreenFor(tileGrid);
        
        screen.setCursorVisibility(true);
        
//        final TextArea txtPhoneNumber = 
//        		
//        		Components
//        		.textArea()
//        		.withSize(Sizes.create(12, 1))
//        		.withPosition(Positions.offset1x1())
//        		.wrapWithBox(true)
//        		.withBoxType(BoxType.SINGLE)
//        		.build();
//        
//        final Button btnSearch = 
//        		
//        		Components
//        		.button()
//        		.withPosition(Positions.offset1x1().relativeToRightOf(txtPhoneNumber))
//        		.withSize(Sizes.create(8, 1))
//        		.withText("Search")
//        		.build();
        
        final TextBox txtResults = 
        		
        		Components
        		.textBox()
//        		.withPosition(Positions.create(0, 1).relativeToBottomOf(txtPhoneNumber))
        		.withPosition(Positions.create(3, 3))
        		.withContentWidth(5)
        		.withTitle("Results")
        		.build();
        		
        
//        btnSearch.onMouseClicked(mouseClicked -> System.out.println(txtPhoneNumber.getText()));
//        
//        screen.addComponent(txtPhoneNumber);
//        
//        screen.addComponent(btnSearch);
        
        screen.addComponent(txtResults);
        
        /*
         * Entrappedinapalette
         * LinuxMintDark
         * ghostofachance (sort of)
         */
        screen.applyColorTheme(ColorThemes.linuxMintDark());
        
        screen.display();

        // KeyListener example
//        screen.onKeyStroke(keyStroked -> tileGrid.putCharacter(keyStroked.getCharacter()));
        
        /*
         * 
         */
//
//        Panel panel = Components.panel()
//                .wrapWithBox(true) // panels can be wrapped in a box
//                .withTitle("Panel") // if a panel is wrapped in a box a title can be displayed
//                .wrapWithShadow(true) // shadow can be added
//                .withSize(Sizes.create(32, 16)) // the size must be smaller than the parent's size
//                .withPosition(Positions.offset1x1())
//                .build(); // position is always relative to the parent
//
//        final Header header = Components.header()
//                // this will be 1x1 left and down from the top left
//                // corner of the panel
//                .withPosition(Positions.offset1x1())
//                .withText("Header")
//                .build();
//
//        final CheckBox checkBox = Components.checkBox()
//                .withText("Check me!")
//                .withPosition(Positions.create(0, 1)
//                        // the position class has some convenience methods
//                        // for you to specify your component's position as
//                        // relative to another one
//                        .relativeToBottomOf(header))
//                .build();
//
//        final Button left = Components.button()
//                .withPosition(Positions.create(0, 1) // this means 1 row below the check box
//                        .relativeToBottomOf(checkBox))
//                .withText("Left")
//                .build();
//
//        final Button right = Components.button()
//                .withPosition(Positions.create(1, 0) // 1 column right relative to the left BUTTON
//                        .relativeToRightOf(left))
//                .withText("Right")
//                .build();
//
//        panel.addComponent(header);
//        panel.addComponent(checkBox);
//        panel.addComponent(left);
//        panel.addComponent(right);
//
//        screen.addComponent(panel);
//
//        // we can apply color themes to a screen
//        screen.applyColorTheme(ColorThemes.monokaiBlue());
//
//        // this is how you can define interactions with a component
//        left.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiGreen())));
//
//        right.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiViolet())));
//
//        // in order to see the changes you need to display your screen.
//        screen.display();
	}

	public static void main(String[] args)
	{
		new MainApp();
	}
	

}
