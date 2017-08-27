FROM node:slim
RUN npm install -g json-server
RUN npm install -g firebase-tools
RUN npm install -g localtunnel
EXPOSE 80
WORKDIR /app
COPY . /app
VOLUME ["/data"]
ENTRYPOINT ["json-server", "-p", "80"]
CMD ["--help"]
