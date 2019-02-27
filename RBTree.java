package rbtreeapp;

import java.util.Stack;

public class RBTree {

    public Node rootRBTtree;

    public RBTree() {
        rootRBTtree = null;
    }

// родственники узла
    private Node G(Node n) { // дед
        if (n != null && n.parent != null) {
            return n.parent.parent;
        } else {
            return null;
        }
    }

    private Node U(Node n) { // дядя
        Node g = G(n);
        if (g == null) {
            return null;
        }
        if (n.parent == g.left) {
            return g.right;
        } else {
            return g.left;
        }
    }

    private Node S(Node n) { // брат
        if (n.parent == null) {
            return null;
        }
        if (isLeft(n)) {
            return n.parent.right;
        } else {
            return n.parent.left;
        }

    }

// проверки
    private boolean isLeft(Node n) { // левый потомок ?
        return n == n.parent.left;
    }

    private boolean hasRedChild(Node n) { // красные потомки ?
        return (n.left != null && n.left.isRed == true) || (n.right != null && n.right.isRed == true);
    }

// поиск
    private Node search(int k) { // поиск для вставки
        Node curr = rootRBTtree;
        while (curr != null) {
            if (k < curr.key) {
                if (curr.left == null) {
                    break;
                } else {
                    curr = curr.left;
                }
            } else {
                if (curr.right == null) {
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        return curr;
    }

    public Node find(int k) { // поиск по ключу и для удаления
        Node curr = rootRBTtree;
        while (curr.key != k) {
            if (k < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr == null) {
//                System.out.println("not find");
                return null;
            }
        }
        return curr;
    }

// повороты с проверкой на ROOT
    private void leftRotate(Node x) { //     x
        Node y = x.right;//                 / \ 
        x.right = y.left; //   X.R set    T1   y
        if (y.left != null) //                / \
        {
            y.left.parent = x; //           T2  T3
        }
        if (y != null) //   Y.P set
        {
            y.parent = x.parent;
        }
        if (x.parent != null) {
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            rootRBTtree = y;
        }
        y.left = x; // X <-> Y
        if (x != null) {
            x.parent = y;
        }
    }

    private void rightRotate(Node y) { //     y
        Node x = y.left;//                   / \
        y.left = x.right;  //   X.L set    x   T3
        if (x.right != null)//           / \
        {
            x.right.parent = y; //     T1  T2
        }
        if (x != null) //   Y.P set
        {
            x.parent = y.parent;
        }
        if (y.parent != null) {
            if (y == y.parent.right) {
                y.parent.right = x;
            } else {
                y.parent.left = x;
            }
        } else {
            rootRBTtree = x;
        }
        x.right = y; // X <-> Y
        if (y != null) {
            y.parent = x;
        }
    }

    private void swapCol(Node n1, Node n2) { // замена цветов
        boolean temp;
        temp = n1.isRed;
        n1.isRed = n2.isRed;
        n2.isRed = temp;
    }

    private void swapKey(Node n1, Node n2) { // замена ключей
        int temp;
        temp = n1.key;
        n1.key = n2.key;
        n2.key = temp;
    }

    public void insert(int key) {
        Node newNode = new Node(key);
        if (rootRBTtree == null) {
            newNode.isRed = false;
            rootRBTtree = newNode;
        } else {
            Node temp = search(key);
            if (temp.key == key) {
            }
            newNode.parent = temp;
            if (key < temp.key) {
                temp.left = newNode;
            } else {
                temp.right = newNode;
            }
            fixInsert(newNode);
//            insert_1fix(newNode); // альтернатива
        }
    }

    private void fixInsert(Node n) {
        if (n.parent == null) { // узел в корне
            n.isRed = false;
            return;
        }
        Node P = n.parent;
        Node G = G(n);
        Node U = U(n);

        if (P.isRed) {
            if (U != null && U.isRed) { // предок и дядя красные
                P.isRed = false;
                U.isRed = false;
                G.isRed = true;
                fixInsert(G);
            } else { // определение поворота для дяди черного
                if (isLeft(P)) {
                    if (isLeft(n)) {
                        swapCol(P, G);
                    } else {
                        leftRotate(P);
                        swapCol(n, G);
                    }
                    rightRotate(G); // для левого-левого и левого-правого
                } else {
                    if (isLeft(n)) {
                        rightRotate(P);
                        swapCol(n, G);
                    } else {
                        swapCol(P, G);
                    }
                    leftRotate(G); // для правого-правого и правого-левого
                }
            }
        }
    }
/////////////////////////////////////////////////////////////// альтернативный фикс вставки пошагам  
//    private void insert_1fix (Node n){ // узел в корне
//        if (n.parent == null)
//            n.isRed = false;
//        else
//            insert_2fix(n);
//    }
//    
//    private void insert_2fix (Node n){ // предок узла черный
//        if (! n.parent.isRed){            
//        }
//
//        else
//            insert_3fix(n);
//    }    
//    
//    private void insert_3fix (Node n){ // предок и дядя красные
//        Node u = U(n);
//        Node g;
//        if (u != null && u.isRed){
//            n.parent.isRed = false;
//            u.isRed = false;
//            g = G(n);
//            g.isRed = true;
//            insert_1fix(g);
//        }
//        else
//            insert_4fix(n);
//    }  
//    
//    private void insert_4fix (Node n){ // предок красный, дядя черный
//        Node g = G(n);
//        if (n == n.parent.right && n.parent == g.left){
//            leftRotate(n.parent);
//            n = n.left;
//        }
//        else if (n == n.parent.left && n.parent == g.right){
//            rightRotate(n.parent);
//            n = n.right;
//        }
//        insert_5fix(n);
//    }  
//    
//   private void insert_5fix (Node n){ // исправление окраски предка и деда
//        Node g = G(n);
//        n.parent.isRed = false;
//        g.isRed = true;        
//        if (n == n.parent.left && n.parent == g.left){ // поворот если узел и предок с одной стороны
//            rightRotate(g);
//        }
//        else 
//            leftRotate(g);
//    }  
////////////////////////////////////////////////////////////////////////////    

// удаление
    public void delete(int key) { // удаление по значению ключа
        if (rootRBTtree == null) {
            return;
        }
        Node delNode = find(key);
        if (delNode == null || delNode.key != key) {
            System.out.println("узел " + key + " не найден");
            return;
        }
        deleteNode(delNode);
    }

    public Node nodeReplace(Node n) { // поиск узла на замену для deleteNode

        if (n.left != null) { // ищем замену вместо удаляемого узла
            Node fndNxt = n.left; // налево самый максимальный
            while (fndNxt.right != null) {
                fndNxt = fndNxt.right;
            }
            
            System.out.println("del " + n.key + " next L " + fndNxt.key);

            return fndNxt;
        } else if (n.right != null) {
            Node fndNxt = n.right; // направо самый минимальный
            while (fndNxt.left != null) {
                fndNxt = fndNxt.left;
            }
            System.out.println("del next R " + fndNxt.key);
            return fndNxt;
        } else {
            return null;
        }

    }

    private void deleteNode(Node n) { // удаление узла
        Node r = nodeReplace(n); // replace
        boolean black2 = ((r == null || !r.isRed) && (!n.isRed)); // оба черные или нет замены
        Node p = n.parent;
        if (r == null) {
            if (n == rootRBTtree) {
                rootRBTtree = null;
            } else {
                if (black2) {
                    fixDelete(n);
                } else {
                    if (S(n) != null) // брата делаем красным
                    {
                        S(n).isRed = true;
                    }
                }
                if (isLeft(n)) {
                    p.left = null;
                } else {
                    p.right = null;
                }
            }
            return;
        }
        if (n.left == null || n.right == null) { // у удаляемого узла один потомок
            if (n == rootRBTtree) {
                n.key = r.key;
                n.left = r.right = null;
            } else {
                if (isLeft(n)) {
                    p.left = r;
                } else {
                    p.right = r;
                }
                r.parent = p;
                if (black2) {
                    fixDelete(r);
                } else {
                    r.isRed = false;
                }
            }
            return;
        }
        swapKey(r, n);
        deleteNode(r);
    }
//    
//    
//    

    private void fixDelete(Node n) {
        if (n == rootRBTtree) {
            return;
        }
        Node S = S(n);
        Node P = n.parent;
        if (S == null) {
            fixDelete(P);
        } else {
            if (S.isRed) { // брат красный
                P.isRed = true;
                S.isRed = false;
                if (isLeft(S)) {
                    rightRotate(P);
                } else {
                    leftRotate(P);
                }
                fixDelete(n);
            } else {
                if (hasRedChild(S)) { // красные потомки у брата
                    if (S.left != null && S.left.isRed) { // левый племянник красный
                        if (isLeft(S)) { // лево лево для брата слева
                            S.left.isRed = S.isRed;
                            S.isRed = P.isRed;
                            rightRotate(P);
                        } else { // право лево для брата справа
                            S.left.isRed = P.isRed;
                            rightRotate(S);
                            leftRotate(P);
                        }
                    } else { // правый племянник красный
                        if (isLeft(S)) { // лево право для брата слева
                            S.right.isRed = P.isRed;
                            leftRotate(S);
                            rightRotate(P);
                        } else { // право право для брата справа
                            S.right.isRed = S.isRed;
                            S.isRed = P.isRed;
                            leftRotate(P);
                        }
                    }
                    P.isRed = false;
                } else {  // 2 черных племянника
                    S.isRed = true;
                    if (!P.isRed) {
                        fixDelete(P);
                    } else {
                        P.isRed = false;
                    }
                }
            }
        }
    }

// вывод дерева
    public void displayTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootRBTtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("......................................................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
                    System.out.print(tmp.key);
                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }

    public void displayTreeColor() {
        Stack globalStack = new Stack();
        globalStack.push(rootRBTtree);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("..............................RBTree...................................");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (globalStack.isEmpty() == false) {
                Node tmp = (Node) globalStack.pop();
                if (tmp != null) {
//                    System.out.print(tmp.isRed);

                    if (tmp.isRed) {
                        System.out.print("\u001B[41m" + "\u001B[37m" + tmp.key + "\u001B[0m");
                    } else {
                        System.out.print("\u001B[44m" + "\u001B[37m" + tmp.key + "\u001B[0m");
                    }

                    localStack.push(tmp.left);
                    localStack.push(tmp.right);
                    if (tmp.left != null || tmp.right != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("  ");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int i = 0; i < nBlanks * 2 - 2; i++) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            nBlanks = nBlanks / 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("......................................................................");
    }

}
