//Determina se o nó foi reconhecido pela rede como um de seus menbros. 
boolean reconhecido_pela_rede = false;

//Defini o tamanho da menssagem recebida pela rede.
int tamanho_da_msg =8;

//Podemos suportar até 5 tipos de sensores por nó.
//Cada tipo de sensor possui um código de dois dígitos.
//Ex: ST1L1L2

int identificadorDeServicos = 0;

//Variável com os tipos de sensores presentes no nó.
String sensores[5]= {"T1","L1","","",""};
  
//Identificador do nó
String MY = "01";

//Tempo entre sensoriamentos em segundos.
int TempoEntreSensoriamentos = 20;
double ultimoSensoriamento = millis();

//Serviços armazenados em memória.Utilizaremos um total de 05 serviços.
typedef struct Servico {
  int livre;
  int idServico;
  String p[4];
  String tipo;
  int idSensor;
  double ultimoAtendimento;
  int iteracoes;
};




//Variável que armazena os serviços solicitados.Atualmente consideramos apenas cinco serviços.
const int numeroDeEspacosNaAgenda = 5;
Servico agendaDeServicos[numeroDeEspacosNaAgenda];

//--------------------------------------------------------------------------------


//xxxxxxxxxxxxxxxxxxx Inicialização xxxxxxxxxxxxxxxxxxxx
void setup() {
   //Inicia e configuração a frequencia da comunicação.
   Serial.begin(9600);
   //Configuração dos pinos disponíveis para os sensores.
   pinMode(A0, INPUT);
   pinMode(A1, INPUT);
   pinMode(A2, INPUT);
   pinMode(A3, INPUT);
   pinMode(A4, INPUT);
   pinMode(A5, INPUT);
   //Inicialização do armazenamento de serviços.  
   for (int i=0; i<5;i++){agendaDeServicos[i].livre=1;} 
}
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//xxxxxxxxxxxxxxxxx  Sensoriamento xxxxxxxxxxxxxxxxxxxxxxxxxx
void sensoriamento(){
   
  if (reconhecido_pela_rede==true){
      
    if ((millis() - ultimoSensoriamento ) > (TempoEntreSensoriamentos*1000)) {
        Serial.println("$Sensoriando...");
       
        atendendoServicosAgendados ();
        
        ultimoSensoriamento = millis();}
    else{
        Serial.print("Proximo sensoriamento em :");
        Serial.print(((1000*TempoEntreSensoriamentos) - (millis()-ultimoSensoriamento))/1000);
        Serial.println();}
   }  
}
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//xxxxxxxxxxxxxxxxxxxxxxxxx servicoEvento xxxxxxxxxxxxxxxxxx
//Faz o tratamento dos serviços dependentes de eventos
void servicoEvento(int i){
  
 float valorSensor(agendaDeServicos[i].idSensor);
 String p0 = agendaDeServicos[i].p[0];
 String p2 = agendaDeServicos[i].p[2];
 
 
 
  
}
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx



//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
void servicoTemporal(int i)
{
           //variávies para contabilizar o tempo de intervalo da solicitação do serviço.       
           
           long intervaloEnvio,intervaloEnvioHora, intervaloEnvioMin, intervaloEnvioSeg;
           /*Hora*/    intervaloEnvioHora = strToint(agendaDeServicos[i].p[1]) * 360;
           /*Minuto*/  intervaloEnvioMin = strToint(agendaDeServicos[i].p[2])*60 ;
           /*Segundo*/ intervaloEnvioSeg = strToint(agendaDeServicos[i].p[3]);
           intervaloEnvio = intervaloEnvioHora+intervaloEnvioMin+intervaloEnvioSeg;
          
           if (  abs(((millis()/1000) - (agendaDeServicos[i].ultimoAtendimento)))  >= intervaloEnvio ){
            
            float valorAnalogico = valorSensor(agendaDeServicos[i].idSensor);
            Serial.print("$>>> valorSensor : ");
            Serial.println(valorAnalogico);
                           
            //Verificamos se já foram entregues todas as solicitações do servico.            
            if(agendaDeServicos[i].iteracoes==0)
              //Liberamos o slot da agenda de servicos para o atendimento concluido.
              {agendaDeServicos[i].livre=1;}
            else
              //Reduzimos a quantidade de iteracoes solicitadas
              {agendaDeServicos[i].iteracoes = agendaDeServicos[i].iteracoes - 1;
              agendaDeServicos[i].ultimoAtendimento=(millis()/1000);}
             } 
          else
            {Serial.print("$Proximo atendimento do servico: ");
             Serial.print((intervaloEnvio+ agendaDeServicos[i].ultimoAtendimento) - (millis()/1000) );
             Serial.println();}
}
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//xxxxxxxxxxxxxxxxxxx 
void atendendoServicosAgendados (){
//Faz uma varredura na agenda de servicos, verifica os serviços agendados e atende quando necessário.
Serial.println("$Atendendo servicos agendados...");

for (int i=0; i<4;i++){
     
   if (agendaDeServicos[i].livre==0){
         Serial.print("$SlotDaAgenda: ");
         Serial.print(i);
         Serial.print("| idServico: ");
         Serial.print(agendaDeServicos[i].idServico);
         Serial.print("| tipo: " + agendaDeServicos[i].tipo);
         //Serial.print("| p0: " +  agendaDeServicos[i].p[0]);
        // Serial.print("| p1: " +  agendaDeServicos[i].p[1]);
         Serial.print("| p2: " +  agendaDeServicos[i].p[2]);
         Serial.print("| p3: " +  agendaDeServicos[i].p[3]);
         Serial.print("| itera: ");  
         Serial.print(agendaDeServicos[i].iteracoes);
         Serial.print("| uAt: ");  
         Serial.println(agendaDeServicos[i].ultimoAtendimento);
 
         //Politica de entrega do servico por intervalo de tempo...
         if (agendaDeServicos[i].tipo.equals("T")) {
             servicoTemporal(i);}
         
         if (agendaDeServicos[i].tipo.equals("E")) {
             servicoEvento(i);}
       
    
   }
    

}



}


//xxxxxxxxxxxxxxxxxx Loop de exeução xxxxxxxxxxxxxxxxxxxxxxxx
void loop() {
   //Fica escutando o meio e envia msgs quando solicitado.
   comunicacao();
   
   //Tempo entre o sensoriamento e atendimento dos serviçõs agendados.
   sensoriamento();
   
   delay(2000);
}  
   
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//xxxxxxxxxxxxxxxxxxxxx  comunicacao   xxxxxxxxxxxxxxxxxxxxxx
void comunicacao(){
//Função responsável pela comunicação do nó com o meio.  
  int incomingByte;
  
  //Variável que recebe o conteudo do buffer de recebimento de menssagens.
  String msgRecebida = "";
  
  //Verificamos se o buffer de recebimento de msgs contém alguma informação.    
  if(Serial.available()) {
    //Atribuimos todo o conteudo do buffer para a variavel 
    while (Serial.available()>0)
      {msgRecebida = msgRecebida + byte(Serial.read());}
    msgRecebida = msgRecebida.trim();
    
    Serial.print("$Serial: " );
    Serial.print("[" + msgRecebida + "]");
    Serial.println();
    //Limpamos o buffer de menssagens.
    Serial.flush();

//Verificação do recebimento de menssagens...
//Menssagem de confirmação de apresentação. 
if (msgRecebida.equals(MY+"#OK")==true){
    reconhecido_pela_rede=true;
    ultimoSensoriamento = millis();
    Serial.println("$Estou na rede!");}

//Mensagem de solicitação de sensor.
//Solicita o retorno da medição do sensor 1.
//SINTAXE MY#SX-Y(A,B,C)

//Polica de entrega por evento..
//SINTAXE MY#SX-E(A,B,C)
//SINTAXE MY#SX-E(=,10,0,0)  v=10
//SINTAXE MY#SX-E(>,10,0,0)  v>10
//SINTAXE MY#SX-E(<,10,0,0)  v<10
//SINTAXE MY#SX-E(<=,10,0,0) v<=10
//SINTAXE MY#SX-E;<;10;>;5  5<v<10

//Y = E ou T
//A - h 
//B - m
//C -s
//D - n repeticao
//T > A= intervalo e B = repetições.
//E > A= expressao01 e B = expressao02.

//Parsing da msg de solicitação...
//Separamos em antes e depois do -


//Verificamos se a msg é uma msg de solicitação.
int i= msgRecebida.indexOf(MY+"#S");
int j= msgRecebida.length();

//Identificamos o id do sensor solicitado.  
  String STemp= msgRecebida.substring(MY.length()+2, MY.length()+3);
  int idSensor = strToint(STemp);

if ( ((i>=0) && (j==5) && (reconhecido_pela_rede==true)) ){
  Serial.print("$>>> valorSensor : ");
  Serial.println(valorSensor(idSensor));
}
 
if ( ((i>=0) && (j>5) && (reconhecido_pela_rede==true)) ){
//A msg recebida é uma msg de solicitação.
  Serial.println("$Analisando msg...");
  
  String PEntrega="";
  //Verificamos se possui alguma politica de entrega diferenciada
 
  if ( msgRecebida.length() >= (MY.length()+3) ){
  //Fazemos o parsing da msg e pegamos seus parametros. 
    //Identificamos a política de entrega
    PEntrega= msgRecebida.substring(MY.length()+4, MY.length()+5);
    
    //Variável para armazenar os quatro parâmetros da solicitação de serviço.
    String parametrosMsg[4]; 
    
    //Exemplo de SINTAXE: MY#SX-E;<;10;>;5  5<v<10
   
   //Variável temporária pra manipulação da menssagem.
   STemp = msgRecebida;
   
   for(i=0;i<4;i++){
      parametrosMsg[3-i]=STemp.substring(STemp.lastIndexOf(";")+1, STemp.length()  );
      STemp = STemp.substring(0, STemp.lastIndexOf(";"));}
      
 // String armazenaServicoNaAgenda( String parametros[4], String tipo, int idSensor){
  armazenaServicoNaAgenda(parametrosMsg,PEntrega,idSensor);
  }
  
}


  }


  if (reconhecido_pela_rede==false){
     Apresentacao();
  }  
  else
     {
  //   delay(2000);
     } 
}



void gerenteDeServicos(int idSensor, int politicaEntrega )
{

}


//xxxxxxxxxxxxxx   armazenaServicoNaAgenda    xxxxxxxxxxxxx
//Armazena um serviço recebino na agenda de serviços ativos.
String armazenaServicoNaAgenda( String parametros[4], String tipo, int idSensor){
  Serial.println("$Agendando servico...");
  String retorno = "Nao temos espaco na agenda para esse servico";
  
  
  //Espaços disponiveis na agenda para se alocar um serviço agendado.
 // Servico  slotAgenda;
  
  //Procura uma espaço livre na agenda de serviços para armazenar o novo serviço.
  for (int i =0;i<5;i++){
   //   slotAgenda = agendaDeServicos[i];
      if (agendaDeServicos[i].livre == 1){
          agendaDeServicos[i].livre = 0;
          identificadorDeServicos = identificadorDeServicos + 1;
          agendaDeServicos[i].idServico = identificadorDeServicos; 
          agendaDeServicos[i].p[0] = parametros[0];
          agendaDeServicos[i].p[1] = parametros[1];
          agendaDeServicos[i].p[2] = parametros[2];
          agendaDeServicos[i].p[3] = parametros[3];
          agendaDeServicos[i].tipo = tipo;
          agendaDeServicos[i].idSensor = idSensor;
          agendaDeServicos[i].ultimoAtendimento = (millis()/1000);
          retorno = "Servico agendado";
          agendaDeServicos[i].iteracoes=strToint(parametros[0]);
          break;
          }
   }
Serial.println("$"+retorno);   
return retorno;}
//xxxxxxxxxxxxxxxxxxxxxxxxxxx



//xxxxxxxxxxxxxxxxxxxxxxxxxxx
//A cada cinco segundos envia uma mensagem se apresentando, isto é, informando o seu id e os sensores disponíveis.
void Apresentacao(){
//Envia uma menssagem ao gateway informando o identificador e os sensores pertencentes ao nó.
   Serial.println("$No se apresentando..."); 
   String msg_sensores="S";  
   //Construimos a msg a ser enviada.
   for (int i=0; i<4; i++)
      {msg_sensores= msg_sensores + sensores[i];}
   //Enviamos a msg.  
   Serial.println(MY + "#" + msg_sensores);
   //Espero 5 segundos antes de verificar se obteve resposta. 
   delay(10000);}
//xxxxxxxxxxxxxxxxxxxxxxxxxxx



 float valorSensor(int pinoSensor){
  
 // float valor = analogRead(pinoSensor);
  float valor = analogRead(pinoSensor);
    
  //sensores[pinoSensor];    
 //  + valor  ;
  
  return valor;
}


int strToint(String entrada){
char this_char[entrada.length() + 1];
entrada.toCharArray(this_char, sizeof(this_char));
return atoi(this_char);}
