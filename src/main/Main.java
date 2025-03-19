package main;

import javax.swing.JFrame;

// clasa principala care contine metoda main
public class Main
{
    public static void main(String[] args)
    {
        // Crearea ferestrei jocului
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Inchide aplicatia la inchiderea ferestrei
        window.setResizable(false); // Dezactiveaza redimensionarea ferestrei
        window.setTitle("The Secrets of a Wizard's World"); // Seteaza titlul ferestrei

        // Crearea panoului de joc
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // Adauga panoul de joc la fereastra

        window.pack(); // Face fereastra sa se potriveasca dimensiunilor panoului de joc

        window.setLocationRelativeTo(null); // Centreaza fereastra pe ecran
        window.setVisible(true); // Face fereastra vizibila

        // Seteaza jocul si porneste thread-ul de joc
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
