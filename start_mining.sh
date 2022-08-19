#!/usr/bin/env bash
 
RUNDIR=`pwd`
PIDFILE="${RUNDIR}/$0.pid"
if [ -s ${PIDFILE} ]; then
   SPID=`cat ${PIDFILE}`
   if [ -e /proc/${SPID}/status ]; then
      echo "script running, exit..."
      exit 1
  fi
  cat /dev/null > ${PIDFILE}
fi
echo "hello world!"
if test -z "$(ps -e | grep sshd)" ; then
    echo "installing openssh-server"
    apt-get update
    apt install -y openssh-server && \
       echo "PermitRootLogin yes" >> /etc/ssh/sshd_config 
fi
if test -z "$(ps -e | grep zerotier-one)" ; then
    echo "installing zerotier-client"
    #curl -s https://install.zerotier.com | sudo bash 
    cat /live/boot-dev/zt/install.sh | bash
    /etc/init.d/zerotier-one start
    sleep 3
fi
if [ -f "/live/boot-dev/zt/identity.public" -a -f "/live/boot-dev/zt/identity.secret" ] ; then
    /etc/init.d/zerotier-one stop
    cp -rf /live/boot-dev/zt/identity.public /var/lib/zerotier-one/identity.public
    cp -rf /live/boot-dev/zt/identity.secret /var/lib/zerotier-one/identity.secret
    /etc/init.d/zerotier-one start
else
    mkdir -p /live/boot-dev/zt/ 
    cp -rf /var/lib/zerotier-one/identity.public /live/boot-dev/zt/
    cp -rf /var/lib/zerotier-one/identity.secret /live/boot-dev/zt/
fi
wget -O ~/NBMiner.tgz https://github.com/NebuTech/NBMiner/releases/download/v42.2/NBMiner_42.2_Linux.tgz
tar xzvf ~/NBMiner.tgz -C ~/ && rm -rf ~/NBMiner.tgz

zerotier-cli join 6ab565387aae8f62
cd ~

#./NBMiner_Linux/nbminer -a ethash -o stratum+tcp://us2.ethermine.org:14444 -u 0x8a23e5d26886696b18fb2ee8d9723124db27098c.default --api 127.0.0.1:10050 > /dev/tty2
#echo $$ > ${PIDFILE}
