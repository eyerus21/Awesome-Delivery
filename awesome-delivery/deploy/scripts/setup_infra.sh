#!/bin/sh
helm install user --set auth.rootPassword=secretpassword,auth.username=user,auth.password=userpassword,auth.database=user bitnami/mongodb
helm install order --set auth.rootPassword=secretpassword,auth.username=order,auth.password=orderpassword,auth.database=order bitnami/mongodb
helm install restaurant --set auth.rootPassword=secretpassword,auth.username=restaurant,auth.password=restaurantpassword,auth.database=restaurant bitnami/mongodb
helm install driver --set auth.rootPassword=secretpassword,auth.username=driver,auth.password=driverpassword,auth.database=driver bitnami/mongodb
helm install my-kafka bitnami/kafka
helm install my-es bitnami/elasticsearch
helm install my-rabbitmq bitnami/rabbitmq


    helm install vehicle --set auth.rootPassword=secretpassword,auth.username=vehicle,auth.password=vehiclepassword,auth.database=vehicle bitnami/mongodb

    helm install account --set auth.rootPassword=secretpassword,auth.username=account,auth.password=accountpassword,auth.database=account bitnami/mongodb

    helm install search --set auth.rootPassword=secretpassword,auth.username=search,auth.password=searchpassword,auth.database=search bitnami/mongodb