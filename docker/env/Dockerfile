FROM java:8

MAINTAINER jzh <813997065@qq.com>

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir /app

COPY app.jar /app/app.jar

# copy arthas，如果不用，请注释或者删除下方这行命令即可
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

ENV JAVA_OPTS="\
 -server \
 -Xms2000m \
 -Xmx2000m \
 -XX:PermSize=32M \
 -XX:MaxPermSize=64M \
 -XX:+HeapDumpOnOutOfMemoryError \
 -XX:HeapDumpPath=/data/logs/myblog/heap/heap-%t.hprof \
 -XX:+PrintGCDetails \
 -XX:+PrintGCTimeStamps \
 -XX:+PrintGCDateStamps \
 -Xloggc:/data/logs/myblog/gc/gc-%t.log \
 -XX:+UseG1GC"

#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar ${JAVA_OPTS} /app/app.jar --spring.profiles.active=prod

EXPOSE 21808