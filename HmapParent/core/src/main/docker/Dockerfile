FROM handmobile/jdk8

ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME
ADD hmap.war $CATALINA_HOME/webapps/hmap.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
