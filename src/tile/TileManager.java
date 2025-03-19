package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    // constructor care initializeaza tilemanager cu gamepanel
    public TileManager(GamePanel gp)
    {
        this.gp=gp;
        tile = new Tile[70];
        mapTileNum= new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldmap.txt",0);
        loadMap("/maps/worldmap02.txt",1);
        loadMap("/maps/worldmap03.txt",2);
    }
    // initializeaza imaginile pentru tile
    public void getTileImage(){
        setup(0,"iarba",false);
        setup(1,"copac1",true);
        setup(2,"pietre",true);
        setup(3,"frunze",true);
        setup(4,"ciuperci",true);
        setup(5,"floricica",true);
        setup(6,"trunchi_copac",true);
        setup(7,"mijlocdr",false);
        setup(8,"minibalta",true);
        setup(9,"coltstgsus",true);
        setup(10,"mijlstg",true);
        setup(11,"coltjosstg",true);
        setup(12,"mijsus",true);
        setup(13,"mijloc",true);
        setup(14,"mijlocjos",true);
        setup(15,"mijlocdr",true);
        setup(16,"mijlstg",true);
        setup(17,"coltdrjos",true);
        setup(18,"grass",false);
        setup(19,"arbust",false);
        setup(20,"floare",true);
        setup(21,"coltdrsus",true);
        setup(22,"portal1",false);
        setup(23,"portral2",false);
        setup(24,"portal3",false);
        setup(25,"portal4",false);
        setup(26,"portal5",false);
        setup(27,"portal6",false);
        setup(28,"copac2",true);
        setup(29,"pamant_gri",false);
        setup(30,"lava",true);
        setup(31,"marginestanga",true);
        setup(32,"marginesus",true);
        setup(33,"despartitor",true);
        setup(34,"despartitor1",true);
        setup(35,"despartitor2",true);
        setup(36,"despartitor3",true);
        setup(37,"coltlavastsus",true);
        setup(38,"marginedreapta",true);
        setup(39,"marginejos",true);
        setup(40,"coltlavastjos",true);
        setup(41,"coltlavadrsus",true);
        setup(42,"coltlavadrjos",true);
        setup(43,"pamant_negru",false);
        setup(44,"coltdespartitordrsus",false);
        setup(45,"coltdespartitordrjos",false);
        setup(46,"coltdespartitorstsus",false);
        setup(47,"coltdespartitorstjos",false);
        setup(48,"baltastsus",true);
        setup(49,"baltasus",true);
        setup(50,"baltadrsus",true);
        setup(51,"baltadr",true);
        setup(52,"baltadrjos",true);
        setup(53,"baltajos",true);
        setup(54,"baltastjos",true);
        setup(55,"baltast",true);
        setup(56,"pamant_gri_textura",false);
        setup(57,"pamant_gri_groapa",false);
        setup(58,"tepi",true);
        setup(59,"p1",false);
        setup(60,"p2",false);
        setup(61,"p3",false);
        setup(62,"p4",false);
        setup(63,"p5",false);
        setup(64,"p6",false);


    }
    // initializeaza o placa la un anumit index cu imaginea si optiunea de coliziune
    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/"+imagePath+".png")));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    // incarca harta de placi dintr-un fisier de text specificat si o stocheaza in mapTileNum
    public void loadMap(String filePath,int map){

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;

            while(col<gp.maxWorldCol && row<gp.maxWorldRow)
            {
                String line = br.readLine();
                while( col<gp.maxWorldCol)
                {
                    String number[]=line.split(" ");
                    int num=Integer.parseInt(number[col]);

                    mapTileNum[map][col][row]=num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){
        }
    }
    // deseneaza placile pe ecran, folosind coordonatele de ecran si coordonatele jucatorului
    public void draw(Graphics2D g2)
    {
        int worldCol=0;
        int worldRow=0;

        while(worldCol < gp.maxWorldCol && worldRow <gp.maxWorldRow)
        {
            int tileNum= mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX -  gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // actualizeaza harta de placi in functie de nivelul jucatorului
    public void update() {
        if(gp.player.nivel==1) {
            loadMap("/maps/worldmap.txt", 0);
           // for(int i=0;i<gp.obj[0].length;i++){
             //   gp.obj[0][i] = null;
            //}
        }
        if(gp.player.nivel==2)
            loadMap("/maps/worldmap02.txt",0);
        if(gp.player.nivel==3)
            loadMap("/maps/worldmap03.txt",0);
    }
}
