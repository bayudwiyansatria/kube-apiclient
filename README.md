# Kube API Client for Spring Boot

[![Main](https://github.com/bayudwiyansatria/kube-apiclient/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/bayudwiyansatria/kube-apiclient/actions/workflows/main.yml)
[![codecov](https://codecov.io/gh/bayudwiyansatria/kube-apiclient/graph/badge.svg)](https://codecov.io/gh/bayudwiyansatria/kube-apiclient)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.1%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)
[![License](https://img.shields.io/github/license/bayudwiyansatria/kube-apiclient)](LICENSE)

![Platforms](https://img.shields.io/badge/%20Platforms-Windows%20/%20Linux-blue.svg?style=flat-square)

A Spring Boot project for managing Kubernetes resources such as secrets. This repository provides a structured starting point for integrating Kubernetes API with Spring Boot applications.

---

## Features

- Integration with Kubernetes API using the official Kubernetes Java client
- Pre-configured Spring Boot setup
- Ready-to-use Maven build scripts
- Example code for managing Kubernetes secrets
- Comprehensive documentation and guidelines

---

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Dependencies](#dependencies)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Versioning](#versioning)
- [Authors](#authors)
- [Acknowledgments](#acknowledgments)

---

## Getting Started

### Dependencies

This project requires the following dependencies:

- **Java 11 or higher**: Ensure you have Java installed. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Maven 3.6 or higher**: Install Maven for building the project. You can download it from [Maven's official site](https://maven.apache.org/).
- **Kubernetes cluster**: A local or remote Kubernetes cluster is required. You can use [Minikube](https://minikube.sigs.k8s.io/docs/) for local development.

---

### Installation

To set up the development environment, follow these steps:

1. **Clone the repository**:

   ```bash
   git clone https://github.com/bayudwiyansatria/kube-apiclient.git
   cd kube-apiclient
   ```

2. **Build the project**:

   ```bash
   mvn clean package
   ```

3. **Run the application**:

   ```bash
   java -jar target/kube-apiclient-0.1.0.jar
   ```

---

## Usage

When deployed in Kubernetes, leave `KUBERNETES_CONFIG_FILE_PATH` empty. The application then uses
the pod service account. Configure namespace-scoped Roles and RoleBindings for only the Secrets the
service is expected to reconcile. Set `KUBERNETES_CONFIG_FILE_PATH` only for local development with
an explicit kubeconfig file.

`PUT /api/v1/secret/{namespace}/{name}` is an idempotent upsert operation: it creates a missing
Secret and replaces the data of an existing Secret.

1. **Configure Kubernetes Access**:
   - Ensure your Kubernetes cluster is accessible via `kubectl`.
   - Update the `application.yml` file with your Kubernetes configuration.

2. **Run the Application**:
   - Start the Spring Boot application using the command:
     ```bash
     java -jar target/kube-apiclient-0.1.0.jar
     ```

3. **Manage Kubernetes Secrets**:
   - Use the provided REST API endpoints to manage Kubernetes secrets.

---

## Contributing

Contributions are welcome! Please follow the [Contributing Guidelines](CONTRIBUTING.md) to submit issues or pull requests.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Versioning

This project uses [Semantic Versioning](https://semver.org/). For the available versions, see the [tags on this repository](https://github.com/bayudwiyansatria/kube-apiclient/tags).

---

## Authors

- **Bayu Dwiyan Satria** - _Initial work_ - [GitHub Profile](https://github.com/bayudwiyansatria)

See also the list of [contributors](https://github.com/bayudwiyansatria/kube-apiclient/contributors) who participated in this project.

---

## Acknowledgments

- Thanks to the Kubernetes and Spring Boot communities for their excellent documentation and tools.
- Inspired by best practices in Kubernetes and Spring Boot development.
