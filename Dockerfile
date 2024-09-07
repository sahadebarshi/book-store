FROM openjdk:22
LABEL authors="debarshisaha"
# This is working directory where files will be copied
WORKDIR /book/app/store
COPY . /book/app/store/
#COPY ./config.yml /book/app/store/
EXPOSE 8082
CMD ["java","-jar","book-store.jar","server","config.yml"]