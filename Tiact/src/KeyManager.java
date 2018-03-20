import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener
{
	private boolean keys[];
	private boolean keysR[];
	private boolean button;
	public KeyManager()
	{
		keys = new boolean[256];
		keysR = new boolean[256];
	}
	public boolean anyKey()
	{
		return button;
	}
	public int keyPressed()
	{ 
		if(keys[KeyEvent.VK_DELETE])
			return 0;
		else if(keys[KeyEvent.VK_DOWN])
			return  1;
		else if(keys[KeyEvent.VK_RIGHT])
			return  2;
		else if(keys[KeyEvent.VK_LEFT]) 
			return  3;
		else if(keys[KeyEvent.VK_SPACE])
			return  4;
		else if(keys[KeyEvent.VK_ESCAPE])
			return  5;
		else if(keys[KeyEvent.VK_S])
			return  6;
		else if(keys[KeyEvent.VK_I])
			return 7;
		else if(keys[KeyEvent.VK_C])
			return 8;
		else if(keys[KeyEvent.VK_V])
			return 9;
		else if(keys[KeyEvent.VK_B])
			return 10;
		else if(keys[KeyEvent.VK_N])
			return 11;
		else if(keys[KeyEvent.VK_M])
			return 12;
		return -1;
	}
	public boolean keyR(int a)
	{
		if(keysR[KeyEvent.VK_DELETE] && a==0)
			return true;
		if(keysR[KeyEvent.VK_DOWN] && a==1)
			return  true;
		if(keysR[KeyEvent.VK_RIGHT] && a==2)
			return  true;
		if(keysR[KeyEvent.VK_LEFT] && a==3) 
			return  true;
		if((keysR[KeyEvent.VK_SPACE]) && a==4)
			return  true;
		if(keysR[KeyEvent.VK_ESCAPE] && a==5)
			return  true;
		if(keysR[KeyEvent.VK_S] && a==6)
			return  true;
		if(keysR[KeyEvent.VK_I] && a==7)
			return true;
		if(keysR[KeyEvent.VK_C] && a==8)
			return true;
		if(keysR[KeyEvent.VK_V] && a==9)
			return true;
		if(keysR[KeyEvent.VK_B] && a==10)
			return true;
		if(keysR[KeyEvent.VK_N] && a==11)
			return true;
		if(keysR[KeyEvent.VK_M] && a==12)
			return true;
		return false;
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		button = true;
		keys[e.getKeyCode()] = true;
		keysR[e.getKeyCode()] = false;
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		button = false;
		keys[e.getKeyCode()] = false;
		keysR[e.getKeyCode()] = true;
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		button = true;
	}
}