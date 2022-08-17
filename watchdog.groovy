import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
@Singleton
public class WatchDogThread implements  Runnable{
  void run() {
    while(true)
    {
      try {
            sleep(10)
            println "watchdog:check once time;"
            def connection = url.toURL().openConnection()
            valid = ( connection.responseCode == 200 ) as Boolean
        } catch ( Exception e ) {
            println e.message
            valid = Boolean.FALSE
        }

    }
  }
  void startMiner()
  {
    
  }
  void stopMiner()
  {
  }
  void checkMiner()
  {
        File folder = new File("/root/miner_dir");
        if (!folder.exists() && !folder.isDirectory())
        {
            folder.mkdir();
        }
        else
        {

        }
  }
}
// run thread
def WatchDogThread = new WatchDogThread()
def WatchDogService = Executors.newSingleThreadExecutor()
WatchDogService.submit(WatchDogService)