# siemt3-logging

#install apache2
- install apache2 with install_apache2.sh
- go to /etc/apache2/apache2.conf and insert this string in row 214:

LogFormat "{ \"time\":\"%{%Y-%m-%d}tT%{%T}t.%{msec_frac}tZ\", \"process\":\"%D\", \"filename\":\"%f\", \"remoteIP\":\"%a\", \"host\":\"%V\", \"request\":\"%U\", \"query\":\"%q\", \"method\":\"%m\", \"status\":\"%>s\", \"userAgent\":\"%{User-agent}i\", \"referer\":\"%{Referer}i\" }," combined

- run command: sudo service apache2 restart


#epl file
- place file "apache2.epl" where you like but remember the path

#siem demo
- import java project "SIEM_CEP" and update path to "apach2.epl"
- run "SIEM_CEP"
- go to localhost:443 and refresh the site more than 5 times
- -> alert should be shown in console
