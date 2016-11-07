#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN  9    //Pin 9 para el reset del RC522
#define SS_PIN  10   //Pin 10 para el SS (SDA) del RC522
MFRC522 mfrc522(SS_PIN, RST_PIN); //Creamos el objeto para el RC522
MFRC522::MIFARE_Key key;


//
String data = "";
int int_array[16];
char char_array[16];
// that is: sector #1, covering block #4
byte sector         = 1;
byte blockAddr      = 4;
byte buffer[18];
byte size = sizeof(buffer);


void setup() {
  Serial.begin(9600); //Iniciamos la comunicación  serial
  SPI.begin();        //Iniciamos el Bus SPI
  mfrc522.PCD_Init(); // Iniciamos  el MFRC522

  // Prepare the key (used both as key A and as key B)
  // using FFFFFFFFFFFFh which is the default at chip delivery from the factory
  for (byte i = 0; i < 6; i++) {
    key.keyByte[i] = 0xFF;
  }

  //Serial.println("Write and Read in RFID: ");
}

void loop() {

  // Look for new cards
  if (mfrc522.PICC_IsNewCardPresent()) {
    // Select one of the cards
    if (mfrc522.PICC_ReadCardSerial()) {

      //PRUEBA CON JAVA EN PUERTO SERIAL
      /*
        stringToInt();
        for (int i = 0; i < 16; i++) {
        int_array[i] = (int)char_array[i];
        Serial.print(int_array[i]);Serial.print(" ");
        }
        Serial.println();
        delay(5000);
      */


      // Show some details of the PICC (that is: the tag/card)
      //Serial.print(F("Card UID:"));
      dump_byte_array(mfrc522.uid.uidByte, mfrc522.uid.size);
      //Serial.println();

      //Se transforma un String en un int array para escribir en la RFID
      /*
        stringToInt();
        Writing();
      */

      //Se lee la RFID y se obtiene un int array que se transforma en un string
      //Serial.println("Reading --------------");
      Reading();
      intToString();
      //Serial.print("String:   -->");Serial.println(data);
      data = "";

      // Halt PICC
      mfrc522.PICC_HaltA();
      // Stop encryption on PCD
      mfrc522.PCD_StopCrypto1();
      delay(2000);
    }
  }
}

void Reading() {
  MFRC522::StatusCode status;

  // Show the whole sector as it currently is
  //Serial.println(F("Current data in sector:"));
  mfrc522.PICC_DumpMifareClassicSectorToSerial(&(mfrc522.uid), &key, sector);
  //Serial.println("estedato");

  // Read data from the block
  // Serial.print(F("Reading data from block ")); Serial.print(blockAddr);
  //Serial.println(F(" ..."));
  mfrc522.MIFARE_Read(blockAddr, buffer, &size);
  //Serial.print(F("Data in block ")); Serial.print(blockAddr); Serial.println(F(":"));
  dump_byte_array(buffer, 16);// Serial.println();
  for (int i = 0; i < 16; i++) {
    int_array[i] = buffer[i];
    Serial.print(int_array[i]);
  }
  //Serial.println();

}

void intToString() {
  for (int i = 0; i < 16; i++) {
    char_array[i] = char(int_array[i]);
    data.concat(char_array[i]);
    //Serial.print(char_array[i]);
  }
  //Serial.println();
}

/**
   Helper routine to dump a byte array as hex values to Serial.
*/
void dump_byte_array(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    //Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    //Serial.print(buffer[i]);
  }
}

