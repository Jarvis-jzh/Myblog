#! /bin/bash

is_update="no"

print_log(){
    echo ============================================== ${1} =================================================================
}

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

function_install_jar() {
    # 打包
    mvn clean package -Dmaven.test.skip=true
    cp target/myblog-1.0.0-SNAPSHOT.jar docker/env/app.jar
}

function_jar_start() {
    # 运行
    cd docker
    docker-compose down
    docker-compose up --build -d
    docker rmi $(docker images -q -f dangling=true)
}

echo "开始"
case "$1" in
    deploy )
        function_check_update
        if [ "${is_update}" == "yes" ]
        then
             function_install_jar
             function_jar_start
        fi
    ;;

    start )
        function_install_jar
        function_jar_start
    ;;

    restart )
        function_jar_start
    ;;

    * )
        echo "deploy or start or restart"
    ;;
esac