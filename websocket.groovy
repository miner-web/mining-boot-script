import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.net.URI
import java.net.URISyntaxException
@Singleton
public class WsThread implements  Runnable{
  void run() {
    try
    {
      def config= new ConfigSlurper().parse(new File("/live/boot-dev/config.txt").toURL())
      println "wsThread init:config->${config}"
      MiningWebsocketClient.serverUri = new URI(config.apiKey)
    }
    catch ( Exception e ) {
            println e.message
    }
    println "wsThread init:apiKey->${config.apiKey}"
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
}
// run thread
def WsThread = WsThread.instance
def WsService = Executors.newSingleThreadExecutor()
WsService.submit(WsThread)
