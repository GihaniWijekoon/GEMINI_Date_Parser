# Use an official Node.js image
FROM node:20

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the application
COPY . .

# Expose Angular development server port
EXPOSE 4300

# Start Angular using the development server
CMD ["npm", "run", "start"]
