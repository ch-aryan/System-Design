package com.practicewithsanketh.SystemDesignLLD.AAC;
class Room{
    String roomName;
}
class House{
    Room room;
    House(){
        room = new Room();
    }
}
public class Composition {
}
