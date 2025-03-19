package AI;

import main.GamePanel;

import java.util.ArrayList;

import entity.Entity;

public class PathFinder {

    GamePanel gp;
    Node[][] node; // matricea de noduri pentru harta
    ArrayList<Node> openList = new ArrayList<>(); // lista nodurilor deschise
    public ArrayList<Node> pathList = new ArrayList<>(); // lista nodurilor pentru cale
    Node startNode, goalNode, currentNode; // nodurile de start, tinta si cel curent
    boolean goalReached = false; // indica daca tinta a fost atinsa

    int step = 0; // contor pentru pasi

    public PathFinder(GamePanel gp) {
        this.gp = gp;

        instantiateNodes(); // initializeaza nodurile
    }

    public void instantiateNodes() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow]; // creeaza matricea de noduri

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row); // creeaza un nou nod
            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false; // reseteaza starea nodului deschis
            node[col][row].checked = false; // reseteaza starea nodului verificat
            node[col][row].solid = false; // reseteaza starea nodului solid

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear(); // goleste lista de noduri deschise
        pathList.clear(); // goleste lista de noduri pentru cale
        goalReached = false; // reseteaza starea tintei atinse
        step = 0; // reseteaza contorul de pasi
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {

        resetNodes(); // reseteaza nodurile
        startNode = node[startCol][startRow]; // seteaza nodul de start
        currentNode = startNode; // seteaza nodul curent
        goalNode = node[goalCol][goalRow]; // seteaza nodul tinta
        openList.add(currentNode); // adauga nodul curent in lista de noduri deschise

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if (gp.tileM.tile[tileNum].collision) {
                node[col][row].solid = true; // marcheaza nodul ca solid
            }

            getCost(node[col][row]); // calculeaza costul pentru fiecare nod

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance; // calculeaza costul de deplasare de la start

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance; // calculeaza costul estimat pana la tinta

        node.fCost = node.gCost + node.hCost; // calculeaza costul total
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true; // marcheaza nodul curent ca verificat
            openList.remove(currentNode); // elimina nodul curent din lista de noduri deschise

            if (row - 1 >= 0) {
                openNode(node[col][row - 1]); // deschide nodul nord
            }
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]); // deschide nodul vest
            }
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]); // deschide nodul sud
            }
            if (row + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]); // deschide nodul est
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i; // gaseste nodul cu cel mai mic cost total
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i; // in caz de egalitate, alege nodul cu costul de deplasare mai mic
                    }
                }
            }

            if (openList.isEmpty()) {
                break; // daca lista de noduri deschise este goala, se opreste cautarea
            }

            currentNode = openList.get(bestNodeIndex); // seteaza nodul curent la nodul cu cel mai mic cost
            if (currentNode == goalNode) {
                goalReached = true; // daca nodul curent este tinta, cautarea s-a terminat
                trackThePath(); // urmareste calea gasita
            }
            step++;
        }

        return goalReached; // returneaza daca tinta a fost atinsa
    }

    private void trackThePath() {

        Node current = goalNode;

        while (current != startNode) {
            pathList.addFirst(current); // adauga nodul curent la calea gasita
            current = current.parent; // trecerea la nodul parinte
        }
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true; // marcheaza nodul ca deschis
            node.parent = currentNode; // seteaza nodul parinte la nodul curent
            openList.add(node); // adauga nodul in lista de noduri deschise
        }
    }
}
