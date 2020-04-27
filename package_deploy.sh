#! /bin/bash
source /etc/profile
version="1.0.0-SNAPSHOT"
# 项目路径
source_path="/data/myblog/SpringBoot-MyBlog"
# 部署路径（jar包）
deploy_path="/data/myblog/SpringBoot-MyBlog/"

mvn_repository="/root/.m2/repository"
package_name="/com/jzh"
#部署环境(切换部署环境改这里)
environment="prod"
JAVA_OPS=" -server -Xms256m -Xmx256m -XX:NewRatio=4 -XX:SurvivorRatio=8 -XX:PermSize=32m -XX:MaxPermSize=64m -Xss256k -XX:+HeapDumpOnOutOfMemoryError"
print_log(){
    echo ============================================== ${1} =================================================================
}
#project_prefix="SpringBoot"
project_name="myblog"
project_port=21808
is_update="no"

# 检查是否需要更新
function_check_update(){
    hcode_old=$(git rev-parse HEAD)
    git pull origin master
    print_log "${hcode_old}"
    hcode_new=$(git rev-parse HEAD)
    print_log "${hcode_new}"  
    if [ "${hcode_old}" == "${hcode_new}" ]
    then
        print_log "没有更新的内容"
    else
        is_update="yes"
        print_log "需要更新，需要重新打包"
    fi
}
#打包到maven
function_install_jars(){
    name=$1
    
    print_log "开始打包${name}的代码"
    
    print_log "打包的路径 ${source_path}"
    mvn clean install -Dmaven.test.skip=true

    jar_all_name=${project_name}-${version}.jar
    cp ${mvn_repository}${package_name}/${name}/${version}/${jar_all_name} ${deploy_path}
}


#重启
function_jar_restart(){
  name=$1
	port=$2
	print_log "部署端口为${port}"
  jar_all_name=${name}-${version}.jar

  cd ${deploy_path}

  print_log "开始部署${name}"
  print_log "部署路径为${deploy_path}"
	#pid文件名
	print_log "关闭正在运行的程序${jar_all_name}"
	pid=$(ps -ef | grep ${jar_all_name} | grep -v 'grep' | awk -F ' ' '{print $2}')
	if [ -n "${pid}" ]
	then
	  	print_log "kill ${pid}"
		  sudo kill -9 ${pid}
	else
		  print_log "没有存在程序，不需要杀死，直接重启"
	fi
  print_log "启动程序${jar_all_name}"

  run_log_name=run.log
  pid_file_name="$name.pid"

  nohup java -jar ${JAVA_OPS} ${jar_all_name} --spring.profiles.active=${environment} >> ${run_log_name}  2>&1 &

  echo $!>${pid_file_name}

  sleep 2s

  today=$(date +%Y-%m-%d)
  print_log "今天的日期为：${today}"

  sleep 2s
	#循环1000秒，要是还没启动证明失败了
	is_ok="fail"
	for i in $(seq 1 1000)
	do
  		live_code=$(curl -s -m 10 -o /dev/null --connect-timeout 10 localhost:${port} -w %{http_code})
	  	if [ ${live_code} -ne 000 ];then
	    		is_ok="ok"
	    		break
		  else
		    	print_log "no live $i"
	  	fi
	  	#等待1s
	  	sleep 1s
	done
	if [ "${is_ok}" = "ok" ];then
  		print_log "启动${jar_all_name}完毕"
	else
  		print_log "启动${jar_all_name}异常"
	fi
}

echo "开始"
case "$1" in
  deploy )
      function_check_update
      if [ "${is_update}" == "yes" ]
      then
          function_install_jars $project_name
          function_jar_restart $project_name $project_port
      fi
  ;;

  start )
      function_install_jars $project_name
      function_jar_restart $project_name $project_port
  ;;

  restart )
      function_jar_restart $project_name $project_port
  ;;

  * )
     echo "deploy or start or restart"
  ;;
esac