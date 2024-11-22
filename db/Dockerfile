FROM alpine:edge AS base

WORKDIR /cert

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*

RUN openssl req -new -text -passout pass:abcd -subj /CN=localhost -out server.req -keyout privkey.pem

RUN openssl rsa -in privkey.pem -passin pass:abcd -out server.key

RUN openssl req -x509 -in server.req -text -key server.key -out server.crt

RUN chmod -R +x * && ls

FROM postgres:13-alpine

COPY --from=base /cert /var/lib/postgresql

RUN chown 0:70 /var/lib/postgresql/server.key && chmod 640 /var/lib/postgresql/server.key
RUN chown 0:70 /var/lib/postgresql/server.crt && chmod 640 /var/lib/postgresql/server.crt

ENTRYPOINT ["docker-entrypoint.sh"]

CMD [ "-c", "ssl=on" , \
    "-c", "ssl_cert_file=/var/lib/postgresql/server.crt", \
    "-c", "ssl_key_file=/var/lib/postgresql/server.key"]