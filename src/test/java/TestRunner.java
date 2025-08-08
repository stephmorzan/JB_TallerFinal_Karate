import com.intuit.karate.Runner;
import com.intuit.karate.Results;
import com.intuit.karate.junit5.Karate;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestRunner {

    @Karate.Test
    public Karate runnerTest(){
        return Karate.run("classpath:api").tags("@Test").relativeTo(getClass());
    }

    @Test
    public void cucumberJson(){
        Results result = Runner.path("classpath:api/").tags("@sanity")
                .relativeTo(getClass())
                .outputCucumberJson(true)
                .karateEnv("karate")
                .parallel(1);

        Collection<File> jsonFiles = FileUtils.listFiles(new File(result.getReportDir())
                , new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>((jsonFiles.size()));
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "demo");
        config.addClassifications("owner", "JBGroup");
        config.addClassifications("suite", "@sanity");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();

    }
}
