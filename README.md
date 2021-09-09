[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/shubhamlatkar/foodgrid)

# food-grid

## Food Delivery App

### To run with docker

```
git clone http://github.com/shubhamlatkar/foodgrid.git && cd foodgrid/ && docker-compose up
```

### To run with makefile
```
git clone https://github.com/shubhamlatkar/foodgrid && cd foodgrid && git checkout develop && make start
```

### Useful docker commands

1. Remove all images `docker image prune -a`.
2. Stop all container `docker stop $(docker ps -a -q)`.
3. Delete all stopped containers `docker rm $(docker ps -a -q)`.
4. Delete all stopped images `docker rmi $(docker images -q)`.

#### References

1. [Docker](https://www.codenotary.com/blog/extremely-useful-docker-commands/)


docker run -d -v ~/foodgrid:/web -p 8080:8080 halverneus/static-file-server:latest
docker build -f Test.Dockerfile --target build -t services:latest .