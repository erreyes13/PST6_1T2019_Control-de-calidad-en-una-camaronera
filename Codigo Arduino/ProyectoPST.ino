// Librerias para el modulo de wifi
#include <ESP8266WiFi.h>

// Librerias para el sensor DS18B20
#include <DallasTemperature.h>
#include <OneWire.h>


// **************************Definicion de variables y constantes*********************************

// CREDENCIALES DE ACCESO A INTERNET
const char* ssid     = "POCOPHONE";
const char* password = "winteralava";
const char* host = "watercontrol.ddns.net";

// DEFINICION DE SENSOR DS18B20
const int pinDatosDQ = 4;
OneWire oneWireObjeto(pinDatosDQ);
DallasTemperature sensorDS18B20(&oneWireObjeto);

// DEFINICION DE SENSOR DE pH
const int analogInPin = A0;
int sensorValue = 0;
unsigned long int avgValue;
float b;
int buf[10], temp1;


void setup() {
  Serial.begin(9600);

  // SETUP CONEXION WIFI
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);
  // Se inicializa el wifi
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  // SETUP SENSOR DS18B20
  sensorDS18B20.begin();

}

void loop() {

  // LEYENDO SENSOR DS18B20
  Serial.println("Mandando comandos a los sensores");
  sensorDS18B20.requestTemperatures();
  Serial.print("Temperatura sensor DS18B20: ");
  Serial.print(sensorDS18B20.getTempCByIndex(0));
  float temp = sensorDS18B20.getTempCByIndex(0);
  
  // LEYENDO SENSOR DE pH
  for (int i = 0; i < 10; i++)
  {
    buf[i] = analogRead(analogInPin);
    delay(10);
  }
  for (int i = 0; i < 9; i++)
  {
    for (int j = i + 1; j < 10; j++)
    {
      if (buf[i] > buf[j])
      {
        temp1 = buf[i];
        buf[i] = buf[j];
        buf[j] = temp1;
      }
    }
  }
  avgValue = 0;
  for (int i = 2; i < 8; i++)
    avgValue += buf[i];
  float pHVol = (float)avgValue * 5.0 / 1024 / 6;
  float phValue = -5.70 * pHVol + 21.34;
   
  Serial.print("sensor = ");
  Serial.println(phValue);

  // REALIZANDO CONEXION A INTERNET
  Serial.print("connecting to ");
  Serial.println(host);

  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  String url = "/enviodedatos.php";
  String key = "?pass=1234";
  String dato1 = "&temperatura=";
  String dato2 = "&ph=";

  Serial.print("Requesting URL: ");
  Serial.println(url);

  // Se envia el paquete de datos por medio de internet, para ejecutar el archio php en el servidor
  // Para que el servidor ejecute los querys en la base de datos
  client.print(String("GET ") + url + key + dato1 + temp + dato2 + phValue + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" +
               "Connection: close\r\n\r\n");
  unsigned long timeout = millis();
  while (client.available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }


  while (client.available()) {
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }

  Serial.println();
  Serial.println("closing connection");

  delay(15000);
}
