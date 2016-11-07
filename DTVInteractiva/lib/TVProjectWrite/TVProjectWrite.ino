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
      //dump_byte_array(mfrc522.uid.uidByte, mfrc522.uid.size);
      //Serial.println();

      //Se transforma un String en un int array para escribir en la RFID
      Serial.println("Writing ---------------");
      stringToInt();
      Writing();
      data = "";

      /*
        //Se lee la RFID y se obtiene un int array que se transforma en un char ------------------------- se debe pasar a String.
        Reading();
        intToString();
        Serial.print("String:   -->");Serial.println(data);
      */

      // Halt PICC
      mfrc522.PICC_HaltA();
      // Stop encryption on PCD
      mfrc522.PCD_StopCrypto1();
      delay(2000);
    }
  }
}

void Writing() {
  MFRC522::StatusCode status;

  // Show the whole sector as it currently is
  Serial.println(F("Current data in sector:"));
  mfrc522.PICC_DumpMifareClassicSectorToSerial(&(mfrc522.uid), &key, sector);
  Serial.println();

  byte dataBlock[16];
  for (int i = 0; i < 16; i++) {
    dataBlock[i] = int_array[i];
  }

  // Write data to the block
  Serial.print(F("Writing data into block ")); Serial.print(blockAddr);
  Serial.println(F(" ..."));
  dump_byte_array(dataBlock, 16); Serial.println();
  status = (MFRC522::StatusCode) mfrc522.MIFARE_Write(blockAddr, dataBlock, 16);
  if (status != MFRC522::STATUS_OK) {
    Serial.print(F("MIFARE_Write() failed: "));
    Serial.println(mfrc522.GetStatusCodeName(status));
  }
  Serial.println();
}

void stringToInt() {
  // Define
  data = "ID:JuanCamilo";
  int str_len = 16;

  str.toCharArray(char_array, str_len);
  for (int i = 0; i < str_len; i++) {
    int_array[i] = (int)char_array[i];
  }
}

/**
   Helper routine to dump a byte array as hex values to Serial.
*/
void dump_byte_array(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    Serial.print(buffer[i]);
  }
}

