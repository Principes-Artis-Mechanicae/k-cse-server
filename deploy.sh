#!/bin/bash

# Constants
DEVELOP_ENV_FILE=".env.develop"
RELEASE_ENV_FILE=".env.release"

# Global variables
DEV_VERSION=""
REL_VERSION=""
ENV_FILE=""

# Functions
get_version() {
  local env_file=$1
  grep '^# Version' "$env_file" | head -n 1 | cut -d' ' -f3-
}

select_build_type() {
  echo "Select build type:"
  echo "1) Development (Current Version: $DEV_VERSION)"
  echo "2) Release (Current Version: $REL_VERSION)"
  read -p "Choice build type: " build_type

  case $build_type in
    1) ENV_FILE="$DEVELOP_ENV_FILE" ;;
    2) ENV_FILE="$RELEASE_ENV_FILE" && exit 1 ;;
    *) echo "deploy.sh: Invalid choice. Exiting script." && exit 1 ;;
  esac
}

check_env_file() {
  if [ ! -f "$ENV_FILE" ]; then
    echo "deploy.sh: File $ENV_FILE not found. Exiting script."
    exit 1
  fi
}

source_env_file() {
  if [ -f "$ENV_FILE" ]; then
    # shellcheck disable=SC1090
    source "$ENV_FILE"
    echo "deploy.sh: Environment variables from $ENV_FILE:"
  else
    echo "deploy.sh: File $ENV_FILE not found. Exiting script."
    exit 1
  fi
}


# Main script execution
DEV_VERSION=$(get_version "$DEVELOP_ENV_FILE")
REL_VERSION=$(get_version "$RELEASE_ENV_FILE")
select_build_type
check_env_file
source_env_file

echo ""

# Java Build
echo "---------------------------------"
echo "deploy.sh: Starting Java build..."
./gradlew clean build
echo "deploy.sh: Java build completed."
echo "---------------------------------"

echo ""

# Run Docker Compose
echo "---------------------------------"
echo "deploy.sh: Starting Docker Compose..."
sudo docker-compose --env-file "$ENV_FILE" up -d --build
echo "deploy.sh: Docker Compose up and running."
echo "---------------------------------"

echo ""
echo "deploy.sh: deploy complete"
