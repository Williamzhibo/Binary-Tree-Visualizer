import java.awt.*;

public class UI {
    private int SCALEMIN = -100;
    private int SCALEMAX = 100;
    private int RESX = 650;
    private int RESY = 650;
    private int YSPACE = 15;
    private StringBuilder inputText = new StringBuilder();
    private String printText = "";
    private ClickableUIElement insert;
    private ClickableUIElement delete;
    private ClickableUIElement intro;
    private ClickableUIElement clear;
    private ClickableUIElement input;
    private ClickableUIElement findExport;
    private ClickableUIElement printTree;
    private ClickableUIElement nextPrint;
    private ClickableUIElement prevButton;
    private String[] prints;
    private String[] findExportArray;
    private BST binaryTree;    
    private boolean isInputActive = false; // Track the input button state

    // CLICKABLE UI ELEMENT CLASS
    class ClickableUIElement {
        double x;
        double y;
        double width;
        double height;
        String text;
        String action;

        // Constructor to initialize the clickable UI element
        ClickableUIElement(double x, double y, double width, double height, String text, String action) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.action = action;
        }

        // Draw the clickable UI element
        public void draw(Color color) {
            StdDraw.setPenColor(color);
            StdDraw.filledRectangle(x, y, width, height);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(x, y - (0.05 * width), text);
        }
        
        public void reClassify(String text, String action) {
        	this.text = text;
        	this.action = action;
        }

        // Handle the click event on the UI element
        public void onClick() {
            if ("insert".equals(action) && inputText.length() != 0) {
                binaryTree.insert(Integer.parseInt(inputText.toString().trim()), true);
                reloadScreen();
            } else if ("delete".equals(action) && inputText.length() != 0) {
                binaryTree.delete(Integer.parseInt(inputText.toString().trim()));
                reloadScreen();
            } else if ("clear".equals(action)) {
            	printText = "";
                binaryTree.clear();
                reloadScreen();
            } else if ("inOrder Print!".equals(action)) {
            	printText = binaryTree.inOrderPrint(true);
            	reloadScreen();
            } else if ("preOrder Print!".equals(action)) {
            	printText = binaryTree.preOrderPrint(true);
            	reloadScreen();
            } else if ("postOrder Print!".equals(action)) {
            	printText = binaryTree.postOrderPrint(true);
            	reloadScreen();
            } else if ("nextPrint".equals(action)) {
            	String next = getNext(prints, printTree.action);
            	printTree.reClassify(next, next);
            } else if ("find!".equals(action)) {
            	Node x = binaryTree.find(Integer.parseInt(inputText.toString().trim())); 
            } else if ("export!".equals(action)) {
            	FileIO file = new FileIO(binaryTree);
                file.writeStatsToFile();
            } else if ("prevButton".equals(action)) {
            	String prev = getNext(findExportArray, findExport.action);
            	findExport.reClassify(prev, prev);
            }
        }
    }

    // Initialize the UI
    public void initialize() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(RESX, RESY);
        StdDraw.setScale(SCALEMIN, SCALEMAX);
        insert = new ClickableUIElement(-15, 90, 15, 4, "Insert!", "insert");
        delete = new ClickableUIElement(30, 90, 15, 4, "Delete!", "delete");
        intro = new ClickableUIElement(-70, 90, 25, 8, "BST Simulator!", "info");
        clear = new ClickableUIElement(75, 90, 16, 4, "Clear!", "clear");
        input = new ClickableUIElement(0, -85, 50, 5, "", "input");
        printTree = new ClickableUIElement(70, -85, 17, 8, "InOrder Print!", "inOrderPrint");
        nextPrint = new ClickableUIElement(95, -85, 5, 8, ">", "nextPrint");
        prints = new String[] {"preOrder Print!", "inOrder Print!", "postOrder Print!"};
        findExport = new ClickableUIElement(-70, -85, 15, 8, "Find!", "find");
        prevButton = new ClickableUIElement(-92, -85, 5, 8, "<", "prevButton");
        findExportArray = new String[] {"find!", "export!"};
        binaryTree = new BST();
    }

    // Reload the UI screen
    public void reloadScreen() {
        StdDraw.clear(); // Clear the screen before redrawing
        drawUIElements();
        drawBST(binaryTree.getRoot(), 0, SCALEMAX - 30, 100);
        StdDraw.show();
    }

    // Draw the UI elements
    private void drawUIElements() {
        // Top Blue Borders
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.filledRectangle(SCALEMIN, SCALEMAX, 2 * SCALEMAX, (SCALEMAX / 5));

        // Draw Buttons
        insert.draw(StdDraw.LIGHT_GRAY);
        delete.draw(StdDraw.LIGHT_GRAY);
        intro.draw(StdDraw.LIGHT_GRAY);
        clear.draw(StdDraw.LIGHT_GRAY);
        
        //printTree.draw(StdDraw.LIGHT_GRAY);
        //nextPrint.draw(StdDraw.LIGHT_GRAY);
        // Draw the bottom divider with a search bar
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledRectangle(0, -90, 100, 20);
        
        // Draw the input field
        StdDraw.setPenColor(StdDraw.WHITE);
        input.draw(StdDraw.WHITE);
        findExport.draw(StdDraw.BOOK_LIGHT_BLUE);
        prevButton.draw(StdDraw.BOOK_LIGHT_BLUE);
        printTree.draw(StdDraw.BOOK_LIGHT_BLUE);
        nextPrint.draw(StdDraw.BOOK_LIGHT_BLUE);
        
        // Indicator for typing activity
        if (isInputActive) {
            // Draw a small indicator next to the input field
            StdDraw.setPenColor(StdDraw.RED); // You can choose any color for the indicator
            StdDraw.text(input.x + input.width + 5, input.y, "Typing...");
        }
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.textLeft(-95, -65, printText);
    }

    // Process user input
    public void processInput() {
        boolean mouseClicked = StdDraw.isMousePressed();
        boolean isMouseOnInput = mouseIsOn(input);
        
        if (!isInputActive) {
            inputText.setLength(0);
            input.text = "";
        }
        
        // Toggle isInputActive when clicking inside or outside the input
        if (mouseClicked && isMouseOnInput) {
            isInputActive = !isInputActive;
        }

        // Handle key input when isInputActive is on
        if (isInputActive && StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            keyPressed(key, inputText);
            input.text = String.valueOf(inputText);
            reloadScreen();
        }

        // Handle button clicks
        for (ClickableUIElement element : getClickableUIElements()) {
            if (mouseClicked && mouseIsOn(element)) {
                element.onClick();
                StdDraw.pause(250);
                if (!element.action.equals("input")) {
                    inputText.setLength(0); // Clear input text when a non-input button is clicked
                    input.text = "";
                    isInputActive = false; 
                }
                
            }
        }
    }


    // Handle key presses
    private void keyPressed(char key, StringBuilder txt) {
        if (Character.isDigit(key)) {
            txt.append(key);
        }
    }
    
    // Check if the mouse is on a UI element
    private boolean mouseIsOn(ClickableUIElement element) {
        return (StdDraw.mouseX() >= (element.x - element.width) &&
                StdDraw.mouseX() <= (element.x + element.width) &&
                StdDraw.mouseY() >= (element.y - element.height) &&
                StdDraw.mouseY() <= (element.y + element.height));
    }

    // Get an array of clickable UI elements
    public ClickableUIElement[] getClickableUIElements() {

        ClickableUIElement[] x = {insert, delete, intro, clear, input, findExport, printTree, nextPrint, prevButton};

        return x;
    }
    
    private String getNext(String[] array, String currPrint) {
        int index = 0;

        // Find the index of currPrint in the array
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(currPrint)) {
                index = i;
            }
        }

        // Calculate the next index in a circular manner
        index = (index + 1) % array.length;

        return array[index];
    }
    
    // Draw the binary search tree recursively
    public void drawBST(Node curr, double x, double y, double childXRange) {
    	
    	if (curr != null) {
            // Draw lines to left and right children
            if (curr.left != null) {
                double childX = x - (childXRange / 2);
                double childY = y - YSPACE;
                StdDraw.setPenColor(StdDraw.BOOK_BLUE); // Set the line color
                StdDraw.line(x, y, childX, childY);
                drawBST(curr.left, childX, childY, childXRange / 2);
            }
            if (curr.right != null) {
                double childX = x + (childXRange / 2);
                double childY = y - YSPACE;
                StdDraw.setPenColor(StdDraw.BOOK_BLUE); // Set the line color
                StdDraw.line(x, y, childX, childY);
                drawBST(curr.right, childX, childY, childXRange / 2);
            }
        	curr.x = x;
            curr.y = y;
            // Draw the current node
            drawNode(x, y, StdDraw.BOOK_BLUE, 8, curr.data);
        }
    }

    private void drawNode(double x, double y, Color color, int radius, int data){
    	StdDraw.setPenColor(color); // Set the node color
        StdDraw.filledCircle(x, y, radius);
        StdDraw.setPenColor(StdDraw.WHITE); // Set the text color
        StdDraw.text(x, y, String.valueOf(data));
    }

    public void highlightNode(double x, double y, int radius, Color color) {
    	StdDraw.setPenColor(color);
    	StdDraw.circle(x, y, radius+1); //hollow circle around node circle
    }
}
