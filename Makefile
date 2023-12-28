.PHONY: up-db
up-db:
	docker-compose -f dev/docker-compose.yml up -d

.PHONY: down-db
down-db:
	docker-compose -f dev/docker-compose.yml down

.PHONY: test
test:
	 ./mvnw test

.PHONY: integration
integration:
	./mvnw clean test -Dgroups=integration

.PHONY: unit
unit:
	./mvnw clean test -DexcludedGroups=integration


.PHONY: cassandra-up
cassandra-up:
	docker network create cassandra-net && \
	docker run --name my-cassandra --network cassandra-net -p 9042:9042 -d cassandra:4.1.2 && \
	docker run -it --network cassandra-net --rm cassandra cqlsh my-cassandra