package main;

import AI.PathFinder;
import entity.Entity;
import entity.Player;
import object.Score;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel  extends JPanel implements Runnable{

    // setări ecran
    final int originalTileSize = 16; // dimensiunea inițială a dalei: 16x16 pixeli
    final int scale = 3;
    public final int tileSize = originalTileSize*scale; // dimensiunea dalei scalate: 46x48 pixeli
    public final int maxScreenCol = 16; // numărul maxim de coloane pe ecran
    public final int maxScreenRow = 12; // numărul maxim de rânduri pe ecran
    public final int screenWidth = tileSize* maxScreenCol; // lățimea ecranului în pixeli: 768 pixeli
    public final int screenHeight = tileSize*maxScreenRow; // înălțimea ecranului în pixeli: 576 pixeli

    // setări lume
    public final int maxWorldCol=43; // numărul maxim de coloane în lume
    public final int maxWorldRow=30; // numărul maxim de rânduri în lume
    public final int maxMap=10; // numărul maxim de hărți
    public int currentMap=0; // harta curentă
    public int score; // scorul
    public Scor scor; // obiectul de scor

    // FPS (cadre pe secundă)
    int FPS = 60; // numărul de cadre pe secundă dorit
    public TileManager tileM = new TileManager(this); // managerul de dale
    public KeyHandler keyH = new KeyHandler(this); // gestionarul de taste
    Sound se = new Sound(); // sunete
    Sound music = new Sound(); // muzică
    public CollisionChecker  cChecker = new CollisionChecker(this); // verificatorul de coliziuni
    public AssetSetter aSetter = new AssetSetter(this); // inițializatorul de obiecte
    public ui ui = new ui(this); // interfața utilizatorului
    public EventHandler eHandler = new EventHandler(this); // gestionarul de evenimente
    public PathFinder pFinder = new PathFinder(this); // găsitorul de căi
    Thread gameThread; // firul de execuție al jocului

    // entități și obiecte
    public Player player = new Player(this,keyH); // jucătorul
    public Entity[][] obj = new Entity[maxMap][50]; // obiectele
    public Entity[][] monster = new Entity[maxMap][20]; // monștrii
    ArrayList<Entity> entityList = new ArrayList<>(); // lista de entități

    // starea jocului
    public int gameState ; // starea jocului curent
    public final int titleState=0; // starea de titlu
    public final int playState=1; // starea de joc
    public final int pauseState=2; // starea de pauză
    public final int gameOverState=3; // starea de game over
    public final int loadGame=4; // încărcarea jocului
    public final int finishGame=5; // finalizarea jocului
    public Save save; // salvarea jocului
    int c,h,niv,ix,iy;

    public GamePanel(){

        save=new Save();
        save.bazaDeDate();
        scor =new Scor();
        scor.BazaDeDate();

        this.setPreferredSize(new Dimension(screenWidth,screenHeight)); // setarea dimensiunii panoului
        this.setBackground(Color.BLACK); // setarea culorii de fundal
        this.setDoubleBuffered(true); // activarea dublului tampon
        this.addKeyListener(keyH); // adăugarea gestionarului de taste
        this.setFocusable(true); // setarea focusului

    }

    // inițializarea jocului
    public void setupGame(){
        aSetter.setObject(); // inițializarea obiectelor
        aSetter.setMonster(); // inițializarea monștrilor
        playMusic(0); // redarea muzicii
        gameState = titleState; // setarea stării la starea de titlu
    }

    // reîncercarea jocului
    public void retry(){
        player.setDefaultPositions(); // restabilirea pozițiilor implicite ale jucătorului
        player.restoreLife(); // restaurarea vieților jucătorului
        aSetter.setMonster(); // inițializarea monștrilor
    }

    // restartarea jocului
    public void restart(){
        player.setDefaultValues(); // restabilirea valorilor implicite ale jucătorului
        player.setDefaultPositions(); // restabilirea pozițiilor implicite ale jucătorului
        player.restoreLife(); // restaurarea vieților jucătorului
        player.hasCoin=0; // resetarea numărului de monede
        aSetter.setObject(); // inițializarea obiectelor
        aSetter.setMonster(); // inițializarea monștrilor
    }

    // pornirea firului de execuție al jocului
    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // intervalul de desenare: 0.01666 secunde
        double nextDrawTime = System.nanoTime()+drawInterval;
        while(gameThread!=null){
            // actualizare: se actualizează informațiile cum ar fi pozițiile caracterelor
            update();
            // desenare: se desenează ecranul cu informațiile actualizate
            repaint();
            try {
                double remainingTime = nextDrawTime-System.nanoTime();
                remainingTime=remainingTime/1000000;

                if(remainingTime < 0 ){
                    remainingTime=0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime +=drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // actualizarea jocului
    public void update()
    {
        if( gameState == playState) {
            player.update(); // actualizarea jucătorului
            for (int i=0;i<monster[1].length;i++) {
                // actualizarea monștrilor
                if(monster[currentMap][i]!=null){
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying)
                        monster[currentMap][i].updateMonster();
                    if(!monster[currentMap][i].alive)
                        monster[currentMap][i]=null;
                }
            }
        }
        // dacă jocul este în stare de pauză, nu se întâmplă nimic în metoda de actualizare
        if(gameState == pauseState){
        }
    }

    // metoda de desenare a componentei
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        long drawStart=0;
        if(keyH.showDebugText == true){
            drawStart=System.nanoTime();
        }
        // ecranul de titlu
        if (gameState == titleState)
        {
            ui.draw(g2); // se desenează ecranul de titlu
        }
        // alte ecrane
        else
        {
            // dale
            tileM.draw(g2); // se desenează dalele

            // ADAUGARE ENTITATI IN LISTA
            entityList.add(player); // se adaugă jucătorul în lista de entități
            for(int i = 0; i<obj[1].length;i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]); // se adaugă obiectele în lista de entități
                }
            }
            for(int i = 0; i<monster[1].length;i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]); // se adaugă monștrii în lista de entități
                }
            }

            // SORTARE
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DESENARE ENTITATI
            for(Entity entity : entityList){
                entity.draw(g2); // se desenează entitățile
            }
            // GOLIRE LISTA ENTITATI
            entityList.clear(); // se golește lista de entități
            // UI
            ui.draw(g2); // se desenează interfața utilizatorului
        }
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd- drawStart;

            g2.setFont(new Font("Times New Roman",Font.PLAIN,20));
            g2.setColor(Color.white);
            int x=10;int y=400;
            int lineHeight=20;

            g2.drawString("WorldX: "+player.worldX,x,y); y+=lineHeight;
            g2.drawString("WorldY: "+player.worldY,x,y);y+=lineHeight;
            g2.drawString("Col: "+(player.worldX+player.solidArea.x)/tileSize,x,y);y+=lineHeight;
            g2.drawString("Row: "+(player.worldY+player.solidArea.y)/tileSize,x,y);y+=lineHeight;
            g2.drawString("Draw time: " +passed,x,y);
        }
        g2.dispose();
    }

    // metoda pentru redarea muzicii
    public void playMusic(int i)
    {
        music.setFile(i); // se setează fișierul audio
        music.play(); // se redă muzica
        music.loop(); // se activează bucla de redare
    }

    // metoda pentru oprirea muzicii
    public void stopMusic()
    {
        music.stop(); // se oprește muzica
    }

    // metoda pentru redarea efectelor sonore
    public void playSE(int i)
    {
        se.setFile(i); // se setează fișierul audio pentru efectul sonor
        se.play(); // se redă efectul sonor
    }

    // metoda pentru obținerea datelor din baza de date
    public void getDateDB() {
        List<String[]> data = Save.getData(); // se obține lista de date din baza de date
        // Se verifică dacă există date pentru a evita ArrayIndexOutOfBoundsException
        if (data.isEmpty()) {
            System.out.println("Nu există date disponibile în baza de date.");
            return;
        }
        // Se presupune că se dorește obținerea ultimei înregistrări din listă
        String[] lastEntry = data.get(data.size() - 1);
        System.out.println("Ultima înregistrare: " + Arrays.toString(lastEntry)); // Se afișează ultima înregistrare
        if (lastEntry.length != 5) {
            System.out.println("Înregistrarea de date nu are numărul de coloane așteptat.");
            return;
        }
        c = Integer.parseInt(lastEntry[0]);
        h = Integer.parseInt(lastEntry[1]);
        niv=Integer.parseInt(lastEntry[2]);
        ix =Integer.parseInt(lastEntry[3]);
        iy =Integer.parseInt(lastEntry[4]);
    }

    // metoda pentru încheierea jocului
    public void endGame() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 50));

        JButton button = new JButton("Introdu numele");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nume = textField.getText();
                scor.insertData(nume, score);
                System.out.println("Scorul a fost salvat în baza de date");
            }
        });

        // Se setează layout-ul Border pentru a poziționa componentele în partea de jos a panoului
        this.setLayout(new BorderLayout());

        // Se adaugă componentele în partea de jos a panoului
        this.add(textField, BorderLayout.SOUTH);
        this.add(button, BorderLayout.SOUTH);

        // Se asigură că panoul este redesenat pentru a afișa noile componente
        this.revalidate();
        this.repaint();
    }

    // metoda pentru afișarea conținutului bazei de date
    public void showDatabaseContents() {
        List<String[]> data = Scor.getData(); // se obține lista de date din baza de date

        // Numele coloanelor pentru tabel
        String[] columnNames = {"Nume", "Scor"};

        // Se convertește lista într-un array 2D pentru JTable
        String[][] dataArray = new String[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }

        // Se creează tabela cu datele
        JTable table = new JTable(dataArray, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Se creează un nou panou pentru a ține tabela
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Se creează un nou cadru pentru a afișa panoul
        JFrame frame = new JFrame("Conținutul bazei de date");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 700);
        frame.add(panel);
        frame.setVisible(true);
    }
}

