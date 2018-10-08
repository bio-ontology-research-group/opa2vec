FROM groovy:2.5

USER root

WORKDIR /tmp

RUN apt-get update && \
  apt install -y python python-pip perl

RUN  pip install gensim

COPY . .

ENTRYPOINT ["python", "runOPA2Vec.py"]
