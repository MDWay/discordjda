FROM dockerfile/java:oracle-java8
MAINTANER Roman Graef <romangraef@gmail.com>

ADD src /usr/share/apitestingrom/src
ADD pom.xml /usr/share/apitestingrom/pom.xml

CMD mvn exec:java