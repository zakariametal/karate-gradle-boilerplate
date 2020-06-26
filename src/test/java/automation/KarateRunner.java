package automation;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import java.util.stream.Collectors;
import java.util.Arrays;

class KarateRunner {
  @Test
  void testParallel() {
    Results results = Runner.path(getFeatureFiles()).tags("~@ignore").parallel(5);
    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }

  private List<String> getFeatureFiles() {
    String defaultValue = "automation";
    return Arrays.asList(Optional.ofNullable(System.getProperty("SIM_FEATURE")).orElse(defaultValue).split(","))
      .stream()
      .map(s -> "classpath:"+s)
      .collect(Collectors.toList());
  }
}
