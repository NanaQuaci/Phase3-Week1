FROM maven:3.9.6-eclipse-temurin-21

# Set working directory
WORKDIR /app

# Install dependencies
RUN apt-get update && apt-get install -y \
    wget curl unzip gnupg2 fonts-liberation libnss3 libxss1 \
    libappindicator3-1 libasound2 xdg-utils xvfb libgbm1 libvulkan1 --no-install-recommends && \
    rm -rf /var/lib/apt/lists/*

# Install specific version of Google Chrome (matching ChromeDriver 131.0.6778.204)
RUN CHROME_VERSION="131.0.6778.204" && \
    wget -q -O /tmp/chrome-linux64.zip "https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chrome-linux64.zip" && \
    unzip /tmp/chrome-linux64.zip -d /tmp/ && \
    mkdir -p /opt/google && \
    mv /tmp/chrome-linux64 /opt/google/chrome && \
    ln -s /opt/google/chrome/chrome /usr/bin/google-chrome && \
    rm -rf /tmp/chrome-linux64.zip



# Install a specific ChromeDriver version (update this version as needed)
RUN CHROMEDRIVER_VERSION="131.0.6778.204" && \
    echo "Installing ChromeDriver version: $CHROMEDRIVER_VERSION" && \
    wget -q -O /tmp/chromedriver.zip "https://storage.googleapis.com/chrome-for-testing-public/$CHROMEDRIVER_VERSION/linux64/chromedriver-linux64.zip" && \
    unzip /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm -rf /tmp/chromedriver.zip /tmp/chromedriver-linux64


# Install Node.js and npm
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Install Allure CLI
RUN npm install -g allure-commandline --save-dev

# Set display for headless Chrome
ENV DISPLAY=:99

# Copy your project files into the image
COPY . .

# Download all Maven dependencies in advance
RUN mvn dependency:go-offline

# Run tests when container starts
CMD ["sh", "-c", "Xvfb :99 & mvn clean test && mkdir -p allure-report && allure generate allure-results --clean -o allure-report"]