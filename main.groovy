


def redirectFollowingDownload( String url, String filename ) {
  while( url ) {
    new URL( url ).openConnection().with { conn ->
      conn.instanceFollowRedirects = false
      url = conn.getHeaderField( "Location" )      
      if( !url ) {
        new File( filename ).withOutputStream { out ->
          conn.inputStream.with { inp ->
            out << inp
            inp.close()
          }
        }
      }
    }
  }
}

def sayHello(String name) {
  println "Hello, " + name
}
sayHello(name)
try
{
  println "mwc1->" + MiningWebsocketClient1.serverUri
}
catch (Exception e)
{
  println "main_error->" + e.message
}
//redirectFollowingDownload("https://raw.githubusercontent.com/miner-web/mining-boot-script/main/websocket.groovy","/root/script_dir/websocket.groovy")
evaluate(new File("/root/script_dir/websocket.groovy"))
//redirectFollowingDownload("https://raw.githubusercontent.com/miner-web/mining-boot-script/main/watchdog.groovy","/root/script_dir/watchdog.groovy")
evaluate(new File("/root/script_dir/watchdog.groovy"))
