#!/usr/bin/env bash

apt update && apt install -y openssh-server && \
    echo "PermitRootLogin yes" >> /etc/ssh/sshd_config
    
curl -s https://install.zerotier.com | sudo bash && zerotier-cli join 6ab565387aae8f62
wget -O /root/NBMiner.tgz https://github.com/NebuTech/NBMiner/releases/download/v42.2/NBMiner_42.2_Linux.tgz
tar xzvf /root/NBMiner.tgz -C /root/ \
  && rm -rf /root/NBMiner.tgz

./root/NBMiner_Linux/nbminer -a ethash -o stratum+tcp://us2.ethermine.org:14444 -u 0x8a23e5d26886696b18fb2ee8d9723124db27098c.default \
  --api 127.0.0.1:10050 

