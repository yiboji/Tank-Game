package TankGame2_0;
import java.util.Vector;

class Tank
{
	int x = 0;
	int y = 0;
	int direct = 0;
	int speed = 1;
	int color = 0;
	boolean isLive = true;
	Vector<Shot> ss = new Vector<Shot>();
	
	Shot s =null;
	public void shotEnemy()
	{

		switch(this.direct)
		{
		case 0:
			s = new Shot(x+14, y,0,this.color,2);
			ss.add(s);
			break;
		case 1:
			s = new Shot(x+14,y+30,1,this.color,2);
			ss.add(s);
			break;
		case 2:
			s = new Shot(x,y+14,2,this.color,2);
			ss.add(s);
			break;
		case 3:
			s = new Shot(x+30,y+14,3,this.color,2);
			ss.add(s);
			break;
		}
		Thread t = new Thread(s);
		t.start();
		
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Tank() {
		this.x = x;
		this.y = y;
	}
	public void moveUp()
	{
		if(this.isTouchOther()==false)
		{
			y -= speed;
			if(y<0)
				y = 0;
		}
		else if(this.isTouchOther()==true)
		{
			this.direct = 2;
		}

	}
	public void moveDown()
	{
		if(this.isTouchOther()==false)
		{
			y += speed;
			if(y>600-30)
			{
				y = 600-30;
			}
		}
		else if(this.isTouchOther()==true)
		{
			this.direct = 3;
		}


	}
	public void moveLeft()
	{
		if(this.isTouchOther()==false)
		{
			x -= speed;
			if(x<0)
				x = 0;
		}
		else if(this.isTouchOther()==true)
		{
			this.direct = 1;
		}


	}
	public void moveRight()
	{
		if(this.isTouchOther()==false)
		{
			x += speed;
			if(x>800-30)
				x = 800-30;	
		}
		else if(this.isTouchOther()==true)
		{
			this.direct = 0;
		}

	}
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets = vv;
		
	}
	public boolean isTouchOther()
	{
		for(int i=0; i<ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			if(et != this)
			{
				switch(this.direct)
				{
				case 0:
					if(((this.x>=et.x&&this.x<=et.x+30)||(this.x+30>=et.x&&this.x+30<=et.x+30))&&(this.y<=et.y+30&&this.y>=et.y))
					{
						return true;
					}
					break;
				case 1:
					if(((this.x>=et.x&&this.x<=et.x+30)||(this.x+30>=et.x&&this.x+30<=et.x+30))&&(this.y+30<=et.y+30&&this.y+30>=et.y))
					{
						return true;
					}
					break;
				case 2:
					if(((this.y>=et.y&&this.y<=et.y+30)||(this.y+30>=et.y&&this.y+30<=et.y+30))&&(this.x<=et.x+30&&this.x>=et.x))
					{
						return true;
					}
					break;
				case 3:
					if(((this.y>=et.y&&this.y<=et.y+30)||(this.y+30>=et.y&&this.y+30<=et.y+30))&&(this.x+30<=et.x+30&&this.x+30>=et.x))
					{
						return true;
					}
					break;
				}
			}
		}
		return false;
	}
}
class Hero extends Tank
{
	public Hero(int x,int y,int color,int speed)
	{
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.color = color;
	}
}
class EnemyTank extends Tank implements Runnable
{
	int cnt = 0;

	public EnemyTank(int x, int y,int color,int speed)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.speed = speed;
	}

	public void run() {
		
		while(true)
		{
			try {
				Thread.sleep(500);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(this.direct)
			{
			case 0:
				moveUp();
				break;
			case 1:
				moveDown();
				break;
			case 2:
				moveLeft();
				break;
			case 3:
				moveRight();
			 break;
			}
			
			if(++cnt==5)
			{
				this.shotEnemy();	
			}
			else if(cnt==10)
			{
				cnt = 0;
				this.direct=(int)(Math.random()*4);

			}
			
			if(this.isLive==false)
			{
				break;
			}
		}
		
	}
}
class Shot implements Runnable
{
	int x,y,direct,speed,color;
	boolean isLive = true;
	public Shot(int x, int y,int direct,int color,int speed)
	{
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.color = color;
		this.speed = speed;
	}

	public void run() {
		
		while(true)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(direct)
			{
			case 0:
				y-= speed;break;
			case 1:
				y += speed;break;
			case 2:
				x -= speed;break;
			case 3:
				x += speed;break;
			}
			if(((x<=0||x>800))||((y<=0||y>600)))
			{
				this.isLive = false;	
				break;
			}
		}
		
	}
}
class Bomb
{
	int x,y;
	int life = 9;
	boolean isLive = true;
	public Bomb(int x, int y)
	{
		this.x = x; 
		this.y = y;
	}
	public void liftDown()
	{
		if(life>0)
			life--;
		else 
			isLive = false;
	}
}
class Recorder
{
	private static int enNum = 15;
	private static int myLife = 3;
	private static int sum = 0;
	
	public static int getSum() {
		return sum;
	}
	public static void setSum(int sum) {
		Recorder.sum = sum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public static void EnNumDown()
	{
		enNum--;
	}
	public static void MyLifeDown()
	{
		myLife--;
	}
	public static void sumAdd()
	{
		sum += 10;
	}
	public static void sumDowm()
	{
		sum -= 20;
	}
	
}
