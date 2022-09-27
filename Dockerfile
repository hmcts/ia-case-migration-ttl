ARG APP_INSIGHTS_AGENT_VERSION=3.2.10
FROM hmctspublic.azurecr.io/base/java:openjdk-11-distroless-1.2

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/ia-case-migration.jar /opt/app/

EXPOSE 4550
CMD [ "ia-case-migration.jar" ]
