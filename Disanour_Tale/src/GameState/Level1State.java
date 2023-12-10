//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package GameState;

import Audio.AudioPlayer;
import Entity.Arachnik;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Slugger;
import TileMap.Background;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class Level1State extends GameState {
    private TileMap tileMap;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private HUD hud;
    private AudioPlayer bgMusic;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        this.init();
    }

    public void init() {
        this.tileMap = new TileMap(30);
        this.tileMap.loadTiles(new File("D:/Assignments/ProjectOOP/Disanour_Tale/src/Resources/Tilesets/grasstileset.gif"));
        this.tileMap.loadMap(new File("D:/Assignments/ProjectOOP/Disanour_Tale/src/Resources/Maps/level1-1.map"));
        this.tileMap.setPosition(0.0, 0.0);
        this.tileMap.setTween(1.0);
        this.bg = new Background(new File("D:/Assignments/ProjectOOP/Disanour_Tale/src/Resources/Backgrounds/grassbg1.gif"), 0.1);
        this.player = new Player(this.tileMap);
        this.player.setPosition(100.0, 100.0);
        this.populateEnemies();
        this.explosions = new ArrayList();
        this.hud = new HUD(this.player);
        this.bgMusic = new AudioPlayer(new File("D:/Assignments/ProjectOOP/Disanour_Tale/src/Resources/Music/level1-1.wav"));
    }

    private void populateEnemies() {
        this.enemies = new ArrayList();
        Point[] points1 = new Point[]{new Point(200, 100), new Point(860, 200), new Point(960, 200), new Point(1525, 200), new Point(1680, 200), new Point(1800, 200)};

        for(int i = 0; i < points1.length; ++i) {
            Slugger s = new Slugger(this.tileMap);
            s.setPosition((double)points1[i].x, (double)points1[i].y);
            this.enemies.add(s);
        }

        Point[] points2 = new Point[]{new Point(400, 50), new Point(860, 200), new Point(960, 200), new Point(1525, 200), new Point(1680, 200), new Point(1800, 200)};

        for(int i = 0; i < points2.length; ++i) {
            Arachnik a = new Arachnik(this.tileMap);
            a.setPosition((double)points2[i].x, (double)points2[i].y);
            this.enemies.add(a);
        }

    }

    public void update() {
        if (this.player.isDead()) {
            this.gsm.setState(0);
        }

        this.player.update();
        this.tileMap.setPosition(160.0 - (double)this.player.getx(), 120.0 - (double)this.player.gety());
        this.bg.setPosition((double)this.tileMap.getx(), (double)this.tileMap.gety());
        this.player.checkAttack(this.enemies);

        int i;
        for(i = 0; i < this.enemies.size(); ++i) {
            Enemy e = (Enemy)this.enemies.get(i);
            e.update();
            if (e.isDead()) {
                this.enemies.remove(i);
                --i;
                this.explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }

        for(i = 0; i < this.explosions.size(); ++i) {
            ((Explosion)this.explosions.get(i)).update();
            if (((Explosion)this.explosions.get(i)).shouldRemove()) {
                this.explosions.remove(i);
                --i;
            }
        }

    }

    public void draw(Graphics2D g) {
        this.bg.draw(g);
        this.tileMap.draw(g);
        this.player.draw(g);

        int i;
        for(i = 0; i < this.enemies.size(); ++i) {
            ((Enemy)this.enemies.get(i)).draw(g);
        }

        for(i = 0; i < this.explosions.size(); ++i) {
            ((Explosion)this.explosions.get(i)).setMapPosition(this.tileMap.getx(), this.tileMap.gety());
            ((Explosion)this.explosions.get(i)).draw(g);
        }

        this.hud.draw(g);
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }

}