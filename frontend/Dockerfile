FROM node:lts-alpine3.20 as build

WORKDIR /app/src
COPY package*.json ./
RUN npm ci
COPY . ./
RUN npm run build

FROM node:lts-alpine3.20

WORKDIR /app
ENV PROJECT_NAME=sabacc
COPY --from=build /app/src/dist/$PROJECT_NAME ./
CMD node server/server.mjs
