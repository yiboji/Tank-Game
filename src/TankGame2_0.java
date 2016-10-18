package TankGame2_0;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.sun.net.ssl.SSLContext;
import java.util.*;

public class TankGame2_0 extends JFrame implements ActionListener{

	MyPanel mp = null;
	MyStartPanel msp = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankGame2_0 mtg = new TankGame2_0();
	}
	public TankGame2_0()
	{
		msp = new MyStartPanel();
		Thread tmsp = new Thread(msp);
		tmsp.start();
		this.add(msp);
		addOption();
		this.setSize(890, 700);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void addOption()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("GAME");
		fileMenu.setMnemonic('G');
		JMenu optionMenu = new JMenu("OPTIONS");
		optionMenu.setMnemonic('O');
		JMenu about = new JMenu("ABOUT");
		about.setMnemonic('A');
		JMenuItem newItem = new JMenuItem("New Game·");
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem closeItem = new JMenuItem("Close");
		
		JMenu mode = new JMenu("Mode");
		JMenuItem signal = new JMenuItem("Easy");
		JMenuItem complex = new JMenuItem("Hard");
		
		JMenuItem speed = new JMenuItem("Speed");
		JMenuItem mission = new JMenuItem("Mission");
		
		JMenuItem gameExplain = new JMenuItem("Help");
		JMenuItem aboutAuthor = new JMenuItem("About");

		newItem.addActionListener(this);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		closeItem.addActionListener(this);
		signal.addActionListener(this);
		complex.addActionListener(this);
		gameExplain.addActionListener(this);
		aboutAuthor.addActionListener(this);

		newItem.setActionCommand("new");
		openItem.setActionCommand("open");
		saveItem.setActionCommand("save");
		closeItem.setActionCommand("close");
		signal.setActionCommand("signal");
		complex.setActionCommand("complex");
		gameExplain.setActionCommand("explain");
		aboutAuthor.setActionCommand("about");
		
		this.setJMenuBar(menuBar);

		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		menuBar.add(about);

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);
		
		optionMenu.add(mode);
		optionMenu.add(speed);
		optionMenu.add(mission);
		mode.add(signal);
		mode.add(complex);
		about.add(gameExplain);
		about.add(aboutAuthor);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("new"))
		{
			this.remove(msp);
			if(mp!=null)
			{
				this.remove(mp);
				Recorder.setEnNum(15);
				Recorder.setMyLife(3);
				Recorder.setSum(0);
			}

			
			mp = new MyPanel();
			Thread t = new Thread(mp);
			t.start();


			this.add(mp);
			this.addKeyListener(mp);
			this.setVisible(true);
		}
	}
}

class MyStartPanel extends JPanel implements Runnable
{
	int times = 0;
	Image loveImage = null;
	
	public void paint(Graphics g)
	{
		loveImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/love.jpg"));
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		
		g.setColor(Color.BLUE);
		Font myFont = new Font("ËÎÌå",Font.BOLD,60);
		g.setFont(myFont);
		times++;
		g.drawImage(loveImage, 250, 150, 300, 300, this);
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.repaint();
			}	
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable 
{
	Hero hero = null;
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	Vector<Bomb> bombs = new Vector<Bomb>();
	
	int enSize = 15;
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	Image image4 = null;
	public MyPanel()
	{
		hero = new Hero(400,570,0,6);
		hero.setEts(ets);
		for(int i=0; i<enSize; i++)
		{
			EnemyTank et = new EnemyTank((i+1)*50+10,10,1,5);
			et.setEts(this.ets);

			
			et.setDirect(1);
			ets.add(et);
			Thread th = new Thread(et);
			th.start();//Æô¶¯µÐÈËÌ¹¿Ë Ïß³Ì
		}
		try {
			image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
			image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
			image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
			image4 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		showInfo(g);

		if(hero.isLive==true)
		{
			this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
		}

		for(int i=0; i<bombs.size();i++)
		{
			Bomb bb = bombs.get(i);
			if(bb.life>6)
				g.drawImage(image1, bb.x, bb.y, 30,30,this);
			else if(bb.life>3)
				g.drawImage(image2, bb.x, bb.y, 30,30,this);
			else 
				g.drawImage(image3, bb.x, bb.y, 30,30,this);
			bb.liftDown();
			if(bb.life==0)
			{
				bombs.remove(bb);
			}
		}
		for(int i=0; i<ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			if(et.isLive)
			{
				this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getDirect(), ets.get(i).getColor());	
			}
			for(int j=0; j<et.ss.size(); j++)
			{
				Shot etShot = et.ss.get(j);
				if(et.s!=null&&et.s.isLive==true)
				{
					g.setColor(Color.cyan);
					g.draw3DRect(etShot.x, etShot.y, 1, 1, false);
				}
				if(etShot.isLive==false)
				{
					hero.ss.remove(etShot);
				}
			}
			
		}
		for(int i=0; i<hero.ss.size();i++)
		{
			Shot myShot = hero.ss.get(i);
			
			if(hero.s!=null&&hero.s.isLive==true)
			{
				g.setColor(Color.yellow);
				g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
			}
			if(myShot.isLive==false)
			{
				hero.ss.remove(myShot);
			}
		}
	}
	public void hitTank(Shot s,Tank et)
	{
		if(s.x>=et.x&&s.x<=et.x+30&&s.y>=et.y&&s.y<=et.y+30)
		{
			s.isLive = false;
			et.isLive = false;

			Bomb bb = new Bomb(et.x, et.y); 
			bombs.add(bb);	
		}
	}
	public void hitEnemyTank()
	{
		for(int i=0; i<hero.ss.size();i++)
		{
			Shot myShot = hero.ss.get(i);
			if(myShot.isLive==true)
			{
				for(int j=0; j<ets.size();j++)
				{
					EnemyTank et = ets.get(j);
					if(et.isLive)
					{
						this.hitTank(myShot, et);
						if(et.isLive==false)
						{
							Recorder.EnNumDown();
							Recorder.sumAdd();
						}
					}
				}
			}
		}
	}
	public void hitMe()
	{
		for(int i=0; i<this.ets.size();i++)
		{
			EnemyTank et = ets.get(i);
			for(int j=0; j<et.ss.size();j++)
			{
				Shot enemyShot = et.ss.get(j);
				if(enemyShot.isLive==true)
				{
					if(hero.isLive)
					{
						this.hitTank(enemyShot, hero);
						if(hero.isLive==false)
						{
							Recorder.MyLifeDown();
							Recorder.sumDowm();
							hero.x = 400;
							hero.y = 570;
							hero.direct = 0;
							hero.isLive = true;
						}
					}
						
				}
			}
		}
	}
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.yellow);
			break;
		case 1:
			g.setColor(Color.cyan);
			break;
		}
		switch(direct)
		{
		case 0:
			g.fill3DRect(x,y, 5, 30,false);
			g.fill3DRect(x+25, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 20, 20,false);
			g.fillOval(x+7, y+7, 15, 15);
			g.drawLine(x+14, y+12, x+14, y);
			break;
		case 1:
			g.fill3DRect(x,y, 5, 30,false);
			g.fill3DRect(x+25, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 20, 20,false);
			g.fillOval(x+7, y+7, 15, 15);
			g.drawLine(x+14, y+30-12, x+14, y+30);
			break;
		case 2:
			g.fill3DRect(x,y, 30, 5,false);
			g.fill3DRect(x, y+25, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 20,false);
			g.fillOval(x+7, y+7, 15, 15);
			g.drawLine(x, y+14, x+12, y+14);
			break;
		case 3:
			g.fill3DRect(x,y, 30, 5,false);
			g.fill3DRect(x, y+25, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 20,false);
			g.fillOval(x+7, y+7, 15, 15);
			g.drawLine(x+30-12, y+14, x+30, y+14);
			break;
		}

	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			if(hero.getDirect()==0)
				this.hero.moveUp();
			hero.setDirect(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if(hero.getDirect()==1)			
				this.hero.moveDown();
			hero.setDirect(1);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(hero.getDirect()==2)		
				this.hero.moveLeft();
			hero.setDirect(2);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(hero.getDirect()==3)	
				this.hero.moveRight();
			hero.setDirect(3);
		}
		if(e.getKeyChar()==KeyEvent.VK_SPACE)
		{
			if(this.hero.ss.size()<=4)
				this.hero.shotEnemy();
		}
		
	}
	public boolean enermyAllDie(EnemyTank et)
	{
		for(int j=0; j<et.ss.size();j++)
		{
			Shot s = et.ss.get(j);
			if(s.isLive==true)
			{
				return false;
			}
		}
		return true;	
	}
	public void showInfo(Graphics g)
	{
		this.drawTank(810, 20, g, 0, 1);
		this.drawTank(810, 80, g, 0, 0);
		g.setColor(Color.black);
		Font myFont = new Font("ËÎÌå",Font.BOLD,30);
		g.setFont(myFont);
		g.drawString(Recorder.getEnNum()+"", 845, 45);
		g.drawString(Recorder.getMyLife()+"", 845, 105);
		g.drawString("score", 800, 165);
		g.drawString(Recorder.getSum()+"",820,210);
	}
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.hitEnemyTank(); 
			this.hitMe();
			for(int i=0; i<this.ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et.isLive==false)
				{
					if(enermyAllDie(et))
					{
						ets.remove(et);
					}
				}
			}		
			this.repaint();
			if(hero.isLive==false)
			{
				break;
			}
		}
	}
}
