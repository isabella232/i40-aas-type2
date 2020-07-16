# i40-aas-type2

**i40-aas-type2** welcomes [contributions](#contributing). Please read about [known issues](#known-issues) and [upcoming changes](#upcoming-changes).



## Contents

- [i40-aas-type2](#i40-aas-type2)
  - [Contents](#contents)
  - [Main Features](#main-features)
  - [Prerequisites](#prerequisites)
  - [Instructions](#instructions)
    - [Getting Started](#getting-started)
  - [Configuration](#configuration)
  - [Known Issues](#known-issues)
  - [Get Support](#get-support)
  - [Contributing](#contributing)
  - [Upcoming Changes](#upcoming-changes)
  - [License](#license)

## Main Features

The AAS-Service provides an implementation of the RAMI 4.0 reference architecture as specified by [Plattform Industrie 4.0](https://www.plattform-i40.de/PI40/Redaktion/DE/Downloads/Publikation/Details-of-the-Asset-Administration-Shell-Part1.html). Main features include:

Following that, we provide the implementation of an asset administration shell type 2.

## Prerequisites

You need to install [Docker](https://www.docker.com) and [Docker Compose](https://docs.docker.com/compose/). Docker Compose comes with Docker if you're on Mac or Windows ([check here for Linux](https://docs.docker.com/compose/install/)). Check the installation by executing:

```bash
$ docker --version
## tested with: 19.03.8

$ docker-compose version
## tested with: 1.25.4, build 8d51620a
```

For easier usage, install Make and verify as follows:

```bash
$ make --version
## tested with: GNU Make 3.81
```

Optionally, you can download and install [Postman](https://www.getpostman.com) to [test the services](docs/markdown/test.md).

## Instructions

### Getting Started

Service interactions can be deployed, tested and developed locally using Docker Compose as described below. For further instructions & information about **i40-aas-type2** check [the documentation](docs/README.md)


## Configuration

The local setup uses the default configurations specified in the `.env` file.

## Known Issues

<!--- Please list all known issues, or bugs, here. Even if the project is provided "as-is" any known problems should be listed. --->

Please refer to the list of [issues](https://github.com/SAP/i40-aas-type2/issues) on GitHub.

## Get Support

<!--- This section should contain details on how the outside user can obtain support, ask questions, or post a bug report on your project. If your project is provided "as-is", with no expected changes or support, you must state that here. --->

Please study the [help provided](docs/README.md) and use the [GitHub issue tracker](https://github.com/SAP/i40-aas-type2/issues) for further assistance, bug reports or feature requests.

## Contributing

<!--- Details on how external developers can contribute to your code should be posted here. You can also link to a dedicated CONTRIBUTING.md file. See further details here. --->

You are welcome to join us in our efforts to improve and increase the set of tools to realize the Asset Administration Shell for Industrie 4.0!

Simply check the [Contribution Guidelines](CONTRIBUTING.md).

## Upcoming Changes

<!--- Details on any expected changes in later versions. If your project is released "as-is", or you know of no upcoming changes, this section can be omitted. --->

This project follows the specification ["Details of the AssetAdministrationShell"](https://www.plattform-i40.de/PI40/Redaktion/EN/Downloads/Publikation/2018-details-of-the-asset-administration-shell.html) part 1 version 1.0, which is work in progress. As the specification changes, so will this project.

## License

Copyright (c) SAP SE or an SAP affiliate company and *i40-aas-type2* contributors. All rights reserved. This repository is licensed as noted in [LICENSES](LICENSES).

Please note that Docker images can contain other software which may be licensed under different licenses. This License file is also included in the Docker image. For any usage of built Docker images please make sure to check the licenses of the artifacts contained in the images.
