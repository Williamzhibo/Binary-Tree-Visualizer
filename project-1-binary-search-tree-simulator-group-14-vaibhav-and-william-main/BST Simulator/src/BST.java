class Node {
	int data;
	Node left;
	Node right;
	double x;
	double y;
	//Initialize node with left and right pointers set to null
	Node(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
}

public class BST {
	
	private Node root;
	private UI userInterface;
	
	public BST() { //ctor
		userInterface = new UI();
	}
	
	//getter and setter methods
	public Node getRoot() {
		return root;
	}
	
	public int getData() {
		return root.data;
	}
	
	public Node getCurr() {
		return root;
	}
	
	public Node getRight() {
		return root.right;
	}
	
	public Node getLeft() {
		return root.left;
	}
	
	public void clear () {
		root = null;
	}
	
	public void insert(int data, boolean animate) {
		//insertHelper returns the updated node after insertion
	    root = insertHelper(root, data, animate);
	    //System.out.print(inOrderPrint());
	}

	private Node insertHelper(Node current, int data, boolean animate) {
		//if the current is empty, create a new node with the provided data
	    if (current == null) {
	        return new Node(data);
	    }
	    if(animate) {
		    userInterface.highlightNode(current.x, current.y, 8, StdDraw.RED);
			StdDraw.show();
			StdDraw.pause(1000);
			userInterface.highlightNode(current.x, current.y, 8, StdDraw.WHITE);
	    }
	    //insert into the left subtree
	    if (data <= current.data) {
	        current.left = insertHelper(current.left, data, animate);
	    //insert into the right subtree
	    } else {
	        current.right = insertHelper(current.right, data, animate);
	    }
	    
	    //returns the updated current node
	    
	    return current;
	}

	public String inOrderPrint(boolean animate) {
		//calls helper method
		return inOrderPrintHelper(root, animate);
	}		
	
	private String inOrderPrintHelper (Node curr, boolean animate) {
		//return string created
		String s = "";
		if (curr == null) {
			//nothing to add, returns
			return "";
		}
		if (animate) {
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.RED);
			StdDraw.show();
			StdDraw.pause(1000);
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.WHITE);
		}
		//adds left subtree of current
		s += inOrderPrintHelper(curr.left, animate);
		//concatenates current's data to string
		s += curr.data + " ";
		//adds right subtree of current
		s += inOrderPrintHelper(curr.right, animate);
		
		//final BST is complete, returns string. 
		return s;

	}
	
    
	public void delete(int data) {
	    root = deleteHelper(root, data); //calls helper method for delete
	}

	private Node deleteHelper(Node curr, int data) {
	    if (curr == null) { //end of the tree reached
	        return curr;
	    }
	    //animation
	    userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.RED);
		StdDraw.show();
		StdDraw.pause(1000);
		//recurse through tree to find data to delete
	    if (curr.data > data) {
	    	userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.WHITE);
	        curr.left = deleteHelper(curr.left, data);
	    } else if (curr.data < data) {
	    	userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.WHITE);
	        curr.right = deleteHelper(curr.right, data);
	    } else {
	        // Node with only one child or no child
	        if (curr.left == null) {
	            return curr.right;
	        } else if (curr.right == null) {
	            return curr.left;
	        }
	        // Node with two children, get the inorder successor
	        curr.data = minValue(curr.right);
	        // Delete the inorder successor
	        curr.right = deleteHelper(curr.right, curr.data);
	    }
	    return curr;
	}

	private int minValue(Node node) { //finds successor 
	    int minValue = node.data;
	    //animation to see how successor is traced
	    userInterface.highlightNode(node.x, node.y, 8, StdDraw.RED);
		StdDraw.show();
		StdDraw.pause(1000);
		userInterface.highlightNode(node.x, node.y, 8, StdDraw.WHITE);
	    while (node.left != null) {
	        minValue = node.left.data;
	        node = node.left;
	    }
	    return minValue;
	}
	
	public String preOrderPrint(boolean animate) {
		//calls helper method
		return preOrderPrintHelper(root, animate);
	}
	
	private String preOrderPrintHelper (Node curr, boolean animate) {
		//return string created
		String s = "";
		if (curr == null) {
			//nothing to add, returns
			return "";
		}
		
		if (animate) {
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.RED);
			StdDraw.show();
			StdDraw.pause(1000);
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.WHITE);
		}
		
		//concatenates current's data to string
		s += curr.data + " ";
		
		//adds left subtree of current
		s += preOrderPrintHelper(curr.left, animate);
		
		//adds right subtree of current
		s += preOrderPrintHelper(curr.right, animate);
		
		//final BST is complete, returns string. 
		return s;

	}
	
	public String postOrderPrint(boolean animate) {
		//calls helper method
		return postOrderPrintHelper(root, animate);
	}
	
	private String postOrderPrintHelper (Node curr, boolean animate) {
		//return string created
		String s = "";
		if (curr == null) {
			//nothing to add, returns
			return "";
		}
		
		if (animate) {
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.RED);
			StdDraw.show();
			StdDraw.pause(1000);
			userInterface.highlightNode(curr.x, curr.y, 8, StdDraw.WHITE);
		}
		
		//adds left subtree of current
		s += postOrderPrintHelper(curr.left, animate);
		//adds right subtree of current
		s += postOrderPrintHelper(curr.right, animate);
		//concatenates current's data to string
		s += curr.data + " ";
		
		//final BST is complete, returns string. 
		return s;

	}
	
	public Node find(int value) {
		return find(root, value); //calls helper method for find
	}
	
	private Node find(Node root, int value) {
		if(root == null) { //checks if node if value to find is nonexsistant 
			return null;	
		}
		//animation 
		StdDraw.setPenRadius(0.01);
		userInterface.highlightNode(root.x, root.y, 8, StdDraw.BLUE);
		StdDraw.show();
		StdDraw.pause(1000);
		userInterface.highlightNode(root.x, root.y, 8, StdDraw.WHITE);
		StdDraw.setPenRadius();
		if(value == root.data) { 
			StdDraw.setPenRadius(0.01);
			userInterface.highlightNode(root.x, root.y, 8, StdDraw.GREEN); //Root found
			StdDraw.show();
			StdDraw.pause(1000);
			userInterface.highlightNode(root.x, root.y, 8, StdDraw.WHITE);
			StdDraw.setPenRadius();
			return root;
		}
		if(value < root.data) { //value lies on the left side of tree
			return find(root.left, value);
		} else { //value lies on right side of tree
			return find(root.right, value);
		}
	}
	
	public int height() {
		return height(root); //height helper method 
	}
	
	private int height(Node root) {
		if (null == root) { //if end reached/tree is empty, return 0
			return 0;
		}
		int hLeftSub = height(root.left); //left side height 
		int hRightSub = height(root.right); //right side height
		return Math.max(hLeftSub, hRightSub) + 1; //picks the longer side
    }
}
	

