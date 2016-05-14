package com.simple.soap.discriminator;

import java.util.UUID;

public enum SingletonUUID {
    
   INSTANCE;

    private UUID uuid = UUID.randomUUID();

    private SingletonUUID() {
    }
    
    public String getId() {
        return this.uuid.toString();
    }

}
