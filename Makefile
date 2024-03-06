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