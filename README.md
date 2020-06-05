# LoggingAndTracing
an easier way to setup logging along with request tracing multilingual exception in your spring application.

## Maven Repository

LoggingAndTracing is deployed at sonatypes open source maven repository. 
This repositories releases will be synched to maven central on a regular basis. Snapshots remain at sonatype.

you can pull LoggingAndTracing from the central maven repository, just add these to your pom.xml file:

```<!-- https://mvnrepository.com/artifact/com.github.sankulgarg/LoggingAndTracing -->
<dependency>
    <groupId>com.github.sankulgarg</groupId>
    <artifactId>LoggingAndTracing</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Building From The Sources
As it is maven project, buidling is just a matter of executing the following in your console:

```mvn package```

This will produce the LoggingAndTracing-VERSION.jar file under the target directory.

## Support
If you need help using **LoggingAndTracing** feel free to drop an email or create an issue in github.com (preferred).

## Usage
This Framework brings lot of configuration and different set of features.
* For Spring Applications one need to just add the Annotation
`@EnableTracingAndExceptionHandling` to any class registered in spring container.
This annotation enables ading of request Id to **MDC thread context** which in printed with every log line, and enabling Exception advice that catches multilingual exception and create the appropriate response entity.
* Provides Three Log mechanism which are passed by System argument **logOutput** by default it prints to console

> Printing to console
```
java -jar yourApp.jar -DlogOutput=console
```
> Printing to file, other optional arguments supported are **rollingFileName** and **logPath**
```
mvn spring-boot:run -DlogOutput=rollingFile -DlogPath=/tmp/log -DrollingFileName=serviceA
```
> Sending logs to a different remote host, other arguments supported are **serviceName** and **sockethost**
```
java -jar yourApp.jar -DlogOutput=SysLogAppender -DserviceName=serviceA  -Dsockethost=127.0.0.0
```
* Logging level is defined by providing system argument, by default it prints ALL log level.
```
-DlogLevel=INFO
-DlogLevel=ERROR
-DlogLevel=DEBUG
```
* Framework uses log4j2 library internally, therefore if your service is using sl4j or log4j2 logger, the logs will automatically be printed. Alternatively, you can use LoggerManager provided by the framework to get Logger object.
```
Logger LOGGER	= LoggersManager.getLogger(YourClass.class);
```
* Other than RequestId, This frameworks provide you the ability to add your custom information that you may want to print with every log line(such as session id,transaction id etc).
```
LogContexts.add("key", "value");
```
* Using multilingual exception handling, The service needs to throw **MultilingualBussinessException**, and it would be catch by the Exception advice, and based on the Bussiness **Exception-Code** will translate to the corresponding language.
Note, That It expects a request header with key **Accept-Language** and service would need to define property files based on the language supported.
For Language en-UK, service need to add en-UK.property file with the corresponding bussiness error code and message to be sent as respose.
```
4000=UK_message
```
**sample response entity in case of error:**
{"responseCode":4000,"errorCode":"4000","details":"UK_message"}

## Extra Information
* SysLogAppender would send logs to remote VM, and Its the remote VM responsibility of maintaining and creating files accordingly.
**Rsyslog** is an open-source software utility used on UNIX and Unix-like computer systems for forwarding log messages in an IP network,
Sample configuration would look like this.
* Rsyslog Configurations:
> Step 1: Verify Rsyslog Installation
```# rpm -q | grep rsyslog
# rsyslogd -v

if not installed 
# yum install rsyslog
```
>Step 2: Configure Rsyslog Service as Client
```
# rsyslog configuration file

# For more information see /usr/share/doc/rsyslog-*/rsyslog_conf.html
# If you experience problems, see http://www.rsyslog.com/doc/troubleshoot.html

#### MODULES ####

# The imjournal module bellow is now used as a message source instead of imuxsock.
$ModLoad imuxsock # provides support for local system logging (e.g. via logger command)
$ModLoad imjournal # provides access to the systemd journal
#$ModLoad imklog # reads kernel messages (the same are read from journald)
#$ModLoad immark # provides --MARK-- message capability

# Provides UDP syslog reception
$ModLoad imudp
$UDPServerRun 514
$MaxMessageSize 64K
# Provides TCP syslog reception
$ModLoad imtcp
$InputTCPServerRun 514
$EscapeControlCharactersOnReceive off
$template RemoteLogs,"/var/log/%HOSTNAME%/%syslogtag%/remote-%$YEAR%-%$MONTH%-%$DAY%.log"
*.* -?RemoteLogs
#### GLOBAL DIRECTIVES ####

# Where to place auxiliary files
$WorkDirectory /var/lib/rsyslog
$maxMessageSize 64k
# Use default timestamp format
$ActionFileDefaultTemplate RSYSLOG_TraditionalFileFormat

# File syncing capability is disabled by default. This feature is usually not required,
# not useful and an extreme performance hit
#$ActionFileEnableSync on

# Include all config files in /etc/rsyslog.d/
$IncludeConfig /etc/rsyslog.d/*.conf

# Turn off message reception via local log socket;
# local messages are retrieved through imjournal now.
$OmitLocalLogging on

# File to store the position in the journal
$IMJournalStateFile imjournal.state


#### RULES ####

# Log all kernel messages to the console.
# Logging much else clutters up the screen.
#kern.* /dev/console

# Log anything (except mail) of level info or higher.
# Don't log private authentication messages!
*.info;mail.none;authpriv.none;cron.none /var/log/messages

# The authpriv file has restricted access.
authpriv.* /var/log/secure

# Log all the mail messages in one place.
mail.* -/var/log/maillog


# Log cron stuff
cron.* /var/log/cron

# Everybody gets emergency messages
*.emerg :omusrmsg:*

# Save news errors of level crit and higher in a special file.
uucp,news.crit /var/log/spooler

# Save boot messages also to boot.log
local7.* /var/log/boot.log


# ### begin forwarding rule ###
# The statement between the begin ... end define a SINGLE forwarding
# rule. They belong together, do NOT split them. If you create multiple
# forwarding rules, duplicate the whole block!
# Remote Logging (we use TCP for reliable delivery)
#
# An on-disk queue is created for this action. If the remote host is
# down, messages are spooled to disk and sent when it is up again.
#$ActionQueueFileName fwdRule1 # unique name prefix for spool files
#$ActionQueueMaxDiskSpace 1g # 1gb space limit (use as much as possible)
#$ActionQueueSaveOnShutdown on # save messages to disk on shutdown
#$ActionQueueType LinkedList # run asynchronously
#$ActionResumeRetryCount -1 # infinite retries if host is down
# remote host is: name/ip:port, e.g. 192.168.0.1:514, port optional
#*.* @@remote-host:514
# ### end of the forwarding rule ###
```
Here, **$template RemoteLogs,"/var/log/%HOSTNAME%/%syslogtag%/remote-%$YEAR%-%$MONTH%-%$DAY%.log"**
creates log files in directory and format mentioned above.
%syslogtag% is the system argument set via **-DserviceName**, therefore if service name is ServiceA
then corresponding log file would look like:
**/var/log/\[serviceA\]/remote-2020-06-05.log**

**Sample Syslog Log line**
```
2020-06-04T15:10:36.511413+00:00 172.18.0.8 [serviceA] [INFO ] Class.method:30 {"requestId":"1e01100f-c066-41cb-9110-52078d57d1641591283436471","contexts":null} - hello world --> 24270
```
## Contributions
To help LoggingAndTracing development you are encouraged to

* Provide suggestion/feedback/Issue
* pull requests for new features
* Star 🌟 the project

[![View My profile on LinkedIn](https://static.licdn.com/scds/common/u/img/webpromo/btn_viewmy_160x33.png)](https://www.linkedin.com/in/sankul-garg)

