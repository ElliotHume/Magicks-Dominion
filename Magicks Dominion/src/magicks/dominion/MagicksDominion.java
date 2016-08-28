package magicks.dominion;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MagicksDominion extends StateBasedGame
{
    // Game state identifiers
    public static final int MAINMENU     = 1;
    public static final int GAME         = 0;
    
	public MagicksDominion(String gamename)
	{
		super(gamename);
	}
        
    @Override
        public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
            this.addState(new Game(GAME));
            this.addState(new MainMenu(MAINMENU));
        }

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new MagicksDominion("Magick's Dominion"));
			appgc.setDisplayMode(2156, 1251, false);
                        appgc.setShowFPS(false);
                        appgc.setVSync(true);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(MagicksDominion.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}