COMPONENT=baseAppC
BUILD_EXTRA_DEPS += base.class
CLEAN_EXTRA = *.class baseMsg.java

CFLAGS += -I$(TOSDIR)/lib/T2Hack

base.class: $(wildcard *.java) baseMsg.java
	javac -target 1.4 -source 1.4 *.java

baseMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=baseMsg http.h http_msg -o $@


include $(MAKERULES)
