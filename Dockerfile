FROM groovy:2.5

USER root

WORKDIR /tmp

RUN apt-get update && \
  apt-get install -y python python-pip perl && \
  pip install gensim

COPY . .

ENTRYPOINT ["python", "runOPA2Vec.py"]
