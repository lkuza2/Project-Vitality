// Define the pins we're going to call pinMode on
int relay = D2;  // You'll need to wire an LED to this one to see it blink.
int greenled = D3;
int redled = D4;
int state = 0;

// This routine runs only once upon reset
void setup() {
  // Initialize D0 + D7 pin as output
  // It's important you do this here, inside the setup() function rather than outside it or in the loop function.
  Spark.function("state", setState);
  Spark.function("getstate", getState);
  Spark.function("cstate", setCustomState);
  Spark.function("setlight", setLightState);


  pinMode(relay, OUTPUT);
  pinMode(redled, OUTPUT);
  pinMode(greenled, OUTPUT);
}

// This routine gets called repeatedly, like once every 5-15 milliseconds.
// Spark firmware interleaves background CPU activity associated with WiFi + Cloud activity with your code. 
// Make sure none of your code delays or blocks for too long (like more than 5 seconds), or weird things can happen.
void loop() {
             // Wait for 1 second in off mode
}

int setLightState(String command){
    if(command == "red"){
      digitalWrite(redled, HIGH);   
      digitalWrite(greenled, LOW);
      return 2;
    }else if(command == "gr"){
      digitalWrite(redled, LOW);   
      digitalWrite(greenled, HIGH);
      return 1;
    }else{
      digitalWrite(redled, LOW);   
      digitalWrite(greenled, LOW);
        return 0;
    }
}

int setState(String command){
    if(state == 0){
      digitalWrite(relay, HIGH);
      state = 1;
      return 1;
    }else{
      digitalWrite(relay, LOW);
      state = 0;
      return 0;
      }
      
}

int setCustomState(String command){
    if(command == "on"){
      digitalWrite(relay, HIGH);
      state = 1;
      return 1;
      }else{
      digitalWrite(relay, LOW);
      state = 0;
      return 0;
      }
      
}

int getState(String command){
    return state;
}