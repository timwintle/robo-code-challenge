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
import util.ErrorLogger;

import java.awt.*;


/**
 * Sample Template Bot for the internal Grad Challenge
 * (Adapted from the THGBotD Official Challanger (Hard)
 *
 *
 * NOTES:
 * 1. This template will provide you with the basic framework to build a robot in no time
 *    Also includes handy-dandy classes that will help you with maths, arena and Robot states
 *
 * 2. You will notice that non-final, non-primitives are declared with an "_" and are never referenced or updated
 *    using "this.", that is because robocode is dumb and will not act responsibly when using it ( I know it's ugly)
 *
 * 3. GOOD LUCK
 *
 *
 */
public class SampleBot extends AdvancedRobot {

        public void run() {
            try {
                initComponents();
                initColors();

                setAdjustGunForRobotTurn(true);
                setAdjustRadarForGunTurn(true);

                while (true) {
                    //Add your execute methods here
                  execute();
                }
            } catch (RuntimeException re) {
                logAndRethrowException(re);
            }
        }

        //Initialization process
        //If you have data structures or preprocessing before the match
        private void initComponents() {
        }

        //Fancy colours for your bot
        private void initColors() {
            Color thgColor = new Color(142, 255, 242);
            setColors(Color.black, Color.red, thgColor);
        }


        //When you scan an opponent do something
        public void onScannedRobot(ScannedRobotEvent e) {
            try {


            } catch (RuntimeException re) {
                logAndRethrowException(re);
            }
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
            String moreInfo = "getOthers(): " + getOthers() + "\n"
                    + "getRoundNum(): " + getRoundNum() + "\n" + "getTime(): " + getTime();
            ErrorLogger.getInstance().logException(e, moreInfo);

            throw e;
        }


}
