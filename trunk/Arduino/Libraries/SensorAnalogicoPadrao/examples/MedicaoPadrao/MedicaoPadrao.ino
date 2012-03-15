#include "SensorAnalogicoPadrao.h"
float result;
SensorAnalogicoPadrao sensor(1);

void setup() {
  Serial.begin(19200);
}

void loop() {

  result=sensor.getResultado();

  Serial.println(result);
  delay(1000);
}

