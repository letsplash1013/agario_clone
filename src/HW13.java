//Press W to eject. Press Space to split
//I've tweaked cow.java a bit so DrawCircle accepts outline colour






//CHANGE LINE 205-6 in COW.java with this
//    
//        void drawCircle(Vector2 center, double radius, Vector3 fillColor, Vector3 outlineColor) {
//    _drawCenterShape(center, new Vector2(2 * radius), fillColor, 1); // Filled circle
//
//    // Draw the outline
//    _graphicsSetColor(outlineColor);
//    Vector2 pixelCenter = _windowPixelFromWorld(center);
//    int pixelRadius = (int) (_windowPixelsPerWorldUnit() * radius);
//    _graphics.drawOval((int) (pixelCenter.x - pixelRadius), (int) (pixelCenter.y - pixelRadius),
//                       2 * pixelRadius, 2 * pixelRadius);
//}

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//fix:
//splitseperation
//virusSplitting
//splitboost
//

class HW13 extends App {
    static class Player {
        Vector3 color;
        double radius;
        Vector2 position;
        double inertia;
        Boolean alive;
        Vector2 velocity;
        Vector2 boostVelocity;
        Boolean velocityBoost;
        int splitTimer;
        Boolean merge;
//        Boolean lock;
    }
    static class Blob {
        Vector3 color;
        double radius;
        Vector2 position;
        Boolean alive;
    }
    
    static class EjectedMass {
    
        Vector3 color;
        double radius;
        Vector2 position;
        Boolean alive;
        Vector2 velocity;
        int immunity;
    }
    
    static class Virus {
        Vector3 color;
        double radius;
        Vector2 position;
        Boolean alive;
    }
    
    
    
    Blob[] blobs = new Blob[100]; 
    
    Virus[] viruses = new Virus[4];
    
    EjectedMass[] ejectedMasses = new EjectedMass[200];
    
    ArrayList<Player> players;
    
    static int frameSize = 128; 
    
    int velocityLimit = 1;
    
    Boolean virusUpOrNo;
    
Vector2[] virusSplitAngles = {
    new Vector2(1, 0),
    new Vector2(0, 1),
    new Vector2(-1, 0),
    new Vector2(0, -1),
    new Vector2(0.9239, 0.3827),
    new Vector2(-0.3827, 0.9239),
    new Vector2(-0.9239, -0.3827),
    new Vector2(0.3827, -0.9239),
    new Vector2(0.7071, 0.7071),
    new Vector2(-0.7071, 0.7071),
    new Vector2(-0.7071, -0.7071),
    new Vector2(0.7071, -0.7071),
    new Vector2(0.3827, 0.9239),
    new Vector2(-0.9239, 0.3827),
    new Vector2(-0.3827, -0.9239),
    new Vector2(0.9239, -0.3827)
};
    
    
    
    //referenced Functions
    
    //assumes u wants to merge into v


//void splitSeperator(){
//    for(int i = 0; i < players.size();++i){
//        Player player = players.get(i);
//        if(player.lock){
//        double magnitude = vectorMagnitudeFinder(player.velocity);
//        double angle = Math.atan2(player.velocity.x , player.velocity.y);
//        player.velocity = new Vector2(magnitude * Math.cos(angle) , magnitude*Math.sin(angle));
//        }
//    }
//    
//    for(int i = 0; i<players.size(); ++i){
//        
//        Player player1 = players.get(i);
//        double radiusSum;
//        double distanceBetweenCentersSquared;
//        for(int j = 0; j < players.size(); ++j){
//            Player player2 = players.get(j);
//            radiusSum = player1.radius + player2.radius;
//            distanceBetweenCentersSquared = Math.pow(vectorMagnitudeFinder(player1.position.minus(player2.position)),2);
//            
//            
//            if(Math.pow(radiusSum,2) > distanceBetweenCentersSquared){
//                double interleave = radiusSum - Math.sqrt(distanceBetweenCentersSquared);
//                double distanceX = player2.position.x - player1.position.x;
//                double distanceY = player2.position.y - player1.position.y;
//                double angle = Math.atan2(distanceX, distanceY);
//                
//                player2.position.x = Math.cos(angle)*(interleave/2);
//                player2.position.y = Math.sin(angle)*(interleave/2);
//                player2.position.x = Math.cos(angle)*(-interleave/2);
//                player2.position.x = Math.sin(angle)*(-interleave/2);
//                
//                
//                
//                player1.lock = true;
//                player2.lock = true;
//                
//            }
//        }
//        
//    }
//    
//}





//    double vectorAngleFinder(Vector2 speedU, Vector2 posU, Vector2 posV){
//        
//        if(vectorMagnitudeFinder(posV.minus(posU)) != 0 && vectorMagnitudeFinder(speedU) != 0){
//            return Math.acos(  (((posV.x - posU.x)*speedU.x)  +    ((posV.y - posU.y)*speedU.y))/(vectorMagnitudeFinder(posV.minus(posU)) *vectorMagnitudeFinder(speedU)  ));
//        }else{
//            return 0;
//        }
//    
//    
//    }
//    
//    Vector2 vFinal(Vector2 speedU, Vector2 posU, Vector2 posV){
//        double angle = vectorAngleFinder( speedU,  posU,  posV);
//        return new Vector2(speedU.x * Math.sin(angle), speedU.y * Math.sin(angle));
//    }
//    
    
//    void splitSeperationMechanism(ArrayList<Player> players){
//        for(int i = 0; i< 15 ; ++i){
//            for(int j = i + 1; j < 16; ++j){
//                 Player player1 = players.get(i);
//                 Player player2 = players.get(j);
//                 if(player1.alive && player2.alive && !player1.merge && !player2.merge && !player1.velocityBoost && player2.velocityBoost&& player1.splitTimer > player1.radius*2 && player2.splitTimer > player2.radius*2){
//                     if(circleCircleIntersection(new Vector2(player1.position.x + player1.velocity.x, player1.position.y + player1.velocity.y) , new Vector2(player2.position.x + player2.velocity.x, player2.position.y + player2.velocity.y), player1.radius, player2.radius, 0.99)){
//                         if(vectorMagnitudeFinder(player1.velocity) > vectorMagnitudeFinder(player2.velocity)){
//                             player1.velocity = vFinal(player1.velocity, player1.position, player2.position);
//                         }else{
//                             player2.velocity = vFinal(player2.velocity, player2.position, player1.position);
//                         }
//                         
//                     }
//                 }
//            }
//        }
//    }
    
    void jimSeperation(ArrayList<Player> players){
        for(int i = 0; i < players.size() ; ++i){
            for(int j =0; j<players.size() ; ++j){
                if (i == j) continue;
                
                Player player1 = players.get(i);
                Player player2 = players.get(j);
                
                double l = vectorMagnitudeFinder(player1.position.minus(player2.position));
                Vector2 eHat = Vector2.directionVectorFrom(player1.position, player2.position);
                double epsilon = player1.radius + player2.radius;
                
                
                if(player1.alive && player2.alive && !player1.velocityBoost && !player2.velocityBoost && !player1.merge && !player2.merge && l < epsilon){
                    if(vectorMagnitudeFinder(player1.position.minus(mousePosition)) > vectorMagnitudeFinder(player2.position.minus(mousePosition))){ 
                        
                        double a = 0.5;
                        player1.velocity.x += -(eHat.x)*(epsilon - l)*a;
                        player1.velocity.y += -(eHat.y)*(epsilon - l)*a;
                        player2.velocity.x += (eHat.x)*(epsilon - l)*a;
                        player2.velocity.y += (eHat.y)*(epsilon - l)*a;
                        
                        
                    }
                }
            }
        }
    }

    boolean circleCircleIntersection( Vector2 positionA , Vector2 positionB, double radiusA, double radiusB, double intersectionMultiplier){
        if(Vector2.distanceBetween(positionA, positionB) < intersectionMultiplier * (radiusA + radiusB)){
        return true; 
        }
        return false;
    }
    
    Vector2 unitVectorFinder(Vector2 vector){
        Vector2 unitVector = new Vector2();
        double vectorMagnitude = vectorMagnitudeFinder(vector);
        if(vectorMagnitude != 0){
            unitVector.x = vector.x/vectorMagnitude;
            unitVector.y = vector.y/vectorMagnitude;
        }else{
            unitVector.x = 0;
            unitVector.y = 0;
        }
        return unitVector;
                                          
    }
    
    Vector2 speedLimiter(Vector2 velocity, double speedLimit ){
        if(vectorMagnitudeFinder(velocity) > speedLimit){
            return new Vector2(unitVectorFinder(velocity).x * speedLimit, unitVectorFinder(velocity).y * speedLimit);
        }else{
            return new Vector2(velocity.x, velocity.y);
        }
    
    }
    
    double vectorMagnitudeFinder(Vector2 vector){
        return Math.sqrt(Math.pow(vector.x,2) + Math.pow(vector.y,2));
    }
    
    
    
    
    ///////non-referenced functions
    
    void updateVirus(){
    //update viruses
        for(int i = 0; i <viruses.length; ++i){
            Virus virus = viruses[i];
            if(virus.alive){
                drawCircle(virus.position, virus.radius, virus.color, new Vector3(0.0,0.7,0.0));
            }else if(Math.random() < 0.7){
                Vector2 position = new Vector2((Math.random() * frameSize*2) - frameSize, (Math.random() * frameSize*2) - frameSize);
                for(int j = 0; j< players.size() ; ++j){
                    if(!circleCircleIntersection(position, players.get(j).position, virus.radius, players.get(i).radius, 0.9)){
                        virus.alive = true;
                        virus.position = position;
                    }
                }
                
            }
            
        }
    }
    
    void splitting(){
        if(keyPressed(' ')){
           //count num of players alive 
            int playersAlive = 0;
            for(int i = 0; i < players.size(); ++i){
                if(players.get(i).alive){
                    playersAlive++;
                }
            }
            
            int targetAlive = 0;
            if(playersAlive*2 > 16){targetAlive = 16 - playersAlive;}else{targetAlive = playersAlive;}
            
            for(int i = 0; i < targetAlive; ++i){
                Player player = players.get(i);
                Player dupPlayer = players.get(i + playersAlive);
                
                Vector2 unitVelocity = unitVectorFinder(player.velocity);
                dupPlayer.alive = true;
//                dupPlayer.position.x = player.position.x + (unitVelocity.x * player.radius);
//                dupPlayer.position.y = player.position.y + (unitVelocity.y * player.radius);
                
                dupPlayer.position.x = player.position.x;
                dupPlayer.position.y = player.position.y;
                
                dupPlayer.radius = (player.radius * Math.sqrt(2)/2);
                player.radius = dupPlayer.radius;
                
                //todo
                dupPlayer.boostVelocity = new Vector2(player.velocity.x, player.velocity.y);
                
                
//                dupPlayer.velocity = speedLimiter(player.velocity, 0.5);
                dupPlayer.velocity.x = player.velocity.x + dupPlayer.boostVelocity.x;
                dupPlayer.velocity.y = player.velocity.y + dupPlayer.boostVelocity.y;
                dupPlayer.velocityBoost = true;  
            }
            
        }
    }
    
    void playerHitsVirus(){
        //if player hits virus
        for(int i = 0; i < players.size(); ++i){
            for(int j = 0 ; j< viruses.length; ++j){
                Player player = players.get(i);
                Virus virus = viruses[j];
                if(circleCircleIntersection(player.position, virus.position, player.radius, virus.radius, 0.6) && player.radius > Math.sqrt(137/Math.PI)  && virus.alive && player.alive){
                    for(int k = 0; k < players.size();++k){
                        Player dupPlayer = players.get(k);
                        if(!dupPlayer.alive){
                            virus.alive = false;
                            dupPlayer.alive = true;
                            dupPlayer.position.x = virus.position.x;
                            dupPlayer.position.y = virus.position.y;
                            
                            Vector2 randDirection = virusSplitAngles[k];
                            dupPlayer.boostVelocity = new Vector2(2*randDirection.x * vectorMagnitudeFinder(player.velocity), 2* randDirection.y * vectorMagnitudeFinder(player.velocity));
                            
                            dupPlayer.velocityBoost = true;


                            dupPlayer.radius = 0.5*Math.random()*player.radius;
                            player.radius = Math.sqrt(Math.pow(player.radius, 2) - Math.pow(dupPlayer.radius,2));
                            

                            
                            
                        }
                    }
                    //extra 100 mass
                    virus.alive = false;
                    player.radius = Math.sqrt((100/Math.PI) + Math.pow(player.radius,2));
                    
                    
                }
                if(circleCircleIntersection(player.position, virus.position, player.radius, virus.radius, 0.001)  && virus.alive && player.alive && player.radius < Math.sqrt(137/Math.PI)){
                        drawCircle(player.position, player.radius, player.color, Vector3.black);
                        System.out.println("Printed");
                }
            
            }
            
            
        }
    }
    
    void updatePlayer(){
        //velocity update and check merge and check boost and draw player
        for(int i = 0; i < players.size();++i){
            Player player = players.get(i);
            if(Double.isNaN(player.position.x) ||Double.isNaN(player.position.y) ){System.out.print("[" + "player " + i + "=" + player.alive + "(" + player.position.x + ", " + player.position.y + ") (" + player.radius + ") (" + player.inertia + ")" );}
            if(player.alive){
                if(player.splitTimer > 600){
                    player.merge = true;
                }
                
                if(player.velocityBoost && player.splitTimer < 30){
                    
                    player.splitTimer++;
                    
                    Vector2 defaultVelocity = speedLimiter(new Vector2(((mousePosition.x - player.position.x) / player.inertia), ((mousePosition.y - player.position.y)) / player.inertia), 1);
                    player.velocity.x =  player.boostVelocity.x    + defaultVelocity.x;
                    player.velocity.y = player.boostVelocity.y   + defaultVelocity.y;
                    
//                    player.velocity = speedLimiter(player.velocity, 0.5);
                }else{

                    player.velocityBoost = false;
                    player.splitTimer++;
                    player.velocity.x = ((mousePosition.x - player.position.x) / player.inertia);
                    player.velocity.y = ((mousePosition.y - player.position.y) / player.inertia);
                    
                    player.velocity = speedLimiter(player.velocity, 1);   
                }
                
                jimSeperation(players);
                // splitSeperationMechanism(players); 
                player.position.x += player.velocity.x;
                player.position.y += player.velocity.y;
                drawCircle(player.position, player.radius, player.color, Vector3.black);
            }
        }
        
    }
    
    void ejection(){
     // W pressed
        for(int j = 0; j < players.size();++j){
            Player player = players.get(j);
            if(keyPressed('W') && player.radius > 2){
                for(int i =0; i < ejectedMasses.length; ++i){
                    EjectedMass ejectedMass = ejectedMasses[i];
                    if(!ejectedMass.alive && player.radius > Math.sqrt(30/Math.PI) && player.alive){
                        ejectedMass.alive = true;
                        ejectedMass.immunity++;
                        
                        ejectedMass.velocity.x = 3*player.velocity.x;
                        ejectedMass.velocity.y = 3*player.velocity.y;
                        ejectedMass.velocity = speedLimiter(ejectedMass.velocity, 5);
                        
                        ejectedMass.position.x = player.position.x + (unitVectorFinder(ejectedMass.velocity).x * Math.sqrt(Math.pow(player.radius,2.5))     );
                        ejectedMass.position.y = player.position.y + (unitVectorFinder(ejectedMass.velocity).y * Math.sqrt(Math.pow(player.radius,2.5))     );
                        player.radius = Math.sqrt(Math.pow(player.radius,2) - 4);
                        break;
                    }
                }
//                break;
                
            }
        }
    
    }
    
    void mergePlayer(){
                //merge
        for(int i = 0; i < players.size()-1; ++i){
            for(int j = i + 1; j < players.size(); ++j){
                Player player1 = players.get(i);
                Player player2 = players.get(j);
                if(i!= j && (player1.merge || player2.merge)){
                    if(circleCircleIntersection(player1.position, player2.position, player1.radius, player2.radius, 0.4)){

                            player2.alive = false;
                            
                            player1.merge = false;
                            player2.merge = false;
                            
                            player1.splitTimer = 0;
                            player2.splitTimer = 0;
                            
                            player1.radius = Math.sqrt(Math.pow(player1.radius,2) + Math.pow(player2.radius,2));
                                
                    
                    }
                }
            }
        }
    }
    
    void updateEjection(){
        //update and draw Ejectedmasses
        for(int i =0; i < ejectedMasses.length; ++i){
            EjectedMass ejectedMass = ejectedMasses[i];
            if(ejectedMass.alive){
                drawCircle(ejectedMass.position, ejectedMass.radius, ejectedMass.color, ejectedMass.color);
                ejectedMass.immunity++;
                ejectedMass.velocity.x *= 0.9;
                ejectedMass.velocity.y *= 0.9;
                
                ejectedMass.position.x += ejectedMass.velocity.x;  
                ejectedMass.position.y += ejectedMass.velocity.y;
            }
            for(int j = 0; j < players.size();++j){
                Player player = players.get(j);
                if(circleCircleIntersection(player.position, ejectedMass.position, player.radius, ejectedMass.radius, 0.6) && ejectedMass.alive && player.alive && ejectedMass.immunity > 15){
                    ejectedMass.immunity = 0;
                    ejectedMass.alive = false;
                    player.radius = Math.sqrt(Math.pow(player.radius,2) + 4);
                }
            }
        }
    }
    
    void updateBlobs(){
                        //update and draw blobs
        for(int i = 0; i < blobs.length; ++i){
            Blob blob = blobs[i];
            if(blob.alive){
                drawCircle(blob.position, blob.radius, blob.color, blob.color);
                
                for(int j = 0; j< players.size(); j++){
                    Player player = players.get(j);
                    if(player.alive){
                        if(circleCircleIntersection(player.position, blob.position, player.radius, blob.radius, 0.8)){
                            blob.alive = false;
                            player.radius = Math.sqrt(Math.pow(player.radius,2) + (2));
                            if(player.radius != 0){player.inertia = Math.pow(player.radius, 2);}
                            
                        }
                    }
                }
            }else if(Math.random() < 0.6){
                blob.alive = true;
                blob.position = new Vector2( (Math.random() * frameSize*2) - frameSize, (Math.random() * frameSize*2) - frameSize);
            }
            
        }
        
        
    }
    
    void drawGrid(){
                        //draw grid
        for(int i = -frameSize; i < frameSize ; i+=5){
            drawLine(new Vector2(-frameSize, i), new Vector2(frameSize, i), Vector3.gray);
        }
        for(int i = -frameSize; i < frameSize ; i+=5){
            drawLine(new Vector2(i, -frameSize), new Vector2(i, frameSize), Vector3.gray);
        }
    }
    
    void reDrawPlayer(){  //redraw player if its bigger than the virus
        for(int i = 0; i < players.size(); ++i){
            for(int j = 0; j < viruses.length; ++j){
                Player player = players.get(i);
                Virus virus = viruses[j];
                if(circleCircleIntersection(player.position, virus.position, player.radius, virus.radius, 1) && player.radius > virus.radius){
                    drawCircle(player.position, player.radius, player.color, Vector3.black);
                }
            }
        }
    
    }
    
    
    
    void setup() { 
        
        
        //set up player
        players = new ArrayList<>();
        
        for(int i = 0; i < 16;++i){
            players.add(new Player());
            Player player = players.get(i);
            if(i>0){player.alive = false;}else{player.alive = true;}
            player.color = new Vector3(0.0,0.8,0.8);
            player.position = new Vector2(0.0, 0.0);
            player.velocity = new Vector2(0.0, 0.0);
            player.radius = 3.0;
            player.inertia = 4;
            player.velocityBoost = false;
            player.splitTimer = 0;
            player.merge = false;
            player.boostVelocity = new Vector2(0.0, 0.0);
//            player.lock = true;

        }
        
       
        //set up blob
       for(int i = 0; i < blobs.length; ++i){
            blobs[i] =  new Blob();
            Blob blob = blobs[i];
            blob.alive = false;
            blob.radius = 2.0;
            blob.color = new Vector3(Math.random(), Math.random(), Math.random());
            blob.position = new Vector2( (Math.random() * frameSize*2) - frameSize    ,    (Math.random() * frameSize*2) - frameSize);
        }
       
       //set up ejectedMasses
       for(int i = 0; i < ejectedMasses.length; ++i){
            ejectedMasses[i] =  new EjectedMass();
            EjectedMass ejectedMass = ejectedMasses[i];
            ejectedMass.alive = false;
            ejectedMass.radius = 2.0;
            ejectedMass.color = players.get(0).color;
            ejectedMass.position = new Vector2(0.0,0.0);
            ejectedMass.velocity = new Vector2(0.0,0.0);
            ejectedMass.immunity = 0;
        }
       
//       set up viruses
       for(int i =0; i<viruses.length; ++i){
           viruses[i] = new Virus();
           Virus virus = viruses[i];
          virus.alive = false;
          virus.radius = 6;
          virus.color = new Vector3(0.0,1.0,0.0);
          virus.position = new Vector2(0.0,0.0);
       }
        
       
    }
    
    
    void loop() {
        
        drawGrid();
        
        updateBlobs();
            
        updateEjection();
        
        mergePlayer();
        
        ejection();
        
        updatePlayer();
        

        
        splitting();
        

        
        updateVirus();
        
        playerHitsVirus();
        
        reDrawPlayer();
        
//        jimSeperation(players);
        
//        splitSeperator();

       

        
        
    }
    
    public static void main(String[] arguments) {
        App app = new HW13();
        app.setWindowBackgroundColor(1.0, 1.0, 1.0);
        app.setWindowSizeInWorldUnits(frameSize, frameSize);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(4*frameSize);
        app.setWindowTopLeftCornerInPixels(0, 0);
        app.run();
    }
}