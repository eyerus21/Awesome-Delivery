#!/bin/sh
helm uninstall user
helm uninstall order
helm uninstall restaurant
helm uninstall driver
helm uninstall my-kafka
helm uninstall my-es
helm uninstall my-rabbitmq