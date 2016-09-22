package timwintle;
import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;
//import util.ErrorLogger;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class SampleBot extends AdvancedRobot {

    private final static int STATE_SCANING = 0;

    private int state = STATE_SCANING;
	private int direction = 1;

    private Map<String, Double> knownLocations = new HashMap<String, Double>();

    public void run() {
        try {
            initComponents();
            initColors();

            startScan();
            while (true) {
                //Add your execute methods here
                //this.setTurnRadarLeftRadians(Math.PI * 2.0);

                double gunHeading = getGunHeading();
                double bestDist = 2.0 * Math.PI;
                for (Map.Entry<String, Double> loc: knownLocations.entrySet()) {
                  double dist = (gunHeading - loc.getValue()) % (2.0 * Math.PI);
                  if (Math.abs(dist) < Math.abs(bestDist)) {
                      bestDist = dist;
                  }
                }
                if (Double.compare(Math.abs(bestDist), 0.001f) < 0) {
                    fire(1);
                } else {
				    turnGunRightRadians(bestDist * direction);
				}
              execute();
            }
        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }

    private void startScan() {
        this.setTurnRadarLeftRadians(Math.PI * 2.0);
        this.setAhead(10f);
    }

    //Initialization process
    //If you have data structures or preprocessing before the match
    private void initComponents() {
        setAdjustGunForRobotTurn(false);
        setAdjustRadarForGunTurn(false);
    }

    //Fancy colours for your bot
    private void initColors() {
        Color thgColor = new Color(142, 255, 242);
        setColors(Color.black, Color.red, thgColor);
    }


    //When you scan an opponent do something
    public void onScannedRobot(ScannedRobotEvent e) {
        knownLocations.put(e.getName(), getRadarHeadingRadians() + e.getHeadingRadians());
        fire(1);
    }



    //Somebody died, maybe record some information about it?
    public void onRobotDeath(RobotDeathEvent e) {
        try {

        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }


    //You got hit by a bullet.. FIGHT OR FLIGHT
    public void onHitByBullet(HitByBulletEvent e) {
        try {

        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }


    //You hit someone with your gun, make sure to use that to your advantage
    public void onBulletHit(BulletHitEvent e) {
        try {
			direction = -1 * direction;
        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }


    //Bullets can disable other bullets, there is also an event for this
    public void onBulletHitBullet(BulletHitBulletEvent e) {
        try {

        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }

    //Trivial
    public void onHitWall(HitWallEvent e) {
        System.out.println("WARNING: I hit a wall (" + getTime() + ").");
    }


    //Congrats
    public void onWin(WinEvent e) {
        try {

        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }


    //GG, code some more to kill them all!
    public void onDeath(DeathEvent e) {
        try {

        } catch (RuntimeException re) {
            logAndRethrowException(re);
        }
    }


    //If your robot gets too big it might not have enough time to complete a turn
    //There is an event for skipped turns, use this to debug
    public void onSkippedTurn(SkippedTurnEvent e) {
        System.out.println("WARNING: Turn skipped at: " + e.getTime());
    }

    //Just to help you out in debugging guys
    protected void logAndRethrowException(RuntimeException e) {
    }
}
