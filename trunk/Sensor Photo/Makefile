COMPONENT=SensorAppC
BUILD_EXTRA_DEPS = SensorMsg.py SensorMsg.class

SensorMsg.py: http.h
	mig python -target=$(PLATFORM) $(CFLAGS) -python-classname=SensorMsg http.h http_msg -o $@

SensorMsg.class: SensorMsg.java
	javac SensorMsg.java

SensorMsg.java: http.h
	mig java -target=$(PLATFORM) $(CFLAGS) -java-classname=SensorMsg http.h http_msg -o $@


include $(MAKERULES)

