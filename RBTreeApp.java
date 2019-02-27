package rbtreeapp;

public class RBTreeApp {


    public static void main(String[] args) {

        RBTree rbTree = new RBTree();        

        rbTree.insert(65);
        rbTree.insert(60);
        rbTree.insert(75);
        rbTree.insert(5);
        rbTree.insert(40);
        rbTree.insert(61);
        rbTree.insert(27);
        rbTree.insert(31);
        rbTree.insert(32);
        rbTree.insert(14);
        rbTree.insert(10);
        rbTree.insert(15);
        rbTree.insert(41);
        rbTree.insert(62);
        rbTree.insert(28);
        rbTree.insert(33);
        rbTree.insert(30);
        rbTree.insert(13);
        rbTree.insert(9);
        rbTree.insert(16);
        rbTree.insert(77);
        rbTree.insert(78);
        rbTree.insert(79);
        rbTree.insert(80);
        rbTree.insert(81);

        
        rbTree.displayTreeColor(); // If ErroR -> rbTree.displayTree();
        
        rbTree.delete(2);
        rbTree.delete(65);
        
        rbTree.displayTreeColor(); // If ErroR -> rbTree.displayTree();        
    }    
}
