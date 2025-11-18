import java.io.BufferedWriter;
import java.io.FileWriter;   
import java.io.IOException; 
import java.io.Writer;

public class FileIO {
	
	private BST binaryTree;
	private FileWriter myWriter;
	
	public FileIO () throws IOException {
		myWriter = new FileWriter("BSTStatistics.txt"); //remake file when program runs for first time
	}
	
	public FileIO (BST binaryTree) {
		this.binaryTree = binaryTree;		
	}
	
	public void writeStatsToFile() {
		try {
			Writer myWriter;
			myWriter = new BufferedWriter(new FileWriter("BSTStatistics.txt", true)); //append to file 
			myWriter.append("Pre order print: " + binaryTree.preOrderPrint(false) + "\n"); //preorder
			myWriter.append("In order print: " + binaryTree.inOrderPrint(false) + "\n"); //inorder
			myWriter.append("Post order print: " + binaryTree.postOrderPrint(false) + "\n"); //postorder
			myWriter.append("Height of tree: " + binaryTree.height() + "\n"); //height
			myWriter.append("=================================================================\n");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
	    }
	}
}


