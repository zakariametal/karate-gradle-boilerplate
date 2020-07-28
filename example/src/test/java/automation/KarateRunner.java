package automation;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import static org.junit.jupiter.api.Assertions.*;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Arrays;

import java.io.File;

class KarateRunner {
  @Test
  void testParallel() {
    Results results = Runner.path(getFeatureFiles()).tags("~@ignore").parallel(5);
    generateReport(results);
    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }

  private List<String> getFeatureFiles() {
    String defaultValue = "automation";
    return Arrays.asList(Optional.ofNullable(System.getProperty("SIM_FEATURE")).orElse(defaultValue).split(","))
      .stream()
      .map(s -> "classpath:"+s)
      .collect(Collectors.toList());
  }

  public static void generateReport(Results results) {
    String karateOutputPath = Paths.get(System.getProperty("projectDir"), results.getReportDir()).toString();
    Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
    List<String> jsonPaths = new ArrayList(jsonFiles.size());
    jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
    String outputDir = System.getProperty("buildDir");
    Configuration config = new Configuration(new File(outputDir), System.getProperty("projectName"));
    config.addClassifications("OS Name", System.getProperty("os.name"));
    config.addClassifications("Triggered by", System.getProperty("user.name"));
    config.addClassifications("Environment", System.getProperty("karate.env"));
    ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
    reportBuilder.generateReports();
    String reportPath = Paths.get(System.getProperty("buildDir"), reportBuilder.BASE_DIRECTORY, reportBuilder.HOME_PAGE).toString();
    System.out.println("Report is generated at :" + reportPath);
  }
}
