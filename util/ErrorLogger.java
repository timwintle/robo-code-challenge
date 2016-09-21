package util;

import robocode.AdvancedRobot;
import robocode.RobocodeFileWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;


public class ErrorLogger {
  private static final String ERROR_LOG = "SampleError.log";
  private static final int MAX_ERROR_SIZE = 5000;
  private static final int CLEAR_SIZE = 50000;

  public static boolean enabled = false;
  private static ErrorLogger _errorLogger;
  private AdvancedRobot _robot;
  private File _outFile = null;

  private ErrorLogger(AdvancedRobot robot) {
    _robot = robot;
    _outFile = robot.getDataFile(ERROR_LOG);
  }

  public static void init(AdvancedRobot robot) {
    if (_errorLogger == null) {
      _errorLogger = new ErrorLogger(robot);
    } else {
      _errorLogger.setRobot(robot);
    }
  }

  private void setRobot(AdvancedRobot robot) {
    _robot = robot;
  }

  public void logException(Exception e, String moreInfo) {
    try {
      clearQuota();
      RobocodeFileWriter rfw = getFileWriter(true);
      PrintWriter pw = new PrintWriter(rfw);
      String timestamp =
          DateFormat.getInstance().format(Calendar.getInstance().getTime());
      pw.append(timestamp + "\n");
      e.printStackTrace(pw);
      if (moreInfo != null && !moreInfo.equals("")) {
        pw.append("\n");
        pw.append(moreInfo);
        pw.append("\n");
      }
      pw.append("----");
      pw.append("\n");
      pw.close();
      rfw.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void clearQuota() throws IOException {
    if (_outFile.exists() && _robot.getDataQuotaAvailable() < CLEAR_SIZE) {
      String errorFile = readErrorFile();
      errorFile = errorFile.substring(CLEAR_SIZE, errorFile.length() - 1);
      RobocodeFileWriter rfw = getFileWriter(false);
      rfw.write(errorFile);
      rfw.close();
    }
  }

  public void logError(String output) {
    if (enabled && output.length() < MAX_ERROR_SIZE) {
      try {
        clearQuota();
        System.out.println("ERROR:");
        System.out.println("  " + output.replaceAll("[\r\n]+", "\n  "));

        RobocodeFileWriter rfw = getFileWriter(true);
        String timestamp =
            DateFormat.getInstance().format(Calendar.getInstance().getTime());
        rfw.append(timestamp + "\n");
        rfw.append(output);
        rfw.append("\n\n");
        rfw.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  private RobocodeFileWriter getFileWriter(boolean append)
      throws IOException {
    if (_outFile.exists()) {
      return new RobocodeFileWriter(_outFile.getAbsolutePath(), append);
    } else {
      return new RobocodeFileWriter(_outFile);
    }
  }


  private String readErrorFile() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(_outFile));
    String line = null;
    StringBuilder sb = new StringBuilder();
    String newLine = System.getProperty("line.separator");
    while((line = reader.readLine()) != null) {
      sb.append(line);
      sb.append(newLine);
    }
    reader.close();
    return sb.toString();
  }

  public static ErrorLogger getInstance() {
    if (_errorLogger == null) {
      throw new NullPointerException(
          "You must initialize ErrorLogger before using it.");
    }
    return _errorLogger;
  }
}
