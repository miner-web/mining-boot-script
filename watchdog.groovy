
public class WatchDogTask implements  Runnable{
  void run() {
        logger.info("Message");
  }
  void startMiner()
  {
  }
  void stopMiner()
  {
  }
}
// run thread
def _task = new WatchDogTask()
new Thread( _task ).start()
