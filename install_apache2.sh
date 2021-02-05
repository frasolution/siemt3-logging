#! /bin/bash

echo ""
echo "apache install started"

sudo apt-get update
yes | sudo apt-get upgrade

#remove existing apache2
sudo service apache2 stop

yes | sudo apt-get purge apache2 apache2-utils apache2-bin apache2.2-common

yes | sudo apt-get autoremove

yes | sudo rm -rf /etc/apache2


#install fresh apache2
yes | sudo apt-get install apache2

sudo service apache2 restart 


#install modules
sudo a2enmod ssl
sudo a2enmod headers
sudo service apache2 restart 

#enable ssl
sudo a2ensite default-ssl
sudo service apache2 restart 



echo ""
echo "apache install finished"
echo ""