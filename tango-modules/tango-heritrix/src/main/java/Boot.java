import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.archive.crawler.framework.CrawlJob;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tango
 * Date: 13-12-21
 * Time: 下午9:00
 */
public class Boot {

    private final static Log log = LogFactory.getLog(Boot.class);

    private Map<String, Object> arguments = null;

    private static Boot bootstrap = null;

    public static void main(String[] args) {
        try {
            if (bootstrap == null) {
                bootstrap = new Boot();
                bootstrap.init(args);
            }
            bootstrap.startup();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void startup() {
        File conf = new File(this.getClass().getClassLoader().getResource(this.getArgument("conf", "application-heritrix.xml").toString()).getFile());
        CrawlJob crawlJob = new CrawlJob(conf);
        crawlJob.launch();
        try {
            Thread.sleep(5000);
            crawlJob.getCrawlController().requestCrawlResume();
            while (crawlJob.isRunning()) {
                Thread.sleep(1000);
            }
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            log.error("startup heritrix error,message:" + e.getMessage());
        }
    }

    private void init(String[] args) {
        this.arguments = new HashMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            String[] arg = args[i].split("=");
            arguments.put(arg[0].replace("--", ""), arg.length > 1 ? arg[1] : Boolean.TRUE.toString());
        }
    }

    private Object getArgument(String argument, Object defaultValue) {
        if (this.arguments.containsKey(argument)) {
            return this.arguments.get(argument);
        }
        return defaultValue;
    }
}
