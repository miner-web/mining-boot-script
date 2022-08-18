import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.net.URI
import java.net.URISyntaxException
@Singleton
public class WsThread implements  Runnable{
  private WsThread()
  {

  }
  void run() {
    try
    {
      def config= new ConfigSlurper().parse(new File("/live/boot-dev/config.txt").toURL())
      println "wsThread init:config->${config} -> ${config.apiKey}"
      println "mwc->" + MiningWebsocketClient.serverUri
      MiningWebsocketClient.serverUri = new URI(config.apiKey)
      println "mwc3->" + MiningWebsocketClient.serverUri
    }
    catch ( Exception e ) {
      println e.message
    }
    while(true)
    {
      try {
            sleep(10000)
            println "wsThread:check once time;"
            def url = new URL("https://www.google.com")
            def connection = url.openConnection()
            valid = ( connection.responseCode == 200 ) as Boolean
            println "watchdog:connection valid->${valid}"
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
}
// run thread
def WsThread = WsThread.instance
def WsService = Executors.newSingleThreadExecutor()
WsService.submit(WsThread)
