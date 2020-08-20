package automation;

import com.intuit.karate.netty.FeatureServer;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MockServer {
  private static int port = 60999;
  private static boolean ssl = false;
  private static Map vars = new HashMap();

  public static void main(String[] args) throws IOException {
    MockServer main = new MockServer();
    // TODO: this example-mock.feature will be replaced by a proper mock
    File file = main.getResourceFile("example-mock.feature");
    FeatureServer.start(file, port, ssl, vars);
  }


  private File getResourceFile(String filename) throws IOException {
    InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
    File tempFile = File.createTempFile(filename, null);
    tempFile.deleteOnExit();
    FileOutputStream out = new FileOutputStream(tempFile);
    IOUtils.copy(in, out);
    return tempFile;
  }
}
