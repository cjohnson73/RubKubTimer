package src;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Main 
{
	static int play = 0;//-1 = exit game//0 = waitng// 1 = insnpecting// 2= tmiing//3 = settings// 4= end 
	static int tsize;
	static int w;
	static int h;
	static int score;
	static int minH;
	static int minW;
	static long min;
	static long mil;
	static long lastMil;
	static long sec;
	static long startTime;
	static int nK = 13;
	static boolean keyRd[] = new boolean[nK];
	static boolean good[] = new boolean[nK];
	static boolean DNF;
	static boolean pt;
	static boolean insp;
	static boolean delete;
	static int fontSize;
	static Rectangle2D r;
	static String title = "RubKubTimer";
	static String scramble;
	static ArrayList<String> scrs = new ArrayList<>();
	static JFrame frame;
	static Graphics g;
	static BufferStrategy bs;
	static Canvas canvas;
	static KeyManager keyList = new KeyManager();
	static Font font;
	static Font font2;
	
	public static void main(String args[]) throws InterruptedException
	{
		w = 800;
		h = 600;
		init();
		createDisplay();
		while(play!=-1)
		{
			tick();
			render();
		}
		frame.dispose();
	}
	public static void init()
	{
		for(int i = 0; i<nK; i++)
		{
			keyRd[i] = true;
			good[i] = false;
		}
		minH = 500;
		minW = 500;
		min = 0;
		mil = 0;
		sec = 0;
		DNF = false;
		pt = false;
		delete = true;
		insp = true;
		lastMil = 0;
		fontSize = 64;
		scramble = "";
		font = new Font("Arial", Font.PLAIN, fontSize);
		font2 = new Font("Arial", Font.PLAIN, fontSize/2);
	}
	public static void tick()
	{
		for(int i = 0; i<nK; i++)
		{
			if(!keyRd[i])
				keyRd[i] = keyList.keyR(i);
		}
		if(keyList.keyPressed()==5 && keyRd[5])
			good[5] = true;
		if(good[5] && keyList.keyR(5))
		{
			play = -1;
			good[5] = false;
		}
		if(keyList.keyPressed()==6 && keyRd[6])
			good[6] = true;
		if(good[6] && keyList.keyR(6))
		{
			scramble = "";
			good[6] = false;
		}
		if(keyList.keyPressed()==7 && keyRd[7])
			good[7] = true;
		if(good[7] && keyList.keyR(7))
		{
			insp = !insp;
			good[7] = false;
		}
		if(play==0)
		{
			waiting();
			if(delete)
			{
				min = 0;
				mil = 0;
				sec = 0;
			}
		}
		else if(play==1)
		{
			inspecting();
		}
		else if(play==2)
		{
			timing();
		}
		//settings on side
		w = frame.getWidth();
		h = frame.getHeight();
		tsize = w<h?w/56:h/35;
		if(w<minW && h<minH)
		{
			w = minW;
			h = minH;
			frame.setSize(new Dimension(w, h));
			render();
		}
		else if(h<minH)
		{
			h = minH;
			frame.setSize(new Dimension(w, h));
			render();
		}
		else if(w<minW)
		{
			w = minW;
			frame.setSize(new Dimension(w, h));
			render();
		}
	}
	public static void render()
	{
		bs = canvas.getBufferStrategy();
		if(bs == null)
		{
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0,  w,  h);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0,  w,  h);
		
		g.setColor(Color.WHITE);
		String timeS;
		if(play==1)
		{
			timeS = DNF?"DNF":(pt?"+2":""+sec);
			if(good[4])
				g.setColor(Color.GREEN);
			else if(keyList.keyPressed()==4)
				g.setColor(Color.RED);
		}
		else
		{
			if(play==0)
			{
				if(!delete)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.GRAY);
				String del = "Delete  ";
				String dn = "DNF  ";
				String pl = "+2";
				g.setFont(font2);
				r = font2.getStringBounds(del, new FontRenderContext(null, true, true));
				Rectangle2D r2 = font2.getStringBounds(dn, new FontRenderContext(null, true, true));
				Rectangle2D r3 = font2.getStringBounds(pl, new FontRenderContext(null, true, true));
				g.drawString(del, (int) (w/2-(r.getWidth()+r2.getWidth()+r3.getWidth())/2), 3*fontSize);
				g.setColor(Color.WHITE);
				g.drawString(dn, (int) (w/2-(-r.getWidth()+r2.getWidth()+r3.getWidth())/2), 3*fontSize);
				g.drawString(pl, (int) (w/2-(-r.getWidth()-r2.getWidth()+r3.getWidth())/2), 3*fontSize);
				if(good[4])
					g.setColor(Color.GREEN);
			}
			timeS = (min>0?min+":":"")+(sec<10?"0":"")+sec+"."+(mil<10?"00":(mil<100?"0":""))+mil;
		}
		g.setFont(font);
		r = font.getStringBounds(timeS, new FontRenderContext(null, true, true));
		g.drawString(timeS, (int) (w/2-r.getWidth()/2), 2*fontSize);
		g.setColor(Color.WHITE);
		g.setFont(font2);
		scrs.clear();
		String s = "";
		for(int i = 0; i<scramble.length(); i++)
		{
			s += scramble.charAt(i);
			r = font2.getStringBounds(s, new FontRenderContext(null, true, true));
			int maxsl = w>800?700:w-100;
			if(r.getWidth()>maxsl || i==scramble.length()-1)
			{
				scrs.add(s);
				s = "";
			}
		}
		for(int i = 0; i<scrs.size(); i++)
		{
			r = font2.getStringBounds(scrs.get(i), new FontRenderContext(null, true, true));
			g.drawString(scrs.get(i), (int) (w/2-r.getWidth()/2), 4*fontSize+i*font2.getSize());
			if(r.getHeight()+4*fontSize+i*font2.getSize()>h)
			{
				h = (int) (r.getHeight()+4*fontSize+i*font2.getSize() + 50);
				frame.setSize(new Dimension(w, h));
				render();
			}
		}
		bs.show();
		g.dispose();
	}
	public static void waiting()
	{
		if(scramble =="")
		{
			scramble = "Bunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' CharactersBunch O' Characters";
		}
		if(keyList.keyPressed()==0 && keyRd[0])
			good[0] = true;
		if(good[0] && keyList.keyR(0))
		{
			delete = true;
			good[0] = false;
		}
		if(keyList.keyPressed()==4 && keyRd[4])
			good[4] = true;
		if(good[4] && keyList.keyR(4))
		{
			play = insp?1:2;
			delete = false;
			good[4] = false;
			scramble = "";
			startTime = System.currentTimeMillis();
		}
	}
	public static void inspecting()
	{
		mil = System.currentTimeMillis()-startTime;
		sec = 15-mil/1000;
		if(sec<0)
			pt = true;
		if(sec<-2)
		{
			pt = false;
			DNF = true;
		}
		if(keyList.keyPressed()==4 && keyRd[4])
		{
			lastMil = System.currentTimeMillis();
			keyRd[4] = false;
		}
		else if(keyList.keyPressed()==4 && System.currentTimeMillis()-lastMil>=550)
		{
			good[4] = true;
		}
		if(good[4] && keyList.keyR(4))
		{
			play = 2;
			mil = 0;
			min = 0;
			sec = 0;
			pt = false;
			DNF = false;
			good[4] = false;
			startTime = System.currentTimeMillis();
		}
	}
	public static void timing()
	{
		mil = System.currentTimeMillis()-startTime;
		sec = mil/1000;
		min = sec/60;
		mil%=1000;
		sec%=60;
		boolean stopT = false;
		for(int i = 0; i<6; i++)
		{
			int q = i+(i==0?4:7);
			if(keyList.keyPressed()==q && keyRd[q])
				stopT = true;
		}
		if(stopT)
		{
			good[4] = true;
			play = 0;
			good[4] = false;
			keyRd[4] = false;
			lastMil = System.currentTimeMillis();
		}
	}
	public static void createDisplay()
	{
		frame = new JFrame(title);
		frame.setSize(w, h);
		frame.addKeyListener(keyList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(w, h));
		canvas.setMaximumSize(new Dimension(w, h));
		canvas.setMinimumSize(new Dimension(300, 300));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
}
