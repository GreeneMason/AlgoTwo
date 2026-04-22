package AVLTree;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        
        for(int i = 0; i < 40; i++) {
            avlTree.insert(i);
        }

        /*for(int i = 35; i > 0; i--) {
            avlTree.insert(i);
        }*/

        /*for(int i = 0; i < 25; i++) {
            Random rand = new Random();
            int randomValue = rand.nextInt(50);
            avlTree.insert(randomValue);
        }*/

        //avlTree.printTree();

        System.out.println("Inorder Traversal");
        avlTree.traversal();

        System.out.println("\nDeleting 40");
        avlTree.delete(40);

        System.out.println("\nInorder Traversal after deletion:");
        avlTree.traversal();

        System.out.println("\nSearching for 25: " + (avlTree.search(25)));
        System.out.println("\nSearching for 40: " + (avlTree.search(40)));
    }
}

class AVLTree {
    private Node root;
    //Node class for AVL Tree
    class Node {
        int key;
        int height;
        Node left;
        Node right;
        
        public Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

}

public void insert(int key) {
    root = insertRec(root, key);

}

private Node insertRec(Node node, int key) {
    if (node == null) {
        return new Node(key);
    }

    if (key < node.key) {
        node.left = insertRec(node.left, key);
    } else if (key > node.key) {
        node.right = insertRec(node.right, key);
    } else {
        return node; // Duplicate keys not allowed
    }

    // Update height and balance
    node.height = 1 + Math.max(height(node.left), height(node.right));
    //int balance = getBalance(node);

    // Rebalance
    return rebalance(node);
}

private Node rightRotate(Node y) {
    Node x = y.left;
    Node temp = x.right;

    // Perform rotation
    x.right = y;
    y.left = temp;

    // Update heights
    y.height = 1 + Math.max(height(y.left), height(y.right));
    x.height = 1 + Math.max(height(x.left), height(x.right));

    // Return new root
    return x;
}

private Node leftRotate(Node x) {
    Node y = x.right;
    Node temp = y.left;

    // Perform rotation
    y.left = x;
    x.right = temp;

    // Update heights
    x.height = 1 + Math.max(height(x.left), height(x.right));
    y.height = 1 + Math.max(height(y.left), height(y.right));

    // Return new root
    return y;
}

private int height(Node node) {
    if (node == null) {
        return 0;
    } else {
        return node.height;
    }
}

private int getBalance(Node node) {
    if (node == null) {
        return 0;
    } else {
        return height(node.left) - height(node.right);
    }
}

public void delete(int key) {
    root = deleteRec(root, key);
}
private Node deleteRec(Node root, int key) {
    // 1. Standard BST Delete
    if (root == null) return root;

    if (key < root.key) {
        root.left = deleteRec(root.left, key);
    } else if (key > root.key) {
        root.right = deleteRec(root.right, key);
    } else {
        // We found the node! 
        if ((root.left == null) || (root.right == null)) {
            // Case: One child or no child
            Node temp = (root.left != null) ? root.left : root.right;
            if (temp == null) { // No child
                root = null;
            } else { // One child
                root = temp; 
            }
        } else {
            // Case: Two children (Find the successor)
            Node temp = minValueNode(root.right);
            root.key = temp.key;
            root.right = deleteRec(root.right, temp.key);
        }
    }

    if (root == null) return root;

    // 2. Update Height
    root.height = Math.max(height(root.left), height(root.right)) + 1;

    // 3. Rebalance
    return rebalance(root); 
}
//search method for AVL Tree
public Boolean search(int key) {
    boolean found = searchRec(root, key);
    return found;
}
private boolean searchRec(Node node, int key) {
    if (node == null) {
        return false;
    }
    
    if (node.key == key) {
        return true;
    }

    // Decision logic for searching the key
    if (key < node.key) {
        return searchRec(node.left, key);
    } else {
        return searchRec(node.right, key);
    }
}
public Node rebalance(Node node) {
    int balance = getBalance(node);

    // Left Left Case
    if (balance > 1 && getBalance(node.left) >= 0) {
        return rightRotate(node);
    }

    // Right Right Case
    if (balance < -1 && getBalance(node.right) <= 0) {
        return leftRotate(node);
    }

    // Left Right Case
    if (balance > 1 && getBalance(node.left) < 0) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    // Right Left Case
    if (balance < -1 && getBalance(node.right) > 0) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    return node;
}

private Node minValueNode(Node node) {
    Node current = node;
    while (current.left != null) {
        current = current.left;
    }
    return current;
}

public void traversal() {
    traversal(root);
}
private void traversal(Node node) {
    if (node != null) {
        traversal(node.left);
        System.out.print(node.key + " ");
        traversal(node.right);
    }
}

public void printTree () {
    printTreeHelper(root);
}

private void printTreeHelper (Node node) {
    if (node != null) {
        printTreeHelper(node.left);
        System.out.println(node.key + " (Height: " + node.height + ")");
        printTreeHelper(node.right);
    }
}
}
