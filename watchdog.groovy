import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
@Singleton
public class WatchDogThread implements Runnable{
  private Boolean valid = Boolean.FALSE
  void run() {
    while(true)
    {
      try {
            sleep(10000)
            println "watchdog:check once time;"
            def url = new URL("https://www.google.com")
            def connection = url.openConnection()
            valid = ( connection.responseCode == 200 ) as Boolean
            println "watchdog:connection valid->${valid}"
        } catch ( Exception e ) {
            println "error->" + e.message
            valid = Boolean.FALSE
        }
    }
  }
  public void startMiner()
  {
    
  }
  public void stopMiner()
  {
  }
  public void checkMiner()
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
def wdThread = WatchDogThread.instance
def WatchDogService = Executors.newSingleThreadExecutor()
WatchDogService.submit(wdThread) 
