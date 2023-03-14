#!/bin/bash
#BUILD_JAR=$(ls /home/ubuntu/app/build/libs/*.jar)
#JAR_NAME=$(basename $BUILD_JAR)
#echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/app/deploy.log
#
#echo "> build 파일 복사" >> /home/ubuntu/app/deploy.log
#DEPLOY_PATH=/home/ubuntu/app/
#cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 Docker 컨테이너 pid 확인" >> /home/ubuntu/app/deploy.log
CURRENT_PID=$(sudo docker container ls -q)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ubuntu/app/deploy.log
else
  echo "> sudo docker stop $CURRENT_PID" # 현재 구동중인 Docker 컨테이너 있으면 모두 중지
  sleep 5
fi

cd /home/ubuntu/app
sudo  docker build -t joonghyun/concert_calendar --platform linux/amd64 .
sudo systemctl restart nginx.service
#sudo docker run -p 8080:8080 joonghyun/concert_calendar #host에서 사용하는 포트 80, 컨테이너에서 사용하는 포트 8080
sudo docker-compose up