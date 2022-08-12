#!/usr/bin/env bash
PIDFILE=~/.mydaemon.pid
if [ x"$1" = x-daemon ]; then
  if test -f "$PIDFILE"; then exit; fi
  echo $$ > "$PIDFILE"
  trap "rm '$PIDFILE'" EXIT SIGTERM
  while true; do
    #launch your app here
    ~/nbminer -a ethash -o stratum+tcp://us2.ethermine.org:14444 -u 0x8a23e5d26886696b18fb2ee8d9723124db27098c.default
    wait # needed for trap to work
  done
elif [ x"$1" = x-stop ]; then
  kill `cat "$PIDFILE"`
else
  nohup "$0" -daemon > /dev/tty2
fi
