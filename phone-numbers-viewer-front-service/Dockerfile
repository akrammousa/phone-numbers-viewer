### STAGE 1: Build ###

# We label our stage as ‘builder’
FROM node:10-alpine as builder


WORKDIR /usr/local/app

# copy the package.json to install dependencies
COPY package.json package-lock.json ./



# Install all the dependencies
RUN npm install --force

# Add the source code to app
COPY . /usr/local/app/

# Generate the build of the application
RUN npm rebuild node-sass
RUN node --max_old_space_size=8192 node_modules/@angular/cli/bin/ng build  --prod --output-path=dist


### STAGE 2: Setup ###

FROM nginx:1.14.1-alpine

## Copy our default nginx config
COPY nginx/default.conf /etc/nginx/nginx.conf

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /usr/local/app/dist /usr/share/nginx/html


