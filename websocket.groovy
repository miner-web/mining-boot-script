import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

public class WsThread implements  Runnable{
  private static final WsThread instance = new WsThread();
    public static WsThread getInstance() {
        return instance;
    }
  private WsThread()
  {
    def config= new ConfigSlurper().parse(new File("/live/boot-dev/config.txt").toURL())
    MiningWebsocketClient.serverUri = new URI(config.apiKey)
    println "wsThread init:apiKey->" + ${config.apiKey}
  }
  void run() {
    while(true)
    {
      try {
            sleep(10)
            println "wsThread:check once time;"
            def connection = url.toURL().openConnection()
            valid = ( connection.responseCode == 200 ) as Boolean
            if(valid)
            {
              if(!MiningWebsocketClient.getInstance().isOpen())
              {
                println "wsGroovy:reconnecting to server;"
                MiningWebsocketClient.getInstance().reconnect()
              }
            }
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
def WsThread = WsThread.getInstance()
def WsService = Executors.newSingleThreadExecutor()
WsService.submit(WsThread)
