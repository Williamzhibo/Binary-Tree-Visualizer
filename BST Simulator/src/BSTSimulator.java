import java.io.IOException;

/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */

public class BSTSimulator {
	
	public static boolean makeNewFile;
	
    public static void main(String[] args) throws IOException {
        UI ui = new UI();
        ui.initialize();
        FileIO file = new FileIO();
        makeNewFile = true;
        while (true) {
            ui.processInput();
            ui.reloadScreen();
            StdDraw.show(); // Add this line to display the graphics
        }
    }
}


