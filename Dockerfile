FROM ubuntu:18.04

LABEL maintainer="furkankayar27@gmail.com"
LABEL version="0.1"
LABEL description="Java Spring Boot"

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get --yes install openjdk-8-jdk openjdk-8-jre
RUN apt-get --yes install postgresql postgresql-contrib
RUN apt-get --yes install sudo
RUN apt-get --yes install nano

ENV user test
 
RUN useradd -m -d /home/${user} ${user} && chown -R ${user} /home/${user} | chpasswd && adduser ${user} sudo
RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers

USER ${user}

CMD ["/bin/bash"]
